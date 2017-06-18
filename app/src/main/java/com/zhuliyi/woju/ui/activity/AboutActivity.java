package com.zhuliyi.woju.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhuliyi.woju.BuildConfig;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于activity
 * Created by zhuliyi on 2017/5/16.
 */

public class AboutActivity extends SwipeBackActivity {
    @BindView(R.id.text_version)
    TextView textVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        textTitle.setText("关于蜗信");
        textVersion.setText(BuildConfig.VERSION_NAME);
    }

    @OnClick({R.id.ll_update_tips,R.id.ll_introduce})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.ll_update_tips:
                startActivity(new Intent(context, UpdateTipsActivity.class));
                break;
            case R.id.ll_introduce:
                startActivity(new Intent(context,IntroduceActivity.class));
                break;
        }

    }
}
