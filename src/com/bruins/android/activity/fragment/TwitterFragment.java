package com.bruins.android.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.bruins.android.v2.R;

/**
 * Created with IntelliJ IDEA.
 * User: david.hodge
 * Date: 9/20/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class TwitterFragment extends RoboSherlockFragment {

    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_webview, container, false);
        webView = (WebView) view.findViewById(R.id.social_web_view);
        webView.setWebViewClient(new SwAWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getResources().getString(R.string.twitter_url));
        getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
        return view;
    }

    private class SwAWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
        }
    }

}
