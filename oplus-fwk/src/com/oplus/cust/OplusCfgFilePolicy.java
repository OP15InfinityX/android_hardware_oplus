package com.oplus.cust;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OplusCfgFilePolicy {
    public static final int DEFAULT_SLOT = -2;

    private OplusCfgFilePolicy() {
    }

    public static String getCarrierId(int slot) {
        return "";
    }

    public static List<String> getCfgLevelList(String configName, int slot) {
        if (configName == null) {
            return Collections.emptyList();
        }

        ArrayList<String> result = new ArrayList<>();
        String sub = configName.startsWith("/") ? configName : "/" + configName;
        String[] roots = {"/system_ext", "/product", "/system", "/vendor", "/odm"};
        for (String root : roots) {
            File dir = new File(root + sub);
            if (dir.isDirectory()) {
                result.add(dir.getAbsolutePath());
            }
        }

        return result;
    }

    // COS17's binary contract returns Files, in increasing policy priority.
    public static List<File> getCfgFileList(String configName, String dir, int slot) {
        if (configName == null || configName.isEmpty() || dir == null || dir.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<File> result = new ArrayList<>();
        for (String level : getCfgLevelList(dir, slot)) {
            File file = new File(level, configName);
            if (file.exists()) {
                result.add(file);
            }
        }
        return result;
    }

    public static File getCfgTopPriorityFile(String configName, String dir, int slot) {
        List<File> files = getCfgFileList(configName, dir, slot);
        return files.isEmpty() ? null : files.get(files.size() - 1);
    }
}
