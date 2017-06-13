package com.zhuliyi.woju.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuliyi.woju.R;
import com.zhuliyi.woju.app.Constants;
import com.zhuliyi.woju.base.BaseFragment;
import com.zhuliyi.woju.leak.SingleInstanceActivity;
import com.zhuliyi.woju.parser.NewsParser;
import com.zhuliyi.woju.ui.activity.ChatActivity;
import com.zhuliyi.woju.ui.adapter.NewsAdapter;
import com.zhuliyi.woju.utils.GsonUtil;
import com.zhuliyi.woju.widget.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页-消息
 * Created by zhuliyi on 2017/1/9.
 */

public class NewsFragment extends BaseFragment {
    @BindView(R.id.rcv)
    RecyclerView rcv;
    List<NewsParser>newsList=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        for (int i=0;i<100;i++){
            NewsParser parser=new NewsParser();
            parser.title="203-何源明";
            parser.time="下午 13:45";
            parser.content="房东，厕所的佃坏了，麻烦处理一下";
            newsList.add(parser);
        }
        rcv.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(context);
        itemDecoration.setmDivider(context.getResources().getDrawable(R.drawable.divider_grey_line));
        rcv.addItemDecoration(itemDecoration);
        NewsAdapter adapter=new NewsAdapter(getDataList());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                NewsParser parser= (NewsParser) adapter.getData().get(position);
                int type=parser.type;
                if(type==1){
                    if(parser.subType==1){
                        getContext().startActivity(new Intent(getContext(), ChatActivity.class));
                    }
                }else if(type==2){
                    getContext().startActivity(new Intent(getContext(), SingleInstanceActivity.class));
                }else if(type==3){

                }
            }
        });
        rcv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
    private  List<NewsParser> getDataList() {

       List<NewsParser> list = GsonUtil.getParserList(GsonUtil.getResponseList(Constants.DATA_JSON_NEWS_TEST),NewsParser.class);
        return list;
    }
}
