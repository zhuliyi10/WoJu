package com.zhuliyi.woju.ui.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.BaseFragment;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.ui.activity.PhoneModActivity;
import com.zhuliyi.woju.utils.ToastUtil;
import com.zhuliyi.woju.widget.editText.EditTextWithDel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 旧密码
 * Created by zhuliyi on 2017/6/15.
 */

public class PhoneOldFragment extends BaseFragment {
    private static final int TOTAL_TIME = 90000;//单位ms
    private static final int ONECE_TIME = 1000;//单位ms
    @BindView(R.id.text_phone)
    TextView textPhone;
    @BindView(R.id.et_code)
    EditTextWithDel etCode;
    Unbinder unbinder;
    @BindView(R.id.btn_code)
    Button btnCode;

    private String phone;
    private CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIME, ONECE_TIME) {
        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setText((int) (millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            btnCode.setText("重新获取验证码");
            setBtnCodeEnable();
        }
    };

    private void setBtnCodeEnable() {
        btnCode.setClickable(true);
        btnCode.setBackgroundResource(R.drawable.selector_color_circle);
    }

    private void setBtnCodeUnable() {
        btnCode.setClickable(false);
        btnCode.setBackgroundResource(R.drawable.shape_gray_circle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pwd_old;
    }

    @Override
    protected void initView() {
        phone=LoginPreference.getPhone();
        textPhone.setText(phone);
        setBtnCodeEnable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        countDownTimer.cancel();
    }

    @OnClick({R.id.btn_code, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                //发送短信验证码
                setBtnCodeUnable();
                countDownTimer.start();
                break;
            case R.id.btn_next:
                if (etCode.getText().toString().equals("")) {
                    ToastUtil.showShort("验证码不能为空");
                }else if (etCode.getText().toString().equals("1111")) {
                    PhoneModActivity activity= (PhoneModActivity) context;
                    activity.toNewPhone();
                } else {
                    ToastUtil.showShort("验证码：1111");
                }
                break;
        }
    }
}
