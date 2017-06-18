package com.zhuliyi.woju.widget.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 内部webivew
 */
public class InnerWebView extends WebView {
	private WebViewClient client = new WebViewClient() {
		/**
		 * 防止加载网页时调起系统浏览器
		 */
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}


		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	};

	@SuppressLint("SetJavaScriptEnabled")
	public InnerWebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		this.setWebViewClient(client);
		this.setWebChromeClient(new WebChromeClient());
		initWebViewSettings();
	}

	private void initWebViewSettings() {
		WebSettings webSetting = this.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
//		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setSupportMultipleWindows(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setLoadWithOverviewMode(true);
	}

	public InnerWebView(Context arg0) {
		super(arg0);
	}

}
