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
import com.zhulilyi.woju.parser.CircleParser;
import com.zhulilyi.woju.ui.adapter.CircleImageAdapter;
import com.zhulilyi.woju.utils.GsonUtil;
import com.zhulilyi.woju.widget.decoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页-蜗居圈
 * Created by zhuliyi on 2017/1/9.
 */

public class CircleFragment extends BaseFragment {
    @BindView(R.id.rcv)
    RecyclerView rcv;
    CircleImageAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initView() {
        rcv.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context);
//        itemDecoration.setmDivider(context.getResources().getDrawable(R.drawable.divider_grey_line));
        rcv.addItemDecoration(itemDecoration);
        adapter = new CircleImageAdapter(context, getDataList());
        rcv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private List<CircleParser.CircleListParser> getDataList() {
        CircleParser circleParser = GsonUtil.getParser(Constants.DATA_JSON_CIRCLE_TEST, CircleParser.class);
        return circleParser.circleList;
    }
}
