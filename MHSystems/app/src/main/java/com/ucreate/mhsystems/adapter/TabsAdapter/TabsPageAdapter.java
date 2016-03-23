package com.ucreate.mhsystems.adapter.TabsAdapter;

/**
 * Created by karan@ucreate.co.in for Tabs Page
 * Adapter on 22/12/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.fragments.CompetitionsTabFragment;
import com.ucreate.mhsystems.fragments.CompletedTabFragment;
import com.ucreate.mhsystems.fragments.CurrentTabFragment;
import com.ucreate.mhsystems.fragments.FutureTabFragment;
import com.ucreate.mhsystems.fragments.MyEventsTabFragment;
import com.ucreate.mhsystems.fragments.CourseDairyTabFragment;
import com.ucreate.mhsystems.fragments.CourseFragmentTabsData;


/**
 * Tab Page Adapter initialization.
 * <p/>
 * <br> @param  Fm        : Instance of Fragment Manager
 * <br> @param  NumOfTabs : Total number of Instance
 * <br> @param  iFromWhat : Value 1 means call from Article and 2 from Media
 */
public class TabsPageAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    /**
     * This Tab Page Adapter is used from three sides:
     * # 1. Course Dairy Tabs Fragment
     * # 2. Competitions Tabs Fragment
     */
    private int iFromWhat;


    /**
     * Tab Page Adapter initialization.
     * <p/>
     * <br> @param  Fm        : Instance of Fragment Manager
     * <br> @param  NumOfTabs : Total number of Instance
     * <br> @param  iFromWhat : Value 1 means call from Article and 2 from Media
     */
    public TabsPageAdapter(FragmentManager fm, int NumOfTabs, int iFromWhat) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.iFromWhat = iFromWhat;
    }

    /**
     * @return Selected tab.
     */
    @Override
    public Fragment getItem(int position) {


        if (iFromWhat == ApplicationGlobal.POSITION_COURSE_DIARY) {
            return loadArticleTabs(position);
        } else if (iFromWhat == ApplicationGlobal.POSITION_COMPETITIONS) {
            return loadCompetitionsEvent(position);
        }
        return null;
    }

    /**
     * Load Article Tabs i.e NEWS, INTERVIEWS and GUIDES.
     * <p/>
     * <br> @return Fragment
     */
    private Fragment loadArticleTabs(int iPosition) {

        switch (iPosition) {
            case 0:
                CourseDairyTabFragment.courseFragmentTabsData = new CourseFragmentTabsData();
                return CourseDairyTabFragment.courseFragmentTabsData;

            case 1:
                CourseDairyTabFragment.courseFragmentTabsData = new CourseFragmentTabsData();
                return CourseDairyTabFragment.courseFragmentTabsData;

            default:
                return null;
        }
    }

    /**
     * Load Article Tabs i.e NEWS, INTERVIEWS and GUIDES.
     * <p/>
     * <br> @return Fragment
     */
    private Fragment loadCompetitionsEvent(int iPosition) {
        switch (iPosition) {
            case 0:
                MyEventsTabFragment competitionsTabFragment = new MyEventsTabFragment();
                return competitionsTabFragment;

            case 1:
                CompletedTabFragment compleTabFragment = new CompletedTabFragment();
                return compleTabFragment;

            case 2:
                CurrentTabFragment currentTabFragment = new CurrentTabFragment();
                return currentTabFragment;

            case 3:
                FutureTabFragment futureTabFragment = new FutureTabFragment();
                return futureTabFragment;
            default:
                return null;
        }
    }

    /**
     * @return Total number of Tabs.
     */
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}