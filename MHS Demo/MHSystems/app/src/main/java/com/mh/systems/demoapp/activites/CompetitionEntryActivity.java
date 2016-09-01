package com.mh.systems.demoapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ScrollView;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.adapter.BaseAdapter.CompTimeGridAdapter;
import com.mh.systems.demoapp.models.competitionsEntry.TimeSlots;
import com.mh.systems.demoapp.util.ScrollGridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompetitionEntryActivity extends AppCompatActivity {

    @Bind(R.id.tbBookingDetail)
    Toolbar tbBookingDetail;

    @Bind(R.id.svPlayerContent)
    ScrollView svPlayerContent;

    @Bind(R.id.gvTimeSlots)
    GridView gvTimeSlots;

    CompTimeGridAdapter compTimeGridAdapter;

    ArrayList<TimeSlots> modelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_entry);

        ButterKnife.bind(this);

        addStaticData();

        compTimeGridAdapter = new CompTimeGridAdapter(CompetitionEntryActivity.this, modelArrayList);
        gvTimeSlots.setAdapter(compTimeGridAdapter);
        ScrollGridView.getListViewSize(gvTimeSlots);

        //Forcefully scroll UP of screen after loading.
        svPlayerContent.post(new Runnable() {
            public void run() {
                svPlayerContent.fullScroll(View.FOCUS_UP);
            }
        });

        if (tbBookingDetail != null) {
            setSupportActionBar(tbBookingDetail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                    finish();
                break;

            case R.id.action_save:
                //callUpdateWebService();
                break;

            default:
                break;
        }
        return true;
    }

    private void addStaticData() {

        modelArrayList.clear();

        for (int iCounter = 0; iCounter < 10; iCounter++) {
            int jCounter = iCounter + 10;
            TimeSlots timeSlots = new TimeSlots("10:0" + iCounter, false);
            modelArrayList.add(timeSlots);
        }
    }


}
