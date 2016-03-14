package com.ucreate.mhsystems.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ucreate.mhsystems.R;

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

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strCourseTitle, strCourseLogo, strCourseDate, strCourseDayName, strCoursePrize, strCourseDesc;
    boolean isJoin;

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
        btJoinEvent.setText(isJoin? "JOINED" : "JOIN");
        tvDateCourseEvent.setText(strCourseDayName + ", "+ strCourseDate);
        tvFeeCourseEvent.setText(strCoursePrize);
        tvDescCourseEvent.setText(strCourseDesc);
    }
}
