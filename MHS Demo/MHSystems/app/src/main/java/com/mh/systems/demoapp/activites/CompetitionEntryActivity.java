package com.mh.systems.demoapp.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.adapter.BaseAdapter.CompTimeGridAdapter;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.EditDetailMode.AJsonParamsEditDetailMode;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeAPI;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeResponse;
import com.mh.systems.demoapp.models.competitionsEntry.AJsonParamsUpdateEntry;
import com.mh.systems.demoapp.models.competitionsEntry.ClubEventStartSheet;
import com.mh.systems.demoapp.models.competitionsEntry.Players;
import com.mh.systems.demoapp.models.competitionsEntry.Slot;
import com.mh.systems.demoapp.models.competitionsEntry.Team;
import com.mh.systems.demoapp.models.competitionsEntry.UpdateCompEntryAPI;
import com.mh.systems.demoapp.models.competitionsEntry.UpdateCompEntryResponse;
import com.mh.systems.demoapp.models.competitionsEntry.Zone;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
import com.mh.systems.demoapp.util.ExpandableHeightGridView;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Create {@link CompetitionEntryActivity} to choose Players/Members and TimeSlots.
 *
 * @author karan@ucreate.co.in
 */
public class CompetitionEntryActivity extends BaseActivity {

    private final String LOG_TAG = CompetitionEntryActivity.class.getSimpleName();

    /**
     * iMemberPosition is used to keep record of which Member position user going to update so that
     * update name of Member when get back from {@link EligiblePlayersActivity}.
     */
    int iMemberPosition;

    //Holds the No. of players.
    int iTotalPlayers = 1;

    String strEventId, strEventPrize, strMemberName;
    int iZoneId, iSlotNo = -1;

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

    @Bind(R.id.tvPlayerName1)
    TextView tvPlayerName1;

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

    @Bind(R.id.tvTeeTimeValue)
    TextView tvTeeTimeValue;

    @Bind(R.id.tvSelectHint)
    TextView tvSelectHint;

    Intent intent;

    CompTimeGridAdapter compTimeGridAdapter;

    //ArrayList<TimeSlots> modelArrayList = new ArrayList<>();
    List<Slot> slotArrayList = new ArrayList<>();
    ArrayList<Integer> playersArrayList = new ArrayList<>();
    ArrayList<Players> nameOfPlayersList = new ArrayList<>();

    Team teamInstance;

    UpdateCompEntryAPI updateCompEntryAPI;
    AJsonParamsUpdateEntry aJsonParamsUpdateEntry;

    UpdateCompEntryResponse updateCompEntryResponse;

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

            intent = new Intent(CompetitionEntryActivity.this, EligiblePlayersActivity.class);
            intent.putExtra("COMPETITIONS_eventId", strEventId);
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

            updatePriceUI();
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

        strEventId = getIntent().getExtras().getString("COMPETITIONS_eventId");
        strEventPrize = getIntent().getExtras().getString("COMPETITIONS_EVENT_PRIZE");
        strMemberName = getIntent().getExtras().getString("COMPETITIONS_MEMBER_NAME");

        tvPlayerName1.setText(strMemberName);

        updatePriceUI();
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
        ClubEventStartSheet clubEventStartSheet = gson.fromJson(jsonFavorites, ClubEventStartSheet.class);

        //Time Slots should be visible if key 'IsTeeTimeSlotsAllowed' TRUE.
        if (getIntent().getExtras().getBoolean("COMPETITIONS_IsTeeTimeSlotsAllowed")) {
            slotArrayList = clubEventStartSheet.getZones().get(0).getSlots();

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

        iZoneId = clubEventStartSheet.getZones().get(0).getZoneID();

        teamInstance = clubEventStartSheet.getZones().get(0).getTeam();
        nameOfPlayersList.addAll(teamInstance.getPlayers());
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
     * Update Tee Time Slot value.
     *
     * @param SlotStartTimeStr
     */
    public void updateTeeTimeValue(int iSlotTimePos) {
        tvTeeTimeValue.setText(slotArrayList.get(iSlotTimePos).getSlotStartTimeStr());
        tvSelectHint.setText("");

        iSlotNo = slotArrayList.get(iSlotTimePos).getSlotNo();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            updateMemberName(data.getStringExtra("NAME_OF_MEMBER"));
        }
    }

    /**
     * Implements this method to update name of Member when user get back from
     * {@link EligiblePlayersActivity} after choose Member/Friend.
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

        updatePriceUI();
    }

    /**
     * Implements this method to update Price after select or remove Members.
     */
    private void updatePriceUI() {
        Scanner scannerInput = new Scanner(strEventPrize).useDelimiter("[^0-9]+");
        int iPrizeVal = scannerInput.nextInt();

        tvDetailPrice.setText("" + iTotalPlayers + "x " + strEventPrize);
        tvTotalPrice.setText("£" + (iTotalPlayers * iPrizeVal) + ".00");
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
                            builder = null;
                            //do things
                            updateCompEntryService();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Implements this method to check Internet Connection and hit web service
     * if connection exists.
     */
    private void updateCompEntryService() {

        if (iSlotNo != -1) {
            if (isOnline(this)) {
                callUpdateEntryService();
            } else {
                showAlertMessage(getResources().getString(R.string.error_no_internet));
                hideProgress();
            }
        } else {
            showAlertMessage("Please select any Tee time slot.");
        }

    }

    /**
     * Implements a method to hit update members detail service.
     */
    private void callUpdateEntryService() {

        //Add Member Id's of Players List.
        playersArrayList.add(Integer.parseInt(getMemberId()));

        showPleaseWait("Please wait...");

        aJsonParamsUpdateEntry = new AJsonParamsUpdateEntry();
        aJsonParamsUpdateEntry.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsUpdateEntry.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsUpdateEntry.setMemberId(getMemberId());
        aJsonParamsUpdateEntry.setEventId(strEventId);
        aJsonParamsUpdateEntry.setPlayers(playersArrayList);
        aJsonParamsUpdateEntry.setSlotNo(iSlotNo);
        aJsonParamsUpdateEntry.setZoneId(iZoneId);

        updateCompEntryAPI = new UpdateCompEntryAPI(getClientId(), "UPDATECLUBEVENTENTRIES", aJsonParamsUpdateEntry, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.updateCompEntry(updateCompEntryAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();
                showAlertMessage("" + error);
            }
        });
    }

    /**
     * Implements a method to update SUCCESS response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<UpdateCompEntryResponse>() {
        }.getType();
        updateCompEntryResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (updateCompEntryResponse.getResult() == 1) {

                showAlertMessage(updateCompEntryResponse.getData());
            } else {
                //If web service not respond in any case.
                showAlertMessage(updateCompEntryResponse.getMessage());
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
        hideProgress();
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    public String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }
}
