/*
 * Copyright (C) 2026 The Infinity-X Project
 * SPDX-License-Identifier: Apache-2.0
 */

package android.os;

import android.util.ArraySet;
import android.util.Log;
import android.view.InputEvent;

public class OplusGestureMonitorManager {
    private static final String TAG = "OplusGestureMonitorManager";
    private static final OplusGestureMonitorManager sInstance = new OplusGestureMonitorManager();

    private final ArraySet<OnPointerEventObserver> mObservers = new ArraySet<>();

    public interface OnPointerEventObserver {
        void onInputEvent(InputEvent inputEvent);
    }

    private OplusGestureMonitorManager() {
    }

    public static OplusGestureMonitorManager getInstance() {
        return sInstance;
    }

    public boolean registerInputEvent(OnPointerEventObserver observer) {
        if (observer == null) {
            return false;
        }
        synchronized (mObservers) {
            mObservers.add(observer);
        }
        Log.d(TAG, "registerInputEvent: compatibility observer registered");
        return true;
    }

    public void unregisterInputEvent(OnPointerEventObserver observer) {
        if (observer == null) {
            return;
        }
        synchronized (mObservers) {
            mObservers.remove(observer);
        }
        Log.d(TAG, "unregisterInputEvent: compatibility observer unregistered");
    }
}
