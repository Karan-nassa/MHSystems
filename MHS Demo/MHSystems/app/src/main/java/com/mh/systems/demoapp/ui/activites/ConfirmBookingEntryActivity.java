package com.mh.systems.demoapp.ui.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.mh.systems.demoapp.web.models.FinanceAJsonParams;
import com.mh.systems.demoapp.web.models.FinanceAPI;
import com.mh.systems.demoapp.web.models.FinanceResultItems;
import com.mh.systems.demoapp.web.models.competitionsentrynew.AllPlayer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final int ERROR_INSUFFICIENT_BALANCE = 1001;
    private final int ERROR_NO_TOPUP__FOUND = 1002;
    private final int ERROR_PLAYERS_REQUIRED = 1003;

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
    CompConfirmEntryAdapter compConfirmEntryAdapter;

    NewCompEventEntryItems mNewCompEventEntryItems;
    AJsonParamsConfirmBooking aJsonParamsConfirmBooking;

    NewCompEntryResponse newCompEntryResponse;

    ///private ArrayList<Slot> mSlotEntryList = new ArrayList<>();
    private ArrayList<Slot> mFinalBookingList = new ArrayList<>();

    int iZoneNo;
    int iEventID;
    int iPayeeId;
    private float mEntryFee;
    private float mTopUpBalance = 0;

    int iPlayersAddedCount = 0;

    String strZoneName = "N/A";

    FinanceResultItems financeResultItems;

    FinanceAPI financeApi;
    FinanceAJsonParams financeAJsonParams;

    Map<Integer, AllPlayer> mapAllPlayer = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking_entry);

        ButterKnife.bind(this);

        initalizeUI();

        btConfirmEntry.setOnClickListener(this);
        llAddPlayer.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(ConfirmBookingEntryActivity.this)) {
            requestFinanceService();
        } else {
            showAlertMessage(getString(R.string.error_no_internet));
        }
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
    public void addPlayersListener(ArrayList<Team> teams, int slotPosition
            , int iTeamPerSlot, int iAddPlayerPosition
            , int iAlreadyBookSlotIdx, boolean isAlertUpdate) {

    }

    @Override
    public void removePlayerListener(ArrayList<Team> teams, int iSlotPosition, int iAddPlayerPosition) {

        int iSlotIdx = teams.get(iAddPlayerPosition).getSlotIdx();
        int iTeamIdx = teams.get(iAddPlayerPosition).getTeamIdx();

        Team mTeamInstance = new Team();
        mTeamInstance.setZoneId(teams.get(iAddPlayerPosition).getZoneId());
        mTeamInstance.setSlotIdx(iSlotIdx);
        mTeamInstance.setTeamIdx(iTeamIdx);

        mTeamInstance.setTeamName("(Free)");
        mTeamInstance.setEntryFee((double) 0);

        //So Remove icon should be visible.
        // mTeamInstance.setEntryStatus(0);
        // mTeamInstance.setAlreadyBooked(true);
        //mTeamInstance.setAnyUpdated(false);

        List<Player> mPlayerList = new ArrayList<>();

        if (teams.get(iAddPlayerPosition).getPlayers().get(0).getMemberId()
                .equalsIgnoreCase(getMemberId())) {
            newCompEntryData.setSelfAlreadyAdded(false);
        }

        mTeamInstance.setPlayers(mPlayerList);
        //teams.get(iAddPlayerPosition).setPlayers(mPlayerList);
        teams.set(iAddPlayerPosition, mTeamInstance);

        if(mEntryFee < 0) {
            mEntryFee += newCompEntryData.getEntryFee();
        }else{
            mEntryFee -= newCompEntryData.getEntryFee();
        }
        newCompEntryData.setMaxTeamAdded((newCompEntryData.getMaxTeamAdded() - 1));

        newCompEntryData.getZones().get(iZoneNo).getSlots()
                .get(iSlotIdx).getTeams().set(iTeamIdx, mTeamInstance);

        filterBookedSlotLists();
    }

    @Override
    public void addorChangePlayerUpdateMaxTeam(List<Player> mPlayersArr
            , ArrayList<Team> teamArrayList, int slotPosition
            , int iTeamsPerSlot, int iTeamPosition
            , int iSlotIdx, int iPlayerCount, int actionCallFromRemove) {

        if (actionCallFromRemove == ApplicationGlobal.ACTION_CALL_FROM_REMOVE) {
            mPlayersArr.remove(iPlayerCount);

            newCompEntryData.getZones().get(iZoneNo)
                    .getSlots().get(slotPosition)
                    .getTeams().get(iTeamPosition)
                    .setPlayers(mPlayersArr);
        }

        double entryFee = newCompEntryData.getEntryFee();
        if (mPlayersArr.size() == 0) {
            mEntryFee -= entryFee;
        }

       newCompEntryData.getZones().get(iZoneNo).getSlots()
                .get(slotPosition).getTeams().get(iTeamPosition).
                setEntryFee(entryFee);
       /* float iCurrentEntryFee = newCompEntryData.getEntryFee();
        if (mEntryFee < iCurrentEntryFee) {
            //   mEntryFee += newCompEntryData.getEntryFee();
            mEntryFee = iCurrentEntryFee;
        }

        newCompEntryData.getZones().get(iZoneNo).getSlots()
                .get(slotPosition).getTeams().get(iTeamPosition).
                setEntryFee((double) mEntryFee);*/

        filterBookedSlotLists();
    }

    @Override
    public void confirmRemoveTeam(List<Player> mPlayersArr, ArrayList<Team> teamArrayList,
                                  int iSlotPos, int iTeamsPerSlot, int iTeamPosition, int iSlotIdx,
                                  int iPlayerCount, int actionCallFromRemove) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddPlayer:
                onBackPressed();
                break;

            case R.id.btConfirmEntry:

                if (mEntryFee <= mTopUpBalance) {
                    /**
                     *  Check internet connection before hitting server request.
                     */
                    if (isOnline(ConfirmBookingEntryActivity.this)) {
                        sendConfirmEntryV2();
                    } else {
                        showAlertMessage(getString(R.string.error_no_connection));
                    }
                } else {
                    String strErrorMessage = "Sorry, you do not have sufficient funds to make this entry. The total entry fees are "
                            + newCompEntryData.getCrnSymbol() + decimalFormat.format(mEntryFee) + ". Your Account balance is "
                            + newCompEntryData.getCrnSymbol() + decimalFormat.format(newCompEntryData.getFundsAvailable()) +
                            ". Please top-up your account by clicking ok.";
                    showAlertError(ERROR_INSUFFICIENT_BALANCE,
                            strErrorMessage);
                }

                break;
        }
    }

   /* @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("RESPONSE_GET_CLUBEVENT_ENTRY_DATA", newCompEntryData*//*new Gson().toJson(newCompEntryData)*//*);
        intent.putExtra("mEntryFee", mEntryFee);
        intent.putExtra("iZoneNo", iZoneNo);
        intent.putExtra("strZoneName", strZoneName);
        setResult(RESULT_OK, intent);
        finish();

        //super.onBackPressed();
    }*/

    public void updateTotalPrice(float iTotalPrice) {
        tvTotalCost.setText("" + newCompEntryData.getCrnSymbol()
                + decimalFormat.format(iTotalPrice));
    }

    private void initalizeUI() {
        if (tbBookingEntry != null) {
            setSupportActionBar(tbBookingEntry);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        String jsonNewCompEntryData = getIntent().getExtras().getString("RESPONSE_GET_CLUBEVENT_ENTRY_DATA");
//        newCompEntryData = new Gson().fromJson(jsonNewCompEntryData, NewCompEntryData.class);

        newCompEntryData = (NewCompEntryData) getIntent().getSerializableExtra("RESPONSE_GET_CLUBEVENT_ENTRY_DATA");

        mapAllPlayer = (Map<Integer, AllPlayer>) getIntent().getSerializableExtra("mapAllPlayer");

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

        List<Booking> mBookingEventsLists = getBookedEventSlotLists();
        int iPlayerArrCount = 0;
        if (mBookingEventsLists.size() > 0) {
            iPlayerArrCount = mBookingEventsLists.get(0).getPlayers().size();
        }

        if (iPlayerArrCount == 0 || iPlayerArrCount == newCompEntryData.getTeamSize()) {

            showPleaseWait("Loading...");

            aJsonParamsConfirmBooking = new AJsonParamsConfirmBooking();
            aJsonParamsConfirmBooking.setClientId(ApplicationGlobal.TAG_GCLUB_CALL_ID);
            aJsonParamsConfirmBooking.setEventId(iEventID);
            aJsonParamsConfirmBooking.setMemberId(getMemberId());
            aJsonParamsConfirmBooking.setPayeeId(iPayeeId);
            aJsonParamsConfirmBooking.setRemoveEntry(false/*getMemberId()*/); //TODO: Set as False as default because don't know what we have to send it here.

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
        } else {
            String strErrorMessage = "Minimum " + newCompEntryData.getTeamSize() + " players required. Please add " +
                    (newCompEntryData.getTeamSize() - iPlayerArrCount) + " more player(s) first.";
            showAlertError(ERROR_PLAYERS_REQUIRED,
                    strErrorMessage);
        }
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
                    showAlertCongrates(getResources().getString(R.string.text_booking_success)
                            , getString(R.string.alert_title_congrates));
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

        //Also append the booking data.
       /* if (newCompEntryData.getBooking().size() > 0) {
            mBookingEntryList.addAll(newCompEntryData.getBooking());
        }*/

        List<Slot> mSlotsList = newCompEntryData.getZones().get(iZoneNo).getSlots();
        int iTeamSize = newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot();

        for (int iSlotCount = 0; iSlotCount < mSlotsList.size(); iSlotCount++) {

            for (int jTeamCount = 0; jTeamCount < iTeamSize; jTeamCount++) {

                ArrayList<Team> teamArrayList = mSlotsList.get(iSlotCount).getTeams();

                /*if (!teamArrayList.get(jTeamCount)
                        .getTeamName().equals("(Free)")
                        && teamArrayList.get(jTeamCount).getEntryStatus() != 1) {*/
                if (teamArrayList.get(jTeamCount)
                        .getPlayers().size() > 0
                        && teamArrayList.get(jTeamCount).getEntryStatus() != 1) {

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

                ArrayList<Team> teamArrayList = mSlotsList.get(iSlotCount).getTeams();

              /*  if (!teamArrayList.get(jTeamCount)
                        .getTeamName().equals("(Free)")
                        && teamArrayList.get(jTeamCount).isAnyUpdated()) {*/

                if (teamArrayList.get(jTeamCount)
                        .getPlayers().size() > 0
                        && teamArrayList.get(jTeamCount).isAnyUpdated()) {

                  /*  iPlayersAddedCount = teamArrayList.get(jTeamCount)
                            .getPlayers().size();*/

                    mFilterTeam.add(mSlotsList.get(iSlotCount).getTeams().get(jTeamCount));
                    isAddedNew = true;
                }

                //mEntryFee+= teamArrayList.get(jTeamCount).getEntryFee();
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

      /*  int iTeamSize = newCompEntryData.getTeamSize();

        if (iTeamSize == 4 || iTeamSize == 3) {
            if (iPlayersAddedCount == iTeamSize) {
                disableAddPlayers();
            }else{
                enableAddPlayers();
            }
        }*/

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
                newCompEntryData.getTeamSize(),
                ConfirmBookingEntryActivity.this);
        gvCompEntry.setAdapter(compConfirmEntryAdapter);
    }

    /**
     * Implement a method Custom showEnterCompetitionDialog
     * Alert Dialog for input user First & Last name,
     * email address and Mobile number.
     */
    public void showAlertCongrates(String strAlertMessage, String strTitle) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle(strTitle);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(ConfirmBookingEntryActivity.this,
                                    CompetitionsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Implement a method Custom showEnterCompetitionDialog
     * Alert Dialog for input user First & Last name,
     * email address and Mobile number.
     */
    public void showAlertError(final int errorType, String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            switch (errorType) {
                                case ERROR_INSUFFICIENT_BALANCE:
                                    Intent intent = new Intent(ConfirmBookingEntryActivity.this,
                                            TopUpActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("PASS_FROM", ConfirmBookingEntryActivity.class.getSimpleName());
                                    intent.putExtra("strClosingBalance", financeResultItems.getData().getClosingBalance());
                                    startActivity(intent);
                                    builder = null;
                                    break;

                                case ERROR_NO_TOPUP__FOUND:

                                    builder = null;
                                    onBackPressed();

                                default:
                                    builder = null;
                                    break;
                            }
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Get Member name along with HCap value
     * by passing Member ID.
     */
    public String getMemberNameFromID(int iMemberID) {

        if (newCompEntryData.getAllPlayers() == null) {
            return "N/A";
        }

        //Also check here if member self added in the list then set Flag.
        if (iMemberID == Integer.parseInt(getMemberId())) {
            newCompEntryData.setSelfAlreadyAdded(true);
        }

        if (mapAllPlayer.containsKey(iMemberID)) {

            return mapAllPlayer.get(iMemberID).getNameAndHandicap();
        }

        /*List<AllPlayer> mAllPlayersList = newCompEntryData.getAllPlayers();

        for (int iCounter = 0; iCounter < mAllPlayersList.size(); iCounter++) {
            if (mAllPlayersList.get(iCounter).getMemberId() == iMemberID) {
                return mAllPlayersList.get(iCounter).getNameAndHandicap();
            }
        }*/

        return "N/A";
    }

    /************************************ FINANCE WEB SERVICE [START] ************************************/

    /**
     * Implement a method to hit MY ACCOUNT
     * web service to get balance.
     */
    private void requestFinanceService() {

        showPleaseWait("Loading...");

        financeAJsonParams = new FinanceAJsonParams();
        financeAJsonParams.setCallid(ApplicationGlobal.TAG_NEW_GCLUB_CALL_ID);
        financeAJsonParams.setDateRange(2);//iFilterType
        financeAJsonParams.setMemberId(getMemberId());

        financeApi = new FinanceAPI(getClientId(), "GetAccStatement"
                , financeAJsonParams, "TRANSACTION", ApplicationGlobal.TAG_GCLUB_MEMBERS);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        WebServiceMethods api = adapter.create(WebServiceMethods.class);
        api.getFinanceDetail(financeApi, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, "RetrofitError : " + error);
                showAlertError(ERROR_NO_TOPUP__FOUND,
                        getString(R.string.error_no_topup_found));
                hideProgress();
            }
        });
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<FinanceResultItems>() {
        }.getType();
        financeResultItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (financeResultItems.getMessage().equalsIgnoreCase("Success")) {

                String strClosingBalance = financeResultItems.getData().getClosingBalance();
                mTopUpBalance = Float.parseFloat(strClosingBalance.substring(1, strClosingBalance.length()));

            } else {
                showAlertMessage(financeResultItems.getMessage());
            }
        } catch (Exception e) {
            reportRollBarException(ConfirmBookingEntryActivity.class.getSimpleName()
                    , e.toString());
        }

        //Dismiss progress dialog.
        hideProgress();
    }

    /************************************ FINANCE WEB SERVICE [END] ************************************/
}
