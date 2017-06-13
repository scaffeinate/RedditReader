package com.app.m.reddit.reader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.app.m.reddit.reader.constants.Constants;

public class WebViewActivity extends Activity {

  private WebView webView;
  private String webUrl;
  private ProgressBar progressBar_webLoading;

  @SuppressLint("SetJavaScriptEnabled")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_webview);
    webView = (WebView) findViewById(R.id.webView);
    progressBar_webLoading = (ProgressBar) findViewById(R.id.progressBar_webLoading);
    webUrl = getIntent().getExtras().getString(Constants.WEBURL);

    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setSupportZoom(true);
    webView.getSettings().setBuiltInZoomControls(true);
    webView.loadUrl(webUrl);
    webView.setWebChromeClient(new WebChromeClient() {
      public void onProgressChanged(WebView view, int progress) {
        if(progress == 100){
          progressBar_webLoading.setVisibility(View.GONE);
        }
      }
    });

  }
  @Override
  protected void onPause() {
    // TODO Auto-generated method stub
    super.onPause();
    this.finish();
  }
  @Override
  public void onBackPressed() {
    // TODO Auto-generated method stub
    super.onBackPressed();
    Intent toHome = new Intent(this, FrontActivity.class);
    startActivity(toHome);
  }

}
