package com.mh.systems.redlibbets.activites;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mh.systems.redlibbets.R;
import com.mh.systems.redlibbets.constants.ApplicationGlobal;
import com.mh.systems.redlibbets.constants.WebAPI;

public class ShowCertificateWebview extends BaseActivity {

    /* ++ LOCAL DATA TYPE INSTANCE DECLARATION ++ */
    String strURL;

    /* ++ INSTANCES OF CLASSES ++ */
    WebView wvWebView;
    ProgressBar progressWebView;
    Toolbar tbCertificate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_certificate);

        progressWebView = (ProgressBar) findViewById(R.id.progressWebView);
        wvWebView = (WebView) findViewById(R.id.wvWebView);

        strURL = WebAPI.API_BASE_URL
                + "/webapi/clubapppdf?aClientId="
                + loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID)
                + "&aCommand=GETMEMBERHCAPCERT&aJsonParams={%22version%22:"
                + ApplicationGlobal.TAG_GCLUB_VERSION 
				+ ",%22callid%22:"
                + ApplicationGlobal.TAG_GCLUB_CALL_ID 
				+ ",memberid:"
                + loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784")
                + "}&aModuleId="
                + ApplicationGlobal.TAG_GCLUB_WEBSERVICES
                + "&aUserClass=" 
				+ ApplicationGlobal.TAG_GCLUB_MEMBERS;
				
        tbCertificate = (Toolbar) findViewById(R.id.tbCertificate);
        setSupportActionBar(tbCertificate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(isOnline(ShowCertificateWebview.this)) {
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
                showErrorMessage(getResources().getString(R.string.error_please_retry));
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

    /**
     * Implement a method to show Error message
     * Alert Dialog.
     */
    public void showErrorMessage(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(ShowCertificateWebview.this);
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
