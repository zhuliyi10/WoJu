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
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.utils.IntentUtil;
import com.zhuliyi.woju.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

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
    @BindView(R.id.text_gender)
    TextView textGender;
    @BindView(R.id.text_birthday)
    TextView textBirthday;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_auth)
    TextView textAuth;



    public static final int REQUEST_ID_AUTH=1;
    private Uri mCropImageUri;
    private String[] selectArr = new String[]{"拍照", "相册"};
    private String[] genderArr = new String[]{"男", "女"};

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
            Glide.with(context).load(iconUrl).placeholder(R.drawable.default_head).bitmapTransform(new CropCircleTransformation(context)).into(imageHead);
        }
        setWoxinNoView();
        setNickView();
        setUserSignatureView();
        setGenderView();
        setBirthdayView();
        setIDAuthView();
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

    private void setUserSignatureView() {
        String userSignature = LoginPreference.getUserSignature();
        if (!userSignature.isEmpty()) {
            textSignature.setText(userSignature);
        }
    }

    private void setGenderView() {
        String gender = LoginPreference.getGender();
        if (!gender.isEmpty()) {
            textGender.setText(gender);
        }
    }

    private void setBirthdayView() {
        String birthday = LoginPreference.getBirthday();
        if (!birthday.isEmpty()) {
            textBirthday.setText(birthday);
        }
    }

    private void setIDAuthView() {
        String trueName = LoginPreference.getTrueName();
        if (trueName.isEmpty()) {
            textAuth.setText("未认证");
        }else {
            textAuth.setText("已认证");
            textName.setText(trueName);
        }
    }

    @OnClick({R.id.ll_head, R.id.ll_woxin_no, R.id.ll_nick, R.id.ll_signature, R.id.ll_gender, R.id.ll_birthday, R.id.ll_qr_code, R.id.ll_id_auth,R.id.ll_contract})
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
                new MaterialDialog.Builder(context).title("设置个性签名").inputRange(1, 30).input("", LoginPreference.getUserSignature(), false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        LoginPreference.saveUserSignature(input.toString());
                        setUserSignatureView();
                    }
                }).show();
                break;
            case R.id.ll_gender:
                String gender = LoginPreference.getGender();
                int pos = 0;
                if (!gender.isEmpty()) {
                    if (gender.equals("女")) {
                        pos = 1;
                    }
                }
                new MaterialDialog.Builder(context).title("设置性别").items(genderArr).itemsCallbackSingleChoice(pos, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        LoginPreference.saveGender(text.toString());
                        setGenderView();
                        return false;
                    }
                }).positiveText("确定").show();
                break;
            case R.id.ll_birthday:
                String birthday = LoginPreference.getBirthday();
                final int year, mon, day;
                Calendar selectedDate = Calendar.getInstance();
                if (!birthday.isEmpty()) {
                    String[] ymd = birthday.split("-");
                    year = Integer.parseInt(ymd[0]);
                    mon = Integer.parseInt(ymd[1]);
                    day = Integer.parseInt(ymd[2]);
                    selectedDate.set(year, mon - 1, day);
                }

                Calendar startDate = Calendar.getInstance();
                startDate.set(1990, 0, 1);
                Calendar endDate = Calendar.getInstance();
                TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        LoginPreference.saveBirthday(format.format(date));
                        setBirthdayView();
                    }
                }).setType(new boolean[]{true, true, true, false, false, false}).setDate(selectedDate).setRangDate(startDate, endDate).build();
                pvTime.show();
                break;
            case R.id.ll_qr_code:
                startActivity(new Intent(context, QRCodeDetailActivity.class));
                break;
            case R.id.ll_id_auth:
                if(LoginPreference.getTrueName().isEmpty()){
                    startActivityForResult(new Intent(context,IDAuthActivity.class),REQUEST_ID_AUTH);
                }else {
                    startActivity(new Intent(context,IDInfoActivity.class));
                }
                break;
            case R.id.ll_contract:
                startActivity(new Intent(context,ContractActivity.class));
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


        if(requestCode==REQUEST_ID_AUTH&&resultCode==RESULT_OK){
            setIDAuthView();
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
