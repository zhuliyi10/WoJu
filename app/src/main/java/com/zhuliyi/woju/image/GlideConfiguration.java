package com.zhuliyi.woju.image;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Glide配置文件
 * Created by zhuliyi on 2017/6/20.
 */

public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //自定义缓存目录，磁盘缓存给150M
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "image_cache", 150 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

}