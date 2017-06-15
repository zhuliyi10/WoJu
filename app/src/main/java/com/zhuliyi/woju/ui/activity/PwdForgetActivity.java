package com.zhuliyi.woju.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.callback.TextWatcherListener;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.utils.ToastUtil;
import com.zhuliyi.woju.utils.VerificationUtils;
import com.zhuliyi.woju.widget.editText.EditTextWithDel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码 activity
 * Created by zhuliyi on 2017/5/23.
 */

public class PwdForgetActivity extends SwipeBackActivity {
    public static final int TYPE_FORGET = 1;//忘记
    public static final int TYPE_MOD = 2;//修改
    @BindView(R.id.et_phone)
    EditTextWithDel etPhone;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.et_code)
    EditTextWithDel etCode;
    @BindView(R.id.text_tip)
    TextView textTip;

    private boolean canGetCode = true;
    private int type;
    private static final int TOTAL_TIME = 90000;//单位ms
    private static final int ONECE_TIME = 1000;//单位ms
    private CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIME, ONECE_TIME) {
        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setText((int) (millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            btnCode.setText("重新获取验证码");
            canGetCode = true;
            setBtnCode();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_forget);
        ButterKnife.bind(this);
        type= getIntent().getIntExtra("type", -1);
        if (type == -1) {
            finish();
        }
        if (type == 1) {
            textTitle.setText("找回登陆密码");
        }
        if (type == 2) {
            textTitle.setText("修改登陆密码");
            if (LoginPreference.getPhone().isEmpty()) {
                textTip.setText("手机号码未设置，验证成功后自动绑定手机号");
            }else {
                textTip.setText("修改密码之前要验证蜗居用户的手机号码");
                etPhone.setDelVisible(false);
                etPhone.setText(LoginPreference.getPhone());
                etPhone.setEnabled(false);
                setBtnCodeEnable();
            }
        }
        etPhone.addTextChangedListener(new TextWatcherListener() {
            @Override
            public void afterTextChanged(Editable s) {
                setBtnCode();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    private void setBtnCode() {
        if (canGetCode) {
            if (isPhoneNumber()) {
                setBtnCodeEnable();
            } else {
                setBtnCodeUnable();
            }
        }
    }

    private void setBtnCodeEnable() {
        btnCode.setClickable(true);
        btnCode.setBackgroundResource(R.drawable.selector_color_circle);
    }

    private void setBtnCodeUnable() {
        btnCode.setClickable(false);
        btnCode.setBackgroundResource(R.drawable.shape_gray_circle);
    }

    private boolean isPhoneNumber() {
        return VerificationUtils.matcherPhoneNum(etPhone.getText().toString());
    }

    @OnClick({R.id.btn_code, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                //发送短信验证码
                canGetCode = false;
                setBtnCodeUnable();
                countDownTimer.start();
                break;
            case R.id.btn_next:
                if (!isPhoneNumber()) {
                    ToastUtil.showShort("请输入正确的手机号码");
                } else if (etCode.getText().toString().equals("")) {
                    ToastUtil.showShort("验证码不能为空");
                }else if (!LoginPreference.getPhone().isEmpty()&&!LoginPreference.getPhone().equals(etPhone.getText().toString())) {
                    ToastUtil.showShort("手机号为："+LoginPreference.getPhone());
                } else if (etCode.getText().toString().equals("1111")) {
                    if(type==2){
                        LoginPreference.savePhone(etPhone.getText().toString());
                    }
                    startActivity(new Intent(context, PwdSetActivity.class));
                } else {
                    ToastUtil.showShort("验证码：1111");
                }
                break;
        }
    }
}
