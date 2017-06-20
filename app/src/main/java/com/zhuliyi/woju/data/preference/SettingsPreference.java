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
    private static final String PRIVACY_ADD_VERIFY="privacy_add_verify";
    private static final String PRIVACY_PHONE_FIND="privacy_phone_find";
    private static final String PRIVACY_WOXIN_NO_FIND="privacy_woxin_no_find";
    private static final String PRIVACY_RECOMMEND_CONTACT="privacy_recommend_contact";
    private static final String PRIVACY_VIEW_CIRCLE_RANGE="privacy_view_circle_range";
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

    public static void savePrivacyAddVerify(boolean value){
        saveBoolean(PRIVACY_ADD_VERIFY,value);
    }
    public static boolean getPrivacyAddVerify(){
        return getBoolean(PRIVACY_ADD_VERIFY,true);
    }
    public static void savePrivacyPhoneFind(boolean value){
        saveBoolean(PRIVACY_PHONE_FIND,value);
    }
    public static boolean getPrivacyPhoneFind(){
        return getBoolean(PRIVACY_PHONE_FIND,true);
    }
    public static void savePrivacyWonxinFind(boolean value){
        saveBoolean(PRIVACY_WOXIN_NO_FIND,value);
    }
    public static boolean getPrivacyWonxinFind(){
        return getBoolean(PRIVACY_WOXIN_NO_FIND,true);
    }
    public static void savePrivacyRecommendContact(boolean value){
        saveBoolean(PRIVACY_RECOMMEND_CONTACT,value);
    }
    public static boolean getPrivacyRecommendContact(){
        return getBoolean(PRIVACY_RECOMMEND_CONTACT,true);
    }
    public static void savePrivacyViewCircleRange(int value){
        saveInteger(PRIVACY_VIEW_CIRCLE_RANGE,value);
    }
    public static int getPrivacyViewCircleRange(){
        return getInteger(PRIVACY_VIEW_CIRCLE_RANGE,0);
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
    public static int getInteger(String key,int dValue){
        SharedPreferences preferences = getSettingsPreferences();
        return preferences.getInt(key,dValue);
    }
    public static void saveBoolean(String key,boolean value){
        SharedPreferences preferences = getSettingsPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static boolean getBoolean(String key){
        SharedPreferences preferences = getSettingsPreferences();
        return preferences.getBoolean(key,false);
    }
    public static boolean getBoolean(String key,boolean dValue){
        SharedPreferences preferences = getSettingsPreferences();
        return preferences.getBoolean(key,dValue);
    }
}
