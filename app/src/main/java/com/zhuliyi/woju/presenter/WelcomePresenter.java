package com.zhuliyi.woju.presenter;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

import com.zhuliyi.woju.base.RxPresenter;
import com.zhuliyi.woju.presenter.contract.WelcomeContract;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by zhuliyi on 2017/1/6.
 */

public class WelcomePresenter extends RxPresenter implements WelcomeContract.Presenter{
    WelcomeContract.View view;
    public WelcomePresenter(WelcomeContract.View view){
        this.view=view;
        view.setPresenter(this);
        getStartData();
    }
    public void getStartData() {
        view.showContent(getImageUrl(),getAnimationSet());
        Subscription subscription=Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        view.jumpToMain();
                    }
                });
        addSubscribe(subscription);
    }

    private String getImageUrl(){
        return "file:///android_asset/c.jpg";
    }

    private Animation getAnimationSet(){
        final AnimationSet animationSet=new AnimationSet(true);
        ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f,1.2f,1.0f,1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setStartOffset(100);
        animationSet.addAnimation(scaleAnimation);
        return scaleAnimation;
    }
}
