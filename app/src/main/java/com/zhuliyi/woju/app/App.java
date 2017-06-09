package com.zhuliyi.woju.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.umeng.socialize.BuildConfig;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhuliyi.woju.ui.activity.MainActivity;
import com.zhuliyi.woju.utils.ToastUtil;

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
        initHotfit();
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
    private void initHotfit(){
        final Context context=instance.getApplicationContext();
        SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        Log.d(MainActivity.class.getSimpleName(), "mode="+mode+"  code="+code+"  info="+info+"  handlePatchVersion="+handlePatchVersion);
                        if(code==PatchStatus.CODE_REQ_NOUPDATE){
                            ToastUtil.showLong(context,"服务器没有可用的补丁");
                        }else if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            ToastUtil.showLong(context,"补丁加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            ToastUtil.showLong(context,"新补丁生效需要重启");
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            // SophixManager.getInstance().cleanPatches();
                            ToastUtil.showLong(context,"内部引擎异常, 推荐此时清空本地补丁,防止失败补丁重复加载");
                        } else if(code==PatchStatus.CODE_REQ_CLEARPATCH){//一键清除补丁
                            SophixManager.getInstance().cleanPatches();
                        }else {
                            ToastUtil.showLong(context,"其他错误");
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
        SophixManager.getInstance().queryAndLoadNewPatch();
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