package com.zhuliyi.woju.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.utils.ActivityManagerUtils;
import com.zhuliyi.woju.utils.StatusBarCompat;
import com.zhuliyi.woju.widget.statusLayout.OnShowHideViewListener;
import com.zhuliyi.woju.widget.statusLayout.StatusLayoutManager;

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
    protected TextView textTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void setContentView(int viewId) {
        super.setContentView(root);
        setFullSecreen(false);
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
    }

    protected void init() {
        activity = this;
        context = this;
        ActivityManagerUtils.getInstance().addActivity(this);
        root = View.inflate(this, R.layout.activity_base, null);
        toolbar= (Toolbar) root.findViewById(R.id.toolbar);
        textTitle= (TextView) root.findViewById(R.id.text_title);
        flContent = (FrameLayout) root.findViewById(R.id.fl_content);
        setToolbarVisible(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        App.getRefWatcher().watch(this);
        ActivityManagerUtils.getInstance().removeActivity(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (basePresenter != null) {
            basePresenter.detachView();
            basePresenter = null;
        }
    }


    protected void addStatus(int color) {
        StatusBarCompat.setColor(this, color);
    }

    protected void removeStatus() {
        StatusBarCompat.setFullScreen(this);
    }

    protected void showContent(){
        statusLayoutManager.showContent();
    }
    protected void showProgress(){
        statusLayoutManager.showLoading();
    }
    protected void showEmpty(){
        statusLayoutManager.showEmptyData();
    }
    protected void showError(){
        statusLayoutManager.showError();
    }

    /**
     * 是否设置全屏,设置全屏时windowSoftInputMode = "adjustResize"无效
     * @param isFull
     */
    protected void setFullSecreen(boolean isFull){
        if(isFull) {
            removeStatus();
        }else {
            addStatus(R.color.colorPrimaryDark);
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

