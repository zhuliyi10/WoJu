package com.zhuliyi.woju.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.BaseActivity;
import com.zhuliyi.woju.presenter.WelcomePresenter;
import com.zhuliyi.woju.presenter.contract.WelcomeContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity<WelcomeContract.Presenter> implements WelcomeContract.View {

    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        setFullSecreen(true);
        new WelcomePresenter(this);
    }

    @Override
    public void showContent(String path, Animation animation) {
        Glide.with(context).load(R.drawable.bg_page_portrait).crossFade().into(ivWelcomeBg);
    }

    @Override
    public void jumpToMain() {
        Intent intent=new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        basePresenter = presenter;
    }

    @Override
    public void onBackPressed() {
    }
}
