package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.LoginPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 身份信息activity
 * Created by zhuliyi on 2017/5/16.
 */

public class IDInfoActivity extends SwipeBackActivity {
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_id_no)
    TextView textIdNo;
    @BindView(R.id.image_head)
    ImageView imageHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_info);
        ButterKnife.bind(this);
        textTitle.setText("身份信息");
        Glide.with(context).load(LoginPreference.getIconUrl()).into(imageHead);
        textName.setText(LoginPreference.getTrueName());
        textIdNo.setText(LoginPreference.getIdNo());
    }
}
