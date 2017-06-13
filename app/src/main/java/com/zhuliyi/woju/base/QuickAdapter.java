package com.zhuliyi.woju.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Leory on 2017/6/13.
 */

public abstract class QuickAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder>{
    public QuickAdapter(@LayoutRes int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }
}
