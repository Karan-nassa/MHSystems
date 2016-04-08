package com.ucreate.mhsystems.fragments;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.CompetitionsActivity;
import com.ucreate.mhsystems.activites.CourseDiaryActivity;
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
    @Bind(R.id.toolBar)
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    View mRootView;
    Context context;
    TabsPageAdapter pageAdapter;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static String strDateFrom; //Start date.
    public static String strDateTo; //End date.
    String strNameOfMonth = "MARCH 2016";

    public static int iLastTabPosition;

    //Used to display DATA according date of COMPLETED tab.
    public static int iActionCalendarStates;

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

            iLastTabPosition = tab.getPosition();

            CompetitionsActivity.resetMonthsNavigationIcons();
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
    @SuppressLint("ValidFragment")
    public CompetitionsTabFragment(int action) {
        iActionCalendarStates = action;
        setCalenderDates(action);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_competitions_tabs, container, false);

        //Initialize view resources.
        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_events)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_current)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_completed)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_upcoming)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.color4942AA));

        viewPager = (ViewPager) mRootView.findViewById(R.id.pager);
        pageAdapter = new TabsPageAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), ApplicationGlobal.POSITION_COMPETITIONS);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(iLastTabPosition);

        //Set MONTH title.
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

        switch (iAction) {

            case ApplicationGlobal.ACTION_NOTHING:

                break;

            case ApplicationGlobal.ACTION_PREVIOUS_MONTH:

                //IF COMPLETED TAB SELECTED THEN DISPLAY DATA FROM 1st JAN of current year.
                if (iLastTabPosition == 2) {

                    if (CompetitionsActivity.iMonth == Calendar.JANUARY) {

                        CompetitionsActivity.setPreviousButton(false);
                    } else {

                        CompetitionsActivity.iMonth--;

                        //Do nothing. Just load data according current date.
                        CompetitionsActivity.strDate = "01";

                        ((CompetitionsActivity) getActivity()).getNumberofDays();
                    }
                } else {

                    /**
                     *  User cannot navigate back to current
                     *  month.
                     */
                    if (/*iMonth == 1 ||*/ CompetitionsActivity.iMonth > CompetitionsActivity.iCurrentMonth) {
                        CompetitionsActivity.iMonth--;

                        if (CompetitionsActivity.iMonth == CompetitionsActivity.iCurrentMonth) {
                            //Do nothing. Just load data according current date.
                            CompetitionsActivity.strDate = CompetitionsActivity.strCurrentDate;
                        } else {
                            //Do nothing. Just load data according current date.
                            CompetitionsActivity.strDate = "01";
                        }

                        ((CompetitionsActivity) getActivity()).getNumberofDays();
                    }else{
                        CompetitionsActivity.setPreviousButton(false);
                    }
                }
                break;

            case ApplicationGlobal.ACTION_NEXT_MONTH:

                if (CompetitionsActivity.iMonth == 12) {

                } else {
                    CompetitionsActivity.iMonth++;

                    if(CompetitionsActivity.iMonth > CompetitionsActivity.iCurrentMonth){
                        CompetitionsActivity.setPreviousButton(true);
                    }

                    //Do nothing. Just load data according current date.
                    CompetitionsActivity.strDate = "01";

                    ((CompetitionsActivity) getActivity()).getNumberofDays();
                }
                break;

            case ApplicationGlobal.ACTION_TODAY:
                CompetitionsActivity.mCalendarInstance.set(Calendar.YEAR, CompetitionsActivity.iCurrentYear);
                CompetitionsActivity.mCalendarInstance.set(Calendar.MONTH, (CompetitionsActivity.iCurrentMonth - 1));
                CompetitionsActivity.mCalendarInstance.set(Calendar.DATE, Integer.parseInt(CompetitionsActivity.strCurrentDate));

                //Initialize the dates of CALENDER to display data according dates.
                CompetitionsActivity.strDate = "" + CompetitionsActivity.mCalendarInstance.get(Calendar.DATE);
                CompetitionsActivity.iNumOfDays = CompetitionsActivity.mCalendarInstance.get(Calendar.DATE);

                //Get MONTH and YEAR.
                CompetitionsActivity.iMonth = (CompetitionsActivity.mCalendarInstance.get(Calendar.MONTH) + 1);
                break;

            case ApplicationGlobal.ACTION_CALENDAR:

                CompetitionsActivity.iNumOfDays = Integer.parseInt(CompetitionsActivity.strDate);
                break;
        }

        //FORMAT : MM-DD-YYYY
        strDateFrom = "" + CompetitionsActivity.iMonth + "/" + CompetitionsActivity.strDate + "/" + CompetitionsActivity.iYear;

        //FORMAT : MM-DD-YYYY
        strDateTo = "" + CompetitionsActivity.iMonth + "/" + CompetitionsActivity.iNumOfDays + "/" + CompetitionsActivity.iYear;

        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
        Log.e(LOG_TAG, "END DATE : " + strDateTo);

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(CompetitionsActivity.iMonth))) + " " + CompetitionsActivity.iYear;

        Log.e(LOG_TAG, strNameOfMonth);
        Log.e("DATA ", "DATE : " + CompetitionsActivity.strDate + " MONTH : " + CompetitionsActivity.iMonth + " YEAR : " + CompetitionsActivity.iYear + " NUM OF DAYS : " + CompetitionsActivity.iNumOfDays);
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
}
