package com.tencent.fakegps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.tencent.fakegps.model.LocPoint;
import com.tencent.fakegps.ui.BookmarkActivity;
import com.tencent.fakegps.ui.JoyStickView;

/**
 * Created by tiger on 7/22/16.
 */
public class JoyStickManager implements IJoyStickPresenter {

    private static final String TAG = "JoyStickManager";

    public static double STEP_DEFAULT = 0.00002;

    private static JoyStickManager INSTANCE = new JoyStickManager();

    private Context mContext;
    private LocationThread mLocationThread;
    private boolean mIsStarted = false;
    private double mMoveStep = STEP_DEFAULT;

    private LocPoint mCurrentLocPoint;

    private LocPoint mTargetLocPoint;
    private boolean mIsFlyMode = false;

    private JoyStickView mJoyStickView;

    private JoyStickManager() {
    }


    public void init(Context context) {
        mContext = context;
    }

    public static JoyStickManager get() {
        return INSTANCE;
    }

    public void start(@NonNull LocPoint locPoint) {
        mCurrentLocPoint = locPoint;
        if (mLocationThread == null || !mLocationThread.isAlive()) {
            mLocationThread = new LocationThread(mContext.getApplicationContext(), this);
            mLocationThread.startThread();
        }
        showJoyStick();
        mIsStarted = true;
    }

    public void stop() {
        if (mLocationThread != null) {
            mLocationThread.stopThread();
            mLocationThread = null;
        }

        hideJoyStick();
        mIsStarted = false;
    }

    public boolean isStarted() {
        return mIsStarted;
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

    public LocPoint getCurrentLocPoint() {
        return mCurrentLocPoint;
    }

    public void jumpToLocation(LocPoint location) {

    }

    public void flyToLocation(LocPoint location) {

    }

    public boolean isFlyMode() {
        return mIsFlyMode;
    }

    public void stopFlyMode() {
        mIsFlyMode = false;
    }

    public void setMoveStep(double moveStep) {
        mMoveStep = moveStep;
    }

    public double getMoveStep() {
        return mMoveStep;
    }


    @Override
    public void onSetLocationClick() {
        Log.d(TAG, "onSetLocationClick");
    }

    @Override
    public void onFlyClick() {
        Log.d(TAG, "onFlyClick");
    }

    @Override
    public void onBookmarkLocationClick() {
        Log.d(TAG, "onBookmarkLocationClick");
        if (mCurrentLocPoint != null) {
            LocPoint locPoint = new LocPoint(mCurrentLocPoint);
            BookmarkActivity.startPage(mContext, "Bookmark", locPoint);
            FakeGpsUtils.copyToClipboard(mContext, locPoint.toString());
            Toast.makeText(mContext, "Current location is copied!" + "\n" + locPoint, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Service is not start!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onArrowUpClick() {
        Log.d(TAG, "onArrowUpClick");
        mCurrentLocPoint.setLatitude(mCurrentLocPoint.getLatitude() + mMoveStep);
    }

    @Override
    public void onArrowDownClick() {
        Log.d(TAG, "onArrowDownClick");
        mCurrentLocPoint.setLatitude(mCurrentLocPoint.getLatitude() - mMoveStep);
    }

    @Override
    public void onArrowLeftClick() {
        Log.d(TAG, "onArrowLeftClick");
        mCurrentLocPoint.setLongitude(mCurrentLocPoint.getLongitude() - mMoveStep);
    }

    @Override
    public void onArrowRightClick() {
        Log.d(TAG, "onArrowRightClick");
        mCurrentLocPoint.setLongitude(mCurrentLocPoint.getLongitude() + mMoveStep);
    }

}
