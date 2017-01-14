package com.zhulilyi.woju.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.zhulilyi.woju.R;
import com.zhulilyi.woju.app.App;
import com.zhulilyi.woju.widget.statusLayout.OnShowHideViewListener;
import com.zhulilyi.woju.widget.statusLayout.StatusLayoutManager;

import butterknife.ButterKnife;
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
    protected FrameLayout flContent;
    protected StatusLayoutManager statusLayoutManager;//状态布局,位于标题之下

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = this;
        init();
    }

    @Override
    public void setContentView(int viewId) {
        View root = View.inflate(this, R.layout.activity_base, null);
        flContent = (FrameLayout) root.findViewById(R.id.fl_content);
        super.setContentView(root);
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(viewId)
                .emptyDataView(R.layout.activity_emptydata)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_loading)
                .netWorkErrorView(R.layout.activity_networkerror)
                .onShowHideViewListener(new OnShowHideViewListener() {
                    @Override
                    public void onShowView(View view, int id) {

                    }

                    @Override
                    public void onHideView(View view, int id) {

                    }
                }).build();
        flContent.addView(statusLayoutManager.getRootLayout());
        unbinder = ButterKnife.bind(this);
        statusLayoutManager.showContent();
    }

    protected void init() {
        setTranslucentStatus(true);
        App.getInstance().registerActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        if (basePresenter != null) {
            basePresenter.detachView();
            basePresenter = null;
        }
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
}

