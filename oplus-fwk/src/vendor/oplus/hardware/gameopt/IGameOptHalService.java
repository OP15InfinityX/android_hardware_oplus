package vendor.oplus.hardware.gameopt;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.oplus.hardware.gameopt.IGameCallback;

/* loaded from: classes2.dex */
public interface IGameOptHalService extends IInterface {
    public static final String DESCRIPTOR = "vendor$oplus$hardware$gameopt$IGameOptHalService".replace('$', '.');
    public static final String HASH = "79187eecf42abc41850736c8590e8c5e0bea0660";
    public static final int VERSION = 2;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void notifyCommonIntPair(int i, int i2, long j) throws RemoteException;

    void notifyCommonIntValue(int i, long j) throws RemoteException;

    void notifyCommonStringPair(int i, String str, String str2) throws RemoteException;

    void notifyCommonStringValue(int i, String str) throws RemoteException;

    void notifyGameInfo(int i, String str) throws RemoteException;

    void notifyRptStatus(int i, int i2, long j) throws RemoteException;

    void notifySFBufferProduced(int i, long j) throws RemoteException;

    void notifySchedFrameTime(int i, long j, long j2, long j3) throws RemoteException;

    int notifyTGPAfps(int i) throws RemoteException;

    void registerCallback(String str, IGameCallback iGameCallback) throws RemoteException;

    int setCallback(IGameCallback iGameCallback) throws RemoteException;

    void setKeyThread(String str) throws RemoteException;

    void unregisterCallback(String str) throws RemoteException;

    int unsetCallback(IGameCallback iGameCallback) throws RemoteException;

