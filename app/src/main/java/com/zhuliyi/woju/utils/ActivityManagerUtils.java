package com.zhuliyi.woju.utils;

import android.app.Activity;

import com.zhuliyi.woju.ui.activity.MainActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Activity的管理类
 * Created by zhuliyi on 2016/3/29.
 *
 */
public class ActivityManagerUtils {
    /**
     * 转载Activity的容器
     */
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ActivityManagerUtils instance = new ActivityManagerUtils();

    /**
     * 将构造函数私有化
     */
    private ActivityManagerUtils(){}

    /**
     * 获取ActivityManagerUtils的实例，保证只有一个ExitAppUtils实例存在
     * @return
     */
    public static ActivityManagerUtils getInstance(){
        return instance;
    }

    public Activity getPreActivity(){
        if(activityList!=null&&activityList.size()>=2){
            return activityList.get(activityList.size()-2);
        }
        return null;
    }

    /**
     * 添加Activity实例到mActivityList中，在onCreate()中调用
     * @param activity
     */
    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    /**
     * 从容器中删除多余的Activity实例，在onDestroy()中调用
     * @param activity
     */
    public void removeActivity(Activity activity){
        if(activityList.contains(activity)){
            activityList.remove(activity);
        }

    }


    /**
     * 退出程序的方法
     */
    public void exit(){
//        for (Activity activity:activityList) {//这个操作有误，因为activity在栈中的左右顺序是先进后出，这个方法先把栈底的元素给finish掉，不合乎常理，程序会出错
//            activity.finish();
//        }
        for(int i=activityList.size()-1;i>=0;i--){
            Activity activity=activityList.get(i);
            activity.finish();
        }

        System.exit(0);
    }
    public void removeActivityExceptMain(){
        for(int i=activityList.size()-1;i>=0;i--){
            Activity activity=activityList.get(i);
            if(!(activity instanceof MainActivity)) {
                activity.finish();
            }
        }
    }
}