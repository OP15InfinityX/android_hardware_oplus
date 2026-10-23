# SPDX-License-Identifier: Apache-2.0

from pathlib import Path
import re


def guard(path, signatures):
    data = path.read_text()
    cls = re.search(r'^\.class .* (L[^;]+;)$', data, re.M).group(1)
    patched = []
    for name, kind in signatures:
        native = 'setOplusMaterialBaseParams' if kind == 'Material' else 'setOutlineCausticShadowColor'
        params = ['I', 'Landroid/graphics/Rect;'] if kind == 'Material' else ['I']
        pat = rf'(?ms)(^\.method public static final {name}\(\)Z\n    \.locals \d+\n)(.*?)(^\.end method)'
        match = re.search(pat, data)
        if match is None:
            raise ValueError(f'{path}: missing original {name}')
        helper = 'ixHas' + kind + 'Renderer'
        if helper in data:
            raise ValueError(f'{path}: guard already applied')
        gate = f'''\n    invoke-static {{}}, {cls}->{helper}()Z
    move-result v0
    if-nez v0, :ix_renderer_available
    const/4 v0, 0x0
    return v0
    :ix_renderer_available
'''
        data = data[:match.start()] + match.group(1) + gate + match.group(2) + match.group(3) + data[match.end():]
        reflection = f'''    const/4 v2, {len(params)}
    new-array v2, v2, [Ljava/lang/Class;
    const/4 v3, 0x0
    sget-object v4, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;
    aput-object v4, v2, v3
'''
        if len(params) == 2:
            reflection += '''    const/4 v3, 0x1
    const-class v4, Landroid/graphics/Rect;
    aput-object v4, v2, v3
'''
        data += f'''
# Require the OEM Java API AND its RenderNode implementation, not just an OS version.
.method private static {helper}()Z
    .locals 5
    sget-object v0, {cls}->{helper}Cache:Ljava/lang/Boolean;
    if-eqz v0, :ix_probe
    invoke-virtual {{v0}}, Ljava/lang/Boolean;->booleanValue()Z
    move-result v0
    return v0
    :ix_probe
    :ix_try_start
    const-string v0, "com.oplus.view.material.OplusMaterialUtil"
    invoke-static {{v0}}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;
    const-class v0, Landroid/graphics/RenderNode;
    const-string v1, "{native}"
{reflection}    invoke-virtual {{v0, v1, v2}}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    :ix_try_end
    .catch Ljava/lang/ReflectiveOperationException; {{:ix_try_start .. :ix_try_end}} :ix_missing
    .catch Ljava/lang/LinkageError; {{:ix_try_start .. :ix_try_end}} :ix_missing
    .catch Ljava/lang/SecurityException; {{:ix_try_start .. :ix_try_end}} :ix_missing
    const/4 v0, 0x1
    goto :ix_store
    :ix_missing
    move-exception v0
    const/4 v0, 0x0
    :ix_store
    invoke-static {{v0}}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;
    move-result-object v1
    sput-object v1, {cls}->{helper}Cache:Ljava/lang/Boolean;
    return v0
.end method
'''
        data = data.replace('# static fields\n', f'# static fields\n.field private static volatile {helper}Cache:Ljava/lang/Boolean;\n', 1)
        patched.append(name)
    path.write_text(data)
    return patched


def blob_fixup_coui_material_capabilities(ctx, file, file_path, *args, tmp_dir=None, **kwargs):
    """Retain COUI's normal renderer when COS17-only HWUI APIs are absent.

    The original SDK gates and RuntimeShader support remain intact. Match the
    same two version thresholds in named and obfuscated COUI distributions;
    fail extraction if a recognized version helper changes unexpectedly.
    """
    if tmp_dir is None:
        raise ValueError('COUI fixup requires decoded APK sources')
    patched = 0
    for path in Path(tmp_dir).glob('smali*/com/coui/appcompat/*.smali'):
        data = path.read_text()
        if '"COUIEffectVersionUtil"' not in data:
            continue
        signatures = []
        for method in re.finditer(r'(?ms)^\.method public static final (\w+)\(\)Z\n(.*?)^\.end method', data):
            name, body = method.groups()
            if not re.search(r'const/16 [vp]\d+, 0x28\b', body):
                continue
            if '(II)Z' not in body:
                continue
            if re.search(r'const/(?:4|16) [vp]\d+, 0xc\b', body):
                signatures.append((name, 'Caustic'))
            elif re.search(r'const/(?:4|16) [vp]\d+, 0x2\b', body):
                signatures.append((name, 'Material'))
        if sorted(kind for _, kind in signatures) != ['Caustic', 'Material']:
            raise ValueError(f'{path}: unexpected COS17 COUI version helper')
        guard(path, signatures)
        patched += 1
    if not patched:
        raise ValueError(f'{file_path}: COS17 COUI version helper not found')


def blob_fixup_coui_unpack_sources(ctx, file, file_path, *args, tmp_dir=None, **kwargs):
    from extract_utils.tools import apktool_path, java_path
    from extract_utils.utils import run_cmd
    if tmp_dir is None:
        raise ValueError('COUI unpack requires a temporary directory')
    run_cmd([java_path, '-jar', apktool_path, 'd', '--no-res', '-f',
             file_path, '-o', str(tmp_dir)])
