package com.tencent.fakegps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import com.tencent.fakegps.model.LocPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * LocationThread
 * Created by tiger on 7/21/16.
 */
public class LocationThread extends HandlerThread {

    private static final String TAG = "LocationThread";


    private Context mContext;
    private JoyStickManager mJoyStickManager;
    private LocationManager mLocationManager;

    private Handler mHandler;
    private LocPoint mLastLocPoint = new LocPoint(0, 0);

    private Method mMethodMakeComplete;

    public LocationThread(Context context, JoyStickManager joyStickManager) {
        super("LocationThread");
        mContext = context;
        mJoyStickManager = joyStickManager;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            mMethodMakeComplete = Location.class.getMethod("makeComplete", new Class[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void start() {
        super.start();

        mHandler = new Handler(getLooper());
        mHandler.post(mUpdateLocation);
    }

    public void startThread() {
        start();
    }

    public void stopThread() {
        mHandler.removeCallbacksAndMessages(null);
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

        mJoyStickManager = null;
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

            LocPoint locPoint = mJoyStickManager.getCurrentLocPoint();
            Log.d(TAG, "UpdateLocation, " + locPoint);
            setMockLocation(1, mContext);
            Location location = new Location("gps");
            try {
                location.setLatitude(locPoint.getLatitude());
                location.setLongitude(locPoint.getLongitude());
                location.setAltitude(50);
                if (Build.VERSION.SDK_INT > 16) {
                    location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                }
                location.setAccuracy(10);
                if (mLastLocPoint.getLatitude() != locPoint.getLatitude()
                        || mLastLocPoint.getLongitude() != locPoint.getLongitude()) {
                    mLastLocPoint.setLatitude(locPoint.getLatitude());
                    mLastLocPoint.setLongitude(locPoint.getLongitude());
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
       
