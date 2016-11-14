package com.enid.rxjavacomm.comm;

import android.app.Application;

/**
 * Created by enid on 2016/3/31.
 */
public class MyApplication extends Application{
    private static MyApplication mInstance = null;
    private String actionFrom = "";
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public synchronized static MyApplication getInstance(){
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}
