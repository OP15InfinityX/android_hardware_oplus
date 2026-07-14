package android.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.os.Parcel;
import android.os.RemoteException;
import android.graphics.Rect;
import com.oplus.app.OplusAppInfo;
import com.oplus.app.OplusTaskInfoChangeListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class OplusActivityTaskManager extends OplusBaseActivityTaskManager implements IOplusActivityTaskManager {
    private static final String GAMES_PACKAGE = "com.oplus.games";

    public static OplusActivityTaskManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    /* loaded from: classes.dex */
    public static class LazyHolder {
        private static final OplusActivityTaskManager INSTANCE = new OplusActivityTaskManager();

        private LazyHolder() {
        }
    }

    @Override // android.app.IOplusActivityTaskManager
    public ComponentName getTopActivityComponentName() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken("android.app.IActivityTaskManager");
            this.mRemote.transact(10007, data, reply, 0);
            reply.readException();
            ComponentName name = ComponentName.readFromParcel(reply);
            return name;
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override // android.app.IOplusActivityTaskManager
    public ApplicationInfo getTopApplicationInfo() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken("android.app.IActivityTaskManager");
            this.mRemote.transact(10011, data, reply, 0);
            reply.readException();
            ApplicationInfo info = ApplicationInfo.CREATOR.createFromParcel(reply);
            return info;
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override // android.app.IOplusActivityTaskManager
    public List<OplusAppInfo> getAllTopAppInfos() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken("android.app.IActivityTaskManager");
            this.mRemote.transact(10053, data, reply, 0);
            reply.readException();
            List<OplusAppInfo> list = reply.createTypedArrayList(OplusAppInfo.CREATOR);
            return list != null && !list.isEmpty() ? list : getTopAppInfosFromAospTasks();
        } catch (RemoteException e) {
            return getTopAppInfosFromAospTasks();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override // android.app.IOplusActivityTaskManager
    public List<OplusAppInfo> getAllTopApps() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        new ArrayList();
        try {
            data.writeInterfaceToken("android.app.IActivityTaskManager");
            this.mRemote.transact(10058, data, reply, 0);
            reply.readException();
            List<OplusAppInfo> list = reply.createTypedArrayList(OplusAppInfo.CREATOR);
            return list;
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    private List<OplusAppInfo> getTopAppInfosFromAospTasks() {
        ArrayList<OplusAppInfo> result = new ArrayList<>();
        try {
            List<ActivityManager.RunningTaskInfo> tasks =
                    ActivityTaskManager.getInstance().getTasks(2, false, true);
            if (tasks == null) {
                return result;
            }
            for (ActivityManager.RunningTaskInfo task : tasks) {
                OplusAppInfo info = new OplusAppInfo();
                info.windowingMode = task.getWindowingMode();
                info.activityType = task.getActivityType();
                info.taskId = task.taskId;
                Rect bounds = task.configuration.windowConfiguration.getBounds();
                info.appBounds = bounds != null ? new Rect(bounds) : new Rect();
                info.appInfo = task.topActivityInfo != null
                        ? task.topActivityInfo.applicationInfo : null;
                info.topActivity = task.topActivity;
                info.displayId = task.displayId;
                info.userId = task.userId;
                info.isRootActivity = task.numActivities <= 1;
                if (info.appInfo != null && GAMES_PACKAGE.equals(info.appInfo.packageName)) {
                    result.add(result.size(), info);
                } else {
                    result.add(0, info);
                }
            }
        } catch (Exception ignored) {
        }
        return result;
    }

    @Override // android.app.IOplusActivityTaskManager
    public boolean registerTaskInfoChangeListener(
            OplusTaskInfoChangeListener listener, int type, int displayId) throws RemoteException {
        return true;
    }

    @Override // android.app.IOplusActivityTaskManager
    public boolean unregisterTaskInfoChangeListener(OplusTaskInfoChangeListener listener)
            throws RemoteException {
        return true;
    }
}
