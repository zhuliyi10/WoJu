package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;

public class ChatActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        textTitle.setText("203-何源明");
    }

}
