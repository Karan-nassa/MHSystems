package com.mh.systems.sunningdale.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.activites.ClubNewsWebCamActivity;
import com.mh.systems.sunningdale.adapter.RecyclerAdapter.ClubNewsSwipeAdapter;

import java.io.InputStream;

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

//    String strURL = "http://www.sunningdale-golfclub.co.uk/visitor-information/webcam1/";
      String strURL = "http://www.waidev2.com/~sunningdale-cam/camera1.jpg";

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    ImageView ivNewsWebCam;

    private View mRootFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootFragment = inflater.inflate(R.layout.fragment_club_news_webcam, container, false);

        ivNewsWebCam = (ImageView) mRootFragment.findViewById(R.id.ivNewsWebCam);

        // Image link from AWS server.
        ((ClubNewsWebCamActivity)getActivity()).new DownloadImageFromInternet(ivNewsWebCam)
                .execute(strURL);

        return mRootFragment;
    }
}