// SPDX-License-Identifier: Apache-2.0

package com.oplus.view.material;

/** COS17 material parameter API. Rendering support is checked separately. */
public class OplusMaterialCornerParams {
    protected static final int CORNER_TYPE_NEW_G2 = 4;
    private float mRadius;
    private float mWeight;

    public OplusMaterialCornerParams(float radius, float weight) {
        mRadius = radius;
        mWeight = weight;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public float getWeight() {
        return mWeight;
    }

    public void setWeight(float weight) {
        mWeight = weight;
    }

    @Override
    public String toString() {
        return "OplusMaterialCornerParams(radius=" + mRadius
                + ", weight=" + mWeight + ")";
    }
}
