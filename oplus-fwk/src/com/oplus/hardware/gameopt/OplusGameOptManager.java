package com.oplus.hardware.gameopt;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import vendor.oplus.hardware.gameopt.IGameCallback;
import vendor.oplus.hardware.gameopt.IGameOptHalService;

/* loaded from: classes.dex */
public class OplusGameOptManager {
    private static final int GAME_OPT_DIED_TYPE = 101;
    private static final int REMOTE_VER_INV = 0;
    private static final int REMOTE_VER_V2 = 2;
    private static final String TAG = "OplusGameOptManager";
    private static volatile OplusGameOptManager sInstance;
    private static String sServiceName = IGameOptHalService.DESCRIPTOR + "/default";
    private volatile IGameOptHalService mGameOptHalService;
    private final Object mLock = new Object();
    private final ArrayList<OplusGameCallbackWrapper> mCallbacks = new ArrayList<>();
    private volatile int mInterfaceVersion = 0;
    private GameCallback mServerCallback = null;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.oplus.hardware.gameopt.OplusGameOptManager.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.i(OplusGameOptManager.TAG, OplusGameOptManager.sServiceName + " binderDied");
            synchronized (OplusGameOptManager.class) {
                OplusGameOptManager.this.mGameOptHalService = null;
                synchronized (OplusGameOptManager.this.mLock) {
                    if (!OplusGameOptManager.this.mCallbacks.isEmpty()) {
                        Iterator it = OplusGameOptManager.this.mCallbacks.iterator();
                        while (it.hasNext()) {
                            OplusGameCallbackWrapper callback = (OplusGameCallbackWrapper) it.next();
                            callback.onCommonCall("gameopt died", 101);
                        }
                    }
                }
            }
        }
    };

    private OplusGameOptManager() {
    }

    public static OplusGameOptManager getInstance() {
        if (sInstance == null) {
            synchronized (OplusGameOptManager.class) {
                if (sInstance == null) {
                    sInstance = new OplusGameOptManager();
                }
            }
        }
        return sInstance;
    }

    private synchronized IGameOptHalService getService() {
        if (this.mGameOptHalService == null) {
            IBinder binder = ServiceManager.checkService(sServiceName);
            if (binder == null) {
                Log.w(TAG, "getService fail." + sServiceName);
                return null;
            }
            try {
                binder.linkToDeath(this.mDeathRecipient, 0);
                this.mGameOptHalService = IGameOptHalService.Stub.asInterface(binder);
                if (this.mGameOptHalService == null) {
                    Log.e(TAG, "asInterface fail.");
                }
            } catch (RemoteException e) {
                Log.e(TAG, "linkToDeath fail ", e);
                return null;
            }
        }
        return this.mGameOptHalService;
    }

    private boolean checkVersionAtLeast(int minSupportedVersion) {
        int version = getInterfaceVersion();
        if (version >= minSupportedVersion) {
            return true;
        }
        Log.e(TAG, "invalid version " + version + ", min supported version " + minSupportedVersion);
        return false;
    }

    public void notifyGameInfo(int type, String jsonData) {
        try {
            IGameOptHalService service = getService();
            if (service != null) {
                service.notifyGameInfo(type, jsonData);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "notifyGameInfo failed.", e);
        }
    }

    public int notifyTGPAfps(int tFps) {
        try {
            IGameOptHalService service = getService();
            if (service != null) {
                return service.notifyTGPAfps(tFps);
            }
            return -1;
        } catch (RemoteException e) {
            Log.e(TAG, "notifyTGPAfps failed.", e);
            return -1;
        }
    }

    public int registerGameCallback(OplusGameCallback callback) {
        if (callback == null) {
            return -1;
        }
        synchronized (this.mLock) {
            int index = findCallbackLocked(callback);
            if (index != -1) {
                Log.d(TAG, "duplicated callback register");
                return -1;
            }
            OplusGameCallbackWrapper wrapper = new OplusGameCallbackWrapper(this, callback);
            this.mCallbacks.add(wrapper);
            try {
                IGameOptHalService service = getService();
                if (service != null && this.mServerCallback == null) {
                    this.mServerCallback = new GameCallback();
                    return service.setCallback(this.mServerCallback);
                }
            } catch (RemoteException e) {
                Log.e(TAG, "setCallback failed.", e);
            }
            return -1;
        }
    }

    private int findCallbackLocked(OplusGameCallback callback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (this.mCallbacks.get(i).mOplusGameCallback.equals(callback)) {
                return i;
            }
        }
        return -1;
    }

    public void setKeyThread(String config) {
        try {
            IGameOptHalService service = getService();
            if (service != null) {
                service.setKeyThread(config);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "setKeyThread failed.", e);
        }
    }

    public int unregisterGameCallback(OplusGameCallback callback) {
        boolean callbackEmpty;
        if (callback == null) {
            return -1;
        }
        Log.i(TAG, "unsetCallback callback = " + callback);
        synchronized (this.mLock) {
            int indexToRemove = findCallbackLocked(callback);
            if (indexToRemove != -1) {
                this.mCallbacks.remove(indexToRemove);
            }
            Log.d(TAG, "callback index " + indexToRemove + " callback size: " + this.mCallbacks.size());
            callbackEmpty = this.mCallbacks.isEmpty();
        }
        if (callbackEmpty) {
            return unsetCallback();
        }
        return 0;
    }

    private int unsetCallback() {
        try {
            try {
                IGameOptHalService service = getService();
                if (service != null && this.mServerCallback != null) {
                    if (service.unsetCallback(this.mServerCallback) == 0) {
                        this.mServerCallback = null;
                        synchronized (this.mLock) {
                            this.mCallbacks.clear();
                        }
                        return 0;
                    }
                }
                this.mServerCallback = null;
                synchronized (this.mLock) {
                    this.mCallbacks.clear();
                }
            } catch (RemoteException e) {
                Log.e(TAG, "unsetCallback failed.", e);
                this.mServerCallback = null;
                synchronized (this.mLock) {
                    this.mCallbacks.clear();
                }
            }
            return -1;
        } catch (Throwable th) {
            this.mServerCallback = null;
            synchronized (this.mLock) {
                this.mCallbacks.clear();
                throw th;
            }
        }
    }

    public void registerCallback(String identifier, OplusGameCallback callback) {
        try {
            IGameOptHalService service = getService();
            if (service != null && checkVersionAtLeast(2)) {
                OplusGameCallbackWrapper wrapper = new OplusGameCallbackWrapper(this, callback);
                service.registerCallback(identifier, wrapper);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "registerCallback failed.", e);
        }
    }

    public void unregisterCallback(String identifier) {
        try {
            IGameOptHalService service = getService();
            if (service != null && checkVersionAtLeast(2)) {
                service.unregisterCallback(identifier);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "unregisterCallback failed.", e);
        }
    }

    public void notifyCommonIntValue(int type, long value) {
        try {
            IGameOptHalService service = getService();
            if (service != null && checkVersionAtLeast(2)) {
                service.notifyCommonIntValue(type, value);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "notifyCommonIntValue failed.", e);
        }
    }

    public void notifyCommonStringValue(int type, String value) {
        try {
            IGameOptHalService service = getService();
            if (service != null && checkVersionAtLeast(2)) {
                service.notifyCommonStringValue(type, value);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "notifyCommonStringValue failed.", e);
        }
    }

    public void notifyCommonIntPair(int type, int key, long value) {
        try {
            IGameOptHalService service = getService();
            if (service != null && checkVersionAtLeast(2)) {
                service.notifyCommonIntPair(type, key, value);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "notifyCommonIntPair failed.", e);
        }
    }

    public void notifyCommonStringPair(int type, String key, String value) {
        try {
            IGameOptHalService service = getService();
            if (service != null && checkVersionAtLeast(2)) {
                service.notifyCommonStringPair(type, key, value);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "notifyCommonStringPair failed.", e);
        }
    }

    public void notifySchedFrameTime(int bufferNum, long produce, long consume, long reserve) {
        try {
            IGameOptHalService service = getService();
            if (service != null && checkVersionAtLeast(2)) {
                service.notifySchedFrameTime(bufferNum, produce, consume, reserve);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "notifySchedFrameTime failed.", e);
        }
    }

    public void notifyRptStatus(int totalRp, int currentRp, long timeStamp) {
        try {
            IGameOptHalService service = getService();
            if (service != null && checkVersionAtLeast(2)) {
                service.notifyRptStatus(totalRp, currentRp, timeStamp);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "notifyRptStatus failed.", e);
        }
    }

    public int getInterfaceVersion() {
        if (this.mInterfaceVersion > 0) {
            return this.mInterfaceVersion;
        }
        try {
            IGameOptHalService service = getService();
            if (service == null) {
                Log.e(TAG, "getService fail.");
            } else {
                this.mInterfaceVersion = service.getInterfaceVersion();
                Log.i(TAG, "getInterfaceVersion " + this.mInterfaceVersion);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "getInterfaceVersion fail.", e);
        }
        return this.mInterfaceVersion;
    }

    private final class GameCallback extends IGameCallback.Stub {
        private GameCallback() {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onNotify(String info) {
            Log.d(OplusGameOptManager.TAG, " callback invocked info:" + info);
            synchronized (OplusGameOptManager.this.mLock) {
                if (!OplusGameOptManager.this.mCallbacks.isEmpty()) {
                    Iterator it = OplusGameOptManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        OplusGameCallbackWrapper wrapper = (OplusGameCallbackWrapper) it.next();
                        wrapper.onNotify(info);
                    }
                }
            }
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onReadTop(String info) {
            Log.d(OplusGameOptManager.TAG, "onReadTop callback invocked info:" + info);
            synchronized (OplusGameOptManager.this.mLock) {
                if (!OplusGameOptManager.this.mCallbacks.isEmpty()) {
                    Iterator it = OplusGameOptManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        OplusGameCallbackWrapper wrapper = (OplusGameCallbackWrapper) it.next();
                        wrapper.onReadTop(info);
                    }
                }
            }
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onFrameLimit(String info) {
            Log.d(OplusGameOptManager.TAG, "onFrameLimit callback invocked info:" + info);
            synchronized (OplusGameOptManager.this.mLock) {
                if (!OplusGameOptManager.this.mCallbacks.isEmpty()) {
                    Iterator it = OplusGameOptManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        OplusGameCallbackWrapper wrapper = (OplusGameCallbackWrapper) it.next();
                        wrapper.onFrameLimit(info);
                    }
                }
            }
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onBigDataReport(String info) {
            Log.d(OplusGameOptManager.TAG, "onBigDataReport callback invocked info:" + info);
            synchronized (OplusGameOptManager.this.mLock) {
                if (!OplusGameOptManager.this.mCallbacks.isEmpty()) {
                    Iterator it = OplusGameOptManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        OplusGameCallbackWrapper wrapper = (OplusGameCallbackWrapper) it.next();
                        wrapper.onBigDataReport(info);
                    }
                }
            }
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onNotifyTemp(String info) {
            Log.d(OplusGameOptManager.TAG, "onNotifyTemp callback invocked info:" + info);
            synchronized (OplusGameOptManager.this.mLock) {
                if (!OplusGameOptManager.this.mCallbacks.isEmpty()) {
                    Iterator it = OplusGameOptManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        OplusGameCallbackWrapper wrapper = (OplusGameCallbackWrapper) it.next();
                        wrapper.onNotifyTemp(info);
                    }
                }
            }
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onFrameProduce(String info) {
            Log.d(OplusGameOptManager.TAG, "onNotifyTemp callback invocked info:" + info);
            synchronized (OplusGameOptManager.this.mLock) {
                if (!OplusGameOptManager.this.mCallbacks.isEmpty()) {
                    Iterator it = OplusGameOptManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        OplusGameCallbackWrapper wrapper = (OplusGameCallbackWrapper) it.next();
                        wrapper.onFrameProduce(info);
                    }
                }
            }
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onInputCollect(String info) {
            Log.d(OplusGameOptManager.TAG, "onInputCollect callback invocked info:" + info);
            synchronized (OplusGameOptManager.this.mLock) {
                if (!OplusGameOptManager.this.mCallbacks.isEmpty()) {
                    Iterator it = OplusGameOptManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        OplusGameCallbackWrapper wrapper = (OplusGameCallbackWrapper) it.next();
                        wrapper.onInputCollect(info);
                    }
                }
            }
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onCommonCall(String info, int type) {
            Log.d(OplusGameOptManager.TAG, "onCommonCall callback invocked info:" + info + " type:" + type);
            synchronized (OplusGameOptManager.this.mLock) {
                if (!OplusGameOptManager.this.mCallbacks.isEmpty()) {
                    Iterator it = OplusGameOptManager.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        OplusGameCallbackWrapper wrapper = (OplusGameCallbackWrapper) it.next();
                        wrapper.onCommonCall(info, type);
                    }
                }
            }
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public final int getInterfaceVersion() {
            return 2;
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public final String getInterfaceHash() {
            return "79187eecf42abc41850736c8590e8c5e0bea0660";
        }
    }

    private final class OplusGameCallbackWrapper extends IGameCallback.Stub {
        private final OplusGameCallback mOplusGameCallback;

        public OplusGameCallbackWrapper(OplusGameOptManager oplusGameOptManager, OplusGameCallback mOplusGameCallback) {
            this.mOplusGameCallback = mOplusGameCallback;
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onNotify(String info) {
            this.mOplusGameCallback.onNotify(info);
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onReadTop(String info) {
            this.mOplusGameCallback.onReadTop(info);
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onFrameLimit(String info) {
            this.mOplusGameCallback.onFrameLimit(info);
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onBigDataReport(String info) {
            this.mOplusGameCallback.onBigDataReport(info);
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onNotifyTemp(String info) {
            this.mOplusGameCallback.onNotifyTemp(info);
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onFrameProduce(String info) {
            this.mOplusGameCallback.onFrameProduce(info);
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onInputCollect(String info) {
            this.mOplusGameCallback.onInputCollect(info);
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public void onCommonCall(String info, int type) {
            this.mOplusGameCallback.onCommonCall(info, type);
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public final int getInterfaceVersion() {
            return 2;
        }

        @Override // vendor.oplus.hardware.gameopt.IGameCallback
        public final String getInterfaceHash() {
            return "79187eecf42abc41850736c8590e8c5e0bea0660";
        }
    }
}
