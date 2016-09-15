package com.mh.systems.demoapp.activites;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.mh.systems.demoapp.models.competitionsEntry.Slot;
import com.mh.systems.demoapp.models.competitionsEntry.TimeSlots;
import com.mh.systems.demoapp.util.ExpandableHeightGridView;
import com.newrelic.com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create {@link CompetitionEntryActivity} to choose Players/Members and TimeSlots.
 *
 * @author karan@ucreate.co.in
 */
public class CompetitionEntryActivity extends BaseActivity {

    String strNameOfMember;

    /**
     * iMemberPosition is used to keep record of which Member position user going to update so that
     * update name of Member when get back from {@link MembersBookingActivity}.
     */
    int iMemberPosition;

    //Holds the No. of players.
    int iTotalPlayers = 1;

    @Bind(R.id.tbBookingDetail)
    Toolbar tbBookingDetail;

    @Bind(R.id.tvDetailPrice)
    TextView tvDetailPrice;

    @Bind(R.id.tvTotalPrice)
    TextView tvTotalPrice;

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

    Intent intent;

    CompTimeGridAdapter compTimeGridAdapter;

    //ArrayList<TimeSlots> modelArrayList = new ArrayList<>();
    List<Slot> slotArrayList = new ArrayList<>();

    /**
     * Implements this method to call when user tap to Add Member.
     */
    private View.OnClickListener mPlayerSelectionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvPlayerName2:
                case R.id.llPlayerGroup2:
                    if (tvPlayerName2.getText().toString().equals("Add (optionaly)")) {
                        iMemberPosition = 2;
                    } else
                        return;
                    break;

                case R.id.tvPlayerName3:
                case R.id.llPlayerGroup3:
                    if (tvPlayerName3.getText().toString().equals("Add (optionaly)")) {
                        iMemberPosition = 3;
                    } else
                        return;
                    break;

                case R.id.tvPlayerName4:
                case R.id.llPlayerGroup4:
                    if (tvPlayerName4.getText().toString().equals("Add (optionaly)")) {
                        iMemberPosition = 4;
                    } else
                        return;
                    break;
            }

            intent = new Intent(CompetitionEntryActivity.this, MembersBookingActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    /**
     * Implements this method to call when user tap on Cross button.
     */
    private View.OnClickListener mCrossListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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

            if (iTotalPlayers > 1) {
                iTotalPlayers--;
            }

            updatePrice();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_entry);

        ButterKnife.bind(this);

        //addStaticData();

        if (tbBookingDetail != null) {
            setSupportActionBar(tbBookingDetail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        updateTimeSlots();

        llPlayerGroup2.setOnClickListener(mPlayerSelectionListener);
        tvPlayerName2.setOnClickListener(mPlayerSelectionListener);

        llPlayerGroup3.setOnClickListener(mPlayerSelectionListener);
        tvPlayerName3.setOnClickListener(mPlayerSelectionListener);

        llPlayerGroup4.setOnClickListener(mPlayerSelectionListener);
        tvPlayerName4.setOnClickListener(mPlayerSelectionListener);

        ivCrossPlayer2.setOnClickListener(mCrossListener);
        ivCrossPlayer3.setOnClickListener(mCrossListener);
        ivCrossPlayer4.setOnClickListener(mCrossListener);
    }

    /**
     * Implements this method to get TEE time slots for book
     * paid Competitions.
     */
    private void updateTimeSlots() {
        List arrayList = null;
        String jsonFavorites = getIntent().getExtras().getString("GET_CLUB_EVENT_RESPONSE");
        Gson gson = new Gson();
        Slot[] slots = gson.fromJson(jsonFavorites, Slot[].class);
        arrayList = Arrays.asList(slots);
        slotArrayList = new ArrayList(arrayList);
       /* return (ArrayList) arrayList;*/

        gvTimeSlots.setExpanded(true);
        compTimeGridAdapter = new CompTimeGridAdapter(CompetitionEntryActivity.this, slotArrayList);
        gvTimeSlots.setAdapter(compTimeGridAdapter);

        //Forcefully scroll UP of screen after loading.
        svPlayerContent.post(new Runnable() {
            public void run() {
                svPlayerContent.fullScroll(View.FOCUS_UP);
            }
        });
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == 1) {*/
        if (resultCode == RESULT_OK) {
            //  strNameOfMember = ;
            updateMemberName(data.getStringExtra("NAME_OF_MEMBER"));
        }
       /* }*/
    }

    /**
     * Implements this method to update name of Member when user get back from
     * {@link MembersBookingActivity} after choose Member/Friend.
     */
    public void updateMemberName(String strNameOfMember) {
        switch (iMemberPosition) {
            case 2:
                iMemberPosition = 2;
                tvPlayerName2.setText(strNameOfMember);
                ivCrossPlayer2.setVisibility(View.VISIBLE);
                break;

            case 3:
                iMemberPosition = 3;
                tvPlayerName3.setText(strNameOfMember);
                ivCrossPlayer3.setVisibility(View.VISIBLE);
                break;

            case 4:
                iMemberPosition = 4;
                tvPlayerName4.setText(strNameOfMember);
                ivCrossPlayer4.setVisibility(View.VISIBLE);
                break;
        }

        if (iTotalPlayers < 4) {
            iTotalPlayers++;
        }

        updatePrice();
    }

    /**
     * Implements this method to update Price after select or remove Members.
     */
    private void updatePrice() {
        tvDetailPrice.setText("" + iTotalPlayers + "x £5.00");
        tvTotalPrice.setText("£" + iTotalPlayers * 5 + ".00");
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
            builder.setMessage("Your account will be charged for " + tvTotalPrice.getText().toString())
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

   /* private void addStaticData() {

        modelArrayList.clear();

        for (int iCounter = 0; iCounter < 10; iCounter++) {
            int jCounter = iCounter + 10;
            TimeSlots timeSlots = new TimeSlots("10:" + jCounter, false);
            modelArrayList.add(timeSlots);
        }
    }*/


}
