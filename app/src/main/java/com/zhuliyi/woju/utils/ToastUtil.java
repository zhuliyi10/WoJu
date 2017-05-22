package com.zhuliyi.woju.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhuliyi on 2015/10/16.
 */
public class ToastUtil {
    public static Toast mToast=null;
    public static void showToast(Context context,String text,int duration){
        if(mToast==null){
            mToast= Toast.makeText(context, text, duration);
        }else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
    public static void showLong(Context context,String text){
        if(mToast==null){
            mToast= Toast.makeText(context, text, Toast.LENGTH_LONG);
        }else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
    public static void showShort(Context context,String text){
        if(mToast==null){
            mToast= Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    public static void showToast(Context context,int resId,int duration){
        String text=context.getResources().getString(resId);
        if(text!=null) {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, duration);
            } else {
                mToast.setText(text);
                mToast.setDuration(duration);
            }
            mToast.show();
        }
    }
}
