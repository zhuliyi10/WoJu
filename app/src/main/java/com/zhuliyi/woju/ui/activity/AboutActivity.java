package com.zhuliyi.woju.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于activity
 * Created by zhuliyi on 2017/5/16.
 */

public class AboutActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        textTitle.setText("关于蜗信");
    }

    @OnClick(R.id.ll_update_tips)
    public void onViewClicked() {
        startActivity(new Intent(context,UpdateTipsActivity.class));
    }
}
