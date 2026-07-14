/*
 * Copyright (C) 2026 The Infinity-X Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.miragewindow;

import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;

public class OplusMirageWindowManager {
    private static final String TAG = "OplusMirageWindowManager";
    private static final OplusMirageWindowManager sInstance = new OplusMirageWindowManager();

    private OplusMirageWindowManager() {
    }

    public static OplusMirageWindowManager getInstance() {
        return sInstance;
    }

    public int createVirtualDisplayDevice(Bundle options) {
        Log.d(TAG, "createVirtualDisplayDevice: Mirage virtualization is not available");
        return -1;
    }

    public Bundle getTaskInfo(ComponentName componentName) {
        return null;
    }

    public void moveTaskToDisplay(int taskId, int displayId, boolean top) {
        Log.d(TAG, "moveTaskToDisplay: Mirage virtualization is not available");
    }

    public void releaseVirtualDisplayDevice(int displayId) {
        Log.d(TAG, "releaseVirtualDisplayDevice: Mirage virtualization is not available");
    }
}
