package com.zhulilyi.woju.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhulilyi.woju.R;
import com.zhulilyi.woju.base.BaseAdapter;
import com.zhulilyi.woju.parser.CircleParser;
import com.zhulilyi.woju.utils.ScreenUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 图片
 * Created by zhuliyi on 2017/5/2.
 */

public class CircleImageGridAdapter extends BaseAdapter<CircleParser.CirclePicParser> {
    private Context context;
    public CircleImageGridAdapter(Context context, List<CircleParser.CirclePicParser> datas) {
        super(context, R.layout.item_circle_image_grid, datas);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder holder, CircleParser.CirclePicParser parser, int position){
        ImageView imageView= holder.getView(R.id.image);
        switch (getDatas().size()){
            case 1:
                Glide.with(context).load(parser.imgUrl).into(imageView);
                break;
            case 2:
            case 4:
                int width=(ScreenUtil.getScreenWidth(context)-ScreenUtil.dip2px(context,6))/2;
                ViewGroup.LayoutParams params=imageView.getLayoutParams();
                params.width=params.height=width;
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(context).load(parser.imgUrl).into(imageView);
                break;
            default:
                int width2=(ScreenUtil.getScreenWidth(context)-ScreenUtil.dip2px(context,6)*2)/2;
                ViewGroup.LayoutParams params2=imageView.getLayoutParams();
                params2.width=params2.height=width2;
                imageView.setLayoutParams(params2);
                Glide.with(context).load(parser.imgUrl).centerCrop().into(imageView);
                break;
        }


    }
}
