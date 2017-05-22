package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.utils.ToastUtil;
import com.zhuliyi.woju.widget.editText.EditTextWithDel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆activity
 * Created by zhuliyi on 2017/5/16.
 */

public class LoginActivity extends SwipeBackActivity {
    @BindView(R.id.et_account)
    EditTextWithDel etAccount;
    @BindView(R.id.et_pwd)
    EditTextWithDel etPwd;
    @BindView(R.id.img_eye)
    ImageView imgEye;

    private boolean visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        textTitle.setText("登陆");
        String account=LoginPreference.getAcount();
        if(!account.isEmpty()) {
            etAccount.setText(account);
            etAccount.setSelection(account.length());
        }
    }

    @OnClick({R.id.img_eye, R.id.btn_login, R.id.text_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_eye:
                if (visible) {
                    visible = false;
                    imgEye.setImageResource(R.drawable.et_pwd_invisible);
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    visible = true;
                    imgEye.setImageResource(R.drawable.et_pwd_visible);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                etPwd.setSelection(etPwd.getText().toString().trim().length());
                break;
            case R.id.btn_login:
                String account=etAccount.getText().toString().trim();
                String pwd=etPwd.getText().toString().trim();
                if(account.isEmpty()){
                    ToastUtil.showShort(context,"手机/用户名不能为空");
                }else if(pwd.isEmpty()){
                    ToastUtil.showShort(context,"密码不能为空");
                }else {
                    requestLogin(account,pwd);
                }
                break;
            case R.id.text_forget:
                break;
        }
    }

    private void requestLogin(String account,String pwd){
        if(account.equals("13250751496")&&pwd.equals("123")){
            ToastUtil.showShort(context,"登陆成功");
            LoginPreference.saveAccountAndPwd(account,pwd);
            LoginPreference.saveLoginState(true);
            finish();
        }else {
            ToastUtil.showShort(context,"登陆失败");
        }
    }
}
