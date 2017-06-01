package com.zhuliyi.woju.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.theartofdev.edmodo.cropper.CropImage;

/**
 * Created by zhuliyi on 2017/6/2.
 */

public class IntentUtil {

    /**
     * 从相册中选择
     * @return
     */
    public static Intent getGalleyIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        return intent;
    }

    /**
     * 拍照
     * @return
     */
    public static Intent getCaptureIntent(Context context) {
        Uri outputFileUri = CropImage.getCaptureImageOutputUri(context);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        return intent;
    }
}
