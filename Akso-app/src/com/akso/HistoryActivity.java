package com.akso;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
 * 跳转服务器查询历史服药纪录
 */
public class HistoryActivity extends Activity {

	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		init();  
	}
	
	private void init(){  
		webView = (WebView) findViewById(R.id.webView1);
		webView.loadUrl("http://192.168.1.103:66/listRecord.jsp");
		webView.setWebViewClient(new WebViewClient(){  
			@Override  
			public boolean shouldOverrideUrlLoading(WebView view, String url) {  
					view.loadUrl(url);  
					return true;  
			}  
		}); 
	}
}
