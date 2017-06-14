package com.zhuliyi.woju.data.preference;

import android.app.Activity;
import android.content.SharedPreferences;

import com.zhuliyi.woju.app.App;

/**
 * 用于保存登陆状态
 * Created by zhuliyi on 2016/6/6.
 */
public class LoginPreference {
    private static final String USER_INFO="user_info";//用户信息表
    private static final String ACCOUNT="account";//账号，登陆时候和密码用的，由客户端决定，可以是phone,woxin_no,wechat_id,qq_id,sina_id
    private static final String PWD="pwd";//加密后的密码
    private static final String USER_ID="user_id";//用户id,注册后由后台生成，数字唯一
    private static final String WOXIN_NO="woxin_no";//蜗信号，用户设置，英文唯一
    private static final String PHONE="phone";//手机号
    private static final String NAME="name";//用户昵称
    private static final String GENDER="gender";//用户性别
    private static final String ICON_URL="icon_url";//用户头像
    private static final String USER_SIGNATURE="user_signature";//用户签名
    private static final String BIRTHDAY="birthday";//生日 1992-3-20
    private static final String TRUE_NAME="true_name";//真实名字
    private static final String ID_NO="id_no";//身份证号
    private static final String LOGIN_STATE="login_state";//登陆状态
    private static final String LOGIN_TYPE="login_type";//登陆类型
    public static final int LOGIN_TYPE_PHONE=1;//手机号
    public static final int LOGIN_TYPE_WOXINNO=2;//蜗信号
    public static final int LOGIN_TYPE_WECHAT=3;//微信
    public static final int LOGIN_TYPE_QQ=4;//qq
    public static final int LOGIN_TYPE_SINA=5;//新浪微博
    private  static  SharedPreferences loginPreferences;
    private static  SharedPreferences getLoginSharePreferences(){
        if (loginPreferences == null) {
            synchronized (SharedPreferences.class) {
                if (loginPreferences == null) {
                    loginPreferences = App.getInstance().getApplicationContext().getSharedPreferences(USER_INFO,
                            Activity.MODE_PRIVATE);
                }
            }
        }
        return loginPreferences;
    }

    public static void saveLoginType(int loginType){
        saveInteger(LOGIN_TYPE,loginType);
    }

    public static void saveAccount(String account){
        saveString(ACCOUNT,account);
    }
    public static void savePhone(String phone){
        saveString(PHONE,phone);
    }
    public static void saveName(String name){
        saveString(NAME,name);
    }
    public static void saveWoxinNo(String woxinNo){
        saveString(WOXIN_NO,woxinNo);
    }
    public static void saveUserSignature(String userSignature){
        saveString(USER_SIGNATURE,userSignature);
    }
    public static void saveBirthday(String birthday){
        saveString(BIRTHDAY,birthday);
    }
    public static void saveGender(String gender){
        saveString(GENDER,gender);
    }
    public static void saveIconUrl(String iconUrl){
        saveString(ICON_URL,iconUrl);
    }
    public static void saveTrueName(String trueName){
        saveString(TRUE_NAME,trueName);
    }
    public static void saveIDNo(String IDNo){
        saveString(ID_NO,IDNo);
    }
    /**
     * @param pwd 加密后的密码
     */
    public static void savePwd(String pwd){
        saveString(PWD,pwd);
    }
    public static int getLoginType(){
        return getInteger(LOGIN_TYPE);
    }
    public static String getAccount(){
        return getString(ACCOUNT);
    }
    public static String getPhone(){
        return getString(PHONE);
    }
    public static String getName(){
        return getString(NAME);
    }
    public static String getWoxinNo(){
        return getString(WOXIN_NO);
    }
    public static String getUserSignature(){
        return getString(USER_SIGNATURE);
    }
    public static String getBirthday(){
        return getString(BIRTHDAY);
    }
    public static String getGender(){
        return getString(GENDER);
    }
    public static String getIconUrl(){
        return getString(ICON_URL);
    }
    public static String getTrueName(){
        return getString(TRUE_NAME);
    }
    public static String getIdNo(){
        return getString(ID_NO);
    }
    public static String getPwd(){
        return getString(PWD);
    }


    /**
     * 点击用户保存状态
     * @param islogn 是否登陆
     */
    public static void saveLoginState(boolean islogn) {
        SharedPreferences preferences = getLoginSharePreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LOGIN_STATE, islogn);
        editor.commit();
    }
    /**
     * 获取用户登陆状态
     */
    public static boolean getLoginState(){
        SharedPreferences preferences = getLoginSharePreferences();
        return preferences.getBoolean(LOGIN_STATE,false);
    }



    public static void saveString(String key,String value){
        SharedPreferences preferences = getLoginSharePreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getString(String key){
        SharedPreferences preferences = getLoginSharePreferences();
        return preferences.getString(key,"");
    }

    public static void saveInteger(String key,int value){
        SharedPreferences preferences = getLoginSharePreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public static int getInteger(String key){
        SharedPreferences preferences = getLoginSharePreferences();
        return preferences.getInt(key,0);
    }
}
