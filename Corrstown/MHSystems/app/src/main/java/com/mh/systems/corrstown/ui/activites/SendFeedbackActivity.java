package com.mh.systems.corrstown.ui.activites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mh.systems.corrstown.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SendFeedbackActivity extends BaseActivity {

    /**
     * ++ LOCAL DATA TYPE INSTANCE DECLARATION ++
     **/
    String strURL = "https://www.surveymonkey.co.uk/r/LC9LVTF";

    /**
     * ++ INSTANCES OF CLASSES ++
     **/
    @Bind(R.id.wvWebView)
    WebView wvWebView;
    @Bind(R.id.tbSendFeedback)
    Toolbar tbSendFeedback;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);

        //Initialize Butter knife instance.
        ButterKnife.bind(SendFeedbackActivity.this);

        setSupportActionBar(tbSendFeedback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Load Web View URL.
        wvWebView.setWebViewClient(new myWebClient());
        wvWebView.getSettings().setJavaScriptEnabled(true);
        wvWebView.loadUrl(strURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            /**
             *  Tool bar back arrow handler.
             */
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Implements a class to Handle and display progress in
     * Web View.
     */
    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            pbLoading.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbLoading.setVisibility(View.GONE);
        }
    }
}
