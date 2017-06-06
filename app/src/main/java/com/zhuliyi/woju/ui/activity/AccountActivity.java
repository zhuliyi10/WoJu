package com.zhuliyi.woju.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.utils.IntentUtil;
import com.zhuliyi.woju.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账号信息activity
 * Created by zhuliyi on 2017/5/16.
 */

public class AccountActivity extends SwipeBackActivity {
    @BindView(R.id.image_head)
    RoundedImageView imageHead;
    @BindView(R.id.text_woxin_no)
    TextView textWoxinNo;
    @BindView(R.id.image_woxin_arrow)
    ImageView imageWoxinArrow;
    @BindView(R.id.ll_woxin_no)
    LinearLayout llWoxinNo;
    @BindView(R.id.text_nick)
    TextView textNick;
    @BindView(R.id.text_signature)
    TextView textSignature;
    private Uri mCropImageUri;
    private String[] selectArr = new String[]{"拍照", "相册"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        textTitle.setText("个人信息");
        initView();
    }

    private void initView() {
        String iconUrl = LoginPreference.getIconUrl();
        if (!iconUrl.isEmpty()) {
            Glide.with(context).load(iconUrl).into(imageHead);
        }
        setWoxinNoView();
        setNickView();
        setUserSignature();
    }

    private void setWoxinNoView() {
        String woxinNo = LoginPreference.getWoxinNo();
        if (!woxinNo.isEmpty()) {
            textWoxinNo.setText(woxinNo);
            textWoxinNo.setTextColor(context.getResources().getColor(R.color.text_low));
            imageWoxinArrow.setVisibility(View.GONE);
            llWoxinNo.setClickable(false);
        } else {
            textWoxinNo.setText("未设置");
            textWoxinNo.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }

    private void setNickView() {
        String nickName = LoginPreference.getName();
        if (!nickName.isEmpty()) {
            textNick.setText(nickName);
        }
    }
    private void setUserSignature() {
        String userSignature = LoginPreference.getUserSignature();
        if (!userSignature.isEmpty()) {
            textSignature.setText(userSignature);
        }
    }
    @OnClick({R.id.ll_head, R.id.ll_woxin_no, R.id.ll_nick,R.id.ll_signature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                new MaterialDialog.Builder(context).title("照片选择").items(selectArr).titleGravity(GravityEnum.CENTER).itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Intent intent = null;
                        if (position == 0) {
                            intent = IntentUtil.getCaptureIntent(context);
                        } else if (position == 1) {
                            intent = IntentUtil.getGalleyIntent();
                        }
                        if (intent != null) {
                            startActivityForResult(intent, CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
                        }
                    }
                }).show();
                break;
            case R.id.ll_woxin_no:
                new MaterialDialog.Builder(context).title("设置蜗信号").content("注：设置成功后不能修改").inputType(InputType.TYPE_NUMBER_FLAG_DECIMAL).inputRange(4, 16).input("请输入4-16位的非特殊字符", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        ToastUtil.showLong(context, "设置成功");
                        LoginPreference.saveWoxinNo(input.toString());
                        setWoxinNoView();
                    }
                }).show();
                break;
            case R.id.ll_nick:
                new MaterialDialog.Builder(context).title("设置昵称").content("设置一个比较容易记住的名字").inputRange(1, 20).input("", LoginPreference.getName(), false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        LoginPreference.saveName(input.toString());
                        setNickView();
                    }
                }).show();
                break;
            case R.id.ll_signature:
                new MaterialDialog.Builder(context).title("设置个性签名").inputRange(1,30).input("", LoginPreference.getUserSignature(), false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        LoginPreference.saveUserSignature(input.toString());
                        setUserSignature();
                    }
                }).show();
                break;
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                String path = result.getUri().getPath();
                LoginPreference.saveIconUrl(path);
                Glide.with(context).load(path).into(imageHead);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setCropShape(CropImageView.CropShape.OVAL).setAspectRatio(1, 1).setFixAspectRatio(true).setOutputCompressQuality(50).start(this);
    }
}
