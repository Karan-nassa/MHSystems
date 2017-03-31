package com.mh.systems.sunningdale.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.ui.adapter.TabsAdapter.TabsPageAdapter;
import com.mh.systems.sunningdale.utils.constants.ApplicationGlobal;

public class NewsWebCamTabFragment extends Fragment {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public final String LOG_TAG = NewsWebCamTabFragment.class.getSimpleName();


    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    TabLayout tabLayout;
    ViewPager viewPager;
    View viewRootFragment;
    TabsPageAdapter pageAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_news_webcam_tabs, container, false);

        //Initialize view resources.
        tabLayout = (TabLayout) viewRootFragment.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_name_webcam1)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_name_webcam2)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorC0995B));

        viewPager = (ViewPager) viewRootFragment.findViewById(R.id.pager);

        pageAdapter = new TabsPageAdapter
                (getActivity(), getActivity().getSupportFragmentManager(),
                        tabLayout.getTabCount(),
                        ApplicationGlobal.POSITION_NEWS_WEBCAM);
        viewPager.setAdapter(pageAdapter);

        //viewPager.setCurrentItem(iLastTabPosition);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Implement Tab selected listener.
        tabLayout.setOnTabSelectedListener(mCourseTabListener);

        return viewRootFragment;
    }

    private TabLayout.OnTabSelectedListener mCourseTabListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            //setTabVisibleStatus(tab.getPosition());

            // iLastTabPosition = tab.getPosition();

            // ((YourAccountActivity) getActivity()).setWhichTab(tab.getPosition());
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
        }
    };
}
