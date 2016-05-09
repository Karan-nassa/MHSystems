package com.ucreate.mhsystems.activites;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompetitionsDetailActivity extends AppCompatActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.toolbarComp)
    Toolbar toolbarComp;
    //    @Bind(R.id.btJoinEvent)
//    Button btJoinEvent;
//    @Bind(R.id.tvTitleCourseEvent)
//    TextView tvTitleCourseEvent;
    @Bind(R.id.tvDateCourseEvent)
    TextView tvDateCourseEvent;
    @Bind(R.id.tvTimeCourseEvent)
    TextView tvTimeCourseEvent;
    @Bind(R.id.tvFeeCourseEvent)
    TextView tvFeeCourseEvent;
    @Bind(R.id.tvDescCourseEvent)
    TextView tvDescCourseEvent;
//    @Bind(R.id.llHomeIcon)
//    LinearLayout llHomeIcon;
    @Bind(R.id.llPriceGroup)
    LinearLayout llPriceGroup;

    @Bind(R.id.fabJoinCompetition)
    FloatingActionButton fabJoinCompetition;


    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strEventTitle, strEventLogo, strEventDate, strEventTime, strEventPrize, strEventDesc;
    boolean isEventJoin;
    private boolean isDialogVisible;

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

            if (!isDialogVisible) {
                Intent mIntent = new Intent(CompetitionsDetailActivity.this, CustomAlertDialogActivity.class);
                //Pass theme green color.
                mIntent.putExtra(ApplicationGlobal.TAG_POPUP_THEME, "#F6EA8C");
                mIntent.putExtra(ApplicationGlobal.TAG_CALL_FROM, ApplicationGlobal.POSITION_COMPETITIONS);
                startActivity(mIntent);
                isDialogVisible = true;
            } else {
                //Don't display again if already display Alert dialog.
                isDialogVisible = false;
            }
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

        setSupportActionBar(toolbarComp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(strEventTitle);

        //Set Content on each Event of Course Diary.
        // tvTitleCourseEvent.setText(strEventTitle);
        /*btJoinEvent.setText(isEventJoin ? getResources().getString(R.string.text_joined) :
               getResources().getString(R.string.text_join));
       */
        tvDateCourseEvent.setText(strEventDate);
        tvTimeCourseEvent.setText(strEventTime);

        tvFeeCourseEvent.setText("£" + strEventPrize + " " + getResources().getString(R.string.title_competitions_prize));
        tvDescCourseEvent.setText(strEventDesc);

        //Set Home icon listener.
        //llHomeIcon.setOnClickListener(mHomeListener);
        //  btJoinEvent.setOnClickListener(mJoinListener);

        fabJoinCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isDialogVisible) {
                    //Yes button clicked
                    fabJoinCompetition.setImageResource(R.mipmap.ic_friend_pending);
                    fabJoinCompetition.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#95D32B")));

                    Intent mIntent = new Intent(CompetitionsDetailActivity.this, CustomAlertDialogActivity.class);
                    //Pass theme green color.
                    mIntent.putExtra(ApplicationGlobal.TAG_POPUP_THEME, "#F6EA8C");
                    mIntent.putExtra(ApplicationGlobal.TAG_CALL_FROM, ApplicationGlobal.POSITION_COMPETITIONS);
                    startActivity(mIntent);
                    isDialogVisible = true;
                } else {
                    //Don't display again if already display Alert dialog.
                    isDialogVisible = false;
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
}
