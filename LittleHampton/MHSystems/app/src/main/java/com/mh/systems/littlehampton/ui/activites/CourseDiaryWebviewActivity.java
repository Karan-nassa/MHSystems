package com.mh.systems.littlehampton.ui.activites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mh.systems.littlehampton.R;
import com.mh.systems.littlehampton.utils.constants.ApplicationGlobal;

public class CourseDiaryWebviewActivity extends BaseActivity {

    /* ++ LOCAL DATA TYPE INSTANCE DECLARATION ++ */
    String strURL = "http://mhsserver3.com/ClubsDiary/BRS/";

    /* ++ INSTANCES OF CLASSES ++ */
    WebView wvWebView;
    ProgressBar progressWebView;
    Toolbar tbCourseDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_diary_webview);

        progressWebView = (ProgressBar) findViewById(R.id.progressWebView);
        wvWebView = (WebView) findViewById(R.id.wvWebView);

        tbCourseDiary = (Toolbar) findViewById(R.id.tbCourseDiary);
        tbCourseDiary.setNavigationIcon(R.mipmap.icon_menu);
        setSupportActionBar(tbCourseDiary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (strURL.length() > 0) {
            //Load Web View URL.
            wvWebView.setWebViewClient(new myWebClient());
            wvWebView.getSettings().setJavaScriptEnabled(true);
            wvWebView.getSettings().setBuiltInZoomControls(true);
            wvWebView.getSettings().setSupportZoom(true);
            wvWebView.setFocusableInTouchMode(false);
            wvWebView.setFocusable(false);
            wvWebView.loadUrl(strURL + ApplicationGlobal.TAG_CLIENT_ID);
        } else {
            progressWebView.setVisibility(View.GONE);
            showAlertMessage(getResources().getString(R.string.error_please_retry));
        }
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
            progressWebView.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressWebView.setVisibility(View.GONE);
        }
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
}