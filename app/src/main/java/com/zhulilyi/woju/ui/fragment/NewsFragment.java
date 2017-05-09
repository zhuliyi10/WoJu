package com.zhulilyi.woju.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhulilyi.woju.R;
import com.zhulilyi.woju.app.Constants;
import com.zhulilyi.woju.base.BaseFragment;
import com.zhulilyi.woju.parser.NewsParser;
import com.zhulilyi.woju.ui.adapter.NewsAdapter;
import com.zhulilyi.woju.utils.GsonUtil;
import com.zhulilyi.woju.widget.decoration.DividerItemDecoration;

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
        rcv.setAdapter(new NewsAdapter(context,getDataList()));
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
