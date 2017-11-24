package com.mh.systems.dunstabledowns.ui.activites;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.dunstabledowns.R;
import com.mh.systems.dunstabledowns.ui.adapter.BaseAdapter.CompetitionDetailAdapter;
import com.mh.systems.dunstabledowns.ui.adapter.expandableAdapter.CompResultsExpandListAdapter;
import com.mh.systems.dunstabledowns.utils.constants.ApplicationGlobal;
import com.mh.systems.dunstabledowns.web.api.WebAPI;
import com.mh.systems.dunstabledowns.web.models.AJsonParamsResultOfCompetition;
import com.mh.systems.dunstabledowns.web.models.CompetitionDetailItems;
import com.mh.systems.dunstabledowns.web.models.CompetitionResultAPI;
import com.mh.systems.dunstabledowns.web.models.Result;
import com.mh.systems.dunstabledowns.web.models.ResultEntries;
import com.mh.systems.dunstabledowns.web.api.WebServiceMethods;
import com.mh.systems.dunstabledowns.utils.ScrollRecycleView;
import com.newrelic.com.google.gson.reflect.TypeToken;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class CompletedDetailActivity extends BaseActivity {

    private final String LOG_TAG = CompletedDetailActivity.class.getSimpleName();

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
    @Bind(R.id.tvCombaseOfCompEvent)
    TextView tvCombaseOfCompEvent;
    @Bind(R.id.tvDescCourseEvent)
    TextView tvDescCourseEvent;
    @Bind(R.id.tvEventStatusStrDD)
    TextView tvEventStatusStrDD;
    @Bind(R.id.nsvContent)
    NestedScrollView nsvContent;
    @Bind(R.id.tvNoDataView)
    TextView tvNoDataView;

    @Bind(R.id.tvTitleOfEvent)
    TextView tvTitleOfEvent;


    /* ++ TABLE RESULT RESOURCES ++ */
    @Bind(R.id.tvTitleTableResult)
    TextView tvTitleTableResult;

    //Create instance of Competitions detail api to display ROUND result.
    CompetitionResultAPI competitionResultAPI;
    AJsonParamsResultOfCompetition aJsonParamsResultOfCompetition;

    CompetitionDetailItems competitionDetailItems;

    ArrayList<ResultEntries> resultEntryArrayList = new ArrayList<>();

    CompetitionDetailAdapter competitionDetailAdapter;

   /* @Bind(R.id.lvListOfMembers)
    ListView lvListOfMembers;*/

    @Bind(R.id.llRankOfMembers)
    LinearLayout llRankOfMembers;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strEventTitle, strEventLogo, strEventDate, strEventTime, strEventPrize, strEventDesc, strEventStatus, strEventId;
    boolean isEventJoin, isJoinVisible, IsMemberJoined;
    int iPopItemPos;

    Typeface tpRobotoMedium, tfSFUITextSemibold, tfButlerLight;

    @Bind(R.id.elvListOfMembers)
    ExpandableListView elvListOfMembers;
    CompResultsExpandListAdapter expListAdapter;
    List<String> expandableListTitle;
    Map<String, List<ResultEntries>> listHashMapOfResults = new TreeMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_detail);

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

        //tvFeeCourseEvent.setText(/*"£" + */strEventPrize + " " + getResources().getString(R.string.title_competitions_prize));
        tvDescCourseEvent.setText(strEventDesc);
        tvEventStatusStrDD.setText(strEventStatus);
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

    /**
     * Implements a method to Initialize the resources using for
     * {@link CompletedDetailActivity}.
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

        //Hide Time of Event for Completed competitions for now cause of static data.
        tvTimeCourseEvent.setVisibility(View.GONE);

        //Display Rank of Members.
        llRankOfMembers.setVisibility(View.VISIBLE);

        callResultOfCompetitionWebService();

        setFontTypeFace();
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

    /**
     * Implements a method to set custom font style.
     */
    private void setFontTypeFace() {
        tpRobotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        tfSFUITextSemibold = Typeface.createFromAsset(getAssets(), "fonts/SF-UI-Display-Bold.otf");
        tfButlerLight = Typeface.createFromAsset(getAssets(), "fonts/Butler_Light.otf");

        tvDateCourseEvent.setTypeface(tpRobotoMedium);
        tvTimeCourseEvent.setTypeface(tpRobotoMedium);
        //tvFeeCourseEvent.setTypeface(tpRobotoMedium);

        tvTitleTableResult.setTypeface(tfSFUITextSemibold);
    }

    /**
     * Implements a method to JOIN competitions web service if
     * user not already JOINED.
     */
    private void callResultOfCompetitionWebService() {

        showPleaseWait("Please wait...");

        aJsonParamsResultOfCompetition = new AJsonParamsResultOfCompetition();
        aJsonParamsResultOfCompetition.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsResultOfCompetition.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsResultOfCompetition.setMemberId(getMemberId());
        aJsonParamsResultOfCompetition.setEventId(strEventId);

        competitionResultAPI = new CompetitionResultAPI(getClientId(), "GETCLUBEVENTRESULTS", aJsonParamsResultOfCompetition, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.resultOfCompetitionEvent(competitionResultAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                resultOfCompetitionSuccess(jsonObject);
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
     * Implements a method to update SUCCESS of Competitions Round Result
     * response of web service.
     */
    private void resultOfCompetitionSuccess(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<CompetitionDetailItems>() {
        }.getType();
        competitionDetailItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        resultEntryArrayList.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (competitionDetailItems.getMessage().equalsIgnoreCase("Success")) {

                resultEntryArrayList.addAll(competitionDetailItems.getCompResultData().getResults().get(0).getResultEntries());

                if (resultEntryArrayList.size() > 0) {

                    tvNoDataView.setVisibility(View.INVISIBLE);

                    List<Result> mResultDataList = competitionDetailItems.getCompResultData().getResults();

                    for (int iCount = mResultDataList.size() - 1; iCount >= 0; iCount--) {
                        String strKey = mResultDataList.get(iCount).getResultID() + mResultDataList.get(iCount).getDescription();
                        //Log.e("icount", ""+mResultDataList.get(iCount).getDescription());
                        listHashMapOfResults.put(strKey, mResultDataList.get(iCount).getResultEntries());
                    }

                    setupExpandableList();

                    //competitionDetailAdapter = new CompetitionDetailAdapter(CompletedDetailActivity.this, resultEntryArrayList);
                    //  lvListOfMembers.setAdapter(competitionDetailAdapter);
                    //  ScrollRecycleView.getListViewSize(lvListOfMembers);

                    //Forcefully scroll UP of screen after loading.
                    nsvContent.post(new Runnable() {
                        public void run() {
                            nsvContent.fullScroll(View.FOCUS_UP);
                        }
                    });
                } else {
                    tvNoDataView.setVisibility(View.VISIBLE);
                }

            } else {
                //If web service not respond in any case.
                showAlertMessage(competitionDetailItems.getMessage());
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            reportRollBarException(CompletedDetailActivity.class.getSimpleName(), e.toString());
        }
        hideProgress();
    }

    private void setupExpandableList() {
        expandableListTitle = new ArrayList<String>(listHashMapOfResults.keySet());
        expListAdapter = new CompResultsExpandListAdapter(this, expandableListTitle, listHashMapOfResults);
        elvListOfMembers.setAdapter(expListAdapter);
        ScrollRecycleView.getListViewSize(elvListOfMembers);
        /*elvListOfMembers.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                int height = 0;
                for (int i = 0; i < elvListOfMembers.getChildCount(); i++) {
                    height += elvListOfMembers.getChildAt(i).getMeasuredHeight();
                    height += elvListOfMembers.getDividerHeight();
                }
                elvListOfMembers.getLayoutParams().height = (height + 6) * 10;
            }
        });*/

        elvListOfMembers.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        elvListOfMembers.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        elvListOfMembers.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        elvListOfMembers.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    elvListOfMembers.collapseGroup(previousGroup);
                previousGroup = groupPosition;

             /*   FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) elvListOfMembers.getLayoutParams();
                param.height = elvListOfMembers.getHeight();
                elvListOfMembers.setLayoutParams(param);
                elvListOfMembers.refreshDrawableState();

                int height = 0;
                for (int i = 0; i < elvListOfMembers.getChildCount(); i++) {
                    height += elvListOfMembers.getChildAt(i).getMeasuredHeight();
                    height += elvListOfMembers.getDividerHeight();
                }
                elvListOfMembers.getLayoutParams().height = (height + 6) * 5;*/
            }
        });
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height + 200;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }
}
