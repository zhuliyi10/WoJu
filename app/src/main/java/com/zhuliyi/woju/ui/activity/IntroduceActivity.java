package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.widget.web.InnerWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 样本activity
 * Created by zhuliyi on 2017/5/16.
 */

public class IntroduceActivity extends SwipeBackActivity {

    public final static String url = "http://q.eqxiu.com/s/bYKdzAMD";

    @BindView(R.id.webView)
    InnerWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);
        textTitle.setText("蜗信介绍");
        webView.loadUrl(url);
        showProgress();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showContent();
            }
        });
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();

    }
}
