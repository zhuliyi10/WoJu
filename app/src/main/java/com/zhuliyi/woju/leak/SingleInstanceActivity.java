package com.zhuliyi.woju.leak;

import android.os.Bundle;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;

import butterknife.ButterKnife;

/**
 * 单例生命周期和应用一样的长，如果单例中存在短生命周期其他对象的引用，则无法释放该对象，造成了内存泄漏
 */
public class SingleInstanceActivity extends SwipeBackActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        textTitle.setText("单例泄漏");
        SingleInstance instance = SingleInstance.getInstance(this);
        //正常单例
//        NormalSingleInstance instance = NormalSingleInstance.getInstance(this);
    }
}
