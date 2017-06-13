package com.zhuliyi.woju.ui.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.QuickAdapter;
import com.zhuliyi.woju.parser.NewsParser;

import java.util.List;

/**
 * 消息列表
 * Created by zhuliyi on 2017/4/18.
 */

public class NewsAdapter extends QuickAdapter<NewsParser> {
    public NewsAdapter(List<NewsParser> datas) {
        super( R.layout.item_news, datas);
    }


    @Override
    protected void convert(BaseViewHolder holder, NewsParser item) {
        if(item.type==1){
            holder.setImageResource(R.id.img_head,R.drawable.news_head);
        }else if(item.type==2){
            holder.setImageResource(R.id.img_head,R.drawable.news_money_change);
        }else if(item.type==3){
            holder.setImageResource(R.id.img_head,R.drawable.news_activity);
        }
        holder.setText(R.id.text_title,item.title);
        holder.setText(R.id.text_time,item.time);
        holder.setText(R.id.text_content,item.content);
    }
}
