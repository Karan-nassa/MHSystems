package com.mh.systems.demoapp.activites;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mh.systems.demoapp.R;

public class MakePaymentWebActivity extends BaseActivity {

    String strURL = "https://staging.mhsystems.co.uk//fsipayment/paymentgateway?aClientId=44071043&aMemberId=9137&aAmount=34";

    /* ++ INSTANCES OF CLASSES ++ */
    WebView wvPaymentView;
    ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment_web);

        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        wvPaymentView = (WebView) findViewById(R.id.wvPaymentView);

        if (strURL.length() > 0) {
            //Load Web View URL.
            wvPaymentView.setWebViewClient(new myWebClient());
            wvPaymentView.getSettings().setJavaScriptEnabled(true);
            wvPaymentView.getSettings().setBuiltInZoomControls(true);
            wvPaymentView.getSettings().setSupportZoom(true);
            wvPaymentView.setFocusableInTouchMode(false);
            wvPaymentView.setFocusable(false);
            wvPaymentView.loadUrl(strURL);
        } else {
            pbLoading.setVisibility(View.GONE);
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
