package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);
        textTitle.setText("账单");
        rcv.setLayoutManager(new LinearLayoutManager(context));
        showProgress();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        },3000);
    }
    private void initData(){
        List<BillParser>bills=new ArrayList<>();
        for (int i=0;i<200;i++){
            BillParser parser=new BillParser();
            parser.billId=String.valueOf(i+1);
            parser.billName="交易记录的名称"+(i+1);
            parser.date="今天";
            parser.time="11:34";
            parser.billMoney="-26.86";
            bills.add(parser);
        }
        if(bills.size()>0) {
            showContent();
            BillAdapter adapter = new BillAdapter(bills);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            rcv.setHasFixedSize(true);
            rcv.setAdapter(adapter);
        }else {
            showEmpty();
        }
    }

    @Override
    protected void onDestroy() {
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
