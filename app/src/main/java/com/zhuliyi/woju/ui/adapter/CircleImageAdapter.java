package com.zhuliyi.woju.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.QuickAdapter;
import com.zhuliyi.woju.parser.CircleParser;
import com.zhuliyi.woju.widget.decoration.DividerGridItemDecoration;

import java.util.List;

/**
 * 圈子列表
 * Created by zhuliyi on 2017/4/18.
 */

public class CircleImageAdapter extends QuickAdapter<CircleParser.CircleListParser> {
    public CircleImageAdapter(List<CircleParser.CircleListParser> datas) {
        super(R.layout.item_circle_image, datas);
    }

    private void setImageGrid(RecyclerView rcv,List<CircleParser.CirclePicParser> datas){
        int colNum=1;
        switch (datas.size()){
            case 1:
                colNum=1;
                break;
            case 2:
            case 4:
                colNum=2;
                break;
            default:
                colNum=3;
                break;

        }
        GridLayoutManager gl=new GridLayoutManager(mContext,colNum);
        gl.setOrientation(GridLayoutManager.VERTICAL);
        rcv.setLayoutManager(gl);
        rcv.setHasFixedSize(true);
        rcv.addItemDecoration(new DividerGridItemDecoration(mContext));
        rcv.setAdapter(new CircleImageGridAdapter(datas));
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleParser.CircleListParser item) {
        holder.setText(R.id.text_title,item.title);
        holder.setText(R.id.text_time,item.time);
        holder.setText(R.id.text_content,item.content);
        RecyclerView rcvImage=holder.getView(R.id.rcv_image);
        if(item.imgList!=null&&item.imgList.size()>0){
            rcvImage.setVisibility(View.VISIBLE);
            setImageGrid(rcvImage,item.imgList);
        }else {
            rcvImage.setVisibility(View.GONE);
        }
    }
}
