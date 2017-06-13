package com.zhuliyi.woju.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.QuickAdapter;
import com.zhuliyi.woju.parser.CircleParser;
import com.zhuliyi.woju.utils.ScreenUtil;

import java.util.List;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 图片
 * Created by zhuliyi on 2017/5/2.
 */

public class CircleImageGridAdapter extends QuickAdapter<CircleParser.CirclePicParser> {
    public CircleImageGridAdapter(List<CircleParser.CirclePicParser> datas) {
        super(R.layout.item_circle_image_grid, datas);
    }



    @Override
    protected void convert(BaseViewHolder holder, CircleParser.CirclePicParser item) {
        ImageView imageView= holder.getView(R.id.image);
        switch (mData.size()){
            case 1:
                Glide.with(mContext).load(item.imgUrl).into(imageView);
                break;
            case 2:
            case 4:
                int width=(ScreenUtil.getScreenWidth(mContext)-ScreenUtil.dip2px(mContext,6))/2;
                ViewGroup.LayoutParams params=imageView.getLayoutParams();
                params.width=params.height=width;
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mContext).load(item.imgUrl).into(imageView);
                break;
            default:
                int width2=(ScreenUtil.getScreenWidth(mContext)-ScreenUtil.dip2px(mContext,6)*2)/2;
                ViewGroup.LayoutParams params2=imageView.getLayoutParams();
                params2.width=params2.height=width2;
                imageView.setLayoutParams(params2);
                Glide.with(mContext).load(item.imgUrl).centerCrop().into(imageView);
                break;
        }
    }
}
