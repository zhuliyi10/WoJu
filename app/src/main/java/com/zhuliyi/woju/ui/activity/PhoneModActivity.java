package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.common.FragmentPagerAdapter;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.ui.fragment.PhoneNewFragment;
import com.zhuliyi.woju.ui.fragment.PhoneOldFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更换手机activity
 * Created by zhuliyi on 2017/5/16.
 */

public class PhoneModActivity extends SwipeBackActivity {
    @BindView(R.id.image_two)
    ImageView imageTwo;
    @BindView(R.id.text_two)
    TextView textTwo;
    @BindView(R.id.image_indicator_two)
    ImageView imageIndicatorTwo;
    @BindView(R.id.image_three)
    ImageView imageThree;
    @BindView(R.id.text_three)
    TextView textThree;
    @BindView(R.id.ll_indicator)
    LinearLayout llIndicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private int color;
    private ArrayList<Fragment> fragmentList;//fragment列表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_mod);
        ButterKnife.bind(this);
        color=context.getResources().getColor(R.color.colorPrimary);
        String phone = LoginPreference.getPhone();
        fragmentList=new ArrayList<>();
        if (phone.isEmpty()) {
            textTitle.setText("设置手机");
            llIndicator.setVisibility(View.GONE);
            fragmentList.add(new PhoneNewFragment());
        } else {
            textTitle.setText("更换手机");
            fragmentList.add(new PhoneOldFragment());
            fragmentList.add(new PhoneNewFragment());
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList));

    }
    public void toNewPhone(){
        imageTwo.setImageResource(R.drawable.two_color);
        textTwo.setTextColor(color);
        imageIndicatorTwo.setImageResource(R.drawable.arrow_indicator_color);
        viewPager.setCurrentItem(1,true);
    }
    public void complete(String newPhone){
        imageThree.setImageResource(R.drawable.three_color);
        textThree.setTextColor(color);
        LoginPreference.savePhone(newPhone);
        new MaterialDialog.Builder(context).title("修改手机号码成功").content("新手机号码为："+newPhone).positiveText("完成").canceledOnTouchOutside(true).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                finish();
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_OK);
        super.onDestroy();
    }
}
