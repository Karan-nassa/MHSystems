package com.ucreate.mhsystems.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.CompetitionsActivity;
import com.ucreate.mhsystems.adapter.TabsAdapter.TabsPageAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import butterknife.Bind;

public class MyAccountTabFragment extends Fragment {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MyAccountTabFragment.class.getSimpleName();


    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    TabLayout tabLayout;
    ViewPager viewPager;
    View mRootView;
    TabsPageAdapter pageAdapter;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/

    /**
     * Declare three bool instances to call api
     * on visible to user.
     */
    public static boolean IsFriendVisible1, IsFriendVisible2, IsFinanceVisible;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_my_account_tabs, container, false);

        //Initialize view resources.
        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_friends)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_friends)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_finances)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorF7E59A));


        viewPager = (ViewPager) mRootView.findViewById(R.id.pager);

        Log.e("COUNT:",""+tabLayout.getTabCount());
        pageAdapter = new TabsPageAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), ApplicationGlobal.POSITION_MY_ACCOUNT);
        viewPager.setAdapter(pageAdapter);

        viewPager.setCurrentItem(2);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Implement Tab selected listener.
        tabLayout.setOnTabSelectedListener(mCourseTabListener);

        return mRootView;
    }

    /**
     * Implements a method to update visibility of tab.
     */
    private void setTabVisibleStatus(int iTabPosition) {

        Log.e(LOG_TAG, "" + iTabPosition);

        switch (iTabPosition) {
            case 0:
                IsFriendVisible1 = true;
                IsFriendVisible2 = IsFinanceVisible = false;
                break;
            case 1:
                IsFinanceVisible = true;
                IsFriendVisible1 = IsFriendVisible2 = false;
                break;
            case 2:
                IsFriendVisible2 = true;
                IsFriendVisible1 = IsFinanceVisible = false;
                break;
        }
    }
}
