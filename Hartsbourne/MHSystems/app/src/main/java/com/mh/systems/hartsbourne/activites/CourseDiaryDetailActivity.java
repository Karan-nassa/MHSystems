package com.mh.systems.hartsbourne.activites;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.constants.ApplicationGlobal;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseDiaryDetailActivity extends AppCompatActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.tbCourse)
    Toolbar tbCourse;
    @Bind(R.id.tvDateCourseEvent)
    TextView tvDateCourseEvent;
    @Bind(R.id.tvTimeCourseEvent)
    TextView tvTimeCourseEvent;
    @Bind(R.id.tvFeeCourseEvent)
    TextView tvFeeCourseEvent;
    @Bind(R.id.tvDescCourseEvent)
    TextView tvDescCourseEvent;
    @Bind(R.id.llPriceGroup)
    LinearLayout llPriceGroup;

    @Bind(R.id.fabJoinCourse)
    FloatingActionButton fabJoinCourse;

    private boolean isDialogVisible;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strCourseTitle, strCourseLogo, strCourseDate, strCourseDayName, strCoursePrize, strCourseDesc, strCourseTime;
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
        strCourseTime = getIntent().getExtras().getString("COURSE_EVENT_TIME");

        setSupportActionBar(tbCourse);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close_white);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(strCourseTitle);

        tvDateCourseEvent.setText(strCourseDayName + ", " + formatDateOfEvent(strCourseDate));
        tvTimeCourseEvent.setText(strCourseTime);

        //tvFeeCourseEvent.setText(strCoursePrize);
        tvDescCourseEvent.setText(strCourseDesc);

        fabJoinCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isDialogVisible) {
                    //Yes button clicked
                    fabJoinCourse.setImageResource(R.mipmap.ic_friend_pending);
                    fabJoinCourse.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C0995B")));

                    Intent mIntent = new Intent(CourseDiaryDetailActivity.this, CustomAlertDialogActivity.class);
                    //Pass theme green color.
                    mIntent.putExtra(ApplicationGlobal.TAG_POPUP_THEME, "#AFD9A1");
                    mIntent.putExtra(ApplicationGlobal.TAG_CALL_FROM, ApplicationGlobal.POSITION_COMPETITIONS);
                    startActivity(mIntent);
                    isDialogVisible = true;
                } else {
                    //Don't display again if already display Alert dialog.
                  //  isDialogVisible = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
