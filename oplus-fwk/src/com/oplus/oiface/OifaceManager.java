package com.oplus.oiface;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.oplus.oiface.IOIfaceInternalService;

/* loaded from: classes.dex */
public class OifaceManager {
    public static final int APP_ENTER = 7;
    public static final int APP_PIP_CLOSED = 9;
    public static final int APP_PIP_OPENED = 8;
    public static final int APP_PIP_SWITCH = 10;
    public static final int BIND_CPU_CLUSTER_ALL = 0;
    public static final int CPU_CLUSTER_GOLD = 1;
    public static final int CPU_CLUSTER_PRIME = 3;
    public static final int CPU_CLUSTER_SILVER = 2;
    private static final String DISCONNECTED = "{\"oiface\":\"disconnected\"}";
    public static final int FPS_PER_SECOND = 0;
    public static final int FPS_RAW_DATA = 1;
    public static final int GPA_CPU_CLUSTER_GOLD = 2;
    public static final int GPA_CPU_CLUSTER_PRIME = 4;
    public static final int GPA_CPU_CLUSTER_SILVER = 1;
    public static final int NETWORK_STATUS_DATA = 2;
    public static final int NETWORK_STATUS_DATA_OFF = 3;
    public static final int NETWORK_STATUS_WIFI = 0;
    public static final int NETWORK_STATUS_WIFI_OFF = 1;
    public static final int PERF_MODE_NORMAL = 0;
    public static final int PERF_MODE_PERFORMANCE = 2;
    public static final int PERF_MODE_POWER_SAVE = 1;
    public static final int REGISTER_GAME_SCENE = 1;
    public static final int REGISTER_GAME_STATUS = 2;
    public static final int SCREEN_OFF = 0;
    public static final int SCREEN_ON = 1;
    private static final String TAG = "OifaceManager";
    public static final int THERMAL_TEMP_BACK = 2;
    public static final int THERMAL_TEMP_FRONT = 1;
    public static final int THERMAL_TEMP_GOLD = 16;
    public static final int THERMAL_TEMP_GPU = 64;
    public static final int THERMAL_TEMP_PRIME = 32;
    public static final int THERMAL_TEMP_SIDE = 4;
    public static final int THERMAL_TEMP_SILVER = 8;
    private static volatile OifaceManager sInstance = null;
    private String mIdentity;
    private IBinder mRemote;
    private IOIfaceInternalService mService;
    private IOIfaceCallback mOifaceCallback = null;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.oplus.oiface.OifaceManager.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.d(OifaceManager.TAG, "Oiface died");
            OifaceManager.this.mService = null;
            if (OifaceManager.this.mOifaceCallback != null) {
                try {
                    OifaceManager.this.mOifaceCallback.onSystemNotify(OifaceManager.DISCONNECTED);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void checkService() throws RemoteException {
        if (this.mService == null) {
            this.mRemote = ServiceManager.getService("oplusoiface");
            if (this.mRemote == null) {
                Log.d(TAG, "unable to getService oplusoiface");
                return;
            }
            this.mService = IOIfaceInternalService.Stub.asInterface(this.mRemote);
            if (this.mService != null) {
                try {
                    this.mRemote.linkToDeath(this.mDeathRecipient, 0);
                    return;
                } catch (Exception e) {
                    Log.e(TAG, "connect to oiface failed");
                    this.mService = null;
                    return;
                }
            }
            Log.d(TAG, "connect to oiface failed");
        }
    }

    private OifaceManager(String identity) {
        this.mIdentity = identity;
        try {
            checkService();
        } catch (RemoteException e) {
            Log.e(TAG, "connect to oiface failed", e);
        }
    }

    public static OifaceManager getInstance(String identity) {
        if (sInstance == null) {
            synchronized (OifaceManager.class) {
                if (sInstance == null) {
                    sInstance = new OifaceManager(identity);
                }
            }
        }
        return sInstance;
    }

    public boolean currentNetwork(int status) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.currentNetwork(status);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
            return false;
        }
    }

