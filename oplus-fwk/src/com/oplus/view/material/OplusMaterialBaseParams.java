// SPDX-License-Identifier: Apache-2.0

package com.oplus.view.material;

import android.graphics.Rect;

/** COS17 material parameter API. Rendering support is checked separately. */
public class OplusMaterialBaseParams {
    private int mMaskColor;
    private Rect mInnerBounds;

    public OplusMaterialBaseParams(int maskColor, Rect innerBounds) {
        mMaskColor = maskColor;
        mInnerBounds = innerBounds;
    }

    public int getMaskColor() {
        return mMaskColor;
    }

    public void setMaskColor(int maskColor) {
        mMaskColor = maskColor;
    }

    public Rect getInnerBounds() {
        return mInnerBounds;
    }

    public void setInnerBounds(Rect innerBounds) {
        mInnerBounds = innerBounds;
    }

    @Override
    public String toString() {
        return "OplusMaterialBaseParams(maskColor=" + mMaskColor
                + ", innerBounds=" + mInnerBounds + ")";
    }
}
