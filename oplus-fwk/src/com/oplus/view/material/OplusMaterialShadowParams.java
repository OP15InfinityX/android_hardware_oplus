// SPDX-License-Identifier: Apache-2.0

package com.oplus.view.material;

/** COS17 material parameter API. Rendering support is checked separately. */
public class OplusMaterialShadowParams {
    public static final int SHADOW_TYPE_NONE = 0;
    public static final int SHADOW_TYPE_RECTANGLE = 1;
    public static final int SHADOW_TYPE_CIRCLE = 2;
    public static final int SHADOW_TYPE_SQUARE = 3;
    private int mType;
    private float mFadeWidthScale;
    private float mFadeInAlpha;
    private float mFadeOutAlpha;

    public OplusMaterialShadowParams(int type, float fadeWidthScale, float fadeInAlpha, float fadeOutAlpha) {
        mType = type;
        mFadeWidthScale = fadeWidthScale;
        mFadeInAlpha = fadeInAlpha;
        mFadeOutAlpha = fadeOutAlpha;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public float getFadeWidthScale() {
        return mFadeWidthScale;
    }

    public void setFadeWidthScale(float fadeWidthScale) {
        mFadeWidthScale = fadeWidthScale;
    }

    public float getFadeInAlpha() {
        return mFadeInAlpha;
    }

    public void setFadeInAlpha(float fadeInAlpha) {
        mFadeInAlpha = fadeInAlpha;
    }

    public float getFadeOutAlpha() {
        return mFadeOutAlpha;
    }

    public void setFadeOutAlpha(float fadeOutAlpha) {
        mFadeOutAlpha = fadeOutAlpha;
    }

    @Override
    public String toString() {
        return "OplusMaterialShadowParams(type=" + mType
                + ", fadeWidthScale=" + mFadeWidthScale
                + ", fadeInAlpha=" + mFadeInAlpha
                + ", fadeOutAlpha=" + mFadeOutAlpha + ")";
    }
}
