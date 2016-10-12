package com.mh.systems.york.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.york.R;
import com.mh.systems.york.constants.ApplicationGlobal;
import com.mh.systems.york.constants.WebAPI;
import com.mh.systems.york.models.AJsonParamsJoinCompetition;
import com.mh.systems.york.models.AJsonParamsUnjoin;
import com.mh.systems.york.models.AddRequestResult;
import com.mh.systems.york.models.CompetitionJoinAPI;
import com.mh.systems.york.models.CompetitionUnjoinAPI;
import com.mh.systems.york.models.UnjoinItems;
import com.mh.systems.york.models.competitionsEntry.AJsonParamsGetClubEvent;
import com.mh.systems.york.models.competitionsEntry.GetClubEventAPI;
import com.mh.systems.york.models.competitionsEntry.GetClubEventData;
import com.mh.systems.york.models.competitionsEntry.GetClubEventResponse;
import com.mh.systems.york.models.competitionsEntry.Player;
import com.mh.systems.york.util.API.WebServiceMethods;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created {@link CompetitionDetailActivity} to display detail screen of
 * <br>- <b>UPCOMING COMPETITION</b>
 * <br>- <b>ENTERED COMPETITION</b>
 * <p>
 * First hit 'GETCLUBEVENT' web service to get the detail of selected
 * COMPETITION. This web service get back with some imp. param like:
 * <br>- <b>AllowCompEntrySelfEntryModeStr</b>
 * <br>- <b>AllowCompEntryAdHocSelection</b> : User has ability to add another Members.
 * <br>- <b>IsEntryAllowed</b> : It describes about entry status OPEN/CLOSE. TRUE means OPEN.
 * <br>- <b>ZoneID</b> : ZoneID used to UPDATE/SAVE booking entry.
 * <br>- <b>TeamSize</b> : Size of Team would be added.
 * <p>
 * If user already ENTERED for COMPETITION then user can UPDATE only.
 */
public class CompetitionDetailActivity extends BaseActivity {

