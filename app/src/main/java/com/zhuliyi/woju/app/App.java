package com.zhuliyi.woju.app;


import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhuliyi.woju.BuildConfig;

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
        initHotfix();
        initUmengSocial();
        initLeakcanary();
    }

    private void initHotfix(){
        SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
//                .setAesKey(null)
//                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Log.d("initialize", "mode="+mode+";code="+code+";info="+info+";handlePatchVersion="+handlePatchVersion);
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            new MaterialDialog.Builder(instance).title("重启提示").content("更新已完成，需要重新启动app才能生效").canceledOnTouchOutside(true).positiveText("确定重启").onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                    SophixManager.getInstance().killProcessSafely();
                                    reLaunchApp();
                                }
                            }).show();

                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                             SophixManager.getInstance().cleanPatches();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
    private void reLaunchApp(){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
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