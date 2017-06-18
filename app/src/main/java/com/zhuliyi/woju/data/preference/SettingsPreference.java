package com.zhuliyi.woju.data.preference;

import android.app.Activity;
import android.content.SharedPreferences;

import com.zhuliyi.woju.app.App;

/**
 * Created by zhuliyi on 2017/6/18.
 */

public class SettingsPreference {
    private static final String SETTINGS="settings";//设置
    private static final String TYPE_APK_DOWNLOAD="type_apk_download";
    private  static SharedPreferences settingsPreferences;
    private static  SharedPreferences getSettingsPreferences(){
        if (settingsPreferences == null) {
            synchronized (SharedPreferences.class) {
                if (settingsPreferences == null) {
                    settingsPreferences = App.getInstance().getApplicationContext().getSharedPreferences(SETTINGS,
                            Activity.MODE_PRIVATE);
                }
            }
        }
        return settingsPreferences;
    }
    public static void saveTypeApkDownload(int type){
        saveInteger(TYPE_APK_DOWNLOAD,type);
    }
    public static int getTypeApkDownload(){
        return getInteger(TYPE_APK_DOWNLOAD);
    }

    public static void saveString(String key,String value){
        SharedPreferences preferences = getSettingsPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getString(String key){
        SharedPreferences preferences = getSettingsPreferences();
        return preferences.getString(key,"");
    }
    public static void saveInteger(String key,int value){
        SharedPreferences preferences = getSettingsPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public static int getInteger(String key){
        SharedPreferences preferences = getSettingsPreferences();
        return preferences.getInt(key,0);
    }
}
