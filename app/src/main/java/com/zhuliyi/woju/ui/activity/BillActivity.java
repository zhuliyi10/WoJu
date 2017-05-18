package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.parser.BillParser;
import com.zhuliyi.woju.ui.adapter.BillAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 账单 activity
 * Created by zhuliyi on 2017/5/16.
 */

public class BillActivity extends SwipeBackActivity {
    @BindView(R.id.rcv)
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);
        textTitle.setText("账单");
        rcv.setLayoutManager(new LinearLayoutManager(context));
        List<BillParser>bills=new ArrayList<>();
        for (int i=0;i<10;i++){
            BillParser parser=new BillParser();
            parser.billId=String.valueOf(i+1);
            parser.billName="交易记录的名称"+(i+1);
            parser.date="今天";
            parser.time="11:34";
            parser.billMoney="-26.86";
            bills.add(parser);
        }
        rcv.setAdapter(new BillAdapter(context,bills));
    }
}
