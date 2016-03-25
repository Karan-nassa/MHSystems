package com.ucreate.mhsystems.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.CompetitionsActivity;
import com.ucreate.mhsystems.adapter.TabsAdapter.TabsPageAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import butterknife.Bind;

public class CompetitionsTabFragment extends Fragment {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = CompetitionsTabFragment.class.getSimpleName();


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

    //Create instance of Fragment.
    public static MyEventsTabFragment myEventsTabFragment;


    Calendar mCalendarInstance;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static String strDate;
    public static int iMonth, iCurrentMonth;
    public static int iYear;

    //To record total number of days.
    int iNumOfDays;

    public static String strDateFrom; //Start date.
    public static String strDateTo; //End date.
    String strNameOfMonth = "MARCH 2016";

    /**
     * Declare three bool instances to call api
     * on visible to user.
     */
    public static boolean isOldCourseVisible, isNewCourseVisible;

    private TabLayout.OnTabSelectedListener mCourseTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            setTabVisibleStatus(tab.getPosition());
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
    public CompetitionsTabFragment() {

    }


    /**
     * Constructor to set action and change
     * CALENDAR accordingly.
     */
    public CompetitionsTabFragment(int action) {
        setCalenderDates(action);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_competitions_tabs, container, false);

        //Initialize view resources.
        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("My Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.addTab(tabLayout.newTab().setText("Current"));
        tabLayout.addTab(tabLayout.newTab().setText("Future"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color4942AA));

        viewPager = (ViewPager) mRootView.findViewById(R.id.pager);
        pageAdapter = new TabsPageAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), ApplicationGlobal.POSITION_COMPETITIONS);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        ((CompetitionsActivity) getActivity()).setTitleBar(strNameOfMonth);

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

        //Get total number of days of selected month.
        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        iYear = mCalendarInstance.get(Calendar.YEAR);

        switch (iAction) {

            case ApplicationGlobal.ACTION_NOTHING:
                //Get MONTH and YEAR.
                iMonth = mCalendarInstance.get(Calendar.MONTH);

                //Increment CALENDAR because MONTH start from 0.
                //Do nothing. Just load data according current date.
                strDate = ""+mCalendarInstance.get(Calendar.DATE);
                iCurrentMonth = mCalendarInstance.get(Calendar.MONTH) + 1;

                iMonth++;
                break;

            case ApplicationGlobal.ACTION_PREVIOUS_MONTH:

//                if (iMonth == 1) {
//
//                } else {
//                    strDate = "01";
//                    iMonth--;
//                }
                /**
                 *  User cannot navigate back to current
                 *  month.
                 */
                if (/*iMonth == 1 ||*/ iMonth > iCurrentMonth) {
                    iMonth--;

                    if (iMonth == iCurrentMonth) {
                        //Initialize the dates of CALENDER to display data according dates.
                        strDate = "" + mCalendarInstance.get(Calendar.DATE);
                    } else {
                        strDate = "01";
                    }
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
                strDate = "" + mCalendarInstance.get(Calendar.DATE);
                iNumOfDays = mCalendarInstance.get(Calendar.DATE);

                //Get MONTH and YEAR.
                iMonth = mCalendarInstance.get(Calendar.MONTH);
                //Increment CALENDAR because MONTH start from 0.
                iMonth++;
                break;
        }

        //FORMAT : MM-DD-YYYY
        strDateFrom = iMonth + "/" + strDate + "/" + iYear;

        //FORMAT : MM-DD-YYYY
        strDateTo = "" + iMonth + "/" + iNumOfDays + "/" + iYear;

        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
        Log.e(LOG_TAG, "END DATE : " + strDateTo);

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(iMonth))) + " " + iYear;

        Log.e(LOG_TAG, strNameOfMonth);
        Log.e("DATA ", "DATE : " + strDate + " MONTH : " + iMonth + " YEAR : " + iYear + " NUM OF DAYS : " + iNumOfDays);
    }

    /**
     * Declares a method to get NAME of MONTH by passing
     * month value.
     */
    public String getMonth(int month) {
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