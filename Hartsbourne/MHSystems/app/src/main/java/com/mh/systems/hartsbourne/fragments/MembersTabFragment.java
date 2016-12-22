package com.mh.systems.hartsbourne.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.activites.MembersActivity;
import com.mh.systems.hartsbourne.adapter.TabsAdapter.TabsPageAdapter;
import com.mh.systems.hartsbourne.constants.ApplicationGlobal;

/**
 * {@Link MembersTabFragment} provides {@link MembersFragment} and {@link FriendsFragment}
 * <br> {@link android.support.design.widget.TabLayout.Tab} to display tabs.
 * <p/>
 * <p>Population of the tabs to display is
 * done through {@link Fragment} instances.
 */
public class MembersTabFragment extends Fragment {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MembersTabFragment.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    TabLayout tabLayout;
    ViewPager viewPager;
    View viewRootFragment;
    TabsPageAdapter pageAdapter;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    //To Record of which SPINNER item selected "All", "Ladies" or "Gentlemen's".
    public static int iMemberType;

    public static int iLastTabPosition = 0;

    /**
     * Declare three bool instances to call api
     * on visible to user.
     */
    public static boolean isMembersVIsible, isFriendsVisible;

    private TabLayout.OnTabSelectedListener mCourseTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            setTabVisibleStatus(tab.getPosition());

            iLastTabPosition = tab.getPosition();

            //Refresh Spinner in Tool bar according selected tab.
            ((MembersActivity) getActivity()).initializeMembersCategory();
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
    public MembersTabFragment() {

    }

    /**
     * Implements a CONSTRUCTOR to display MEMBERS list according three
     * type selected from SPINNER would be:
     * <p/>
     * <br> 1.) ALL         : 0
     * <br> 2.) LADIES      : 1
     * <br> 3.) MENS        : 2
     */
    @SuppressLint("ValidFragment")
    public MembersTabFragment(int action) {
        iMemberType = action;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_members_tabs, container, false);

        //Initialize view resources.
        tabLayout = (TabLayout) viewRootFragment.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_memebers)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_friends)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorC0995B));

        viewPager = (ViewPager) viewRootFragment.findViewById(R.id.pager);
        pageAdapter = new TabsPageAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), ApplicationGlobal.POSITION_MEMBERS);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(iLastTabPosition);

        //Implement Tab selected listener.
        tabLayout.setOnTabSelectedListener(mCourseTabListener);

        return viewRootFragment;
    }

    /**
     * Implements a method to update visibility of tab.
     */
    private void setTabVisibleStatus(int iTabPosition) {

        Log.e(LOG_TAG, "" + iTabPosition);

        switch (iTabPosition) {
            case 0:
                isMembersVIsible = true;
                isFriendsVisible = false;
                break;
            case 1:
                isMembersVIsible = false;
                isFriendsVisible = true;
                break;
        }
    }
}
