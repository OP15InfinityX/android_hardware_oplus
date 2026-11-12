# SPDX-License-Identifier: Apache-2.0

from hashlib import sha256
from pathlib import Path


def blob_fixup_consumerir_sdk(ctx, file, file_path, *args, tmp_dir=None, **kwargs):
    """Adapt KuKong's OEM certificate check for a ROM-signed ConsumerIR APK.

    The COUI compatibility changes require repacking and signing the app. The
    bundled SDK otherwise returns -82 from its local certificate comparison,
    leaves its encoders uninitialized and sends an empty d2 request to the
    server. Keep the complete native initialization and request handling;
    adapt only the final certificate comparison in this verified SDK build.
    """
    if tmp_dir is None:
        raise ValueError('ConsumerIR SDK fixup requires an unpacked APK')
    lib = Path(tmp_dir) / 'lib/arm64-v8a/libkksdk.so'
    data = lib.read_bytes()
    original = 'e9b8ed7f22366183ed681427e7d1c53f9ced0e5ba5cf40425083604b88afd321'
    patched = '623fedde80f5bb1aef40b18640011fb245c306aaaca4b20f7aa798a0905468ab'
    digest = sha256(data).hexdigest()
    if digest == patched:
        return
    if digest != original:
        raise ValueError('Unknown ConsumerIR KuKong SDK; review its certificate check')
    offset = 0x58f00
    # csinc w25,w25,wzr,ne -> mov w25,#1, after the certificate hash comparison.
    if data[offset:offset + 4] != bytes.fromhex('39 17 9f 1a'):
        raise ValueError('Unexpected ConsumerIR SDK certificate comparison')
    data = data[:offset] + bytes.fromhex('39 00 80 52') + data[offset + 4:]
    if sha256(data).hexdigest() != patched:
        raise ValueError('ConsumerIR SDK patch verification failed')
    lib.write_bytes(data)
