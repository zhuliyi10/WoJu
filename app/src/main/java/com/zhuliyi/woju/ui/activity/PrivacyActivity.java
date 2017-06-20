package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.suke.widget.SwitchButton;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.SettingsPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 隐私activity
 * Created by zhuliyi on 2017/6/19
 */

public class PrivacyActivity extends SwipeBackActivity implements SwitchButton.OnCheckedChangeListener {
    @BindView(R.id.sb_add_verify)
    SwitchButton sbAddVerify;
    @BindView(R.id.sb_phone_find)
    SwitchButton sbPhoneFind;
    @BindView(R.id.sb_woxin_find)
    SwitchButton sbWoxinFind;
    @BindView(R.id.sb_recommend_contact)
    SwitchButton sbRecommendContact;
    @BindView(R.id.text_circle_range)
    TextView textCircleRange;

    private String[] circleRange = new String[]{"最近5条", "全部可见", "不可见"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.bind(this);
        textTitle.setText("隐私设置");
        initView();
    }

    private void initView() {
        setAddVerifyView();
        setPhoneFindView();
        setWoxinFindView();
        setRecommendContactView();
        setCircleRangeView();
        sbAddVerify.setOnCheckedChangeListener(this);
        sbPhoneFind.setOnCheckedChangeListener(this);
        sbWoxinFind.setOnCheckedChangeListener(this);
        sbRecommendContact.setOnCheckedChangeListener(this);
    }

    private void setAddVerifyView() {
        if (SettingsPreference.getPrivacyAddVerify()) {
            sbAddVerify.setChecked(true);
        } else {
            sbAddVerify.setChecked(false);
        }
    }

    private void setPhoneFindView() {
        if (SettingsPreference.getPrivacyPhoneFind()) {
            sbPhoneFind.setChecked(true);
        } else {
            sbPhoneFind.setChecked(false);
        }
    }

    private void setWoxinFindView() {
        if (SettingsPreference.getPrivacyWonxinFind()) {
            sbWoxinFind.setChecked(true);
        } else {
            sbWoxinFind.setChecked(false);
        }
    }

    private void setRecommendContactView() {
        if (SettingsPreference.getPrivacyRecommendContact()) {
            sbRecommendContact.setChecked(true);
        } else {
            sbRecommendContact.setChecked(false);
        }
    }

    private void setCircleRangeView() {
        textCircleRange.setText(circleRange[SettingsPreference.getPrivacyViewCircleRange()]);
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.sb_add_verify:
                SettingsPreference.savePrivacyAddVerify(isChecked);
                break;
            case R.id.sb_phone_find:
                SettingsPreference.savePrivacyPhoneFind(isChecked);
                break;
            case R.id.sb_woxin_find:
                SettingsPreference.savePrivacyWonxinFind(isChecked);
                break;
            case R.id.sb_recommend_contact:
                SettingsPreference.savePrivacyRecommendContact(isChecked);
                break;
        }
    }

    @OnClick(R.id.ll_circle_range)
    public void onViewClicked() {
        new MaterialDialog.Builder(context).title("允许陌生人查看蜗信圈的范围").items(circleRange).itemsCallbackSingleChoice(SettingsPreference.getPrivacyViewCircleRange(), new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                SettingsPreference.savePrivacyViewCircleRange(which);
                setCircleRangeView();
                return true;
            }
        }).show();
    }
}
