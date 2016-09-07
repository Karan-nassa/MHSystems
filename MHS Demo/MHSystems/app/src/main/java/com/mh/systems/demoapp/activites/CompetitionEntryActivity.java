package com.mh.systems.demoapp.activites;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.adapter.BaseAdapter.CompTimeGridAdapter;
import com.mh.systems.demoapp.models.competitionsEntry.TimeSlots;
import com.mh.systems.demoapp.util.ExpandableHeightGridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create {@link CompetitionEntryActivity} to choose Players/Members and TimeSlots.
 *
 * @author karan@ucreate.co.in
 */
public class CompetitionEntryActivity extends BaseActivity {

    @Bind(R.id.tbBookingDetail)
    Toolbar tbBookingDetail;

    @Bind(R.id.svPlayerContent)
    ScrollView svPlayerContent;

    @Bind(R.id.gvTimeSlots)
    ExpandableHeightGridView gvTimeSlots;

    @Bind(R.id.llPlayerGroup2)
    LinearLayout llPlayerGroup2;

    @Bind(R.id.llPlayerGroup3)
    LinearLayout llPlayerGroup3;

    @Bind(R.id.llPlayerGroup4)
    LinearLayout llPlayerGroup4;

    @Bind(R.id.tvPlayerName2)
    TextView tvPlayerName2;

    @Bind(R.id.tvPlayerName3)
    TextView tvPlayerName3;

    @Bind(R.id.tvPlayerName4)
    TextView tvPlayerName4;

    @Bind(R.id.ivCrossPlayer2)
    ImageView ivCrossPlayer2;

    @Bind(R.id.ivCrossPlayer3)
    ImageView ivCrossPlayer3;

    @Bind(R.id.ivCrossPlayer4)
    ImageView ivCrossPlayer4;

    CompTimeGridAdapter compTimeGridAdapter;

    ArrayList<TimeSlots> modelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_entry);

        ButterKnife.bind(this);

        addStaticData();

        gvTimeSlots.setExpanded(true);
        compTimeGridAdapter = new CompTimeGridAdapter(CompetitionEntryActivity.this, modelArrayList);
        gvTimeSlots.setAdapter(compTimeGridAdapter);

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
                showAlertConfirm();
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * Implements this method to call when user tap to Add Member.
     */
    public void onAddPlayer(View view) {
        switch (view.getId()) {
            case R.id.tvPlayerName2:
            case R.id.llPlayerGroup2:
                tvPlayerName2.setText("Mr Calvin Jennings");
                ivCrossPlayer2.setVisibility(View.VISIBLE);
                break;

            case R.id.tvPlayerName3:
            case R.id.llPlayerGroup3:
                tvPlayerName3.setText("Mr Kevin Okrah");
                ivCrossPlayer3.setVisibility(View.VISIBLE);
                break;

            case R.id.tvPlayerName4:
            case R.id.llPlayerGroup4:
                tvPlayerName4.setText("Mr Grzegorz Hadala");
                ivCrossPlayer4.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * Implements this method to call when user tap on Cross button.
     */
    public void onRemovePlayer(View view) {
        switch (view.getId()) {
            case R.id.ivCrossPlayer2:
                tvPlayerName2.setText("Add (optionaly)");
                ivCrossPlayer2.setVisibility(View.INVISIBLE);
                showAlertOk();
                break;

            case R.id.ivCrossPlayer3:
                tvPlayerName3.setText("Add (optionaly)");
                ivCrossPlayer3.setVisibility(View.INVISIBLE);
                showAlertOk();
                break;

            case R.id.ivCrossPlayer4:
                tvPlayerName4.setText("Add (optionaly)");
                ivCrossPlayer4.setVisibility(View.INVISIBLE);
                showAlertOk();
                break;
        }
    }

    /**
     * Implements this method to display {@link AlertDialog} with
     * OK button.
     */
    private void showAlertOk() {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Changes");
            builder.setMessage("Your account will be adjusted accordingly")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Implements this method to display {@link AlertDialog} with
     * CANCEL and CONFIRM button.
     */
    private void showAlertConfirm() {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setMessage("Your account will be charged for £10.00")
                    .setCancelable(false)
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                        }
                    })
            .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //do things
                    onBackPressed();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
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
