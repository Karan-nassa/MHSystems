package com.ucreate.mhsystems.adapter.TabsAdapter;

/**
 * Created by karan@ucreate.co.in for Tabs Page
 * Adapter on 20/12/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ucreate.mhsystems.fragments.NewsFragment;


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
     * Tab Page Adapter initialization.
     * <p/>
     * <br> @param  Fm        : Instance of Fragment Manager
     * <br> @param  NumOfTabs : Total number of Instance
     * <br> @param  iFromWhat : Value 1 means call from Article and 2 from Media
     */
    public TabsPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    /**
     * @return Selected tab.
     */
    @Override
    public Fragment getItem(int position) {
        return loadArticleTabs(position);
    }

    /**
     * Load Article Tabs i.e NEWS, INTERVIEWS and GUIDES.
     * <p/>
     * <br> @return Fragment
     */
    private Fragment loadArticleTabs(int iPosition) {

        switch (iPosition) {
            case 0:
                NewsFragment articlesContentFragment = new NewsFragment();
                return articlesContentFragment;
            case 1:
                NewsFragment interviewsFragment = new NewsFragment();
                return interviewsFragment;
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