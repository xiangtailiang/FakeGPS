package com.tencent.fakegps;

import android.app.Application;

/**
 * Created by tiger on 7/23/16.
 */
public class FakeGpsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JoyStickManager.get().init(this);
    }
}
