/*
 * Copyright (C) 2026 The LineageOS Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package android.view;

import android.graphics.Rect;

public class OplusScreenDragUtil {
    private static final int SCREEN_DRAG_STATE_NORMAL = 0;

    private static int sScreenDragState = SCREEN_DRAG_STATE_NORMAL;

    public static void setScreenDragState(int dragState) {
        sScreenDragState = dragState;
    }

    public static int getScreenDragState() {
        return sScreenDragState;
    }

    public static int getOffsetX() {
        return 0;
    }

    public static int getOffsetY() {
        return 0;
    }

    public static float getScale() {
        return 1.0f;
    }

    public static int getWidth() {
        return 0;
    }

    public static int getHeight() {
        return 0;
    }

    public static boolean isNormalState() {
        return sScreenDragState == SCREEN_DRAG_STATE_NORMAL;
    }

    public static boolean isDragState() {
        return false;
    }

    public static boolean isHoldState() {
        return false;
    }

    public static boolean isOffsetState() {
        return false;
    }

    public static void resetState() {
        sScreenDragState = SCREEN_DRAG_STATE_NORMAL;
    }

    public static float getOffsetPosX(float x) {
        return x;
    }

    public static float getOffsetPosY(float y) {
        return y;
    }

    public static float getOffsetPosXScale(float x, float scale) {
        return x;
    }

    public static float getOffsetPosYScale(float y, float scale) {
        return y;
    }

    public static void scaleScreenshotIfNeeded(Rect sourceCrop) {
    }

    public static int shouldMagnify(WindowManager.LayoutParams attr) {
        return -1;
    }
}
