package com.tencent.fakegps;

import android.app.Application;
import android.content.Context;

import com.litesuits.orm.LiteOrm;

/**
 * Created by tiger on 7/23/16.
 */
public class FakeGpsApp extends Application {

    private static Context sAppContext;
    private volatile static LiteOrm sLiteOrm;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sAppContext = base;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JoyStickManager.get().init(this);
    }

    public static LiteOrm getLiteOrm() {
        if (sLiteOrm == null) {
            synchronized (FakeGpsApp.class) {
                if (sLiteOrm == null) {
                    sLiteOrm = LiteOrm.newSingleInstance(sAppContext, "fake_gps.db");
                    sLiteOrm.setDebugged(true);
                }
            }
        }
        return sLiteOrm;
    }
}
