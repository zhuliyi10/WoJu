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
    private static final String ACOUNT="account";//账号
    private static final String PWD="pwd";//加密后的密码
    private static final String LOGIN_STATE="login_state";//登陆状态
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
    /**
     * 点击用户保存状态
     * @param account
     * @param pwd 加密后的密码
     */
    public static void saveAccountAndPwd(String account, String pwd) {
        SharedPreferences preferences = getLoginSharePreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ACOUNT, account);
        editor.putString(PWD,pwd);
        //提交当前数据
        editor.commit();
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
     * 获取用户名
     */
    public static String getAcount(){
        SharedPreferences preferences = getLoginSharePreferences();
        return preferences.getString(ACOUNT,"");
    }

    /**
     * 获取密码
     */
    public static String getPwd(){
        SharedPreferences preferences = getLoginSharePreferences();
        return preferences.getString(PWD,"");
    }
    /**
     * 获取用户登陆状态
     */
    public static boolean getLoginState(){
        SharedPreferences preferences = getLoginSharePreferences();
        return preferences.getBoolean(LOGIN_STATE,false);
    }




}
