package com.zhulilyi.woju.ui.activity;

import android.os.Bundle;

import com.zhulilyi.woju.R;
import com.zhulilyi.woju.base.BaseViewActivity;
import com.zhulilyi.woju.presenter.WelcomePresenter;
import com.zhulilyi.woju.ui.view.StartView;

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
