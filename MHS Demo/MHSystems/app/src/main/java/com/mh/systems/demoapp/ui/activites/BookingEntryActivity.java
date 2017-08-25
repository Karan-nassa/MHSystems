package com.mh.systems.demoapp.ui.activites;

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
import com.mh.systems.demoapp.web.models.competitionsentrynew.Player;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Team;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Zone;
import com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking.AJsonParamsConfirmBooking;
import com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking.Booking;
import com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking.NewCompEventEntryItems;
import com.newrelic.com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BookingEntryActivity extends BaseActivity implements
        View.OnClickListener, OnUpdatePlayers {

    private final String LOG_TAG = BookingEntryActivity.class.getSimpleName();
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
    private List<Booking> mBookingList = new ArrayList<>();
    private Booking mBookingInstance;

    List<Zone> zoneCompEntryList = new ArrayList<>();
    int iZoneNo;
    int iEventID;
    int iPayeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_entry);

        ButterKnife.bind(this);

        initalizeUI();

        updateTotalPrice(10);

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
    public void addPlayersListener(List<Team> teams, int slotPosition, int iTeamPerSlot, int iAddPlayerPosition) {

    }

    @Override
    public void removePlayerListener() {

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
                if (isOnline(BookingEntryActivity.this)) {
                    sendConfirmEntryV2();
                } else {
                    showAlertMessage(getString(R.string.error_no_connection));
                }
                break;
        }
    }

    public void updateTotalPrice(int iTotalPrice) {
        tvTotalCost.setText((newCompEntryData.getCrnSymbol()
                + iTotalPrice));
    }

    private void initalizeUI() {
        if (tbBookingEntry != null) {
            setSupportActionBar(tbBookingEntry);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String jsonNewCompEntryData = getIntent().getExtras().getString("RESPONSE_GET_CLUBEVENT_ENTRY_DATA");
        newCompEntryData = new Gson().fromJson(jsonNewCompEntryData, NewCompEntryData.class);

        iZoneNo = getIntent().getExtras().getInt("iZoneNo");
        iEventID = newCompEntryData.getEventID();
        iPayeeId = newCompEntryData.getPayeeId();

        tvTitleOfComp.setText(newCompEntryData.getEventName());
        tvTimeOfComp.setText(newCompEntryData.getEventStartDate().getFullDateStr());

        gvCompEntry.setExpanded(true);
        compConfirmEntryAdapter = new CompConfirmEntryAdapter(BookingEntryActivity.this,
                zoneCompEntryList,
                iZoneNo,
                newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot(),
                BookingEntryActivity.this);
        gvCompEntry.setAdapter(compConfirmEntryAdapter);
    }

    /**
     * Confirm Booking Event Entry V2.
     */
    public void sendConfirmEntryV2() {

        showPleaseWait("Loading...");

        aJsonParamsConfirmBooking = new AJsonParamsConfirmBooking();
        aJsonParamsConfirmBooking.setClientId(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsConfirmBooking.setEventId(iEventID);
        aJsonParamsConfirmBooking.setMemberId(getMemberId());
        aJsonParamsConfirmBooking.setPayeeId(iPayeeId);
        aJsonParamsConfirmBooking.setRemoveEntry(false/*getMemberId()*/); //TODO: Set as False as default because don't know what we have to send it here.


        //TODO: Remove Entry booking.
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

        mBookingList.add(mBookingInstance);

        aJsonParamsConfirmBooking.setBooking(mBookingList);

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

                updateSuccessResponse(jsonObject);
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
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

       /* Type type = new TypeToken<ResetPasswordItems>() {
        }.getType();
        resetPasswordItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            *//**
         *  Check "Result" 1 or 0. If 1, means data received successfully.
         *//*
            if (resetPasswordItems.getMessage().equalsIgnoreCase("Success")) {

                btResetPassword.setVisibility(View.GONE);
                tvCurrPwdError.setVisibility(View.GONE);
                tvNewPwdError.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 1000);
            } else if (resetPasswordItems.getMessage().contains("Incorrect old password!")) {
                tvCurrPwdError.setVisibility(View.VISIBLE);
            } else {
                showAlertMessage(resetPasswordItems.getMessage());
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            reportRollBarException(ResetPasswordActivity.class.getSimpleName(), e.toString());
        }*/

        hideProgress();
        showAlertMessage(jsonObject.toString()
        );
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

}
