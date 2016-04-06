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
import com.ucreate.mhsystems.fragments.CompletedFragment;
import com.ucreate.mhsystems.fragments.CurrentFragment;
import com.ucreate.mhsystems.fragments.FinanceFragment;
import com.ucreate.mhsystems.fragments.HandicapFragment;
import com.ucreate.mhsystems.fragments.MyDetailsFragment;
import com.ucreate.mhsystems.fragments.FutureFragment;
import com.ucreate.mhsystems.fragments.MyEventsFragment;
import com.ucreate.mhsystems.fragments.CourseDairyTabFragment;
import com.ucreate.mhsystems.fragments.CourseFragment;


/**
 * Tab Page Adapter initialization.
 * <p>
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
     * <p>
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

        switch (iFromWhat) {
            case ApplicationGlobal.POSITION_COURSE_DIARY:
                return loadCourseDiaryTabs(position);

            case ApplicationGlobal.POSITION_COMPETITIONS:
                return loadCompetitionsEvent(position);

            case ApplicationGlobal.POSITION_MY_ACCOUNT:
                Log.e("HERE","HERE");
                return loadMyAccountTabs(position);

        }
        return null;
    }

    /**
     * Load Article Tabs i.e
     * <br> 1. Old
     * <br> 2. New Courses
     * <p>
     * <br> @return Fragment
     */
    private Fragment loadCourseDiaryTabs(int iPosition) {

        switch (iPosition) {
            case 0:
                CourseDairyTabFragment.courseFragment = new CourseFragment();
                return CourseDairyTabFragment.courseFragment;

            case 1:
                CourseDairyTabFragment.courseFragment = new CourseFragment();
                return CourseDairyTabFragment.courseFragment;

            default:
                return null;
        }
    }

    /**
     * Load My Account tabs i.e
     * <br> 1. My Details
     * <br> 2. Handicap
     * <br> 3. Finances
     * <p>
     * <br> @return Fragment
     */
    private Fragment loadMyAccountTabs(int iPosition) {

        switch (iPosition) {
            case 0:
               MyDetailsFragment financeFragment1 = new MyDetailsFragment();
                return financeFragment1;

            case 1:
                HandicapFragment handicapFragment = new HandicapFragment();
                return handicapFragment;

            case 2:
                FinanceFragment financeFragment = new FinanceFragment();
                return financeFragment;

            default:
                return null;
        }
    }

    /**
     * Load Competitions tabs i.e.
     * <br> 1. My Events.
     * <br> 2. Completed.
     * <br> 3. Current.
     * <br> 4. Future.
     * <p>
     * <br> @return Fragment
     */
    private Fragment loadCompetitionsEvent(int iPosition) {

        switch (iPosition) {
            case 0:
                MyEventsFragment competitionsTabFragment = new MyEventsFragment();
                return competitionsTabFragment;

            case 1:
                CompletedFragment compleTabFragment = new CompletedFragment();
                return compleTabFragment;

            case 2:
                CurrentFragment currentFragment = new CurrentFragment();
                return currentFragment;

            case 3:
                FutureFragment futureFragment = new FutureFragment();
                return futureFragment;
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