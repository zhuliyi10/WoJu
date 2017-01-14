package com.zhulilyi.woju.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhulilyi.woju.R;
import com.zhulilyi.woju.base.RootView;
import com.zhulilyi.woju.presenter.contract.WelcomeContract;

import butterknife.BindView;

/**
 * Created by zhuliyi on 2017/1/6.
 */

public class StartView extends RootView<WelcomeContract.Presenter> implements WelcomeContract.View {
    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_view;
    }

    @Override
    protected void initView() {

    }

    public StartView(Context context) {
        super(context);
    }

    public StartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        basePresenter = presenter;
    }

    @Override
    public void showContent(String path, Animation animation) {
        Glide.with(context).load(path).animate(animation).into(ivWelcomeBg);
    }

    @Override
    public void jumpToMain() {

    }
}
