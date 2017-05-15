package com.zhuliyi.woju.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.BaseAdapter;
import com.zhuliyi.woju.parser.CircleParser;
import com.zhuliyi.woju.widget.decoration.DividerGridItemDecoration;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 圈子列表
 * Created by zhuliyi on 2017/4/18.
 */

public class CircleImageAdapter extends BaseAdapter<CircleParser.CircleListParser> {
    private Context context;
    public CircleImageAdapter(Context context, List<CircleParser.CircleListParser> datas) {
        super(context, R.layout.item_circle_image, datas);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder holder, CircleParser.CircleListParser parser, int position){
        holder.setText(R.id.text_title,parser.title);
        holder.setText(R.id.text_time,parser.time);
        holder.setText(R.id.text_content,parser.content);
        RecyclerView rcvImage=holder.getView(R.id.rcv_image);
        if(parser.imgList!=null&&parser.imgList.size()>0){
            rcvImage.setVisibility(View.VISIBLE);
            setImageGrid(rcvImage,parser.imgList);
        }else {
            rcvImage.setVisibility(View.GONE);
        }
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
        GridLayoutManager gl=new GridLayoutManager(context,colNum);
        gl.setOrientation(GridLayoutManager.VERTICAL);
        rcv.setLayoutManager(gl);
        rcv.setHasFixedSize(true);
        rcv.addItemDecoration(new DividerGridItemDecoration(context));
        rcv.setAdapter(new CircleImageGridAdapter(context,datas));
    }
}
