// SPDX-License-Identifier: Apache-2.0

package com.oplus.wrapper.app;

/** COS17 lifecycle accessors used by the OPlus recorder. */
public class Activity {
    private final android.app.Activity mActivity;

    public Activity(android.app.Activity activity) {
        mActivity = activity;
    }

    public static boolean isResumed(android.app.Activity target) {
        return target.isResumed();
    }

    public final boolean isResumed() {
        return mActivity.isResumed();
    }
}
