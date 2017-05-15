package com.zhuliyi.woju.presenter.contract;

import android.view.animation.Animation;

import com.zhuliyi.woju.base.BasePresenter;
import com.zhuliyi.woju.base.BaseView;

/**
 * Created by zhuliyi on 2017/1/6.
 */

public interface WelcomeContract {
    interface View extends BaseView<Presenter> {
        void showContent(String path, Animation animation);
        void jumpToMain();
    }
    interface Presenter extends BasePresenter {
    }
}
