package com.ucreate.mhsystems.activites;

import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.RecyclerAdapter.CourseDiaryRecyclerAdapter;
import com.ucreate.mhsystems.utils.RecycleViewDividerDecoration;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseDairyActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.cdlCourseDiary)
    CoordinatorLayout cdlCourseDiary;
    @Bind(R.id.rvCourseDiary)
    RecyclerView rvCourseDiary;
    @Bind(R.id.toolBar)
    Toolbar toolbar;
    @Bind(R.id.tvCourseSchedule)
    TextView tvCourseSchedule;
    @Bind(R.id.tvCourseTitle)
    TextView tvCourseTitle;
    RecyclerView.Adapter recyclerViewAdapter;
    ArrayList<CourseDiaryData> arrayListCourseData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_dairy);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(this);

        //Let's first set up toolbar
        setupToolbar();

        RecyclerView.ItemDecoration itemDecoration =
                new RecycleViewDividerDecoration(CourseDairyActivity.this, LinearLayoutManager.VERTICAL);
        rvCourseDiary.addItemDecoration(itemDecoration);

        /**
         *It is must to set a Layout Manager For Recycler View
         *As per docs ,
         *RecyclerView allows client code to provide custom layout arrangements for child views.
         *These arrangements are controlled by the RecyclerView.LayoutManager.
         *A LayoutManager must be provided for RecyclerView to function.
         */
        rvCourseDiary.setLayoutManager(new LinearLayoutManager(CourseDairyActivity.this));


        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(CourseDairyActivity.this)) {
            //Method to hit Squads API.
            //requestFriendsListService();
            LoadCourseData();
        } else {
            showSnackBarMessages(cdlCourseDiary, getResources().getString(R.string.error_no_internet));
        }
    }

    /**
     * Initialize tool bar to display UI title with
     * EVENTS date and Back button.
     */
    void setupToolbar() {
        setSupportActionBar(toolbar);
        tvCourseTitle.setText(getResources().getString(R.string.title_course_diary));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Implements a method to load Course Diary
     * data statically.
     */
    private void LoadCourseData() {
        //Set Friends Detail Recycler Adapter.
        recyclerViewAdapter = new CourseDiaryRecyclerAdapter(CourseDairyActivity.this, arrayListCourseData);
        rvCourseDiary.setAdapter(recyclerViewAdapter);
    }
}
