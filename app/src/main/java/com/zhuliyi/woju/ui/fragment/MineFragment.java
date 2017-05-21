package com.zhuliyi.woju.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.BaseFragment;
import com.zhuliyi.woju.ui.activity.AboutActivity;
import com.zhuliyi.woju.ui.activity.AccountActivity;
import com.zhuliyi.woju.ui.activity.BalanceActivity;
import com.zhuliyi.woju.ui.activity.BillActivity;
import com.zhuliyi.woju.ui.activity.DepositActivity;
import com.zhuliyi.woju.ui.activity.RentActivity;
import com.zhuliyi.woju.ui.activity.SettingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页-我的
 * Created by zhuliyi on 2017/1/9.
 */

public class MineFragment extends BaseFragment {
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
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

    @OnClick({R.id.ll_account,R.id.ll_balance,R.id.ll_rent,R.id.ll_deposit,R.id.bill, R.id.setting,R.id.about})
    public void onViewClicked(View view) {
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
            case R.id.about:
                startActivity(new Intent(context, AboutActivity.class));
                break;
        }
    }
}
