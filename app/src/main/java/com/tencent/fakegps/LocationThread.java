package com.tencent.fakegps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * LocationThread
 * Created by tiger on 7/21/16.
 */
public class LocationThread extends HandlerThread {

    private static final String TAG = "LocationThread";

    //    private final double LAT_DEFAULT = 37.802406;
//    private final double LON_DEFAULT = -122.401779;
    private final double LAT_DEFAULT = 23.151637;
    private final double LON_DEFAULT = 113.344721;

//    23.151637, 113.344721

    public static final int MSG_UP = 1;
    public static final int MSG_DOWN = 2;
    public static final int MSG_LEFT = 3;
    public static final int MSG_RIGHT = 4;

    private Context mContext;
    private LocationManager mLocationManager;

    private Handler mHandler;

    private LocPoint mLocPoint = new LocPoint(LAT_DEFAULT, LON_DEFAULT);
    private LocPoint mLastLocPoint = new LocPoint(0, 0);
    private double mStep = 0.00001;

    private Method mMethodMakeComplete;

    public LocationThread(Context context) {
        super("LocationThread");
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            mMethodMakeComplete = Location.class.getMethod("makeComplete", new Class[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void setLocPoint(LocPoint locPoint) {
        mLocPoint = locPoint;
    }

    public void setStep(double step) {
        mStep = step;
    }

    @Override
    public synchronized void start() {
        super.start();

        mHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_UP:
                        mLocPoint.setLatitude(mLocPoint.getLatitude() + mStep);
                        break;
                    case MSG_DOWN:
                        mLocPoint.setLatitude(mLocPoint.getLatitude() - mStep);
                        break;
                    case MSG_LEFT:
                        mLocPoint.setLongitude(mLocPoint.getLongitude() - mStep);
                        break;
                    case MSG_RIGHT:
                        mLocPoint.setLongitude(mLocPoint.getLongitude() + mStep);

                        break;
                }
            }
        };
        mHandler.post(mUpdateLocation);
    }

    public void startThread() {
        start();
    }

    public void stopThread() {
        try {
            quit();
            interrupt();
            setMockLocation(1, mContext);
            try {
                mLocationManager.removeTestProvider("gps");
            } catch (Exception e) {
                Log.e("Excp gps", e.toString());
                try {
                    mLocationManager.removeTestProvider("network");
                } catch (Exception e2) {
                    Log.e("Excp network", e.toString());
                }
            }
            mLocationManager.removeTestProvider("network");

            setMockLocation(0, mContext);
        } catch (Exception e) {
            Log.e(TAG, "stopThread fail!", e);
        }
    }


    protected static boolean setMockLocation(int i, Context context) {
        try {
            return Settings.Secure.putInt(context.getContentResolver(), "mock_location", i);
        } catch (Exception e) {
            return false;
        }
    }

    Runnable mUpdateLocation = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "UpdateLocation, " + mLocPoint);
            setMockLocation(1, mContext);
            Location location = new Location("gps");
            try {
                location.setLatitude(mLocPoint.getLatitude());
                location.setLongitude(mLocPoint.getLongitude());
                location.setAltitude(50);
                if (Build.VERSION.SDK_INT > 16) {
                    location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                }
                location.setAccuracy(10);
                if (mLastLocPoint.getLatitude() != mLocPoint.getLatitude()
                        || mLocPoint.getLongitude() != mLocPoint.getLongitude()) {
                    mLastLocPoint.setLatitude(mLocPoint.getLatitude());
                    mLastLocPoint.setLongitude(mLocPoint.getLongitude());
                    location.setSpeed(1);
                } else {
                    location.setSpeed(0);
                }
                location.setTime(System.currentTimeMillis());
                if (mMethodMakeComplete != null) {
                    try {
                        mMethodMakeComplete.invoke(location, new Object[0]);
                    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                mLocationManager.addTestProvider("gps", false, false, false, false, false, false, false, 1, 1);
                mLocationManager.setTestProviderStatus("gps", 2, null, System.currentTimeMillis());
                mLocationManager.setTestProviderLocation("gps", location);
                mLocationManager.addTestProvider("network", true, false, true, false, false, false, false, 1, 2);
                mLocationManager.setTestProviderStatus("network", 2, null, System.currentTimeMillis());
                mLocationManager.setTestProviderLocation("network", location);
            } catch (Exception e) {
                Log.e(TAG, "add Location fail!", e);
            }


            mHandler.postDelayed(mUpdateLocation, 1000);
        }

    };

    public Handler getHandler() {
        return mHandler;
    }
}
       
