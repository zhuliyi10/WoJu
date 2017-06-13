package com.zhuliyi.woju.app;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * apllicaction类定义
 * Created by zhuliyi on 2017/1/3.
 */
public class App extends Application {
    private static App instance;
    private static RefWatcher sRefWatcher;
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initUmengSocial();
        initLeakcanary();
    }

    /**
     * 初始化友盟第三方社会化设置
     */
    private void initUmengSocial() {
        UMShareAPI.get(this);
//        Config.DEBUG = true;
        //微信 appid appsecret
        PlatformConfig.setWeixin(Config.WEIXIN_APP_ID, Config.WEIXIN_APP_SECRET);
        //qq和qq空间 appid appkey
        PlatformConfig.setQQZone(Config.QQ_APP_ID, Config.QQ_APP_KEY);
        //新浪微博 appid appsecret
        PlatformConfig.setSinaWeibo(Config.SINA_APP_KEY, Config.SINA_APP_SECRET, "http://www.dwelling.cn");
    }

    private void initLeakcanary() {
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        sRefWatcher=LeakCanary.install(this);
    }
    public static RefWatcher getRefWatcher() {
        return sRefWatcher;
    }
}