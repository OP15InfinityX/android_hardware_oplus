/*
 * Copyright (C) 2026 The Infinity-X Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.miragewindow;

import android.os.Bundle;

public class OplusMirageOptions {
    private static final String KEY_DISPLAY_HEIGHT = "mirage:display_height";
    private static final String KEY_DISPLAY_WIDTH = "mirage:display_width";
    private static final String KEY_DENSITY_DPI = "mirage:density_dpi";
    private static final String KEY_X_DPI = "mirage:x_dpi";
    private static final String KEY_Y_DPI = "mirage:y_dpi";

    private final Bundle mBundle = new Bundle();

    public static OplusMirageOptions makeAppKeepModeOptions() {
        return new OplusMirageOptions();
    }

    public void setDensityDpi(int densityDpi) {
        mBundle.putInt(KEY_DENSITY_DPI, densityDpi);
    }

    public void setDisplayHeight(int displayHeight) {
        mBundle.putInt(KEY_DISPLAY_HEIGHT, displayHeight);
    }

    public void setDisplayWidth(int displayWidth) {
        mBundle.putInt(KEY_DISPLAY_WIDTH, displayWidth);
    }

    public void setXDpi(int xDpi) {
        mBundle.putInt(KEY_X_DPI, xDpi);
    }

    public void setYDpi(int yDpi) {
        mBundle.putInt(KEY_Y_DPI, yDpi);
    }

    public Bundle toBundle() {
        return new Bundle(mBundle);
    }
}
