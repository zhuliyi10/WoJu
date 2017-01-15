package com.zhulilyi.woju.ui.fragment;

import com.zhulilyi.woju.R;
import com.zhulilyi.woju.base.BaseFragment;

/**
 * 首页-蜗居圈
 * Created by zhuliyi on 2017/1/9.
 */

public class CircleFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initView() {
        statusLayoutManager.showLoading();
    }
}
