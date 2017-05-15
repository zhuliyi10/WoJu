package com.zhuliyi.woju.base;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * 封闭baseAdapter
 * Created by zhuliyi on 2017/4/18.
 */

public abstract class BaseAdapter<T> extends CommonAdapter<T> {
    public BaseAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }
}
