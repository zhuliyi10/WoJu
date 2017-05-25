package com.zhuliyi.woju.app;


import android.app.Activity;
import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.HashSet;
import java.util.Set;

/**
 * apllicaction类定义
 * Created by zhuliyi on 2017/1/3.
 */
public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initUmengSocial();
    }

    /**
     * 初始化友盟第三方社会化设置
     */
    private void initUmengSocial(){
        UMShareAPI.get(this);
//        Config.DEBUG = true;
        //微信 appid appsecret
        PlatformConfig.setWeixin(Config.WEIXIN_APP_ID, Config.WEIXIN_APP_SECRET);
        //qq和qq空间 appid appkey
        PlatformConfig.setQQZone(Config.QQ_APP_ID,Config.QQ_APP_KEY);
        //新浪微博 appid appsecret
        PlatformConfig.setSinaWeibo(Config.SINA_APP_KEY,Config.SINA_APP_SECRET,"http://www.dwelling.cn");
    }
    public void registerActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void unregisterActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (act != null && !act.isFinishing())
                        act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}