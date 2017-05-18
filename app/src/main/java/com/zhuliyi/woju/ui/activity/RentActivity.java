package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;

/**
 * 租金activity
 * Created by zhuliyi on 2017/5/18.
 */

public class RentActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        textTitle.setText("租金");
    }
}
