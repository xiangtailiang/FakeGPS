package com.github.fakegps.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

import tiger.radio.loggerlibrary.Logger;

/**
 * Button can keep invoke click event when keep pressed
 * Created by tiger on 7/23/16.
 */
public class JoyStickButton extends ImageButton {

    private static final String TAG = "JoyStickButton";

    private boolean mIsPressDown = false;

    public JoyStickButton(Context context) {
        super(context);
    }

    public JoyStickButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mIsPressDown = true;
                postDelayed(mLongPressDetectorRunnable, 1000);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                removeCallbacks(mLongPressDetectorRunnable);
                mIsPressDown = false;
                break;

        }

        return super.onTouchEvent(event);

    }


    private Runnable mLongPressDetectorRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsPressDown) {
                Logger.d(TAG, "invoke click");
                performClick();
                postDelayed(this, 500);
            }
        }
    };
}
