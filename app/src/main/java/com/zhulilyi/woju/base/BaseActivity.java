package com.zhulilyi.woju.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zhulilyi.woju.app.App;

import butterknife.Unbinder;

/**
 * Description: BaseActivity  activity做MVP中的V
 * 与baseView的主要的区别是activity销毁的时候执行detachView();
 * Creator: zhuliyi
 * date: 2017/1/8
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected Unbinder unbinder;
    protected T basePresenter;
    protected Activity activity;
    protected Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        activity=this;
        context=this;
    }

    protected void init() {
        setTranslucentStatus(true);
        onPreCreate();
        App.getInstance().registerActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitleHeight(getRootView(this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().unregisterActivity(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
        if(basePresenter!=null) {
            basePresenter.detachView();
            basePresenter = null;
        }
    }

    private void onPreCreate() {

    }

    /**
     * 设置沉浸式
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    private void setTitleHeight(View view) {

    }

    protected static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