    public static class Default implements IGameOptHalService {
        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void notifyGameInfo(int type, String jsonData) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void notifySFBufferProduced(int bufferNum, long timeStamp) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public int notifyTGPAfps(int tFps) throws RemoteException {
            return 0;
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public int setCallback(IGameCallback callback) throws RemoteException {
            return 0;
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void setKeyThread(String config) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public int unsetCallback(IGameCallback callback) throws RemoteException {
            return 0;
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void registerCallback(String identifier, IGameCallback callback) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void unregisterCallback(String identifier) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void notifyCommonIntValue(int type, long value) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void notifyCommonStringValue(int type, String value) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void notifyCommonIntPair(int type, int key, long value) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void notifyCommonStringPair(int type, String key, String value) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void notifySchedFrameTime(int bufferNum, long produce, long consume, long reserve) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public void notifyRptStatus(int totalRp, int currentRp, long timeStamp) throws RemoteException {
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGameOptHalService {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_notifyCommonIntPair = 11;
        static final int TRANSACTION_notifyCommonIntValue = 9;
        static final int TRANSACTION_notifyCommonStringPair = 12;
        static final int TRANSACTION_notifyCommonStringValue = 10;
        static final int TRANSACTION_notifyGameInfo = 1;
        static final int TRANSACTION_notifyRptStatus = 14;
        static final int TRANSACTION_notifySFBufferProduced = 2;
        static final int TRANSACTION_notifySchedFrameTime = 13;
        static final int TRANSACTION_notifyTGPAfps = 3;
        static final int TRANSACTION_registerCallback = 7;
        static final int TRANSACTION_setCallback = 4;
        static final int TRANSACTION_setKeyThread = 5;
        static final int TRANSACTION_unregisterCallback = 8;
        static final int TRANSACTION_unsetCallback = 6;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IGameOptHalService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IGameOptHalService)) {
                return (IGameOptHalService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "notifyGameInfo";
                case 2:
                    return "notifySFBufferProduced";
                case 3:
                    return "notifyTGPAfps";
                case 4:
                    return "setCallback";
                case 5:
                    return "setKeyThread";
                case 6:
                    return "unsetCallback";
                case 7:
                    return "registerCallback";
                case 8:
                    return "unregisterCallback";
                case 9:
                    return "notifyCommonIntValue";
                case 10:
                    return "notifyCommonStringValue";
                case 11:
                    return "notifyCommonIntPair";
                case 12:
                    return "notifyCommonStringPair";
                case 13:
                    return "notifySchedFrameTime";
                case 14:
                    return "notifyRptStatus";
                case TRANSACTION_getInterfaceHash /* 16777214 */:
                    return "getInterfaceHash";
                case TRANSACTION_getInterfaceVersion /* 16777215 */:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= TRANSACTION_getInterfaceVersion) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == TRANSACTION_getInterfaceVersion) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == TRANSACTION_getInterfaceHash) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    notifyGameInfo(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    long _arg12 = data.readLong();
                    data.enforceNoDataAvail();
                    notifySFBufferProduced(_arg02, _arg12);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = notifyTGPAfps(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 4:
                    IGameCallback _arg04 = IGameCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result2 = setCallback(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    data.enforceNoDataAvail();
                    setKeyThread(_arg05);
                    return true;
                case 6:
                    IGameCallback _arg06 = IGameCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result3 = unsetCallback(_arg06);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    IGameCallback _arg13 = IGameCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCallback(_arg07, _arg13);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    unregisterCallback(_arg08);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    long _arg14 = data.readLong();
                    data.enforceNoDataAvail();
                    notifyCommonIntValue(_arg09, _arg14);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    notifyCommonStringValue(_arg010, _arg15);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    int _arg16 = data.readInt();
                    long _arg2 = data.readLong();
                    data.enforceNoDataAvail();
                    notifyCommonIntPair(_arg011, _arg16, _arg2);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    String _arg17 = data.readString();
                    String _arg22 = data.readString();
                    data.enforceNoDataAvail();
                    notifyCommonStringPair(_arg012, _arg17, _arg22);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    long _arg18 = data.readLong();
                    long _arg23 = data.readLong();
                    long _arg3 = data.readLong();
                    data.enforceNoDataAvail();
                    notifySchedFrameTime(_arg013, _arg18, _arg23, _arg3);
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    int _arg19 = data.readInt();
                    long _arg24 = data.readLong();
                    data.enforceNoDataAvail();
                    notifyRptStatus(_arg014, _arg19, _arg24);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGameOptHalService {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void notifyGameInfo(int type, String jsonData) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(jsonData);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method notifyGameInfo is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void notifySFBufferProduced(int bufferNum, long timeStamp) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(bufferNum);
                    _data.writeLong(timeStamp);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method notifySFBufferProduced is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public int notifyTGPAfps(int tFps) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(tFps);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method notifyTGPAfps is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public int setCallback(IGameCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method setCallback is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void setKeyThread(String config) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(config);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setKeyThread is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public int unsetCallback(IGameCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status) {
                        throw new RemoteException("Method unsetCallback is unimplemented.");
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void registerCallback(String identifier, IGameCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(identifier);
                    _data.writeStrongInterface(callback);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method registerCallback is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void unregisterCallback(String identifier) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(identifier);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method unregisterCallback is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void notifyCommonIntValue(int type, long value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeLong(value);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method notifyCommonIntValue is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void notifyCommonStringValue(int type, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(value);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method notifyCommonStringValue is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void notifyCommonIntPair(int type, int key, long value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(key);
                    _data.writeLong(value);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method notifyCommonIntPair is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void notifyCommonStringPair(int type, String key, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(key);
                    _data.writeString(value);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method notifyCommonStringPair is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void notifySchedFrameTime(int bufferNum, long produce, long consume, long reserve) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(bufferNum);
                    _data.writeLong(produce);
                    _data.writeLong(consume);
                    _data.writeLong(reserve);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method notifySchedFrameTime is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public void notifyRptStatus(int totalRp, int currentRp, long timeStamp) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(totalRp);
                    _data.writeInt(currentRp);
                    _data.writeLong(timeStamp);
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method notifyRptStatus is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceVersion, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // vendor.oplus.hardware.gameopt.IGameOptHalService
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceHash, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        public int getMaxTransactionId() {
            return TRANSACTION_getInterfaceHash;
        }
    }
}
