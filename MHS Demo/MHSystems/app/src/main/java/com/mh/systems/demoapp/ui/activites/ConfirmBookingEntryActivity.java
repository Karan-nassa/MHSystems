package com.mh.systems.demoapp.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.ui.adapter.BaseAdapter.CompConfirmEntryAdapter;
import com.mh.systems.demoapp.ui.interfaces.OnUpdatePlayers;
import com.mh.systems.demoapp.utils.ExpandableHeightGridView;
import com.mh.systems.demoapp.utils.constants.ApplicationGlobal;
import com.mh.systems.demoapp.web.api.WebAPI;
import com.mh.systems.demoapp.web.api.WebServiceMethods;
import com.mh.systems.demoapp.web.models.competitionsentrynew.NewCompEntryData;
import com.mh.systems.demoapp.web.models.competitionsentrynew.NewCompEntryResponse;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Player;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Slot;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Team;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Zone;
import com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking.AJsonParamsConfirmBooking;
import com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking.Booking;
import com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking.NewCompEventEntryItems;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ConfirmBookingEntryActivity extends BaseActivity implements
        View.OnClickListener, OnUpdatePlayers {

    private final String LOG_TAG = ConfirmBookingEntryActivity.class.getSimpleName();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Bind(R.id.tbBookingEntry)
    Toolbar tbBookingEntry;

    @Bind(R.id.tvTitleOfComp)
    TextView tvTitleOfComp;

    @Bind(R.id.tvTimeOfComp)
    TextView tvTimeOfComp;

    @Bind(R.id.tvTotalCost)
    TextView tvTotalCost;

    @Bind(R.id.gvCompEntry)
    ExpandableHeightGridView gvCompEntry;

    @Bind(R.id.llAddPlayer)
    LinearLayout llAddPlayer;

    @Bind(R.id.btConfirmEntry)
    Button btConfirmEntry;

    private NewCompEntryData newCompEntryData;
    private CompConfirmEntryAdapter compConfirmEntryAdapter;

    private NewCompEventEntryItems mNewCompEventEntryItems;
    AJsonParamsConfirmBooking aJsonParamsConfirmBooking;

    private NewCompEntryResponse newCompEntryResponse;

    ///private ArrayList<Slot> mSlotEntryList = new ArrayList<>();
    private ArrayList<Slot> mFinalBookingList = new ArrayList<>();

    int iZoneNo;
    int iEventID;
    int iPayeeId;
    private float mEntryFee;

    String strZoneName = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking_entry);

        ButterKnife.bind(this);

        initalizeUI();

        llAddPlayer.setOnClickListener(this);
        btConfirmEntry.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void addPlayersListener(ArrayList<Team> teams, int slotPosition, int iTeamPerSlot, int iAddPlayerPosition, boolean isAlertUpdate) {

    }

    @Override
    public void removePlayerListener(ArrayList<Team> teams, int iSlotPosition, int iAddPlayerPosition) {

        int iSlotIdx = teams.get(iAddPlayerPosition).getSlotIdx();
        int iTeamIdx = teams.get(iAddPlayerPosition).getTeamIdx();

        Team mTeamInstance = new Team();
        mTeamInstance.setZoneId(teams.get(iAddPlayerPosition).getZoneId());
        mTeamInstance.setSlotIdx(iSlotIdx);
        mTeamInstance.setTeamIdx(iTeamIdx);

            /*String iMemberID = teams.get(slotPosition)
                    .getPlayers()
                    .get(0)
                    .getMemberId();*/

        mTeamInstance.setTeamName("(Free)");
        mTeamInstance.setEntryFee((double) 0);

        List<Player> mPlayerList = new ArrayList<>();

        if (teams.get(iAddPlayerPosition).getPlayers().get(0).getMemberId()
                .equalsIgnoreCase(getMemberId())) {
            newCompEntryData.setSelfAlreadyAdded(false);
        }

        mTeamInstance.setPlayers(mPlayerList);
        //teams.get(iAddPlayerPosition).setPlayers(mPlayerList);
        teams.set(iAddPlayerPosition, mTeamInstance);

        mEntryFee -= newCompEntryData.getEntryFee();

        newCompEntryData.getZones().get(iZoneNo).getSlots().get(iSlotIdx)
                .setTeams(teams);
        filterBookedSlotLists();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddPlayer:
                onBackPressed();
                break;

            case R.id.btConfirmEntry:
                /**
                 *  Check internet connection before hitting server request.
                 */
                if (isOnline(ConfirmBookingEntryActivity.this)) {
                    sendConfirmEntryV2();
                } else {
                    showAlertMessage(getString(R.string.error_no_connection));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("mEntryFee", mEntryFee);
        intent.putExtra("iZoneNo", iZoneNo);
        intent.putExtra("strZoneName", strZoneName);
        intent.putExtra("RESPONSE_GET_CLUBEVENT_ENTRY_DATA", new Gson().toJson(newCompEntryData));
       /* Bundle informacion = new Bundle();
        informacion.putSerializable("filterSlotList", mSlotEntryList);
        intent.putExtras(informacion);*/
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }

    public void updateTotalPrice(float iTotalPrice) {
        tvTotalCost.setText("" + newCompEntryData.getCrnSymbol()
                + decimalFormat.format(iTotalPrice));
    }

    private void initalizeUI() {
        if (tbBookingEntry != null) {
            setSupportActionBar(tbBookingEntry);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String jsonNewCompEntryData = getIntent().getExtras().getString("RESPONSE_GET_CLUBEVENT_ENTRY_DATA");
        newCompEntryData = new Gson().fromJson(jsonNewCompEntryData, NewCompEntryData.class);

        // mSlotEntryList = (ArrayList<Slot>) getIntent().getSerializableExtra("filterSlotList");

        mEntryFee = getIntent().getExtras().getFloat("mEntryFee");
        strZoneName = getIntent().getExtras().getString("strZoneName");
        iZoneNo = getIntent().getExtras().getInt("iZoneNo");
        iEventID = newCompEntryData.getEventID();
        iPayeeId = newCompEntryData.getPayeeId();

        tvTitleOfComp.setText(newCompEntryData.getEventName());
        tvTimeOfComp.setText(newCompEntryData.getEventStartDate().getFullDateStr());

        filterBookedSlotLists();
    }

    /**
     * Confirm Booking Event Entry V2.
     */
    public void sendConfirmEntryV2() {

        showPleaseWait("Loading...");

        List<Booking> mBookingEventsLists = getBookedEventSlotLists();

        aJsonParamsConfirmBooking = new AJsonParamsConfirmBooking();
        aJsonParamsConfirmBooking.setClientId(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsConfirmBooking.setEventId(iEventID);
        aJsonParamsConfirmBooking.setMemberId(getMemberId());
        aJsonParamsConfirmBooking.setPayeeId(iPayeeId);
        aJsonParamsConfirmBooking.setRemoveEntry(false/*getMemberId()*/); //TODO: Set as False as default because don't know what we have to send it here.

        /*//TODO: Remove Entry booking.
        mBookingInstance = new Booking();
        mBookingInstance.setZoneId(1);
        mBookingInstance.setSlotIdx(0);
        mBookingInstance.setTeamIdx(1);
        mBookingInstance.setTeamName("Anurag Team");

        List<Player> mPlayerList = new ArrayList<>();
        Player mPlayer = new Player();
        mPlayer.setIsGuest(false);
        mPlayer.setMemberId(getMemberId());
        mPlayerList.add(mPlayer);

        mBookingInstance.setPlayers(mPlayerList);
        mBookingInstance.setEntryFee(2.0);

        mBookingEntryList.add(mBookingInstance);*/

        aJsonParamsConfirmBooking.setBooking(mBookingEventsLists);

        mNewCompEventEntryItems = new NewCompEventEntryItems(getClientId()
                , "ApplyEventEntryV2"
                , aJsonParamsConfirmBooking
                , ApplicationGlobal.TAG_GCLUB_WEBSERVICES
                , ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.sendClubEventEntryV2(mNewCompEventEntryItems, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {

                updateConfirmBookingSuccess(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();
                showAlertMessage(error.toString());
            }
        });
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateConfirmBookingSuccess(JsonObject jsonObject) {

        Log.e(LOG_TAG, "updateConfirmBookingSuccess : " + jsonObject.toString());

        Type type = new TypeToken<NewCompEntryResponse>() {
        }.getType();
        newCompEntryResponse = new Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (newCompEntryResponse.getMessage().equalsIgnoreCase("Success")) {

                newCompEntryData = newCompEntryResponse.getData();

                if (newCompEntryData.isUpdateFailed()) {
                    showAlertMessage(newCompEntryData.getErrorMessage());
                } else {
                    showAlertMessage(newCompEntryResponse.getMessage());
                }

            } else {
                showAlertMessage(newCompEntryResponse.getMessage());
            }

            hideProgress();
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            hideProgress();
            reportRollBarException(ConfirmBookingEntryActivity.class.getSimpleName(), e.toString());
        }
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    private String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    private String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

  /*  private void filterBookingList() {

        Zone mZoneInstance = newCompEntryData.getZones().get(iZoneNo);
        List<Slot> mAllSlotsList = mZoneInstance.getSlots();

       *//* for (int iSlotCount = 0; iSlotCount < mAllSlotsList.size(); iSlotCount++) {

            for (int jCount = 0; jCount < mZoneInstance.getTeamsPerSlot(); jCount++) {

            }
        }*//*
    }*/

    /**
     * Filter Array with selected
     * members list.
     *
     * @return List<Booking> : List of Booking members.
     */
    private List<Booking> getBookedEventSlotLists() {

        ArrayList<Booking> mBookingEntryList = new ArrayList<>();

        List<Slot> mSlotsList = newCompEntryData.getZones().get(iZoneNo).getSlots();
        int iTeamSize = newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot();

        for (int iSlotCount = 0; iSlotCount < mSlotsList.size(); iSlotCount++) {

            for (int jTeamCount = 0; jTeamCount < iTeamSize; jTeamCount++) {

                if (!mSlotsList.get(iSlotCount).getTeams()
                        .get(jTeamCount).getTeamName().equals("(Free)")) {

                    Team teamInstance = mSlotsList.get(iSlotCount).getTeams().get(jTeamCount);

                    Booking mBookingInstance = new Booking();
                    mBookingInstance.setZoneId(teamInstance.getZoneId());
                    mBookingInstance.setSlotIdx(teamInstance.getSlotIdx());
                    mBookingInstance.setTeamIdx(teamInstance.getTeamIdx());
                    mBookingInstance.setTeamName(teamInstance.getTeamName());
                    mBookingInstance.setPlayers(teamInstance.getPlayers());
                    mBookingInstance.setEntryFee(teamInstance.getEntryFee());

                    mBookingEntryList.add(mBookingInstance);
                }
            }
        }
        return mBookingEntryList;
    }

    /**
     * Filter Array with selected
     * members list.
     */
    private void filterBookedSlotLists() {

        mFinalBookingList.clear();

        List<Slot> mSlotsList = newCompEntryData.getZones().get(iZoneNo).getSlots();


        boolean isAddedNew;

        for (int iSlotCount = 0; iSlotCount < mSlotsList.size(); iSlotCount++) {

            ArrayList<Team> mFilterTeam = new ArrayList<>();
            isAddedNew = false;

            int iTeamSize = newCompEntryData.getZones().get(iZoneNo).getSlots()
                    .get(iSlotCount).getTeams().size();

            for (int jTeamCount = 0; jTeamCount < iTeamSize; jTeamCount++) {

                if (!mSlotsList.get(iSlotCount).getTeams()
                        .get(jTeamCount).getTeamName().equals("(Free)")) {

                    mFilterTeam.add(mSlotsList.get(iSlotCount).getTeams().get(jTeamCount));
                    isAddedNew = true;
                }
            }

            if (isAddedNew) {
                Slot mSlotInstance = new Slot();
                mSlotInstance.setTeams(mFilterTeam);
                mSlotInstance.setTeeOffTime(mSlotsList.get(iSlotCount).getTeeOffTime());
                mSlotInstance.setiFreeSlotsAvail(mSlotsList.get(iSlotCount).getiFreeSlotsAvail());
                mSlotInstance.setStatus(mSlotsList.get(iSlotCount).getStatus());
                mFinalBookingList.add(mSlotInstance);
            }
        }

        updateBookingListAdapter();
    }

    private void updateBookingListAdapter() {

        updateTotalPrice(mEntryFee);

        Zone mZoneInstance = newCompEntryData.getZones().get(iZoneNo);
        //   mFinalBookingList.addAll(mZoneInstance.getSlots());
        gvCompEntry.setExpanded(true);
        compConfirmEntryAdapter = new CompConfirmEntryAdapter(ConfirmBookingEntryActivity.this,
                mFinalBookingList,
                iZoneNo,
                mZoneInstance.getTeamsPerSlot(),
                strZoneName,
                newCompEntryData.getCrnSymbol(),
                ConfirmBookingEntryActivity.this);
        gvCompEntry.setAdapter(compConfirmEntryAdapter);
    }

}
