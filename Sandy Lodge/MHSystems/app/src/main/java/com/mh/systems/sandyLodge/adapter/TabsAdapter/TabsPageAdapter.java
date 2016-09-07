package com.mh.systems.sandyLodge.adapter.TabsAdapter;

/**
 * Created by karan@mh.co.in for Tabs Page
 * Adapter on 22/12/2015.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mh.systems.sandyLodge.activites.YourAccountActivity;
import com.mh.systems.sandyLodge.constants.ApplicationGlobal;
import com.mh.systems.sandyLodge.fragments.FinanceFragment;
import com.mh.systems.sandyLodge.fragments.FriendsFragment;
import com.mh.systems.sandyLodge.fragments.HandicapFragment;
import com.mh.systems.sandyLodge.fragments.MembersFragment;
import com.mh.systems.sandyLodge.fragments.MyDetailsFragment;


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

    Context context;

    /**
     * Tab Page Adapter initialization.
     * <p/>
     *
     * @param fm
     * @param NumOfTabs
     * @param iFromWhat
     */
    public TabsPageAdapter(FragmentManager fm, int NumOfTabs, int iFromWhat) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.iFromWhat = iFromWhat;
    }

    public TabsPageAdapter(Context context, FragmentManager fm, int NumOfTabs, int iFromWhat) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.iFromWhat = iFromWhat;
        this.context = context;
    }

    /**
     * @param position
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
                return loadMyAccountTabs(position);

            case ApplicationGlobal.POSITION_MEMBERS:
                return loadMembersTab(position);
        }
        return null;
    }

    /**
     * Load Course Diary Tabs i.e
     * <br> 1. Old Courses
     * <br> 2. New Courses
     * <p/>
     *
     * @param iPosition
     * @return Fragment
     */
    private Fragment loadCourseDiaryTabs(int iPosition) {

//        switch (iPosition) {
//            case 0:
//                OldCourseFragment oldCourseFragment = new OldCourseFragment();
//                return oldCourseFragment;
//
//            case 1:
//                NewCourseFragment newCourseFragment = new NewCourseFragment();
//                return newCourseFragment;
//
//            default:
        return null;
//        }
    }

    /**
     * Load MEMBERS Tabs i.e
     * <br> 1. Members
     * <br> 2. Friends
     * <p/>
     *
     * @param iPosition
     * @return Fragment
     */
    private Fragment loadMembersTab(int iPosition) {

        switch (iPosition) {
            case 0:
                MembersFragment membersFragment = new MembersFragment();
                return membersFragment;

            case 1:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            default:
                return null;
        }
    }

    /**
     * Load My Account tabs i.e
     * <br> 1. My Details
     * <br> 2. Handicap
     * <br> 3. Finances
     * <p/>
     *
     * @param iPosition
     * @return Fragment
     */
    private Fragment loadMyAccountTabs(int iPosition) {

        switch (iPosition) {
            case 0:
                MyDetailsFragment myDetailsFragment = new MyDetailsFragment();
                ((YourAccountActivity) context).setFragmentInstance(myDetailsFragment);
                return myDetailsFragment;

            case 1:
                HandicapFragment handicapFragment = new HandicapFragment();
                ((YourAccountActivity) context).setFragmentInstance(handicapFragment);
                return handicapFragment;

            case 2:
                FinanceFragment financeFragment = new FinanceFragment();
                ((YourAccountActivity) context).setFragmentInstance(financeFragment);
                return financeFragment;
        }
        return null;
    }

    /**
     * Load Competitions tabs i.e.
     * <br> 1. My Events.
     * <br> 2. Current.
     * <br> 3. Completed.
     * <br> 4. Future.
     * <p/>
     *
     * @param iPosition
     * @return Fragment
     */
    private Fragment loadCompetitionsEvent(int iPosition) {

       /* switch (iPosition) {
            case 0:
                EventsFragment competitionsTabFragment = new EventsFragment();
                return competitionsTabFragment;

            case 1:
                CurrentFragment currentFragment = new CurrentFragment();
                return currentFragment;

            case 2:
                CompletedFragment compleTabFragment = new CompletedFragment();
                return compleTabFragment;

            case 3:
                UpcomingFragment upcomingFragment = new UpcomingFragment();
                return upcomingFragment;
            default:
                return null;
        }*/
        return null;
    }

    /**
     * @return Total number of Tabs.
     */
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}