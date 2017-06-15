package com.zhuliyi.woju.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.data.preference.LoginPreference;
import com.zhuliyi.woju.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 我的二维码activity
 * Created by zhuliyi on 2017/6/7
 */

public class QRCodeDetailActivity extends SwipeBackActivity {
    @BindView(R.id.image_head)
    ImageView imageHead;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.image_qr)
    ImageView imageQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_detail);
        ButterKnife.bind(this);
        textTitle.setText("二维码名片");
        initView();
    }

    private void initView() {
        String iconUrl = LoginPreference.getIconUrl();
        Glide.with(context).load(iconUrl).placeholder(R.drawable.default_head).bitmapTransform(new CropCircleTransformation(context)).into(imageHead);
        String name=LoginPreference.getName();
        textName.setText(name);
        generateQRCode();
    }
    private void generateQRCode(){
        String id="zhuliyi";
        String woxinNo=LoginPreference.getWoxinNo();
        if(!woxinNo.isEmpty()){
            id=woxinNo;
        }
        QRCodeEncoder.encodeQRCode(id, 200,context.getResources().getColor(R.color.colorAccent), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                if(bitmap!=null) {
                    imageQr.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onEncodeQRCodeFailure() {
                ToastUtil.showLong("生成二维码失败");
            }
        });
    }
}
