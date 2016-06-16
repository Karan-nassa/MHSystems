package com.ucreate.mhsystems.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.ClubNewsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClubNewsActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.lvClubNewsList)
    ListView lvClubNewsList;

    ClubNewsAdapter clubNewsAdapter;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/

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

        setClubNewsAdapter();

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);
    }

    /**
     * Implements a method to set Club news adapter.
     */
    private void setClubNewsAdapter() {
        clubNewsAdapter = new ClubNewsAdapter(ClubNewsActivity.this);
        lvClubNewsList.setAdapter(clubNewsAdapter);
    }
}
