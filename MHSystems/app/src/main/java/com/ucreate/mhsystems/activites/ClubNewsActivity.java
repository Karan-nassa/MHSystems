package com.ucreate.mhsystems.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.ClubNewsAdapter;
import com.ucreate.mhsystems.adapter.RecyclerAdapter.ClubNewsSwipeAdapter;
import com.ucreate.mhsystems.models.ClubNews.ClubNewsItems;
import com.ucreate.mhsystems.util.DividerItemDecoration;
import com.ucreate.mhsystems.util.RecycleViewDividerDecoration;

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
    ArrayList<ClubNewsItems> clubNewsItemses;

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

        //Add Static data.
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.clear();
        stringArrayList.add("1");
        stringArrayList.add("1");
        stringArrayList.add("1");
        stringArrayList.add("1");
        stringArrayList.add("1");
        stringArrayList.add("1");
        stringArrayList.add("1");
        stringArrayList.add("1");
        stringArrayList.add("1");
        stringArrayList.add("1");

        //Set Adapter.
        clubNewsSwipeAdapter = new ClubNewsSwipeAdapter(ClubNewsActivity.this, stringArrayList);
        rvClubNewsList.setAdapter(clubNewsSwipeAdapter);
    }
}
