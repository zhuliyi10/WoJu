package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;

/**
 * 我的蜗圈activity
 * Created by zhuliyi on 2017/5/16.
 */

public class MineCircleActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_circle);
        textTitle.setText("我的蜗圈");
    }
}
