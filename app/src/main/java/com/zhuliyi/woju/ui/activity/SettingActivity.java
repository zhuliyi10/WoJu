package com.zhuliyi.woju.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.LoginPreference;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 样本activity
 * Created by zhuliyi on 2017/5/16.
 */

public class SettingActivity extends SwipeBackActivity {
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.text_pwd_tip)
    TextView textPwdTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        textTitle.setText("设置");
        initView();
    }

    private void initView() {
        if(!LoginPreference.getPwd().isEmpty()){
            textPwdTip.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_logout,R.id.ll_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_pwd:
                Intent intent=new Intent(context,PwdForgetActivity.class);
                intent.putExtra("type",PwdForgetActivity.TYPE_MOD);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                int loginType = LoginPreference.getLoginType();
                SHARE_MEDIA platform = null;
                if (loginType == LoginPreference.LOGIN_TYPE_WECHAT) {
                    platform = SHARE_MEDIA.WEIXIN;
                } else if (loginType == LoginPreference.LOGIN_TYPE_QQ) {
                    platform = SHARE_MEDIA.QQ;
                } else if (loginType == LoginPreference.LOGIN_TYPE_SINA) {
                    platform = SHARE_MEDIA.SINA;
                }
                LoginPreference.saveLoginState(false);
                if (platform != null) {
                    UMShareAPI.get(context).deleteOauth(activity, platform, authListener);
                } else {
                    finish();
                }
                break;
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            btnLogout.setClickable(false);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            finish();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}
