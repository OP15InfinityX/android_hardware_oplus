package com.oplus.uah.info;

import android.os.Bundle;
import java.util.ArrayList;

public class UAHResRequest {
    private Bundle mInfo;
    private ArrayList<UAHResourceInfo> mList;
    private int mTimeout = -1;

    public UAHResRequest() {}

    public UAHResRequest(int timeout, ArrayList<UAHResourceInfo> list) {
        mTimeout = timeout;
        mList = list;
    }

    public UAHResRequest(Bundle info) {
        mInfo = info;
    }

    public ArrayList<UAHResourceInfo> getList() {
        return mList;
    }

    public int getTimeout() {
        return mTimeout;
    }

    @Override
    public String toString() {
        return "UahEventRequest{resList='" + mList + "', timeout=" + mTimeout + ", info=" + mInfo
                + "}";
    }
}
