package com.oplus.zoomwindow;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class OplusZoomWindowInfo implements Parcelable {

    public static final Parcelable.Creator<OplusZoomWindowInfo> CREATOR =
            new Parcelable.Creator<OplusZoomWindowInfo>() {

        @Override
        public OplusZoomWindowInfo createFromParcel(Parcel source) {
            return new OplusZoomWindowInfo(source);
        }

        @Override
        public OplusZoomWindowInfo[] newArray(int size) {
            return new OplusZoomWindowInfo[size];
        }
    };

    public String cpnName;
    public int cvActionFlag;
    public Bundle extension;
    public int inputMethodType;
    public boolean inputShow;
    public int lastExitMethod;
    public float leftScaleOfFloatHandleCenter;
    public String lockPkg;
    public int lockUserId;
    public float rightScaleOfFloatHandleCenter;
    public int rotation;
    public float scale;
    public int sideOfFloatHandle;
    public int systemRotation;
    public boolean windowShown;
    public int windowType;
    public String zoomPkg;
    public Rect zoomRect;
    public int zoomUserId;

    public OplusZoomWindowInfo() {
        zoomRect = new Rect();
        extension = new Bundle();
    }

    public OplusZoomWindowInfo(Parcel in) {
        readFromParcel(in);
    }

    public OplusZoomWindowInfo(OplusZoomWindowInfo in) {
        this();
        if (in == null) {
            return;
        }
        rotation = in.rotation;
        systemRotation = in.systemRotation;
        windowShown = in.windowShown;
        lockPkg = in.lockPkg;
        zoomRect = in.zoomRect != null ? new Rect(in.zoomRect) : new Rect();
        scale = in.scale;
        zoomPkg = in.zoomPkg;
        lockUserId = in.lockUserId;
        zoomUserId = in.zoomUserId;
        inputShow = in.inputShow;
        cpnName = in.cpnName;
        lastExitMethod = in.lastExitMethod;
        inputMethodType = in.inputMethodType;
        extension = in.extension != null ? new Bundle(in.extension) : new Bundle();
        cvActionFlag = in.cvActionFlag;
        windowType = in.windowType;
        leftScaleOfFloatHandleCenter = in.leftScaleOfFloatHandleCenter;
        rightScaleOfFloatHandleCenter = in.rightScaleOfFloatHandleCenter;
        sideOfFloatHandle = in.sideOfFloatHandle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rotation);
        dest.writeInt(systemRotation);
        dest.writeByte((byte) (windowShown ? 1 : 0));
        dest.writeString(lockPkg);
        dest.writeParcelable(zoomRect, flags);
        dest.writeFloat(scale);
        dest.writeString(zoomPkg);
        dest.writeInt(lockUserId);
        dest.writeInt(zoomUserId);
        dest.writeByte((byte) (inputShow ? 1 : 0));
        dest.writeString(cpnName);
        dest.writeInt(lastExitMethod);
        dest.writeInt(inputMethodType);
        dest.writeBundle(extension);
        dest.writeInt(cvActionFlag);
        dest.writeInt(windowType);
        dest.writeFloat(leftScaleOfFloatHandleCenter);
        dest.writeFloat(rightScaleOfFloatHandleCenter);
        dest.writeInt(sideOfFloatHandle);
    }

    public void readFromParcel(Parcel in) {
        rotation = in.readInt();
        systemRotation = in.readInt();
        windowShown = in.readByte() != 0;
        lockPkg = in.readString();
        zoomRect = in.readParcelable(null);
        scale = in.readFloat();
        zoomPkg = in.readString();
        lockUserId = in.readInt();
        zoomUserId = in.readInt();
        inputShow = in.readByte() != 0;
        cpnName = in.readString();
        lastExitMethod = in.readInt();
        inputMethodType = in.readInt();
        extension = in.readBundle();
        cvActionFlag = in.readInt();
        windowType = in.readInt();
        leftScaleOfFloatHandleCenter = in.readFloat();
        rightScaleOfFloatHandleCenter = in.readFloat();
        sideOfFloatHandle = in.readInt();
        if (zoomRect == null) {
            zoomRect = new Rect();
        }
        if (extension == null) {
            extension = new Bundle();
        }
    }
}
