package com.zhuliyi.woju.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * activity中创建的视图的父类
 * Created by zhuliyi on 2017/1/5.
 */

public abstract class RootView<T extends BasePresenter> extends LinearLayout{
    protected Context context;
    protected Unbinder unbinder;
    protected T basePresenter;
    protected abstract int getLayoutId();
    protected abstract void initView();
    public RootView(Context context) {
        super(context);
        init();
    }

    public RootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    protected void init(){
        context=getContext();
        inflate(context,getLayoutId(),this);
        unbinder= ButterKnife.bind(this);
        initView();

    }

    @Override
    protected void onAttachedToWindow() {//在Act resume的时候被调用的,只调用一次
        super.onAttachedToWindow();
        if(basePresenter!=null){
            basePresenter.attachView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {//在Act destroy的时候被调用的
        super.onDetachedFromWindow();
        if(basePresenter!=null){
            basePresenter.detachView();
        }
        unbinder.unbind();
        context=null;
    }
}
