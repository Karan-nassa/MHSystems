package com.mh.systems.corrstown.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mh.systems.corrstown.R;
import com.mh.systems.corrstown.ui.activites.YourAccountActivity;
import com.mh.systems.corrstown.ui.adapter.TabsAdapter.TabsPageAdapter;
import com.mh.systems.corrstown.utils.constants.ApplicationGlobal;

@SuppressLint("ValidFragment")
public class MyAccountTabFragment extends Fragment {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public final String LOG_TAG = MyAccountTabFragment.class.getSimpleName();


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
    public static int iLastTabPosition;

    /**
     * Declare three bool instances to call api
     * on visible to user.
     */
    public static boolean IsFriendVisible1, IsFriendVisible2, IsFinanceVisible;


    @SuppressLint("ValidFragment")
    public MyAccountTabFragment(int iOpenTabPosition) {
        iLastTabPosition = iOpenTabPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_my_account_tabs, container, false);

        //Initialize view resources.
        tabLayout = (TabLayout) viewRootFragment.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_your_details)));

        //Check is Handicap available or not!
        boolean isHandicapFeature = ((YourAccountActivity) getActivity())
                .loadPreferenceBooleanValue(ApplicationGlobal.KEY_HANDICAP_FEATURE, false);
        if (isHandicapFeature) {
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_handicap)));
        }

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_title_finances)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorC0995B));

        viewPager = (ViewPager) viewRootFragment.findViewById(R.id.pager);

        //  Log.e("COUNT:",""+tabLayout.getTabCount());
        pageAdapter = new TabsPageAdapter
                (getActivity(), getActivity().getSupportFragmentManager(),
                        tabLayout.getTabCount(),
                        ApplicationGlobal.POSITION_MY_ACCOUNT,
                        isHandicapFeature);
        viewPager.setAdapter(pageAdapter);

        // iLastTabPosition = ((YourAccountActivity) getActivity()).getIntent().getExtras().getInt("iTabPosition");
        ((YourAccountActivity) getActivity()).setWhichTab(iLastTabPosition);
        viewPager.setCurrentItem(iLastTabPosition);
        //iLastTabPosition = 0;

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Implement Tab selected listener.
        tabLayout.setOnTabSelectedListener(mCourseTabListener);

        return viewRootFragment;
    }

    public TabLayout.OnTabSelectedListener mCourseTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            setTabVisibleStatus(tab.getPosition());

            iLastTabPosition = tab.getPosition();

            ((YourAccountActivity) getActivity()).setWhichTab(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            /**
             *  When user direct navigtate to Handicap Graph and tap on My Detail Tab then tab selection
             *  and content not replacing so change and notify pageAdapter here onReselected.
             */
            viewPager.setCurrentItem(tab.getPosition());
            pageAdapter.notifyDataSetChanged();
            ((YourAccountActivity) getActivity()).setWhichTab(tab.getPosition());
        }
    };

    public  MyAccountTabFragment(){}

    /**
     * Implements a method to update visibility of tab.
     */
    private void setTabVisibleStatus(int iTabPosition) {

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
