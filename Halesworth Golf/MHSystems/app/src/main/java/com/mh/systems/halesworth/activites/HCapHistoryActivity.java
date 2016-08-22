package com.mh.systems.halesworth.activites;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.mh.systems.halesworth.R;
import com.mh.systems.halesworth.adapter.RecyclerAdapter.HCapHistoryRecyclerAdapter;
import com.mh.systems.halesworth.constants.ApplicationGlobal;
import com.mh.systems.halesworth.constants.WebAPI;
import com.mh.systems.halesworth.models.HCapHistory.AJsonParamsHcapHistory;
import com.mh.systems.halesworth.models.HCapHistory.HCapHistoryAPI;
import com.mh.systems.halesworth.models.HCapHistory.HCapHistoryData;
import com.mh.systems.halesworth.models.HCapHistory.HCapHistoryResult;
import com.mh.systems.halesworth.util.API.WebServiceMethods;
import com.mh.systems.halesworth.util.DividerItemDecoration;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class HCapHistoryActivity extends BaseActivity {

    private String LOG_TAG = HCapHistoryActivity.class.getSimpleName();

    Toolbar tbHcapHistory;

    RecyclerView rvHcapList;

    HCapHistoryAPI hCapHistoryAPI;
    AJsonParamsHcapHistory aJsonParamsHcapHistory;

    HCapHistoryResult hCapHistoryResult;
    HCapHistoryRecyclerAdapter HCapHistoryRecyclerAdapter;

    ArrayList<HCapHistoryData> handicapDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcap_history);

        rvHcapList = (RecyclerView) findViewById(R.id.rvHcapList);

        tbHcapHistory = (Toolbar) findViewById(R.id.tbHcapHistory);
        setSupportActionBar(tbHcapHistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Layout Managers:
        rvHcapList.setLayoutManager(new LinearLayoutManager(this));
        // Item Decorator:
        rvHcapList.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        // mRecyclerView.setItemAnimator(new FadeInLeftAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            /**
             *  Tool bar back arrow handler.
             */
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
        if (isOnline(this)) {
            //showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
            // inc_message_view.setVisibility(View.GONE);
            requestHCapHistory();
        } else {
            // showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
            // inc_message_view.setVisibility(View.VISIBLE);
            showAlertMessage(getResources().getString(R.string.error_no_internet));
            hideProgress();
        }
    }

    /**
     * Implements a method to hit Handicap History web service.
     */
    private void requestHCapHistory() {

        showPleaseWait("Please wait...");

        aJsonParamsHcapHistory = new AJsonParamsHcapHistory();
        aJsonParamsHcapHistory.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsHcapHistory.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsHcapHistory.setMemberId(getMemberId());

        hCapHistoryAPI = new HCapHistoryAPI(getClientId(), "GETMEMBERHCAPRECORD", aJsonParamsHcapHistory, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getHCapHistory(hCapHistoryAPI, new Callback<JsonObject>() {
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

        Type type = new TypeToken<HCapHistoryResult>() {
        }.getType();
        hCapHistoryResult = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        handicapDataArrayList.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (hCapHistoryResult.getMessage().equalsIgnoreCase("Success")) {

                handicapDataArrayList.addAll(hCapHistoryResult.getData());

                HCapHistoryRecyclerAdapter = new HCapHistoryRecyclerAdapter(HCapHistoryActivity.this, handicapDataArrayList);
                rvHcapList.setAdapter(HCapHistoryRecyclerAdapter);

            } else {
                //If web service not respond in any case.
                showAlertMessage(hCapHistoryResult.getMessage());
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