package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.BaseViewActivity;
import com.zhuliyi.woju.presenter.WelcomePresenter;
import com.zhuliyi.woju.ui.view.StartView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartViewActivity extends BaseViewActivity {

    @BindView(R.id.start_view)
    StartView startView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        unbinder=ButterKnife.bind(this);
        mPresenter=new WelcomePresenter(startView);
    }
}
