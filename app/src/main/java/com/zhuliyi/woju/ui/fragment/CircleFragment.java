package com.zhuliyi.woju.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.app.Constants;
import com.zhuliyi.woju.base.BaseFragment;
import com.zhuliyi.woju.parser.CircleParser;
import com.zhuliyi.woju.ui.adapter.CircleImageAdapter;
import com.zhuliyi.woju.utils.GsonUtil;
import com.zhuliyi.woju.utils.ScreenUtil;
import com.zhuliyi.woju.widget.decoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 首页-蜗居圈
 * Created by zhuliyi on 2017/1/9.
 */

public class CircleFragment extends BaseFragment {
    @BindView(R.id.rcv)
    RecyclerView rcv;
    CircleImageAdapter adapter;
    @BindView(R.id.store_house_ptr_frame)
    PtrFrameLayout frame;

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
        adapter = new CircleImageAdapter(getDataList());
        rcv.setAdapter(adapter);

        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, ScreenUtil.dip2px(context,16), 0, ScreenUtil.dip2px(context,16));
        header.initWithString(context.getResources().getString(R.string.head_refresh));
        header.setLineWidth(ScreenUtil.dip2px(context,1.5f));
        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);

        frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rcv, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                }, 1000);
            }
        });
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
