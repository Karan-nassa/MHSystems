package com.mh.systems.demoapp.activites;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.adapter.RecyclerAdapter.ClubNewsSwipeAdapter;
import com.mh.systems.demoapp.adapter.RecyclerAdapter.HandicapHistoryRecyclerAdapter;
import com.mh.systems.demoapp.util.DividerItemDecoration;

public class HandicapHistoryActivity extends BaseActivity {

    Toolbar tbHcapHistory;

    RecyclerView rvHcapList;

    HandicapHistoryRecyclerAdapter handicapHistoryRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handicap_history);

        rvHcapList = (RecyclerView) findViewById(R.id.rvHcapList);

        tbHcapHistory = (Toolbar) findViewById(R.id.tbHcapHistory);
        setSupportActionBar(tbHcapHistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Layout Managers:
        rvHcapList.setLayoutManager(new LinearLayoutManager(this));
        // Item Decorator:
        rvHcapList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        // mRecyclerView.setItemAnimator(new FadeInLeftAnimator());

        //Set Adapter.
        handicapHistoryRecyclerAdapter = new HandicapHistoryRecyclerAdapter(HandicapHistoryActivity.this);
        rvHcapList.setAdapter(handicapHistoryRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            /**
             *  Tool bar back arrow handler.
             */
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
