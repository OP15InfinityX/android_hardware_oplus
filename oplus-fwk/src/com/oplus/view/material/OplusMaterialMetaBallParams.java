// SPDX-License-Identifier: Apache-2.0

package com.oplus.view.material;

/** COS17 material parameter API. Rendering support is checked separately. */
public class OplusMaterialMetaBallParams {
    public static final int MODE_X = 0;
    public static final int MODE_Y = 1;
    private int mIndex;
    private int mMode;
    private int mExtension;
    private float mExtensionFactorX;
    private float mExtensionFactorY;

    public OplusMaterialMetaBallParams(int index, int mode) {
        this(index, mode, 0, 0f, 0f);
    }

    public OplusMaterialMetaBallParams(int index, int mode, int extension, float extensionFactorX, float extensionFactorY) {
        checkIndex(index);
        checkMode(mode);
        mIndex = index;
        mMode = mode;
        mExtension = extension;
        mExtensionFactorX = extensionFactorX;
        mExtensionFactorY = extensionFactorY;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        checkIndex(index);
        mIndex = index;
    }

    public int getMode() {
        return mMode;
    }

    public void setMode(int mode) {
        checkMode(mode);
        mMode = mode;
    }

    public int getExtension() {
        return mExtension;
    }

    public void setExtension(int extension) {
        mExtension = extension;
    }

    public float getExtensionFactorX() {
        return mExtensionFactorX;
    }

    public void setExtensionFactorX(float extensionFactorX) {
        mExtensionFactorX = extensionFactorX;
    }

    public float getExtensionFactorY() {
        return mExtensionFactorY;
    }

    public void setExtensionFactorY(float extensionFactorY) {
        mExtensionFactorY = extensionFactorY;
    }

    private static void checkIndex(int index) {
        if (index < 1 || index > 3) {
            throw new IllegalArgumentException("Index must be between 1 and 3, but got " + index);
        }
    }

    private static void checkMode(int mode) {
        if (mode != MODE_X && mode != MODE_Y) {
            throw new IllegalArgumentException("Mode must be MODE_X or MODE_Y, but got " + mode);
        }
    }

    @Override
    public String toString() {
        return "OplusMaterialMetaBallParams(index=" + mIndex
                + ", mode=" + mMode
                + ", extension=" + mExtension
                + ", extensionFactorX=" + mExtensionFactorX
                + ", extensionFactorY=" + mExtensionFactorY + ")";
    }
}
