package com.tencent.fakegps.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.tencent.fakegps.IJoyStickPresenter;
import com.tencent.fakegps.R;
import com.tencent.fakegps.ScreenUtils;

/**
 * Created by tiger on 7/22/16.
 */
public class JoyStickView extends FrameLayout {

    private static int sStatusBarHeight;

    private int mViewWidth;
    private int mViewHeight;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowLayoutParams;
    // touch point x pos according to screen
    private float mXInScreen;
    // touch point y pos according to screen
    private float mYInScreen;
    // touch point x pos according to view
    private float mXInView;
    // touch point y pos according to view
    private float mYInView;

    private boolean isShowing = false;

    private IJoyStickPresenter mJoyStickPresenter;

    public JoyStickView(Context context) {
        super(context);
        mWindowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.joystick_layout, this);

        sStatusBarHeight = ScreenUtils.getStatusBarHeight(context);
        mViewWidth = context.getResources().getDimensionPixelSize(R.dimen.joystick_width);
        mViewHeight = context.getResources().getDimensionPixelSize(R.dimen.joystick_height);

        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mWindowLayoutParams.format = PixelFormat.RGBA_8888;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        //the real width and height is needed, or the view cannot be shown
        mWindowLayoutParams.width = mViewWidth;
        mWindowLayoutParams.height = mViewHeight;
        mWindowLayoutParams.x = ScreenUtils.getScreenWidth(context);
        mWindowLayoutParams.y = ScreenUtils.getScreenHeight(context) / 2;

        findViewById(R.id.btn_set_loc).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_fly_to).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_copy_loc).setOnClickListener(mOnClickListener);

        findViewById(R.id.btn_up).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_left).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_right).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_down).setOnClickListener(mOnClickListener);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void addToWindow() {
        mWindowManager.addView(this, mWindowLayoutParams);
        isShowing = true;
    }

    public void removeFromWindow() {
        mWindowManager.removeView(this);
        isShowing = false;
    }

    public boolean isShowing() {
        return isShowing;
    }

    private View.OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_up:
                    if (mJoyStickPresenter != null) mJoyStickPresenter.onArrowUpClick();
                    break;
                case R.id.btn_down:
                    if (mJoyStickPresenter != null) mJoyStickPresenter.onArrowDownClick();
                    break;
                case R.id.btn_left:
                    if (mJoyStickPresenter != null) mJoyStickPresenter.onArrowLeftClick();
                    break;
                case R.id.btn_right:
                    if (mJoyStickPresenter != null) mJoyStickPresenter.onArrowRightClick();
                    break;

                case R.id.btn_set_loc:
                    if (mJoyStickPresenter != null) mJoyStickPresenter.onSetLocationClick();
                    break;

                case R.id.btn_fly_to:
                    if (mJoyStickPresenter != null) mJoyStickPresenter.onFlyClick();
                    break;

                case R.id.btn_copy_loc:
                    if (mJoyStickPresenter != null) mJoyStickPresenter.onBookmarkLocationClick();
                    break;

                default:
                    break;

            }
        }
    };

    public void setJoyStickPresenter(IJoyStickPresenter joyStickPresenter) {
        mJoyStickPresenter = joyStickPresenter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXInView = event.getX();
                mYInView = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mXInScreen = event.getRawX();
                mYInScreen = event.getRawY() - sStatusBarHeight;
                updateViewPosition();
                break;
        }
        return true;
    }


    private void updateViewPosition() {
        mWindowLayoutParams.x = (int) (mXInScreen - mXInView);
        mWindowLayoutParams.y = (int) (mYInScreen - mYInView);
        mWindowManager.updateViewLayout(this, mWindowLayoutParams);
    }
}
