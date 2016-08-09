package com.mh.systems.halesworth.activites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mh.systems.halesworth.R;
import com.mh.systems.halesworth.constants.ApplicationGlobal;
import com.mh.systems.halesworth.constants.WebAPI;

public class FinanceDetailWebActivity extends BaseActivity {

    /* ++ LOCAL DATA TYPE INSTANCE DECLARATION ++ */
    String strURL;
    boolean IsTopup;
    int iTransactionId, iMemberId;

    /* ++ INSTANCES OF CLASSES ++ */
    WebView wvWebView;
    ProgressBar progressWebView;
    Toolbar tbFinanceDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_detail_web);

        progressWebView = (ProgressBar) findViewById(R.id.progressWebView);
        wvWebView = (WebView) findViewById(R.id.wvWebView);

        IsTopup = getIntent().getExtras().getBoolean("IsTopup");
        iTransactionId = getIntent().getExtras().getInt("iTransactionId");
        iMemberId = getIntent().getExtras().getInt("iMemberId");

        strURL = WebAPI.API_BASE_URL + "/webapi/ClubTransDetail?aClientId=" + loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID) +"&aCommand=GetAccReceipt&aJsonParams={%22MemberId%22:"+iMemberId+",%22TranId%22:"+iTransactionId+",%22IsTopup%22:"+IsTopup+",IsDemoApp:true}&aModuleId="+ApplicationGlobal.TAG_GCLUB_WEBSERVICES;

        tbFinanceDetail = (Toolbar) findViewById(R.id.tbFinanceDetail);
        setSupportActionBar(tbFinanceDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Receipt Reprint");

        if (strURL.length() > 0) {
            //Load Web View URL.
            wvWebView.setWebViewClient(new myWebClient());
            wvWebView.getSettings().setJavaScriptEnabled(true);
            wvWebView.getSettings().setBuiltInZoomControls(true);
            wvWebView.getSettings().setSupportZoom(true);
            wvWebView.setFocusableInTouchMode(false);
            wvWebView.setFocusable(false);
            wvWebView.loadUrl(strURL);
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