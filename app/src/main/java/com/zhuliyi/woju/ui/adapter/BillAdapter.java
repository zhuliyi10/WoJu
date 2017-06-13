package com.zhuliyi.woju.ui.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.QuickAdapter;
import com.zhuliyi.woju.parser.BillParser;

import java.util.List;

/**
 * 账单
 * Created by zhuliyi on 2017/5/18.
 */

public class BillAdapter extends QuickAdapter<BillParser> {
    public BillAdapter(List<BillParser> datas) {
        super(R.layout.item_bill, datas);
    }
    @Override
    protected void convert(BaseViewHolder holder, BillParser item) {
        holder.setText(R.id.text_date,item.date);
        holder.setText(R.id.text_time,item.time);
        holder.setImageResource(R.id.image_head,R.drawable.mine_head);
        holder.setText(R.id.text_money,item.billMoney);
        holder.setText(R.id.text_name,item.billName);
    }
}
