package com.zhuliyi.woju.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.utils.SwipeWindowHelper;

/**
 * Created by zhuliyi on 2017/5/16.
 */

public  class SwipeBackActivity <T extends BasePresenter> extends BaseActivity<T> {
    private  boolean isSupportSlideBack=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarVisible(true);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private SwipeWindowHelper mSwipeWindowHelper;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!isSupportSlideBack) {
            return super.dispatchTouchEvent(ev);
        }

        if(mSwipeWindowHelper == null) {
            mSwipeWindowHelper = new SwipeWindowHelper(getWindow());
        }
        return mSwipeWindowHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }
    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected  void setSupportSlideBack(boolean isSupportSlideBack) {
        this.isSupportSlideBack=isSupportSlideBack;
    }
}
