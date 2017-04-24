package com.zhulilyi.woju.ui.adapter;

import android.content.Context;

import com.zhulilyi.woju.R;
import com.zhulilyi.woju.base.BaseAdapter;
import com.zhulilyi.woju.parser.NewsParser;
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
    protected void convert(ViewHolder holder, NewsParser newsParser, int position) {
        holder.setImageResource(R.id.img_head,newsParser.head);
        holder.setText(R.id.text_title,newsParser.title);
        holder.setText(R.id.text_time,newsParser.time);
        holder.setText(R.id.text_content,newsParser.content);
    }
}
