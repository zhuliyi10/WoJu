package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.callback.TextWatcherListener;
import com.zhuliyi.woju.utils.VerificationUtils;
import com.zhuliyi.woju.widget.editText.EditTextWithDel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 短信登陆或注册activity
 * Created by zhuliyi on 2017/5/23.
 */

public class SmsActivity extends SwipeBackActivity {
    @BindView(R.id.et_phone)
    EditTextWithDel etPhone;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.et_code)
    EditTextWithDel etCode;

    private boolean canGetCode=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
        textTitle.setText("短信登陆");
        etPhone.addTextChangedListener(new TextWatcherListener(){
            @Override
            public void afterTextChanged(Editable s) {
                if(canGetCode){
                    if(VerificationUtils.matcherPhoneNum(etPhone.getText().toString())){
                        btnCode.setClickable(true);
                        btnCode.setBackgroundResource(R.drawable.selector_color_circle);
                    }else {
                        btnCode.setClickable(false);
                        btnCode.setBackgroundResource(R.drawable.shape_gray_circle);
                    }
                }
            }
        });
    }

    @OnClick({R.id.btn_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                break;
            case R.id.btn_login:
                break;
        }
    }
}
