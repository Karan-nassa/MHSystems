package com.mh.systems.sunningdale.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.activites.ClubNewsWebCamActivity;

/**
 * The {@link NewsWebCamFragment2} used to display the detail of LOGIN
 * MEMBER by passing MemberId.
 *
 * @author {@link karan@ucreate.co.in}
 * @version 1.0
 * @since 13 Feb, 2017
 */
public class NewsWebCamFragment2 extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public final String LOG_TAG = NewsWebCamFragment2.class.getSimpleName();

    //String strURL = "http://www.sunningdale-golfclub.co.uk/visitor-information/webcam2/";
    String strURL = "http://www.waidev2.com/~sunningdale-cam/camera2.jpg";

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    ImageView ivNewsWebCam;

    private View mRootFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootFragment = inflater.inflate(R.layout.fragment_club_news_webcam, container, false);

        //Initialize the view resources.
        ivNewsWebCam = (ImageView) mRootFragment.findViewById(R.id.ivNewsWebCam);

        // Image link from AWS server.
        ((ClubNewsWebCamActivity)getActivity()).new DownloadImageFromInternet(ivNewsWebCam)
                .execute(strURL);

        return mRootFragment;
    }
}