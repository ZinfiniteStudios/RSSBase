package com.bruins.android.activity.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.bruins.android.v2.R;
import roboguice.inject.InjectView;

/**
 * Created with IntelliJ IDEA.
 * User: david.hodge
 * Date: 9/21/13
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewArticle extends RoboSherlockFragmentActivity {

    @InjectView(R.id.social_web_view) WebView webView;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_webview);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(android.R.color.transparent);
        actionBar.setTitle(getResources().getString(R.string.app_name));

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            url = extras.getString("URL");
            Log.d("URL", url);
        }

        webView.setWebViewClient(new SwAWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            webView.getSettings().setDisplayZoomControls(false);
        }
        webView.loadUrl(url);
        setSupportProgressBarIndeterminateVisibility(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class SwAWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setSupportProgressBarIndeterminateVisibility(false);
        }
    }

}
