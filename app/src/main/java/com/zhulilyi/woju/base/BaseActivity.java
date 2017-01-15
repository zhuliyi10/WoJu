package com.zhulilyi.woju.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.zhulilyi.woju.R;
import com.zhulilyi.woju.app.App;
import com.zhulilyi.woju.widget.statusLayout.OnShowHideViewListener;
import com.zhulilyi.woju.widget.statusLayout.StatusLayoutManager;
import com.zhulilyi.woju.widget.theme.ColorTextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description: BaseActivity  activity做MVP中的V
 * 与baseView的主要的区别是activity销毁的时候执行detachView();
 * Creator: zhuliyi
 * date: 2017/1/8
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    protected StatusLayoutManager statusLayoutManager;//状态布局,位于标题之下
    protected Unbinder unbinder;
    protected T basePresenter;
    protected Activity activity;
    protected Context context;
    protected View root;
    protected FrameLayout flContent;
    protected Toolbar toolbar;
    protected ColorTextView textTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = this;
        init();
    }

    @Override
    public void setContentView(int viewId) {
        root = View.inflate(this, R.layout.activity_base, null);
        toolbar= (Toolbar) root.findViewById(R.id.toolbar);
        textTitle= (ColorTextView) root.findViewById(R.id.text_title);
        flContent = (FrameLayout) root.findViewById(R.id.fl_content);
        super.setContentView(root);
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(viewId)
                .emptyDataView(R.layout.page_emptydata)
                .errorView(R.layout.page_error)
                .loadingView(R.layout.page_loading)
                .netWorkErrorView(R.layout.page_networkerror)
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
        setToolbarVisible(false);
    }

    protected void init() {
        setTranslucentStatus(false);
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
    protected void setTranslucentStatus(boolean on) {//对4.4没效果
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

    /**
     * 显示标题栏
     */
    protected void setToolbarVisible(boolean isVisible){
        if(isVisible){
            toolbar.setVisibility(View.VISIBLE);
        }else {
            toolbar.setVisibility(View.GONE);
        }
    }
    protected void setTitleName(String titleName){
        if(titleName!=null){
            textTitle.setText(titleName);
        }
    }
}

