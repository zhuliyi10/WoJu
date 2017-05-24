package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.utils.ActivityManagerUtils;
import com.zhuliyi.woju.utils.ToastUtil;
import com.zhuliyi.woju.widget.editText.EditTextWithDel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 密码设置activity
 * Created by zhuliyi on 2017/5/24.
 */

public class PwdSetActivity extends SwipeBackActivity {
    @BindView(R.id.et_pwd1)
    EditTextWithDel etPwd1;
    @BindView(R.id.et_pwd2)
    EditTextWithDel etPwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_set);
        ButterKnife.bind(this);
        textTitle.setText("设置登陆密码");
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        String pwd1=etPwd1.getText().toString().trim();
        String pwd2=etPwd2.getText().toString().trim();
        if(!pwd1.equals(pwd2)){
            ToastUtil.showShort(context,"两次密码不一致");
        }else {
            ToastUtil.showShort(context,"密码设置成功");
            ActivityManagerUtils.getInstance().removeActivityExceptMain();
        }
    }
}
