// SPDX-License-Identifier: Apache-2.0

package com.oplus.view.material;

/** COS17 material parameter API. Rendering support is checked separately. */
public class OplusMaterialEdgeParams {
    public static final int EDGE_TYPE_NONE = 0;
    public static final int EDGE_TYPE_RECTANGLE = 1;
    public static final int EDGE_TYPE_CIRCLE = 2;
    public static final int EDGE_TYPE_SQUARE = 3;
    private int mType;
    private float mLineWidth;
    private float mLineAlpha;
    private float mLineAngle;

    public OplusMaterialEdgeParams(int type, float lineWidth, float lineAlpha, float lineAngle) {
        mType = type;
        mLineWidth = lineWidth;
        mLineAlpha = lineAlpha;
        mLineAngle = lineAngle;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public float getLineWidth() {
        return mLineWidth;
    }

    public void setLineWidth(float lineWidth) {
        mLineWidth = lineWidth;
    }

    public float getLineAlpha() {
        return mLineAlpha;
    }

    public void setLineAlpha(float lineAlpha) {
        mLineAlpha = lineAlpha;
    }

    public float getLineAngle() {
        return mLineAngle;
    }

    public void setLineAngle(float lineAngle) {
        mLineAngle = lineAngle;
    }

    @Override
    public String toString() {
        return "OplusMaterialEdgeParams(type=" + mType
                + ", lineWidth=" + mLineWidth
                + ", lineAlpha=" + mLineAlpha
                + ", lineAngle=" + mLineAngle + ")";
    }
}
