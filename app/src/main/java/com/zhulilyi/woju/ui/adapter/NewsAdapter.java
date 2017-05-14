package com.zhulilyi.woju.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhulilyi.woju.R;
import com.zhulilyi.woju.base.BaseAdapter;
import com.zhulilyi.woju.parser.NewsParser;
import com.zhulilyi.woju.ui.activity.ChatActivity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 消息列表
 * Created by zhuliyi on 2017/4/18.
 */

public class NewsAdapter extends BaseAdapter<NewsParser> {
    private Context context;
    public NewsAdapter(Context context, List<NewsParser> datas) {
        super(context, R.layout.item_news, datas);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder holder, final NewsParser newsParser, int position) {
        if(newsParser.type==1){
            holder.setImageResource(R.id.img_head,R.drawable.news_head);
        }else if(newsParser.type==2){
            holder.setImageResource(R.id.img_head,R.drawable.news_money_change);
        }else if(newsParser.type==3){
            holder.setImageResource(R.id.img_head,R.drawable.news_activity);
        }
        holder.setText(R.id.text_title,newsParser.title);
        holder.setText(R.id.text_time,newsParser.time);
        holder.setText(R.id.text_content,newsParser.content);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holdr, int position) {
                NewsParser parser=getDatas().get(position);
                int type=parser.type;
                if(type==1){
                    if(parser.subType==1){
                        context.startActivity(new Intent(context, ChatActivity.class));
                    }
                }else if(type==2){

                }else if(type==3){

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}
