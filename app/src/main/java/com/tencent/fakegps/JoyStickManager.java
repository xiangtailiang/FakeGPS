package com.tencent.fakegps;

import android.content.Context;
import android.util.Log;

/**
 * Created by tiger on 7/22/16.
 */
public class JoyStickManager implements IJoyStickPresenter {

    private static final String TAG = "JoyStickManager";

    private static JoyStickManager INSTANCE = new JoyStickManager();

    private Context mContext;
    private LocationThread mLocationThread;
    private JoyStickView mJoyStickView;


    private JoyStickManager() {
    }


    public void init(Context context) {
        mContext = context;
    }

    public static JoyStickManager get() {
        return INSTANCE;
    }

    public void start() {
        if (mLocationThread == null || !mLocationThread.isAlive()) {
            mLocationThread = new LocationThread(mContext.getApplicationContext());
            mLocationThread.startThread();
        }
        showJoyStick();

    }

    public void stop() {
        if (mLocationThread != null) {
            mLocationThread.stopThread();
            mLocationThread = null;
        }

        hideJoyStick();
    }


    public void showJoyStick() {
        if (mJoyStickView == null) {
            mJoyStickView = new JoyStickView(mContext);
            mJoyStickView.setJoyStickPresenter(this);
        }

        if (!mJoyStickView.isShowing()) {
            mJoyStickView.addToWindow();
        }
    }

    public void hideJoyStick() {
        if (mJoyStickView != null && mJoyStickView.isShowing()) {
            mJoyStickView.removeFromWindow();
        }
    }

    @Override
    public void onArrowUpClick() {
        Log.d(TAG, "onArrowUpClick");
        mLocationThread.getHandler().sendEmptyMessage(LocationThread.MSG_UP);
    }

    @Override
    public void onArrowDownClick() {
        Log.d(TAG, "onArrowDownClick");
        mLocationThread.getHandler().sendEmptyMessage(LocationThread.MSG_DOWN);
    }

    @Override
    public void onArrowLeftClick() {
        Log.d(TAG, "onArrowLeftClick");
        mLocationThread.getHandler().sendEmptyMessage(LocationThread.MSG_LEFT);
    }

    @Override
    public void onArrowRightClick() {
        Log.d(TAG, "onArrowRightClick");
        mLocationThread.getHandler().sendEmptyMessage(LocationThread.MSG_RIGHT);
    }
}