    public boolean bindGameTask(int type, int tid) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.bindGameTask(type, tid);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean enableHQV(int enable) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.enableHQV(enable);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public String registerHQV(String packageName, int type, String config) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            String result = this.mService.registerHQV(packageName, type, config);
            return result;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public boolean setHalfHQV(int half) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.setHalfHQV(half);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean registerClientThroughCosa(IOIfaceCallback cb, String json, int uid, int pid) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            int result = this.mService.registerClientThroughCosa(cb, json, uid, pid);
            Log.e(TAG, "registerClientThroughCosa return " + result);
            return result > 0;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public String setGeneralSignalCosa(String json, int uid, int pid) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            String result = this.mService.setGeneralSignalCosa(json, uid, pid);
            return result;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public boolean registerNetworkListener(int thresholdMs, int minReportMs, IOIfaceCallback oifaceCallback) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.registerNetworkListener(thresholdMs, minReportMs, oifaceCallback);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean registerGameRoleListener(int type, String config, IOIfaceCallback oifaceCallback) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.registerGameRoleListener(type, config, oifaceCallback);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean unRegisterGameRoleListener(IOIfaceCallback oifaceCallback) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.unRegisterGameRoleListener(oifaceCallback);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public String getSupportGameStartPackage() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            String result = this.mService.getSupportGameStartPackage();
            return result;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public String getAllLoadInfo(String packageName) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            String result = this.mService.getAllLoadInfo(packageName);
            return result;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public boolean setGCPEffectMode(int mode) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            String result = this.mService.setGCPEffectMode(mode);
            Log.e(TAG, "setGCPEffectMode return " + result);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public String getDeviceID() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            String result = this.mService.getDeviceID();
            return result;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public boolean setPerfMode(int mode) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.setPerfMode(mode);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean notifyScreenEvent(int mode) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.notifyScreenEvent(mode);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean currentPkgStatus(int status, String packageName, String newPIPPackage, String oldPIPPackage) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.currentPkgStatus(status, packageName, newPIPPackage, oldPIPPackage);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public int getFPS(String packageName, int type) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return 0;
        }
        try {
            int fps = this.mService.getFPS(packageName, type);
            return fps;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return 0;
        }
    }

    public String generalOifaceSignal(String signal) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            String result = this.mService.generalOifaceSignal(signal);
            return result;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public boolean oifaceDecision(String decision) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.oifaceDecision(decision);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean oifaceControl(String control) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.oifaceControl(control);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public int getCpuClusterNum() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return -1;
        }
        try {
            return this.mService.getCpuClusterNum();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return -1;
        }
    }

    public long[] getCpuAvailableFreqTable(int type) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getCpuAvailableFreqTable(type);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public long[] getCpuLimitedFreqs(int type) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getCpuLimitedFreqs(type);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public long[] getCpuCurrentFreq(int type) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getCpuCurrentFreq(type);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public float[] getCpuLoads(int type) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getCpuLoads(type);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public long[] getGpuAvailableFreqTable() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getGpuAvailableFreqTable();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public long[] getGpuLimitedFreqs() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getGpuLimitedFreqs();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public long getGpuCurrentFreq() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return -1L;
        }
        try {
            return this.mService.getGpuCurrentFreq();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return -1L;
        }
    }

    public float getGpuLoad() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return -1.0f;
        }
        try {
            return this.mService.getGpuLoad();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return -1.0f;
        }
    }

    public float[] getThermalTemps(int type) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getThermalTemps(type);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public boolean enableHapticScreenCaptureService(int enable) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.enableHapticScreenCaptureService(enable);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean setTouchSensibility(int level) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.setTouchSensibility(level);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean setTouchResponsiveness(int level) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.setTouchResponsiveness(level);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean setGyroscopeLevel(int level) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.setGyroscopeLevel(level);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean setTouchProtection(boolean enabled) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.setTouchProtection(enabled);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public boolean registerOifaceCallback(IOIfaceCallback oifaceCallback) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.registerOifaceCallback(oifaceCallback);
            this.mOifaceCallback = oifaceCallback;
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
    }

    public int getBatteryRemain() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return -1;
        }
        try {
            return this.mService.getBatteryRemain();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return -1;
        }
    }

    public float getBatteryCurrentNow() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return -1.0f;
        }
        try {
            return this.mService.getBatteryCurrentNow();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return -1.0f;
        }
    }

    public int getSuperVOOCStatus() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return -1;
        }
        try {
            return this.mService.getSuperVOOCStatus();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return -1;
        }
    }

    public float[] getGPASystemInfo() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getGPASystemInfo();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public void setCoolExFilterType(int type, String config) throws RemoteException {
        checkService();
        if (this.mService != null) {
            try {
                this.mService.setCoolExFilterType(type, config);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    public void setGameModeStatus(int status, String packageName) throws RemoteException {
        checkService();
        if (this.mService != null) {
            try {
                this.mService.setGameModeStatus(status, packageName);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    public int getGameModeStatus() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return -1;
        }
        try {
            return this.mService.getGameModeStatus();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return -1;
        }
    }

    public String getCurrentGamePackage() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            String result = this.mService.getCurrentGamePackage();
            return result;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public void setInstalledGameList(String[] games) throws RemoteException {
        checkService();
        if (this.mService != null) {
            try {
                this.mService.setInstalledGameList(games);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    public String[] getInstalledGameList() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getInstalledGameList();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public String getCpuTimeInState() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getCpuTimeInState();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public String triggerFrameStat(String status, String packageName) throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.triggerFrameStat(status, packageName);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public String getChipName() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getChipName();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public int[] getCpuClusterInfo() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getCpuClusterInfo();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public int getBatteryFCC() throws RemoteException {
        checkService();
        if (this.mService == null) {
            return -1;
        }
        try {
            return this.mService.getBatteryFCC();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return -1;
        }
    }
}
