package com.mh.systems.demoapp.activites;

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
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.adapter.BaseAdapter.CompetitionDetailAdapter;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.AJsonParamsJoinCompetition;
import com.mh.systems.demoapp.models.AJsonParamsResultOfCompetition;
import com.mh.systems.demoapp.models.AJsonParamsUnjoin;
import com.mh.systems.demoapp.models.AddRequestResult;
import com.mh.systems.demoapp.models.CompetitionDetailItems;
import com.mh.systems.demoapp.models.CompetitionJoinAPI;
import com.mh.systems.demoapp.models.CompetitionResultAPI;
import com.mh.systems.demoapp.models.CompetitionUnjoinAPI;
import com.mh.systems.demoapp.models.ResultEntries;
import com.mh.systems.demoapp.models.UnjoinItems;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
import com.mh.systems.demoapp.util.ScrollRecycleView;
import com.newrelic.com.google.gson.reflect.TypeToken;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

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

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strEventTitle, strEventLogo, strEventDate, strEventTime, strEventPrize, strEventDesc, strEventStatus;
    boolean isEventJoin, isJoinVisible, IsMemberJoined;
    int iPopItemPos;

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
     * {@link com.mh.systems.demoapp.fragments.UpcomingFragment} because
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
                if (!IsMemberJoined) {

                    /* +++++++++++++++++++++++++ NOW USER HAVE TO ENTER COMPETITION WITH FRIENDS/MEMBERS +++++++++++++++++++++++++ */

                    //startActivity(new Intent(CompetitionDetailActivity.this, CompetitionEntryActivity.class));

                    /**
                     *  Check internet connection before hitting server request.
                     */
                    if (isOnline(CompetitionDetailActivity.this)) {
                        callJoinCompetitionWebService();
                    } else {
                        showAlertMessage(getResources().getString(R.string.error_no_internet));
                    }
                }
            }
        }
    };

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

        tvDateCourseEvent.setText(strEventDate);
        tvTimeCourseEvent.setText(strEventTime);

        tvFeeCourseEvent.setText(/*"£" + */strEventPrize + " " + getResources().getString(R.string.title_competitions_prize));
        tvDescCourseEvent.setText(strEventDesc);
        tvEventStatusStrDD.setText(strEventStatus);

        fabJoinCompetition.setOnClickListener(mJoinOnClickListener);
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
         *  {@link com.mh.systems.demoapp.fragments.UpcomingFragment} only.
         */
        if (isJoinVisible) {
            fabJoinCompetition.setVisibility(View.VISIBLE);

            switch (iPopItemPos) {
                case 0:
                    if (IsMemberJoined) {
                        fabJoinCompetition.setImageResource(R.mipmap.ic_friends);
                        fabJoinCompetition.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C0995B")));
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
     * Implements a method to JOIN competitions web service if
     * user not already JOINED.
     */
    private void callJoinCompetitionWebService() {

        String strEventId = getIntent().getExtras().getString("COMPETITIONS_eventId");

        showPleaseWait("Please wait...");

        aJsonParamsJoinCompetition = new AJsonParamsJoinCompetition();
        aJsonParamsJoinCompetition.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsJoinCompetition.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsJoinCompetition.setMemberId(getMemberId());
        aJsonParamsJoinCompetition.setEventId(strEventId);

        competitionJoinAPI = new CompetitionJoinAPI(getClientId(), "ADDMEMBERTOEVENT", aJsonParamsJoinCompetition, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

       /* //Defining the method
        api.joinCompetitionEvent(competitionJoinAPI, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, retrofit.client.Response response) {

                        updateSuccessResponse(jsonObject);
                    }*/

        api.joinCompetitionEventGet(getClientId(), "ADDMEMBERTOEVENT", "{version :" + ApplicationGlobal.TAG_GCLUB_VERSION + ",callid:" + ApplicationGlobal.TAG_GCLUB_CALL_ID + ",MemberId:" + getMemberId() + ",EventId:" + strEventId + "}", ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS, new Callback<JsonObject>() {
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
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<AddRequestResult>() {
        }.getType();
        addRequestResult = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (addRequestResult.getMessage().equalsIgnoreCase("Success")) {
                //Yes button clicked
                fabJoinCompetition.setImageResource(R.mipmap.ic_friends);
                fabJoinCompetition.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C0995B")));

                //Set Member JOIN event programmatically so that user cannot apply for JOIN again.
                IsMemberJoined = true;

                showAlertMessage(addRequestResult.getData().toString());
            } else {
                //If web service not respond in any case.
                showAlertMessage(addRequestResult.getMessage());
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

                showAlertMessage("" + error);
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
