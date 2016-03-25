package com.ucreate.mhsystems.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompetitionsDetailActivity extends AppCompatActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.btJoinEvent)
    Button btJoinEvent;
    @Bind(R.id.tvTitleCourseEvent)
    TextView tvTitleCourseEvent;
    @Bind(R.id.tvDateCourseEvent)
    TextView tvDateCourseEvent;
    @Bind(R.id.tvFeeCourseEvent)
    TextView tvFeeCourseEvent;
    @Bind(R.id.tvDescCourseEvent)
    TextView tvDescCourseEvent;
    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;
    @Bind(R.id.llPriceGroup)
    LinearLayout llPriceGroup;


    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strEventTitle, strEventLogo, strEventDate, strEventTime, strEventPrize, strEventDesc;
    boolean isEventJoin;

    /**
     * Implements a listener of HOME.
     */
    private View.OnClickListener mHomeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Navigate back to Course Dairy events.
            onBackPressed();
        }
    };
    private View.OnClickListener mJoinListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent = new Intent(CompetitionsDetailActivity.this, CourseAlertDialog.class);
            //Pass theme green color.
            mIntent.putExtra(ApplicationGlobal.TAG_POPUP_THEME, "#F6EA8C");
            startActivity(mIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions_detail);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(this);

        strEventTitle = getIntent().getExtras().getString("COMPETITIONS_TITLE");
        strEventLogo = getIntent().getExtras().getString("COMPETITIONS_EVENT_IMAGE");
        isEventJoin = getIntent().getExtras().getBoolean("COMPETITIONS_EVENT_JOIN");
        strEventDate = getIntent().getExtras().getString("COMPETITIONS_EVENT_DATE");
        strEventPrize = getIntent().getExtras().getString("COMPETITIONS_EVENT_PRIZE");
        strEventTime = getIntent().getExtras().getString("COMPETITIONS_EVENT_TIME");
        strEventDesc = getIntent().getExtras().getString("COMPETITIONS_EVENT_DESCRIPTION");

        //Set Content on each Event of Course Diary.
        tvTitleCourseEvent.setText(strEventTitle);
        btJoinEvent.setText(isEventJoin ? getResources().getString(R.string.text_joined) :
                getResources().getString(R.string.text_join));
        tvDateCourseEvent.setText(strEventDate + ", " + strEventTime);

        tvFeeCourseEvent.setText("£" + strEventPrize + " " + getResources().getString(R.string.title_competitions_prize));
        tvDescCourseEvent.setText(strEventDesc);

        //Set Home icon listener.
        llHomeIcon.setOnClickListener(mHomeListener);
        btJoinEvent.setOnClickListener(mJoinListener);
    }
}
