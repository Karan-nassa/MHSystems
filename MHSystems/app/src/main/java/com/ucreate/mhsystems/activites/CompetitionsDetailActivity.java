package com.ucreate.mhsystems.activites;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.CompetitionDetailAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.models.AJsonParamsJoinCompetition;
import com.ucreate.mhsystems.models.AddRequestResult;
import com.ucreate.mhsystems.models.CompetitionJoinAPI;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
import com.ucreate.mhsystems.util.ScrollRecycleView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class CompetitionsDetailActivity extends BaseActivity {

    private final String LOG_TAG = CompetitionsDetailActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.toolbarComp)
    Toolbar toolbarComp;
    @Bind(R.id.tvDateCourseEvent)
    TextView tvDateCourseEvent;
    @Bind(R.id.tvTimeCourseEvent)
    TextView tvTimeCourseEvent;
    @Bind(R.id.tvFeeCourseEvent)
    TextView tvFeeCourseEvent;
    @Bind(R.id.tvDescCourseEvent)
    TextView tvDescCourseEvent;
    @Bind(R.id.llPriceGroup)
    LinearLayout llPriceGroup;

    //Join Competition Button
    @Bind(R.id.fabJoinCompetition)
    FloatingActionButton fabJoinCompetition;

    //List of type books this list will store type Book which is our data model
    CompetitionJoinAPI competitionJoinAPI;
    AJsonParamsJoinCompetition aJsonParamsJoinCompetition;

    //Create instance of Model class for display result.
    AddRequestResult addRequestResult;

    CompetitionDetailAdapter competitionDetailAdapter;

    @Bind(R.id.lvListOfMembers)
    ListView lvListOfMembers;

    @Bind(R.id.llRankOfMembers)
    LinearLayout llRankOfMembers;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strEventTitle, strEventLogo, strEventDate, strEventTime, strEventPrize, strEventDesc;
    boolean isEventJoin, isJoinVisible, IsMemberJoined;
    int iPopItemPos;

    /**
     * Declares the field to JOIN a COMPETITIONS if user come from
     * {@link com.ucreate.mhsystems.fragments.UpcomingFragment} because
     * user can JOIN only for future COMPETITIONS not past.
     */
    private View.OnClickListener mJoinOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (!IsMemberJoined) {
                /**
                 *  Check internet connection before hitting server request.
                 */
                if (isOnline(CompetitionsDetailActivity.this)) {
                    callJoinCompetitionWebService();
                } else {
                    showAlertMessage(getResources().getString(R.string.error_no_internet));
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
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close_white);

        toolbarComp.setTitleTextColor(0xFFFFFFFF);

        tvDateCourseEvent.setText(strEventDate);
        tvTimeCourseEvent.setText(strEventTime);

        tvFeeCourseEvent.setText("£" + strEventPrize + " " + getResources().getString(R.string.title_competitions_prize));
        tvDescCourseEvent.setText(strEventDesc);

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
     * {@link CompetitionsDetailActivity}.
     */
    private void initializeResources() {
        strEventTitle = getIntent().getExtras().getString("COMPETITIONS_TITLE");
        strEventLogo = getIntent().getExtras().getString("COMPETITIONS_EVENT_IMAGE");
        isEventJoin = getIntent().getExtras().getBoolean("COMPETITIONS_EVENT_JOIN");
        strEventDate = getIntent().getExtras().getString("COMPETITIONS_EVENT_DATE");
        strEventPrize = getIntent().getExtras().getString("COMPETITIONS_EVENT_PRIZE");
        strEventTime = getIntent().getExtras().getString("COMPETITIONS_EVENT_TIME");
        strEventDesc = getIntent().getExtras().getString("COMPETITIONS_EVENT_DESCRIPTION");

        isJoinVisible = getIntent().getExtras().getBoolean("COMPETITIONS_JOIN_STATE");
        IsMemberJoined = getIntent().getExtras().getBoolean("COMPETITIONS_IsMemberJoined");

        iPopItemPos = getIntent().getExtras().getInt("COMPETITIONS_iPopItemPos");

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(strEventTitle);

        /**
         *  FAB ({@link FloatingActionButton}) button should be visible for
         *  {@link com.ucreate.mhsystems.fragments.UpcomingFragment} only.
         */
        if (iPopItemPos < 2) {
            if (isJoinVisible) {
                fabJoinCompetition.setVisibility(View.VISIBLE);

                if (IsMemberJoined) {
                    fabJoinCompetition.setImageResource(R.mipmap.ic_friends);
                    fabJoinCompetition.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C0995B")));

                    //Display Rank of Members.
                    llRankOfMembers.setVisibility(View.VISIBLE);

                    competitionDetailAdapter = new CompetitionDetailAdapter(CompetitionsDetailActivity.this);
                    lvListOfMembers.setAdapter(competitionDetailAdapter);
                    ScrollRecycleView.getListViewSize(lvListOfMembers);
                }
            }
        } else {
            //Floating Action button should not VISIBLE when user view the detail of COMPLETED COMPETITIONS.
            fabJoinCompetition.setVisibility(View.GONE);
        }
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

        //Defining the method
        Log.e("getClientId():", "" + getClientId());
        Log.e("aJsonParamsJoin:", "" + aJsonParamsJoinCompetition.toString());
        Log.e("GCLUB_WEBSERVICES:", "" + ApplicationGlobal.TAG_GCLUB_WEBSERVICES);
        Log.e("GCLUB_MEMBERS:", "" + ApplicationGlobal.TAG_GCLUB_MEMBERS);

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


                //Display Rank of Members.
                llRankOfMembers.setVisibility(View.VISIBLE);

                competitionDetailAdapter = new CompetitionDetailAdapter(CompetitionsDetailActivity.this);
                lvListOfMembers.setAdapter(competitionDetailAdapter);
                ScrollRecycleView.getListViewSize(lvListOfMembers);
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
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, "44118078");
    }
}
