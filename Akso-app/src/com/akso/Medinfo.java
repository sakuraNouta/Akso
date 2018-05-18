package com.akso;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Medinfo extends Activity {

	
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medinfo);
		
		init();
		
	}
	private void init(){  
		webView = (WebView) findViewById(R.id.webView1);
		webView.loadUrl("https://www.315jiage.cn/mIndex.aspx");
		
		webView.setWebViewClient(new WebViewClient(){  
			@Override  
			public boolean shouldOverrideUrlLoading(WebView view, String url) {  
					view.loadUrl(url);  
					return true;  
			}  
		}); 
	}	
}
