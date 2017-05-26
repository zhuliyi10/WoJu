package com.zhuliyi.woju.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.BaseFragment;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.ui.activity.AboutActivity;
import com.zhuliyi.woju.ui.activity.AccountActivity;
import com.zhuliyi.woju.ui.activity.BalanceActivity;
import com.zhuliyi.woju.ui.activity.BillActivity;
import com.zhuliyi.woju.ui.activity.DepositActivity;
import com.zhuliyi.woju.ui.activity.LoginActivity;
import com.zhuliyi.woju.ui.activity.RentActivity;
import com.zhuliyi.woju.ui.activity.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 首页-我的
 * Created by zhuliyi on 2017/1/9.
 */

public class MineFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.img_head)
    RoundedImageView imgHead;
    @BindView(R.id.text_nick)
    TextView textNick;
    @BindView(R.id.text_signature)
    TextView textSignature;
    @BindView(R.id.rotate_header_web_view_frame)
    PtrClassicFrameLayout ptrFrame;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        ptrFrame.setLastUpdateTimeRelateObject(this);
        ptrFrame.setResistance(1.7f);
        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrame.setDurationToClose(500);
        ptrFrame.setDurationToCloseHeader(1000);
        // default is false
        ptrFrame.setPullToRefresh(false);
        // default is true
        ptrFrame.setKeepHeaderWhenRefresh(true);
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrame.autoRefresh();
            }
        }, 100);
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 300);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        judgeLogin();
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
    }

    @OnClick({R.id.ll_account, R.id.ll_balance, R.id.ll_rent, R.id.ll_deposit, R.id.bill, R.id.setting})
    public void needLogin(View view) {
        if (!LoginPreference.getLoginState()) {
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        switch (view.getId()) {
            case R.id.ll_account:
                startActivity(new Intent(context, AccountActivity.class));
                break;
            case R.id.ll_balance:
                startActivity(new Intent(context, BalanceActivity.class));
                break;
            case R.id.ll_rent:
                startActivity(new Intent(context, RentActivity.class));
                break;
            case R.id.ll_deposit:
                startActivity(new Intent(context, DepositActivity.class));
                break;
            case R.id.bill:
                startActivity(new Intent(context, BillActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(context, SettingActivity.class));
                break;

        }
    }

    @OnClick({R.id.about})
    public void noNeedLogin(View view) {
        switch (view.getId()) {
            case R.id.about:
                startActivity(new Intent(context, AboutActivity.class));
                break;
        }
    }

    private void judgeLogin() {
        if (LoginPreference.getLoginState()) {
            switch (LoginPreference.getLoginType()) {
                case LoginPreference.LOGIN_TYPE_PHONE:
                case LoginPreference.LOGIN_TYPE_WOXINNO:
                    //通过type ,account和pwd请求登陆
                    break;
                case LoginPreference.LOGIN_TYPE_WECHAT:
                case LoginPreference.LOGIN_TYPE_QQ:
                case LoginPreference.LOGIN_TYPE_SINA:
                    //通过type 和account请求登陆
                    break;
            }
            String name = LoginPreference.getName();
            String iconUrl = LoginPreference.getIconUrl();
            if (!name.isEmpty()) {
                textNick.setText(name);
            } else {
                textNick.setText("");
            }
            if (!iconUrl.isEmpty()) {
                Glide.with(context).load(iconUrl).into(imgHead);
            }
            textSignature.setText(context.getResources().getString(R.string.login_no_sign));
        } else {
            imgHead.setImageResource(R.drawable.default_head);
            textNick.setText("立即登陆");
            textSignature.setText("登陆后可享受更多特权");
        }
    }
}
