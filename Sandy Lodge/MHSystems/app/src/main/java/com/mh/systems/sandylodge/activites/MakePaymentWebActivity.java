package com.mh.systems.sandylodge.activites;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mh.systems.sandylodge.R;
import com.mh.systems.sandylodge.constants.ApplicationGlobal;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MakePaymentWebActivity extends BaseActivity {

    final int ACTION_MAKE_PAYMENT = 111;
    final String LOG_TAG = MakePaymentWebActivity.class.getSimpleName();


    /* ++ INSTANCES OF CLASSES ++ */

    @Bind(R.id.wvPaymentView)
    WebView wvPaymentView;

    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;

     /* ++ LOCAL INSTANCES DECLARATION ++ */

    String strURL = "";
    String strCurrencySign = "";

    float fTopUpPrize;
    float fCardBalance;

    boolean isPaymentSuccess = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment_web);

        ButterKnife.bind(MakePaymentWebActivity.this);

        fTopUpPrize = getIntent().getExtras().getFloat("fTopUpPrize");
        fCardBalance = getIntent().getExtras().getFloat("fCardBalance");
        strCurrencySign = getIntent().getExtras().getString("strCurrencySign");

        strURL = "https://staging.mhsystems.co.uk//fsipayment/paymentgateway?aClientId="
                + getClientId() +
                "&aMemberId=" + getMemberId()
                + "&aAmount=" + fTopUpPrize;

        if (isOnline(MakePaymentWebActivity.this)) {
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
        } else {
            showMessage("", getString(R.string.error_no_internet));
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("Is_PAYMENT_SUCCESS", isPaymentSuccess);
        setResult(111, intent);
        finish();//finishing activity
        super.onBackPressed(); //Call this line after pass data.
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

            if (url.contains("failure")) {
                isPaymentSuccess = false;
                wvPaymentView.goBack();
                showMessage(getString(R.string.alert_title_payment_failure),
                        getString(R.string.error_title_try_again));
                return false;
            } else if (url.contains("success")) {
                isPaymentSuccess = true;
                wvPaymentView.goBack();
                showMessage(getString(R.string.alert_title_payment_success),
                        (getString(R.string.text_title_new_balance) + " "
                                + strCurrencySign + (fCardBalance + fTopUpPrize)));
                return false;
            }

            pbLoading.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbLoading.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            wvPaymentView.setVisibility(View.INVISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                showMessage("", error.getDescription().toString());
            } else {
                showMessage("", getString(R.string.error_please_retry));
            }
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
    public void showMessage(String strTitle, String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(MakePaymentWebActivity.this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                            onBackPressed();
                        }
                    });

            if (strTitle.length() >= 1) {
                builder.setTitle(strTitle);
            }

            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
