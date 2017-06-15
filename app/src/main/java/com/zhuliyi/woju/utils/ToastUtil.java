package com.zhuliyi.woju.utils;

import android.content.Context;
import android.widget.Toast;

import com.zhuliyi.woju.app.App;

/**
 * Created by zhuliyi on 2015/10/16.
 */
public class ToastUtil {
    public static Toast mToast=null;
    public static void showLong(String text){
        if(mToast==null){
            mToast= Toast.makeText(App.getInstance().getApplicationContext(), text, Toast.LENGTH_LONG);
        }else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
    public static void showShort(String text){
        if(mToast==null){
            mToast= Toast.makeText(App.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    public static void showToast(String text,int duration){
        if(mToast==null){
            mToast= Toast.makeText(App.getInstance().getApplicationContext(), text, duration);
        }else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void showToast(int resId,int duration){
        Context context=App.getInstance().getApplicationContext();
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
