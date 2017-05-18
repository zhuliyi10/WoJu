package com.zhuliyi.woju.ui.adapter;

import android.content.Context;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.BaseAdapter;
import com.zhuliyi.woju.parser.BillParser;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 账单
 * Created by zhuliyi on 2017/5/18.
 */

public class BillAdapter extends BaseAdapter<BillParser>{
    public BillAdapter(Context context,  List<BillParser> datas) {
        super(context, R.layout.item_bill, datas);
    }

    @Override
    protected void convert(ViewHolder holder, BillParser billParser, int position) {
        holder.setText(R.id.text_date,billParser.date);
        holder.setText(R.id.text_time,billParser.time);
        holder.setImageResource(R.id.image_head,R.drawable.mine_head);
        holder.setText(R.id.text_money,billParser.billMoney);
        holder.setText(R.id.text_name,billParser.billName);
    }
}
