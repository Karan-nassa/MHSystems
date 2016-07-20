package com.mh.systems.porterspark.activites;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mh.systems.porterspark.R;
import com.mh.systems.porterspark.adapter.RecyclerAdapter.ClubNewsSwipeAdapter;
import com.mh.systems.porterspark.models.ClubNews.ClubNewsItems;
import com.mh.systems.porterspark.util.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClubNewsActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.rvClubNewsList)
    RecyclerView rvClubNewsList;

    // ClubNewsAdapter clubNewsAdapter;
    ClubNewsSwipeAdapter clubNewsSwipeAdapter;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    ArrayList<String> clubNewsItemses;

    /**
     * Implements HOME icons press listener.
     */
    private View.OnClickListener mHomePressListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_news);

        //Initialize view resources.
        ButterKnife.bind(this);
        // Layout Managers:
        rvClubNewsList.setLayoutManager(new LinearLayoutManager(this));
        // Item Decorator:
        rvClubNewsList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        // mRecyclerView.setItemAnimator(new FadeInLeftAnimator());

        setClubNewsAdapter();

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);
    }

    /**
     * Implements a method to set Club news adapter.
     */
    private void setClubNewsAdapter() {

       /* ClubNewsItems clubNewsItems1 = new ClubNewsItems("These offers are only available when you book your table online, for free, through Skiddle.",
                "Monday, 18 July 2016", "50% of select food this weekend:");
        ClubNewsItems clubNewsItems2 = new ClubNewsItems("These offers are only available when you book your table online, for free, through Skiddle.",
                "Monday, 18 July 2016", "50% of select food this weekend:");
        ClubNewsItems clubNewsItems3 = new ClubNewsItems("These offers are only available when you book your table online, for free, through Skiddle.",
                "Monday, 18 July 2016", "50% of select food this weekend:");
        //Add Static data.
        clubNewsItemses.clear();
        clubNewsItemses.add(clubNewsItems1);
        clubNewsItemses.add(clubNewsItems2);
        clubNewsItemses.add(clubNewsItems3);*/

        //Set Adapter.
        clubNewsSwipeAdapter = new ClubNewsSwipeAdapter(ClubNewsActivity.this, clubNewsItemses);
        rvClubNewsList.setAdapter(clubNewsSwipeAdapter);
    }
}