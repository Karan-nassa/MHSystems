package com.mh.systems.dunstabledowns.ui.activites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mh.systems.dunstabledowns.R;
import com.mh.systems.dunstabledowns.utils.constants.ApplicationGlobal;



public class DashboardWebviewActivity extends BaseActivity {

    /* ++ LOCAL DATA TYPE INSTANCE DECLARATION ++ */
    //String strURL = "http://mhsserver3.com/ClubsDiary/BRS/";
    String strProAgendaUrl = "https://www.facebook.com/MHS-Group-573336842719392/";

    /* ++ INSTANCES OF CLASSES ++ */
    WebView wvWebView;
    ProgressBar progressWebView;
    Toolbar tbDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_diary_webview);
        progressWebView = (ProgressBar) findViewById(R.id.progressWebView);
        wvWebView = (WebView) findViewById(R.id.wvWebView);

        tbDashboard = (Toolbar) findViewById(R.id.tbDashboard);
        if (ApplicationGlobal.KEY_DASHBOARD_SOCIAL == 1) {
            tbDashboard.setTitle("Facebook Feeds");
            strProAgendaUrl = "https://www.facebook.com/MHS-Group-573336842719392/";
        } else if (ApplicationGlobal.KEY_DASHBOARD_SOCIAL == 2) {
            tbDashboard.setTitle("Twitter Feeds");
            strProAgendaUrl = "https://twitter.com/mhsystemsltd?lang=en";
        }


        /// tbDashboard.setNavigationIcon(R.mipmap.icon_menu);
        setSupportActionBar(tbDashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (strProAgendaUrl.length() > 0) {
            //Load Web View URL.
            wvWebView.setWebViewClient(new myWebClient());
            wvWebView.getSettings().setJavaScriptEnabled(true);
            wvWebView.getSettings().setBuiltInZoomControls(true);
            wvWebView.getSettings().setSupportZoom(true);
            wvWebView.setFocusableInTouchMode(false);
            wvWebView.setFocusable(false);
            wvWebView.loadUrl(strProAgendaUrl);
            /*wvWebView.loadUrl(strURL + ApplicationGlobal.TAG_CLIENT_ID);*/
        } else {
            progressWebView.setVisibility(View.GONE);
            showAlertMessage(getResources().getString(R.string.error_please_retry));
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
}
