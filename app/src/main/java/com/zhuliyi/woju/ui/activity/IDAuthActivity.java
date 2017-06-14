package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.utils.ToastUtil;
import com.zhuliyi.woju.utils.VerificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 身份认证activity
 * Created by zhuliyi on 2017/5/16.
 */

public class IDAuthActivity extends SwipeBackActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id_no)
    EditText etIdNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_auth);
        ButterKnife.bind(this);
        textTitle.setText("身份验证");
    }

    @OnClick(R.id.btn_auth)
    public void onViewClicked() {
        String name = etName.getText().toString();
        String IDNo=etIdNo.getText().toString();
        if(name.isEmpty()){
            ToastUtil.showShort(context,"姓名不能为空");
        }else if(!VerificationUtils.matcherRealName(name)){
            ToastUtil.showShort(context,"请输入正确的名字");
        }else if(IDNo.isEmpty()){
            ToastUtil.showShort(context,"身份证号不能为空");
        }else if(!VerificationUtils.matcherIdentityCard(IDNo)){
            ToastUtil.showShort(context,"请输入正确的身份证号码");
        }else {
            ToastUtil.showLong(context,"认证成功");
            LoginPreference.saveTrueName(name);
            LoginPreference.saveIDNo(IDNo);
            setResult(RESULT_OK);
            finish();
        }
    }
}
