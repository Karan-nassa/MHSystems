package com.ucreate.mhsystems.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.CourseDiaryActivity;
import com.ucreate.mhsystems.adapter.TabsAdapter.TabsPageAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;


import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    static Context context;
    TabsPageAdapter pageAdapter;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static String strDateFrom; //Start date.
    public static String strDateTo; //End date.
    public static String strNameOfMonth = "MARCH 2016";

    /**
     * Declare three bool instances to call api
     * on visible to user.
     */
    public static boolean isOldCourseVisible, isNewCourseVisible;

    /**
     * To record the last position of selected tab.
     */
    public static int iLastTabPosition, iLastCalendarAction;


    private TabLayout.OnTabSelectedListener mCourseTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            setTabVisibleStatus(tab.getPosition());

            iLastTabPosition = tab.getPosition();

            CourseDiaryActivity.resetMonthsNavigationIcons();
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
        context = getActivity();
    }

    /**
     * Constructor to set action and change
     * CALENDAR accordingly.
     */
    @SuppressLint("ValidFragment")
    public CourseDairyTabFragment(int action) {
        /**
         *  'iLastCalendarAction' will represned Today, CALENDAR, previous or next actions in
         *  Tabs for Scroll down functionality.
         */
        iLastCalendarAction = action;
        setCalenderDates(action);
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

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorF7E59A));

        viewPager = (ViewPager) mRootView.findViewById(R.id.pager);
        pageAdapter = new TabsPageAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), ApplicationGlobal.POSITION_COURSE_DIARY);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(iLastTabPosition);

        ((CourseDiaryActivity) getActivity()).setTitleBar(strNameOfMonth);

        //Implement Tab selected listener.
        tabLayout.setOnTabSelectedListener(mCourseTabListener);

        return mRootView;
    }

    /**
     * Implements a method to display calender
     * instances.
     */
    private void setCalenderDates(int iAction) {

        switch (iAction) {

            case ApplicationGlobal.ACTION_NOTHING:

                createDateForData();
                break;

            case ApplicationGlobal.ACTION_PREVIOUS_MONTH:

                callPrevMonthAction();

                break;

            case ApplicationGlobal.ACTION_NEXT_MONTH:

                callNextMonthAction();

                break;

            case ApplicationGlobal.ACTION_TODAY:
                //Reset To current date.
//                CourseDiaryActivity.mCalendarInstance.set(Calendar.YEAR, CourseDiaryActivity.iCurrentYear);
//                CourseDiaryActivity.mCalendarInstance.set(Calendar.MONTH, (CourseDiaryActivity.iCurrentMonth - 1));
//                CourseDiaryActivity.mCalendarInstance.set(Calendar.DATE, Integer.parseInt(CourseDiaryActivity.strCurrentDate));

                CourseDiaryActivity.resetCalendar();

                /** +++++ START OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++ **/
                callTodayScrollEvents();
                /** +++++ END OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++ **/

                break;

            case ApplicationGlobal.ACTION_CALENDAR:


                //CourseDiaryActivity.iNumOfDays = Integer.parseInt(CourseDiaryActivity.strDate);
                callTodayScrollEvents();
                break;
        }
    }

    /**
     * Implements a method call for NEXT MONTH action called on
     * NEXT icon navigation and on SCROLL down to load
     * more COURSE EVENTS.
     */
    public static void callNextMonthAction() {
        if (CourseDiaryActivity.iMonth == 12) {

        } else {
            CourseDiaryActivity.iMonth++;

            if (CourseDiaryActivity.iMonth > CourseDiaryActivity.iCurrentMonth) {
                CourseDiaryActivity.setPreviousButton(true);
            }

            //Do nothing. Just load data according current date.
            CourseDiaryActivity.strDate = "01";

            ((CourseDiaryActivity) context).getNumberofDays();
        }

        createDateForData();
    }

    /**
     * Implements a method call for NEXT MONTH action called on
     * NEXT icon navigation and on SCROLL down to load
     * more COURSE EVENTS.
     */
    public static void callPrevMonthAction() {
        /**
         *  User cannot navigate back to current
         *  month.
         */
        if (/*iMonth == 1 ||*/ CourseDiaryActivity.iMonth > CourseDiaryActivity.iCurrentMonth) {
            CourseDiaryActivity.iMonth--;

            if (CourseDiaryActivity.iMonth == CourseDiaryActivity.iCurrentMonth) {
                //Do nothing. Just load data according current date.
                CourseDiaryActivity.strDate = CourseDiaryActivity.strCurrentDate;
            } else {
                //Do nothing. Just load data according current date.
                CourseDiaryActivity.strDate = "01";
            }

            ((CourseDiaryActivity) context).getNumberofDays();
        }

        createDateForData();
    }

    /**
     * Implements Today functionality to display CURRENT date to
     * next specific dates.
     */
    private static void callTodayScrollEvents() {

        // Create a calendar object and set year and month
        CourseDiaryActivity.mCalendarInstance = new GregorianCalendar(CourseDiaryActivity.iYear, (CourseDiaryActivity.iMonth - 1), Integer.parseInt(CourseDiaryActivity.strDate));

        // Get the number of days in that month
        CourseDiaryActivity.iNumOfDays = CourseDiaryActivity.mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        CourseDiaryActivity.iLessDays = CourseDiaryActivity.iNumOfDays - Integer.parseInt(CourseDiaryActivity.strDate);

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(CourseDiaryActivity.iMonth))) + " " + CourseDiaryActivity.iYear;

        Log.e(LOG_TAG, "callTodayScrollEvents : " + strNameOfMonth);
        int iDate;
        if (CourseDiaryActivity.iLessDays < ApplicationGlobal.LOAD_MORE_VALUES) {

            strDateFrom = CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.strDate + "/" + CourseDiaryActivity.iYear;
            /**
             *  Suppose Current date is near to end of Month then increment to
             *  Next Month.
             */
            CourseDiaryActivity.iMonth += 1;
            CourseDiaryActivity.strDate = "" + (ApplicationGlobal.LOAD_MORE_VALUES - CourseDiaryActivity.iLessDays);
            strDateTo = CourseDiaryActivity.iMonth + "/" + (CourseDiaryActivity.strDate) + "/" + CourseDiaryActivity.iYear;
        } else {
            strDateFrom = CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.strDate + "/" + CourseDiaryActivity.iYear;

            iDate = Integer.parseInt(CourseDiaryActivity.strDate);
            CourseDiaryActivity.strDate = "" + (iDate + ApplicationGlobal.LOAD_MORE_VALUES);

            strDateTo = CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.strDate + "/" + CourseDiaryActivity.iYear;
        }


        Log.e(LOG_TAG, "strDate " + strDateFrom);
        Log.e(LOG_TAG, "strDateTo " + strDateTo);
    }

    /**
     * Finally, create DATE to get data and for CALENDAR, PREVIOUS/NEXT MONTH
     * functionality.
     */
    public static void createDateForData() {
        //FORMAT : MM-DD-YYYY
        strDateFrom = CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.strDate + "/" + CourseDiaryActivity.iYear;

        //FORMAT : MM-DD-YYYY
        strDateTo = "" + CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.iNumOfDays + "/" + CourseDiaryActivity.iYear;

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(CourseDiaryActivity.iMonth))) + " " + CourseDiaryActivity.iYear;

        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
        Log.e(LOG_TAG, "END DATE : " + strDateTo);
        Log.e(LOG_TAG, "NAME OF MONTH : " + strNameOfMonth);
    }


    /**
     * Declares a method to get NAME of MONTH by passing
     * month value.
     */
    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
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