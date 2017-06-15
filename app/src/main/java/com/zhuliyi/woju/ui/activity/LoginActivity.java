package com.zhuliyi.woju.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.utils.ActivityManagerUtils;
import com.zhuliyi.woju.utils.ToastUtil;
import com.zhuliyi.woju.widget.editText.EditTextWithDel;

import java.util.Map;

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
        toolbar.inflateMenu(R.menu.login_verification);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sms:
                        startActivity(new Intent(context, SmsActivity.class));
                        break;
                }
                return true;
            }
        });
    }

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

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            int type = 0;
            if (platform == SHARE_MEDIA.WEIXIN) {
                type = LoginPreference.LOGIN_TYPE_WECHAT;
            } else if (platform == SHARE_MEDIA.QQ) {
                type = LoginPreference.LOGIN_TYPE_QQ;
            } else if (platform == SHARE_MEDIA.SINA) {
                type = LoginPreference.LOGIN_TYPE_SINA;
            }
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
            if (uid.isEmpty()) {
                ToastUtil.showShort( "登陆失败");
            } else {
                //传给接口uid,type,name,gender,iconUrl，接口判断是否注册，
                // 如果uid还没关联userId,则注册用户，并且name,gender,iconUrl写入，并返回用户的基本信息
                // 如果uid已经关联userId,则返回用户的基本信息
                LoginPreference.saveLoginState(true);
                LoginPreference.saveLoginType(type);
                if(LoginPreference.getName().isEmpty()) {
                    LoginPreference.saveName(name);
                }
                if(LoginPreference.getGender().isEmpty()) {
                    LoginPreference.saveGender(gender);
                }
                if(LoginPreference.getIconUrl().isEmpty()) {
                    LoginPreference.saveIconUrl(iconUrl);
                }
                if (type == LoginPreference.LOGIN_TYPE_WECHAT) {
                    LoginPreference.saveWechatId(uid);
                } else if (type == LoginPreference.LOGIN_TYPE_QQ) {
                    LoginPreference.saveQQId(uid);
                } else if (type == LoginPreference.LOGIN_TYPE_SINA) {
                    LoginPreference.saveSinaId(uid);
                }
                ActivityManagerUtils.getInstance().removeActivityExceptMain();
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.showShort("错误" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.showShort("取消授权");
        }
    };

    @OnClick({R.id.img_eye, R.id.btn_login, R.id.text_forget, R.id.login_whchat, R.id.login_qq, R.id.login_weibo})
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
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                if (account.isEmpty()) {
                    ToastUtil.showShort("手机/用户名不能为空");
                } else if (pwd.isEmpty()) {
                    ToastUtil.showShort("密码不能为空");
                } else {
                    requestLogin(account, pwd);
                }
                break;
            case R.id.text_forget:
                Intent intent = new Intent(context, PwdForgetActivity.class);
                intent.putExtra("type", PwdForgetActivity.TYPE_FORGET);
                startActivity(intent);
                break;
            case R.id.login_whchat:
                authLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.login_qq:
                authLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.login_weibo:
                authLogin(SHARE_MEDIA.SINA);
                break;

        }
    }

    private void authLogin(SHARE_MEDIA e) {
        UMShareAPI.get(this).getPlatformInfo(this, e, authListener);
    }

    private void requestLogin(String account, String pwd) {
        if ((account.equals(LoginPreference.getPhone()) || account.equals(LoginPreference.getWoxinNo()))&&pwd.equals(LoginPreference.getPwd())) {
            ToastUtil.showShort("登陆成功");
            if (account.equals(LoginPreference.getPhone())) {
                LoginPreference.saveLoginType(LoginPreference.LOGIN_TYPE_PHONE);
            } else {
                LoginPreference.saveLoginType(LoginPreference.LOGIN_TYPE_WOXINNO);
            }
            LoginPreference.saveLoginState(true);
            finish();
        } else {
            ToastUtil.showShort("账号或密码错误");
        }
    }
}
