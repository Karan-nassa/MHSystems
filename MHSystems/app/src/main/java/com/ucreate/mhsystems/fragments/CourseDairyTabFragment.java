package com.ucreate.mhsystems.fragments;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.CourseActivity;
import com.ucreate.mhsystems.adapter.TabsAdapter.TabsPageAdapter;


import java.util.Calendar;

import butterknife.Bind;

public class CourseDairyTabFragment extends Fragment {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = CourseDairyTabFragment.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.cdlCourseDiary)
    CoordinatorLayout cdlCourseDiary;
    @Bind(R.id.toolBar)
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    View mRootView;
    Context context;
    TabsPageAdapter pageAdapter;

    public static OldCourseFragment oldCourseFragment;
    public static NewCourseFragment newCourseFragment;


    Calendar mCalendarInstance;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public String strDate;
    public int iMonth;
    public int iYear;

    //To record total number of days.
    int iNumOfDays;

    public static String strDateFrom; //Start date.
    public static String strDateTo; //End date.

    /**
     * Declare three bool instances to call api
     * on visible to user.
     */
    public static boolean isOldCourseVisible, isNewCourseVisible;

    private TabLayout.OnTabSelectedListener mCourseTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            Log.e("pos: ", "" + tab.getPosition());

            setTabVisibleStatus(tab.getPosition());

            if (tab.getPosition() == 0) {
                //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("OLD COURSE");
            } else if (tab.getPosition() == 1) {
//                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("NEW COURSE");
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            /**
             *  Replace Tab bar icons.
             */
            if (tab.getPosition() == 1) {
                // tab.setIcon(R.drawable.tabbaricontasks);
            } else if (tab.getPosition() == 0) {
                //tab.setIcon(R.drawable.tabbaricontasks);
            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
               /* if(tab.getPosition()==1){
                    tab.setIcon(R.drawable.tabbaricontasks);
                }
                else if(tab.getPosition()==0){
                    tab.setIcon(R.drawable.tabbaricontasks);
                }
                else if(tab.getPosition()==2){
                    tab.setIcon(R.drawable.tabbaricontasks);
                }*/
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_course_diary_tabs, container, false);

        //Initialize view resources.
        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("OLD COURSE")/*.setIcon(R.drawable.tabbaricontasks)*/);
        tabLayout.addTab(tabLayout.newTab().setText("NEW COURSE")/*.setIcon(R.drawable.tabbaricontasks)*/);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorF7E59A));

        viewPager = (ViewPager) mRootView.findViewById(R.id.pager);
        pageAdapter = new TabsPageAdapter
                (getActivity(), getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Initialize the dates of CALENDER to display data according dates.
        setCalenderDates(false); //FALSE means no call from TODAY icon pressed.

        //Implement Tab selected listener.
        tabLayout.setOnTabSelectedListener(mCourseTabListener);

        return mRootView;
    }

    /**
     * Implements a method to display calender
     * instances.
     */
    private void setCalenderDates(boolean isTodayCall) {

        //Initialize CALENDAR instance.
        mCalendarInstance = Calendar.getInstance();

        if (isTodayCall) {
            strDate = "" + mCalendarInstance.get(Calendar.DATE);
            iNumOfDays = mCalendarInstance.get(Calendar.DATE);
        } else {
            strDate = "01";

            //Get total number of days of selected month.
            iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        iMonth = mCalendarInstance.get(Calendar.MONTH);
        iYear = mCalendarInstance.get(Calendar.YEAR);

        //Increment CALENDAR because MONTH start from 0.
        iMonth++;


        //FORMAT : MM-DD-YYYY
        strDateFrom =  iMonth + "/" + strDate + "/" + iYear;

        //FORMAT : MM-DD-YYYY
        strDateTo = "" + iMonth + "/" + iNumOfDays + "/" + iYear;

        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
        Log.e(LOG_TAG, "END DATE : " + strDateTo);

        Log.e("DATA ", "DATE : " + strDate + " MONTH : " + iMonth + " YEAR : " + iYear + " NUM OF DAYS : " + iNumOfDays);
    }

    /**
     * Implements a method to update visibility of tab.
     */
    private void setTabVisibleStatus(int iTabPosition) {

        Log.e("MEDIA STATUS CALL", "" + iTabPosition);

        switch (iTabPosition) {
            case 0:
                isOldCourseVisible = true;
                isNewCourseVisible = false;
                break;
            case 1:
                isOldCourseVisible = false;
                isNewCourseVisible = true;
                break;
        }
    }

    /**
     * Show snackBar message defined in BaseActivity.
     */
    public void showSnackMessage(String strSnackMessage) {
        Log.e(LOG_TAG, strSnackMessage);
        ((BaseActivity) getActivity()).showSnackBarMessages(cdlCourseDiary, strSnackMessage);
    }
}
