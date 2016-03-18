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
import android.widget.Toast;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.CourseActivity;
import com.ucreate.mhsystems.adapter.TabsAdapter.TabsPageAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;


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
    public static String strDate;
    public static int iMonth;
    public static int iYear;

    //To record total number of days.
    int iNumOfDays;

    public static String strDateFrom; //Start date.
    public static String strDateTo; //End date.

    /**
     * Declare three bool instances to call api
     * on visible to user.
     */
    public static boolean isOldCourseVisible, isNewCourseVisible;

    /**
     * This instance used to identify which tab is
     * selected and describe CourseKey.
     * <p>
     * <br> 1.1 for OLD COURSE
     * <br> 1.3 for NEW COURSE
     */
    public static String mCourseKey = "1.1";

    private TabLayout.OnTabSelectedListener mCourseTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            Log.e("pos: ", "" + tab.getPosition());

            setTabVisibleStatus(tab.getPosition());

            if (tab.getPosition() == 0) {
                mCourseKey = "1.1";
            } else if (tab.getPosition() == 1) {
                mCourseKey = "1.3";
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    /**
     * Constructor to set dates.
     */
    public CourseDairyTabFragment() {

        //Initialize the dates of CALENDER to display data according dates.
        //  setCalenderDates(false); //FALSE means no call from TODAY icon pressed.
    }

    /**
     * Constructor to set action and change
     * CALENDAR accordingly.
     */
    public CourseDairyTabFragment(int action) {

        setCalenderDates(action);
    }

    /**
     * Implements a method to navigate to
     * PREVIOUS month.
     */
    private void previousMonthSelected() {

        if (iMonth == 1) {

        } else {
            iMonth--;
        }
        // Toast.makeText(getActivity(), "Previous Month listener pressed", Toast.LENGTH_LONG).show();
    }

    /**
     * Implements a method to navigate to
     * NEXT month.
     */
    private void nextMonthSelected() {

        if (iMonth == 12) {

        } else {
            iMonth++;
        }

        // Toast.makeText(getActivity(), "Next Month listener pressed", Toast.LENGTH_LONG).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_course_diary_tabs, container, false);

        //Initialize view resources.
        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("OLD COURSE"));
        tabLayout.addTab(tabLayout.newTab().setText("NEW COURSE"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorF7E59A));

        viewPager = (ViewPager) mRootView.findViewById(R.id.pager);
        pageAdapter = new TabsPageAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Implement Tab selected listener.
        tabLayout.setOnTabSelectedListener(mCourseTabListener);

        return mRootView;
    }

    /**
     * Implements a method to display calender
     * instances.
     */
    private void setCalenderDates(int iAction) {

        //Initialize CALENDAR instance.
        mCalendarInstance = Calendar.getInstance();

        //Do nothing. Just load data according current date.
        strDate = "01";

        //Get total number of days of selected month.
        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        iYear = mCalendarInstance.get(Calendar.YEAR);

        switch (iAction) {

            case ApplicationGlobal.ACTION_NOTHING:
                //Get MONTH and YEAR.
                iMonth = mCalendarInstance.get(Calendar.MONTH);
                //Increment CALENDAR because MONTH start from 0.

                iMonth++;

                break;

            case ApplicationGlobal.ACTION_PREVIOUS_MONTH:

                if (iMonth == 1) {

                } else {
                    iMonth--;
                }
                break;

            case ApplicationGlobal.ACTION_NEXT_MONTH:

                if (iMonth == 12) {

                } else {
                    iMonth++;
                }
                break;

            case ApplicationGlobal.ACTION_TODAY:
                //Initialize the dates of CALENDER to display data according dates.
                strDate = "" +  mCalendarInstance.get(Calendar.DATE);
                iNumOfDays =  mCalendarInstance.get(Calendar.DATE);
                break;
        }

        //FORMAT : MM-DD-YYYY
        strDateFrom = iMonth + "/" + strDate + "/" + iYear;

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
