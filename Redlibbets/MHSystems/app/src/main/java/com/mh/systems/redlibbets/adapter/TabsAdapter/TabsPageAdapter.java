package com.mh.systems.redlibbets.adapter.TabsAdapter;

/**
 * Created by karan@ucreate.co.in for Tabs Page
 * Adapter on 22/12/2015.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mh.systems.redlibbets.activites.BaseActivity;
import com.mh.systems.redlibbets.activites.YourAccountActivity;
import com.mh.systems.redlibbets.constants.ApplicationGlobal;
import com.mh.systems.redlibbets.fragments.ContactUsFragment;
import com.mh.systems.redlibbets.fragments.EligibleFriendsFragment;
import com.mh.systems.redlibbets.fragments.EligibleMemberFragment;
import com.mh.systems.redlibbets.fragments.FinanceFragment;
import com.mh.systems.redlibbets.fragments.FriendsFragment;
import com.mh.systems.redlibbets.fragments.HandicapFragment;
import com.mh.systems.redlibbets.fragments.MembersFragment;
import com.mh.systems.redlibbets.fragments.MyDetailsFragment;
import com.mh.systems.redlibbets.fragments.NoInternetFragment;


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
    private boolean isHandicapAvailable;

    Context context;

    /**
     * Tab Page Adapter initialization.
     * <p/>
     *
     * @param fm
     * @param NumOfTabs
     * @param iFromWhat
     */
   /* public TabsPageAdapter(FragmentManager fm, int NumOfTabs, int iFromWhat) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.iFromWhat = iFromWhat;
    }*/
    public TabsPageAdapter(Context context, FragmentManager supportFragmentManager, int NumOfTabs, int iFromWhat) {
        super(supportFragmentManager);
        this.mNumOfTabs = NumOfTabs;
        this.iFromWhat = iFromWhat;
        this.context = context;
    }

    public TabsPageAdapter(Context context, FragmentManager supportFragmentManager, int NumOfTabs, int iFromWhat, boolean isHandicapAvailable) {
        super(supportFragmentManager);
        this.mNumOfTabs = NumOfTabs;
        this.iFromWhat = iFromWhat;
        this.context = context;
        this.isHandicapAvailable = isHandicapAvailable;
    }

    /**
     * @param position
     * @return Selected tab.
     */
    @Override
    public Fragment getItem(int position) {

        if (((BaseActivity) context).isOnline(context)) {

            switch (iFromWhat) {
                case ApplicationGlobal.POSITION_COURSE_DIARY:
                    return loadCourseDiaryTabs(position);

                case ApplicationGlobal.POSITION_COMPETITIONS:
                    return loadCompetitionsEvent(position);

                case ApplicationGlobal.POSITION_MY_ACCOUNT:
                    if (isHandicapAvailable) {
                        return loadMyAccountTabsWithHCap(position);
                    } else {
                        return loadMyAccountTabsWithoutHCap(position);
                    }

                case ApplicationGlobal.POSITION_MEMBERS:
                    return loadMembersTab(position);

                case ApplicationGlobal.POSITION_MEMBERS_BOOKING:
                    return loadMembersBookingTab(position);
            }
        }
        return new NoInternetFragment();
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
     * <br> 3. Contact Us
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

            case 2:
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                return contactUsFragment;

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
    private Fragment loadMyAccountTabsWithHCap(int iPosition) {

        Fragment fragment = null;

        switch (iPosition) {
            case 0:
                fragment = new MyDetailsFragment();
                break;

            case 1:
                fragment = new HandicapFragment();
                break;

            case 2:
                fragment = new FinanceFragment();
                break;
        }
        ((YourAccountActivity) context).setFragmentInstance(fragment);
        return fragment;
    }

    /**
     * Load My Account tabs i.e
     * <br> 1. My Details
     * <br> 2. Finances
     * <p/>
     *
     * @param iPosition
     * @return Fragment
     */
    private Fragment loadMyAccountTabsWithoutHCap(int iPosition) {

        Fragment fragment = null;

        switch (iPosition) {
            case 0:
                fragment = new MyDetailsFragment();
                break;

            case 1:
                fragment = new FinanceFragment();
                break;
        }
        ((YourAccountActivity) context).setFragmentInstance(fragment);
        return fragment;
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
     * Load MEMBERS BOOKING for Competitions Entry Tabs i.e
     * <br> 1. {@link EligibleMemberFragment}
     * <br> 2. {@link EligibleFriendsFragment}
     * <p/>
     *
     * @param iPosition
     * @return Fragment
     */
    private Fragment loadMembersBookingTab(int iPosition) {

        switch (iPosition) {
            case 0:
                EligibleMemberFragment eligibleMemberFragment = new EligibleMemberFragment();
                return eligibleMemberFragment;

            case 1:
                EligibleFriendsFragment eligibleFriendsFragment = new EligibleFriendsFragment();
                return eligibleFriendsFragment;

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