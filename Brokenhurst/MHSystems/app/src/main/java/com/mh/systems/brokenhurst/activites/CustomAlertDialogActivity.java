package com.mh.systems.brokenhurst.activites;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mh.systems.brokenhurst.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by karan@mh.co.in to display
 * alert dialog of COURSE DIARY and COMPETITIONS
 * on tap of JOIN on 16-03-2016.
 */
public class CustomAlertDialogActivity extends AppCompatActivity {

    @Bind(R.id.tvCloseDialog)
    TextView tvCloseDialog;

    @Bind(R.id.flPopupTheme)
    FrameLayout flPopupTheme;

    @Bind(R.id.tvAlertMessage)
    TextView tvAlertMessage;

    @Bind(R.id.tvAlertTitle)
    TextView tvAlertTitle;

    Typeface tfRobotoLight;

    /* + LOCAL INSTANCES DECLARATION  + */
    String strColorTheme;
    int iCallFrom;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(CustomAlertDialogActivity.this); //Initialize facebook Fresco for round profile pic.
        setContentView(R.layout.activity_custom_alert_dialog);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(CustomAlertDialogActivity.this);

        tfRobotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        tvAlertTitle.setTypeface(tfRobotoLight);

        /**
         * Get Intent to get theme according screen.
         * For example: If come from Course Diary then Theme would be #AFD9A1
         * and from COMPETITIONS then Theme would be #F6EA8C
         */
        //strColorTheme = getIntent().getExtras().getString("colorTheme");

        /**
         *  callFrom describe the calling class name.
         *  IF 2, Course Diary.
         *     3, Competitions.
         */
        iCallFrom = getIntent().getExtras().getInt("callFrom");

        setConentofAlert(iCallFrom);

        //Set Theme of Alert Dialog.
       // flPopupTheme.setBackgroundColor(Color.parseColor(strColorTheme));
    }

    /**
     * Implements a method which invoke
     * when user press on HOME icon.
     */
    public void onClose(View view) {
        onBackPressed();
    }

    /**
     * Change content of App according calling screen.
     */
    public void setConentofAlert(int conentofAlert) {

        switch (conentofAlert) {

            case 2: //Course Diary Events.
                tvAlertMessage.setText(getResources().getString(R.string.text_alert_course_message));
                tvAlertTitle.setText(getResources().getString(R.string.text_alert_book_course));
                break;

            case 3: //Competitions
                tvAlertMessage.setText(getResources().getString(R.string.text_alert_competions_message));
                tvAlertTitle.setText(getResources().getString(R.string.text_alert_book_competitons));
                break;
        }

    }
}