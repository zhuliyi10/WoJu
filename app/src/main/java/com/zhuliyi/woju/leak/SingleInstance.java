package com.zhuliyi.woju.leak;

import android.content.Context;

/**
 * 单例泄漏
 * Created by Leory on 2017/3/15.
 */

public class SingleInstance {
    private Context context;
    private static SingleInstance instance;
    private SingleInstance(Context context){
        this.context=context;
    }
    public static SingleInstance getInstance(Context contex){
        if(instance==null){
            synchronized (SingleInstance.class) {
                if(instance==null) {
                    instance = new SingleInstance(contex);
                }
            }
        }
        return instance;
    }
}
