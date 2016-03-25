package com.ucreate.mhsystems.activites;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by karan@ucreate.co.in to display
 * alert dialog of COURSE DIARY and COMPETITIONS
 * on tap of JOIN on 16-03-2016.
 */
public class CourseAlertDialog extends AppCompatActivity {

    @Bind(R.id.tvCloseDialog)
    TextView tvCloseDialog;

    @Bind(R.id.flPopupTheme)
    FrameLayout flPopupTheme;

    String strColorTheme;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(CourseAlertDialog.this); //Initialize facebook Fresco for round profile pic.
        setContentView(R.layout.activity_alert_dialog);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(CourseAlertDialog.this);

        /**
         * Get Intent to get theme according screen.
         * For example: If come from Course Diary then Theme would be #AFD9A1
         * and from COMPETITIONS then Theme would be #F6EA8C
         */
        strColorTheme = getIntent().getExtras().getString("colorTheme");

        //Set Theme of Alert Dialog.
        flPopupTheme.setBackgroundColor(Color.parseColor(strColorTheme));
    }

    /**
     * Implements a method which invoke
     * when user press on HOME icon.
     */
    public void onClose(View view) {
        onBackPressed();
    }
}