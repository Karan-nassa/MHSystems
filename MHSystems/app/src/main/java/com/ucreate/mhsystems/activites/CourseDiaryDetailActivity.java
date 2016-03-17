package com.ucreate.mhsystems.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucreate.mhsystems.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseDiaryDetailActivity extends AppCompatActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.btJoinEvent)
    Button btJoinEvent;
    @Bind(R.id.tvTitleCourseEvent)
    TextView tvTitleCourseEvent;
    @Bind(R.id.tvDateCourseEvent)
    TextView tvDateCourseEvent;
    @Bind(R.id.tvTimeCourseEvent)
    TextView tvTimeCourseEvent;
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
    String strCourseTitle, strCourseLogo, strCourseDate, strCourseDayName, strCoursePrize, strCourseDesc;
    boolean isJoin;

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
    private View.OnClickListener mJoinListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent  mIntent=new Intent(CourseDiaryDetailActivity.this,CourseAlertDialog.class);
            startActivity(mIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_diary_detail);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(this);

        strCourseTitle = getIntent().getExtras().getString("COURSE_TITLE");
        strCourseLogo = getIntent().getExtras().getString("COURSE_EVENT_IMAGE");
        isJoin = getIntent().getExtras().getBoolean("COURSE_EVENT_JOIN");
        strCourseDate = getIntent().getExtras().getString("COURSE_EVENT_DATE");
        strCourseDayName = getIntent().getExtras().getString("COURSE_EVENT_DAY_NAME");
        strCoursePrize = getIntent().getExtras().getString("COURSE_EVENT_PRIZE");
        strCourseDesc = getIntent().getExtras().getString("COURSE_EVENT_DESCRIPTION");

        //Set Content on each Event of Course Diary.
        tvTitleCourseEvent.setText(strCourseTitle);
        btJoinEvent.setText(isJoin ? "JOINED" : "JOIN");
        tvDateCourseEvent.setText(strCourseDayName + ", " + formatDateOfEvent(strCourseDate));

        //tvFeeCourseEvent.setText(strCoursePrize);
        tvDescCourseEvent.setText(strCourseDesc);

        //Set Home icon listener.
        llHomeIcon.setOnClickListener(mHomeListener);
        btJoinEvent.setOnClickListener(mJoinListener);
    }

    /**
     * Get date and return in dd-MMMM-yyyy format.
     * Example: 2015-12-1
     *
     * @return 1-DECEMBER-2015
     */
    public String formatDateOfEvent(String strNewDate) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");

        try {
            Date date = inputFormat.parse(strNewDate);
            strNewDate = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }

        return strNewDate.toUpperCase();
    }
}
