package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;

/**
 * 余额activity
 * Created by zhuliyi on 2017/5/18.
 */

public class BalanceActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        textTitle.setText("余额");
    }
}