    private final String LOG_TAG = CompetitionDetailActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.toolbarComp)
    Toolbar toolbarComp;
    @Bind(R.id.tvDateCourseEvent)
    TextView tvDateCourseEvent;
    @Bind(R.id.tvTypeOfCompEvent)
    TextView tvTypeOfCompEvent;
    @Bind(R.id.tvTimeCourseEvent)
    TextView tvTimeCourseEvent;
    @Bind(R.id.tvFeeCourseEvent)
    TextView tvFeeCourseEvent;
    @Bind(R.id.tvCombaseOfCompEvent)
    TextView tvCombaseOfCompEvent;
    @Bind(R.id.tvDescCourseEvent)
    TextView tvDescCourseEvent;
    @Bind(R.id.llPriceGroup)
    LinearLayout llPriceGroup;
    @Bind(R.id.tvEventStatusStrDD)
    TextView tvEventStatusStrDD;
    @Bind(R.id.nsvContent)
    NestedScrollView nsvContent;

    @Bind(R.id.tvTitleOfEvent)
    TextView tvTitleOfEvent;

    @Bind(R.id.tvPlayingMembers)
    TextView tvPlayingMembers;

    @Bind(R.id.tvSelectedTeeTime)
    TextView tvSelectedTeeTime;

    @Bind(R.id.tvManageBooking)
    TextView tvManageBooking;

    @Bind(R.id.llUpdateBookingView)
    LinearLayout llUpdateBookingView;

    //Join Competition Button
    @Bind(R.id.fabJoinCompetition)
    FloatingActionButton fabJoinCompetition;

    AddRequestResult addRequestResult;

    //List of type books this list will store type Book which is our data model
    CompetitionJoinAPI competitionJoinAPI;
    AJsonParamsJoinCompetition aJsonParamsJoinCompetition;

    //Create instance of Competitions unjoin API.
    CompetitionUnjoinAPI competitionUnjoinAPI;
    AJsonParamsUnjoin aJsonParamsUnjoin;
    UnjoinItems unjoinItems;

    //Create instance of GetClubEventAPI.
    GetClubEventAPI getClubEventAPI;
    AJsonParamsGetClubEvent aJsonParamsGetClubEvent;
    GetClubEventResponse getClubEventResponse;
    GetClubEventData getClubEventData;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strEventTitle, strEventLogo, strEventDate, strEventTime, strEventPrize, strEventDesc, strEventStatus, strEventId, strMemberName;
    boolean isEventJoin, isJoinVisible, IsMemberJoined;

    // If its value is 'Occupied' means user JOINED Competition entry. And work same like IsMemberJoined (TRUE).
    //String strStatus;

    int iPopItemPos;
    int iEntryID;

    Typeface tpRobotoMedium, tfSFUITextSemibold, tfButlerLight;

    /**
     * Define Floating action button tap Alert Dialog
     * events handle here.
     */
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked...
                    /**
                     *  Check internet connection before hitting server request.
                     */
                    if (isOnline(CompetitionDetailActivity.this)) {
                        unJoinWebService();
                    } else {
                        showAlertMessage(getResources().getString(R.string.error_no_internet));
                    }

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //Cancel button clicked
                    break;
            }
        }
    };

    /**
     * Declares the field to JOIN a COMPETITIONS if user come from
     * {@link com.mh.systems.york.fragments.UpcomingFragment} because
     * user can JOIN only for future COMPETITIONS not past.
     */
    private View.OnClickListener mJoinOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Check for Unjoin competition.
            if (iPopItemPos == 1 && IsMemberJoined) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CompetitionDetailActivity.this);
                builder.setTitle(getResources().getString(R.string.alert_title_unjoin))
                        .setMessage(getResources().getString(R.string.alert_title_unjoin_message))
                        .setPositiveButton("Leave", dialogClickListener)
                        .setNegativeButton("Stay", dialogClickListener).show();
            } else {
                //iEntryID == 0 means Not Entered for competition yet.
                if (!IsMemberJoined && iEntryID == 0) {

                    /* +++++++++++++++++++++++++ NOW USER HAVE TO ENTER COMPETITION WITH FRIENDS/MEMBERS +++++++++++++++++++++++++ */

                    intentToCompetitionEntry();

                    /* ----------------------- NOW USER HAVE TO ENTER COMPETITION WITH FRIENDS/MEMBERS ----------------------- */
                }
            }
        }
    };

    /**
     * Call this method from '+' sign or JOIN competitions and from 'MANAGE YOUR BOOKING'.
     * If its called from 'MANAGE YOUR BOOKING' then 'iEntryID' value would be 0 and used
     * for UPDATE competition entry.
     */
    private void intentToCompetitionEntry() {
        if (getClubEventResponse.getGetClubEventData().getIsEntryAllowed()) {

            Intent intent = new Intent(CompetitionDetailActivity.this, CompetitionEntryActivity.class);
            Gson gson = new Gson();
            //Pass Time Slots to Book Tee Time.
            intent.putExtra("RESPONSE_GET_CLUB_EVENT_DATA", gson.toJson(getClubEventResponse.getGetClubEventData()));
            intent.putExtra("COMPETITIONS_eventId", strEventId);
            intent.putExtra("COMPETITIONS_EVENT_PRIZE", strEventPrize);
            intent.putExtra("COMPETITIONS_MEMBER_NAME", strMemberName);
            startActivity(intent);
        } else {
            showAlertMessage("Entry is not open for this Competition yet.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions_detail);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(this);

        //Initialize resouces.
        initializeResources();

        setSupportActionBar(toolbarComp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarComp.setTitle("");
        toolbarComp.setSubtitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close_white);

        toolbarComp.setTitleTextColor(0xFFFFFFFF);

        fabJoinCompetition.setOnClickListener(mJoinOnClickListener);

        //Handle Manage booking view event hanlder here.
        tvManageBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToCompetitionEntry();
            }
        });
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(CompetitionDetailActivity.this)) {
            getClubEventService();
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_internet));
        }
    }

    /**
     * Implements this method to get CLUB EVENT web service to get detail
     * of COMPETITION event by passing 'eventId'.
     */
    private void getClubEventService() {

        showPleaseWait("Please wait...");

        aJsonParamsGetClubEvent = new AJsonParamsGetClubEvent();
        aJsonParamsGetClubEvent.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsGetClubEvent.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsGetClubEvent.setEventId(strEventId);
        aJsonParamsGetClubEvent.setMemberId(getMemberId());
        aJsonParamsGetClubEvent.setLoginMemberId(getMemberId());

        getClubEventAPI = new GetClubEventAPI(getClientId(), "GETCLUBEVENT", aJsonParamsGetClubEvent, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getClubEvent(getClubEventAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                getClubEventSuccess(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();

                showAlertMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });

    }

    /**
     * Implements this method to handle success response of
     * GetClubEvent service.
     */
    private void getClubEventSuccess(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<GetClubEventResponse>() {
        }.getType();
        getClubEventResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (getClubEventResponse.getMessage().equalsIgnoreCase("Success")) {

                tvEventStatusStrDD.setText(strEventStatus);

                tvDateCourseEvent.setText("" + strEventDate/*getClubEventResponse.getGetClubEventData().getEventDateStr()*/);
                tvTimeCourseEvent.setText("" + getClubEventResponse.getGetClubEventData().getEventTime());
                tvFeeCourseEvent.setText("" + strEventPrize + " " + getResources().getString(R.string.title_competitions_prize));
                tvCombaseOfCompEvent.setText("" + getClubEventResponse.getGetClubEventData().getCompBasis());
                tvDescCourseEvent.setText("" + getClubEventResponse.getGetClubEventData().getEventDescription());

                strMemberName = getClubEventResponse.getGetClubEventData().getMemberName();

                tvTypeOfCompEvent.setText("CONGU(tm), 18 Holes, 1 Round");

                iEntryID = getClubEventResponse.getGetClubEventData().getEntry().getEntryID();
                //Log.e(LOG_TAG, "iEntryID : " + iEntryID);

                /**
                 * Implements check for display selected member list.
                 */
                if (iEntryID != 0) {

                    llUpdateBookingView.setVisibility(View.VISIBLE);

                    if (getClubEventResponse.getGetClubEventData().getEntry().getPlayers() != null && iPopItemPos == 0) {
                        updateJoinIcon();
                    }

                    List<Player> playersArrayList = getClubEventResponse.getGetClubEventData().getEntry().getPlayers();

                    String strPlayersNameList = "";
                    for (int iCounter = 0; iCounter < playersArrayList.size(); iCounter++) {
                        strPlayersNameList = strPlayersNameList + playersArrayList.get(iCounter).getPlayerName() + "\n";
                        tvPlayingMembers.setText("" + strPlayersNameList);
                    }

                    String strReservedSlotTime = getClubEventResponse.getGetClubEventData().getEntry().getReservedSlotTime();
                    tvSelectedTeeTime.setText(strReservedSlotTime != null ? strReservedSlotTime : "N/A");
                }
            } else {
                //If web service not respond in any case.
                showAlertMessage(getClubEventResponse.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
        hideProgress();
    }

    /**
     * Implements a method to Initialize the resources using for
     * {@link CompetitionDetailActivity}.
     */
    private void initializeResources() {
        strEventTitle = getIntent().getExtras().getString("COMPETITIONS_TITLE");
        strEventLogo = getIntent().getExtras().getString("COMPETITIONS_EVENT_IMAGE");
        isEventJoin = getIntent().getExtras().getBoolean("COMPETITIONS_EVENT_JOIN");
        strEventDate = getIntent().getExtras().getString("COMPETITIONS_EVENT_DATE");
        strEventPrize = getIntent().getExtras().getString("COMPETITIONS_EVENT_PRIZE");
        strEventTime = getIntent().getExtras().getString("COMPETITIONS_EVENT_TIME");
        strEventDesc = getIntent().getExtras().getString("COMPETITIONS_EVENT_DESCRIPTION");
        strEventStatus = getIntent().getExtras().getString("COMPETITIONS_EventStatusStr");
        strEventId = getIntent().getExtras().getString("COMPETITIONS_eventId");

        isJoinVisible = getIntent().getExtras().getBoolean("COMPETITIONS_JOIN_STATE");
        IsMemberJoined = getIntent().getExtras().getBoolean("COMPETITIONS_IsMemberJoined");

        iPopItemPos = getIntent().getExtras().getInt("COMPETITIONS_iPopItemPos");

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // collapsingToolbar.setTitle(strEventTitle);
        tvTitleOfEvent.setText(strEventTitle);

        /**
         * Display Collapse toolbar title when collapse layout.
         */
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(strEventTitle);
                    collapsingToolbar.setCollapsedTitleTypeface(tfButlerLight);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        /**
         *  FAB ({@link FloatingActionButton}) button should be visible for
         *  {@link com.mh.systems.york.fragments.UpcomingFragment} only.
         */
        if (isJoinVisible) {
            fabJoinCompetition.setVisibility(View.VISIBLE);

            switch (iPopItemPos) {
                case 0:
                    if (IsMemberJoined) {
                        updateJoinIcon();
                    }
                    break;

                case 1:
                    fabJoinCompetition.setImageResource(R.mipmap.ic_minus);
                    fabJoinCompetition.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                    break;
            }
        }

        setFontTypeFace();
    }

    /**
     * Implements this method to UPDATE the '+' sign with tick means user ENTERED
     * the competition successfully.
     */
    private void updateJoinIcon() {
        fabJoinCompetition.setImageResource(R.mipmap.ic_enteredcompetition);
        fabJoinCompetition.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C0995B")));
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

    /**
     * Implements a method to set custom font style.
     */
    private void setFontTypeFace() {
        tpRobotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        tfSFUITextSemibold = Typeface.createFromAsset(getAssets(), "fonts/SF-UI-Display-Bold.otf");
        tfButlerLight = Typeface.createFromAsset(getAssets(), "fonts/Butler_Light.otf");

        tvDateCourseEvent.setTypeface(tpRobotoMedium);
        tvTimeCourseEvent.setTypeface(tpRobotoMedium);
        tvFeeCourseEvent.setTypeface(tpRobotoMedium);
    }

    /**
     * Implements a method to unjoin competitions web service if
     * user already JOINED.
     */
    private void unJoinWebService() {

        String strEventId = getIntent().getExtras().getString("COMPETITIONS_eventId");

        showPleaseWait("Please wait...");

        aJsonParamsUnjoin = new AJsonParamsUnjoin();
        aJsonParamsUnjoin.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsUnjoin.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsUnjoin.setMemberId(getMemberId());
        aJsonParamsUnjoin.setEventId(strEventId);

        competitionUnjoinAPI = new CompetitionUnjoinAPI(getClientId(), "UNJOINEVENT", aJsonParamsUnjoin, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.unjoinCompetition(competitionUnjoinAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                unJoinCompetitionSuccess(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();

                showAlertMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });

    }

    /**
     * Implements a method to update SUCCESS unJoin competition
     * response of web service.
     */
    private void unJoinCompetitionSuccess(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<UnjoinItems>() {
        }.getType();
        unjoinItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (unjoinItems.getMessage().equalsIgnoreCase("Success")) {

                //unJoin competition event.
                showAlertMessage(unjoinItems.getData().toString());

            } else {
                //If web service not respond in any case.
                showAlertMessage(unjoinItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
        hideProgress();
    }

    /**
     * Implement a method to display Alert Dialog message
     * after unJoin Competitions.
     */
    public void showAlertMessage(String strAlertMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(strAlertMessage)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        onBackPressed();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
