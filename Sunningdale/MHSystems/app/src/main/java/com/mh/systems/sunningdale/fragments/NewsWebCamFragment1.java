package com.mh.systems.sunningdale.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mh.systems.sunningdale.R;

/**
 * The {@link NewsWebCamFragment1} used to display the detail of LOGIN
 * MEMBER by passing MemberId.
 *
 * @author {@link karan@ucreate.co.in}
 * @version 1.0
 * @since 13th Feb, 2017
 */
public class NewsWebCamFragment1 extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public final String LOG_TAG = NewsWebCamFragment2.class.getSimpleName();

    String strURL = "http://www.sunningdale-golfclub.co.uk/visitor-information/webcam1/";

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    WebView wvNewsWebCam;
    ProgressBar pbNewsCam;

    private View mRootFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootFragment = inflater.inflate(R.layout.fragment_club_news_webcam, container, false);

        //Initialize the view resources.
        wvNewsWebCam = (WebView) mRootFragment.findViewById(R.id.wvNewsWebCam);
        pbNewsCam = (ProgressBar) mRootFragment.findViewById(R.id.pbNewsCam);

        //Load Web View URL.
        wvNewsWebCam.setWebViewClient(new myWebClient());
        wvNewsWebCam.getSettings().setJavaScriptEnabled(true);
        wvNewsWebCam.getSettings().setBuiltInZoomControls(true);
        wvNewsWebCam.getSettings().setSupportZoom(true);
        wvNewsWebCam.loadUrl(strURL);

        return mRootFragment;
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
            pbNewsCam.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbNewsCam.setVisibility(View.GONE);
        }
    }
}