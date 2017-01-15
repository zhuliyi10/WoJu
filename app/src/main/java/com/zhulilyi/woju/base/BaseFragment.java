package com.zhulilyi.woju.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.zhulilyi.woju.R;
import com.zhulilyi.woju.widget.statusLayout.OnShowHideViewListener;
import com.zhulilyi.woju.widget.statusLayout.StatusLayoutManager;
import com.zhulilyi.woju.widget.theme.ColorTextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhuliyi on 2017/1/6.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    private final String TAG = getClass().getSimpleName();

    protected StatusLayoutManager statusLayoutManager;//状态布局,位于标题之下
    protected T basePresenter;
    protected Unbinder unbinder;
    protected Context context;
    protected View rootView;
    protected FrameLayout flContent;
    protected ViewStub stubToolbar;
    protected Toolbar toolbar;
    protected ColorTextView textTitle;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    protected abstract int getLayoutId();
    protected void initView(){

    }
    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected void lazyFetchData() {
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_base,container,false);//false获取container的layoutparam，为true时保留了其自己的layoutparam
            flContent = (FrameLayout) rootView.findViewById(R.id.fl_content);
            statusLayoutManager = StatusLayoutManager.newBuilder(context)
                    .contentView(getLayoutId())
                    .emptyDataView(R.layout.page_emptydata)
                    .errorView(R.layout.page_error)
                    .loadingView(R.layout.page_loading)
                    .netWorkErrorView(R.layout.page_networkerror)
                    .onShowHideViewListener(new OnShowHideViewListener() {
                        @Override
                        public void onShowView(View view, int id) {

                        }

                        @Override
                        public void onHideView(View view, int id) {

                        }
                    }).build();
            flContent.addView(statusLayoutManager.getRootLayout());
            unbinder= ButterKnife.bind(this,rootView);
            statusLayoutManager.showContent();

            initView();
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared=true;

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false;
        isViewPrepared = false;
        basePresenter = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            lazyFetchDataIfPrepared();
        }
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }

    }
    /**
     * 显示标题栏
     */
    protected void showToolbar(){
        if(stubToolbar==null){
            stubToolbar= (ViewStub) rootView.findViewById(R.id.stub_toolbar);
            stubToolbar.inflate();
            toolbar= (Toolbar) rootView.findViewById(R.id.toolbar);
            textTitle= (ColorTextView) rootView.findViewById(R.id.text_title);
        }
    }
    protected void setTitleName(String titleName){
        if(titleName!=null){
            textTitle.setText(titleName);
        }
    }

}
