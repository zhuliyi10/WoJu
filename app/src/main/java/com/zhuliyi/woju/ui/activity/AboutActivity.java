package com.zhuliyi.woju.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhuliyi.woju.BuildConfig;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.SettingsPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于activity
 * Created by zhuliyi on 2017/5/16.
 */

public class AboutActivity extends SwipeBackActivity {
    @BindView(R.id.text_version)
    TextView textVersion;
    @BindView(R.id.text_type_download)
    TextView textTypeDownload;
    @BindView(R.id.text_version_name)
    TextView textVersionName;

    private String[] downloadArr = new String[]{"仅WIFI网络", "从不"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        textTitle.setText("关于蜗信");
        textVersionName.setText("蜗信 "+BuildConfig.VERSION_NAME);
        textVersion.setText(BuildConfig.VERSION_NAME);
        initView();
    }

    private void initView() {
        setTypeDownload();
    }

    private void setTypeDownload() {
        textTypeDownload.setText(downloadArr[SettingsPreference.getTypeApkDownload() % downloadArr.length]);
    }

    @OnClick({R.id.ll_update_tips, R.id.ll_introduce, R.id.ll_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_update_tips:
                startActivity(new Intent(context, UpdateTipsActivity.class));
                break;
            case R.id.ll_introduce:
                startActivity(new Intent(context, IntroduceActivity.class));
                break;
            case R.id.ll_download:
                new MaterialDialog.Builder(context).title("自动下载蜗信安装包").items(downloadArr).itemsCallbackSingleChoice(SettingsPreference.getTypeApkDownload(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        SettingsPreference.saveTypeApkDownload(which);
                        setTypeDownload();
                        return true;
                    }
                }).show();
                break;
        }

    }
}
