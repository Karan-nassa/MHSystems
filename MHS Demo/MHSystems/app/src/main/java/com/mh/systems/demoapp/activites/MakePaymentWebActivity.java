package com.mh.systems.demoapp.activites;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.constants.ApplicationGlobal;

public class MakePaymentWebActivity extends BaseActivity {

    private final String LOG_TAG = MakePaymentWebActivity.class.getSimpleName();

    String strURL;

    /* ++ INSTANCES OF CLASSES ++ */
    WebView wvPaymentView;
    ProgressBar pbLoading;

    float fTopUpPrize;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment_web);

        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        wvPaymentView = (WebView) findViewById(R.id.wvPaymentView);

        fTopUpPrize = getIntent().getExtras().getFloat("fTopUpPrize");

        strURL = "https://staging.mhsystems.co.uk//fsipayment/paymentgateway?aClientId="
                + getClientId() +
                "&aMemberId=" + getMemberId()
                + "&aAmount=" + fTopUpPrize;

        Log.e(LOG_TAG, "URL :" + strURL);

        if(isOnline(MakePaymentWebActivity.this)) {
            if (strURL.length() > 0) {
                //Load Web View URL.
                wvPaymentView.setWebViewClient(new myWebClient());
                wvPaymentView.getSettings().setJavaScriptEnabled(true);
                wvPaymentView.getSettings().setBuiltInZoomControls(true);
                wvPaymentView.getSettings().setSupportZoom(true);
                wvPaymentView.loadUrl(strURL);
            } else {
                pbLoading.setVisibility(View.GONE);
                showAlertMessage(getResources().getString(R.string.error_please_retry));
            }
        }else{
            showErrorMessage(getString(R.string.error_no_internet));
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

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    public String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implement a method to show Error message
     * Alert Dialog.
     */
    public void showErrorMessage(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(MakePaymentWebActivity.this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
