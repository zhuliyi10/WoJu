package com.zhuliyi.woju.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.utils.ToastUtil;

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
    @BindView(R.id.text_phone_tip)
    TextView textPhoneTip;
    @BindView(R.id.text_wechat_tip)
    TextView textWechatTip;
    @BindView(R.id.text_qq_tip)
    TextView textQqTip;
    @BindView(R.id.text_sina_tip)
    TextView textSinaTip;

    private int textLow;
    public static final int REQUEST_PHONE_MOD=1;//修改手机号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        textTitle.setText("设置");
        initView();
    }

    private void initView() {
        textLow = context.getResources().getColor(R.color.text_low);
        if (!LoginPreference.getPwd().isEmpty()) {
            textPwdTip.setVisibility(View.GONE);
        }
        setPhoneView();
        setWechatView();
        setQQView();
        setSinaView();
    }

    private void setPhoneView() {
        String phone = LoginPreference.getPhone();
        if (!phone.isEmpty()) {
            textPhoneTip.setText(phone);
            textPhoneTip.setTextColor(textLow);
        }
    }

    private void setWechatView() {
        String wechatId = LoginPreference.getWechatId();
        if (!wechatId.isEmpty()) {
            textWechatTip.setVisibility(View.GONE);
        }else {
            textWechatTip.setVisibility(View.VISIBLE);
        }
    }

    private void setQQView() {
        String qqId = LoginPreference.getQqId();
        if (!qqId.isEmpty()) {
            textQqTip.setVisibility(View.GONE);
        }else {
            textQqTip.setVisibility(View.VISIBLE);
        }
    }

    private void setSinaView() {
        String sinaId = LoginPreference.getSinaId();
        if (!sinaId.isEmpty()) {
            textSinaTip.setVisibility(View.GONE);
        }else {
            textSinaTip.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btn_logout, R.id.ll_pwd, R.id.ll_phone,R.id.ll_wechat, R.id.ll_qq, R.id.ll_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_pwd:
                Intent intent = new Intent(context, PwdForgetActivity.class);
                intent.putExtra("type", PwdForgetActivity.TYPE_MOD);
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
            case R.id.ll_phone:
                startActivityForResult(new Intent(context,PhoneModActivity.class),REQUEST_PHONE_MOD);
                break;
            case R.id.ll_wechat:
                if (LoginPreference.getWechatId().isEmpty()) {
                    authBind(SHARE_MEDIA.WEIXIN);
                } else {
                    authUnbind(SHARE_MEDIA.WEIXIN);
                }
                break;
            case R.id.ll_qq:
                if (LoginPreference.getQqId().isEmpty()) {
                    authBind(SHARE_MEDIA.QQ);
                } else {
                    authUnbind(SHARE_MEDIA.QQ);
                }
                break;
            case R.id.ll_sina:
                if (LoginPreference.getSinaId().isEmpty()) {
                    authBind(SHARE_MEDIA.SINA);
                } else {
                    authUnbind(SHARE_MEDIA.SINA);
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
        if(requestCode==REQUEST_PHONE_MOD&&resultCode==RESULT_OK){
            textPhoneTip.setText(LoginPreference.getPhone());
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    UMAuthListener bindListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {
            ToastUtil.showShort("绑定成功");
            String uid = "";
            String name = "";
            String gender = "";
            String iconUrl = "";
            for (String key : data.keySet()) {
                if (key.equals("uid")) {
                    uid = data.get(key);
                } else if (key.equals("name")) {
                    name = data.get(key);
                } else if (key.equals("gender")) {
                    gender = data.get(key);
                } else if (key.equals("iconurl")) {
                    iconUrl = data.get(key);
                }
            }
            if (LoginPreference.getName().isEmpty()) {
                LoginPreference.saveName(name);
            }
            if (LoginPreference.getGender().isEmpty()) {
                LoginPreference.saveGender(gender);
            }
            if (LoginPreference.getIconUrl().isEmpty()) {
                LoginPreference.saveIconUrl(iconUrl);
            }
            if (share_media == SHARE_MEDIA.WEIXIN) {
                LoginPreference.saveWechatId(uid);
                setWechatView();
            } else if (share_media == SHARE_MEDIA.QQ) {
                LoginPreference.saveQQId(uid);
                setQQView();
            } else if (share_media == SHARE_MEDIA.SINA) {
                LoginPreference.saveSinaId(uid);
                setSinaView();
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            ToastUtil.showShort("绑定失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };
    UMAuthListener unBindListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            ToastUtil.showShort("解绑成功");
            if (share_media == SHARE_MEDIA.WEIXIN) {
                LoginPreference.saveWechatId("");
                setWechatView();
            } else if (share_media == SHARE_MEDIA.QQ) {
                LoginPreference.saveQQId("");
                setQQView();
            } else if (share_media == SHARE_MEDIA.SINA) {
                LoginPreference.saveSinaId("");
                setSinaView();
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

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

    private void authBind(SHARE_MEDIA e) {
        UMShareAPI.get(this).getPlatformInfo(this, e, bindListener);
    }

    private void authUnbind(final SHARE_MEDIA e) {
        StringBuilder content = new StringBuilder("确定解除账号与");
        if (e == SHARE_MEDIA.WEIXIN) {
            content.append("微信");
        } else if (e == SHARE_MEDIA.QQ) {
            content.append("QQ");
        } else if (e == SHARE_MEDIA.SINA) {
            content.append("微博");
        }
        content.append("的关联吗？");
        new MaterialDialog.Builder(context).title("解除绑定").content(content).positiveText("解除绑定").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                UMShareAPI.get(context).deleteOauth(activity, e, unBindListener);
            }
        }).show();

    }
}
