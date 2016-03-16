package com.ucreate.mhsystems.fragments;

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

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.adapter.TabsAdapter.TabsPageAdapter;


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
    TabsPageAdapter pageAdapter;

    /**
     * Declare three bool instances to call api
     * on visible to user.
     */
    public static boolean isOldCourseVisible, isNewCourseVisible;

    private TabLayout.OnTabSelectedListener mArticleTabListener = new TabLayout.OnTabSelectedListener() {
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

        tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("OLD COURSE")/*.setIcon(R.drawable.tabbaricontasks)*/);
        tabLayout.addTab(tabLayout.newTab().setText("NEW COURSE")/*.setIcon(R.drawable.tabbaricontasks)*/);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAFD9A1));

        viewPager = (ViewPager) mRootView.findViewById(R.id.pager);
        pageAdapter = new TabsPageAdapter
                (getActivity().getSupportFragmentManager() , tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Implement Tab selected listener.
        tabLayout.setOnTabSelectedListener(mArticleTabListener);

        return mRootView;
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
        ((BaseActivity)getActivity()).showSnackBarMessages(cdlCourseDiary, strSnackMessage);
    }
}
