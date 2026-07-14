package com.oplus.uah.info;

public class UAHResourceInfo {
    private int mResId;
    private String mResValue;

    public UAHResourceInfo(int resId, String resValue) {
        mResId = resId;
        mResValue = resValue;
    }

    public int getResId() {
        return mResId;
    }

    public void setResId(int resId) {
        mResId = resId;
    }

    public String getResValue() {
        return mResValue;
    }

    public void setResValue(String resValue) {
        mResValue = resValue;
    }
}
