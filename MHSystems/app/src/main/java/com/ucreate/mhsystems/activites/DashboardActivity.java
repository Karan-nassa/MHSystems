package com.ucreate.mhsystems.activites;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.GridAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.models.AJsonParamsDashboard;
import com.ucreate.mhsystems.models.DashboardAPI;
import com.ucreate.mhsystems.models.DashboardData;
import com.ucreate.mhsystems.models.DashboardItems;
import com.ucreate.mhsystems.util.API.WebServiceMethods;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class DashboardActivity extends BaseActivity {

    private static final String LOG_TAG = DashboardActivity.class.getSimpleName();

    @Bind(R.id.gvMenuOptions)
    GridView gvMenuOptions;

    //Instance of Grid Adapter.
    GridAdapter mGridAdapter;
    Intent intent = null;

    TypedArray gridIcons;
    String gridTitles[];
    TypedArray gridBackground;

    //List of type books this list will store type Book which is our data model
    private DashboardAPI dashboardAPI;
    AJsonParamsDashboard aJsonParamsDashboard;

    DashboardItems dashboardItems;
    DashboardData dashboardData;

    /**
     * Set click event listener of Grid Menu Options to
     * use functionality.
     */
    private AdapterView.OnItemClickListener mGridItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    intent = new Intent(DashboardActivity.this, MyAccountActivity.class);
                    break;
                case 1:
                    intent = new Intent(DashboardActivity.this, CourseDiaryActivity.class);
                    break;
                case 2:
                    intent = new Intent(DashboardActivity.this, CompetitionsActivity.class);
                    break;
                case 3:
                    intent = new Intent(DashboardActivity.this, MembersActivity.class);
                    break;
                case 5:
                    intent = new Intent(DashboardActivity.this, MyAccountActivity.class);
                    break;
            }

            //Check if intent not NULL then navigate to that selected screen.
            if (intent != null) {
                /**
                 * Use for display Handicap on select HANDICAP
                 * from dashboard.
                 */
                ApplicationGlobal.TAB_DETAIL = (position + 1);
                startActivity(intent);
                intent = null;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(DashboardActivity.this); //Initialize facebook Fresco for round profile pic.
        setContentView(R.layout.activity_home);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(DashboardActivity.this);

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(DashboardActivity.this)) {
            //Method to hit Squads API.
            requestHomeService();
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_internet));
        }

        //Set Menu Options click event handle.
        gvMenuOptions.setOnItemClickListener(mGridItemListener);
    }

    /**
     * Implements a method to set Grid
     * MENU options.
     * @param hCapExactStr
     */
    private void setGridMenuOptions(String hCapExactStr) {

        //Setup Titles and Icons of Navigation Drawer
        gridTitles = getResources().getStringArray(R.array.homeGridItems);
        gridIcons = getResources().obtainTypedArray(R.array.HomeGridIcons);
        gridBackground = getResources().obtainTypedArray(R.array.gridBackgroundColors);

        //Set Grid options adapter.
        mGridAdapter = new GridAdapter(this, gridTitles, gridIcons, gridBackground, hCapExactStr);
        gvMenuOptions.setAdapter(mGridAdapter);

        // ScrollRecycleView.getListViewSize(gvMenuOptions);
    }

    /**
     * Implement a method to hit Dashboard web service to
     * get response and information.
     */
    public void requestHomeService() {

        showPleaseWait("Loading...");

        aJsonParamsDashboard = new AJsonParamsDashboard();
        aJsonParamsDashboard.setCallid("1457589857009");
        aJsonParamsDashboard.setVersion(1);
        aJsonParamsDashboard.setUserID("NABECASIS");
        aJsonParamsDashboard.setPassword("BILLABONG1");

        dashboardAPI = new DashboardAPI(44118078, "AuthenticateMember", aJsonParamsDashboard, "WEBSERVICES", "Members");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getDashboardData(dashboardAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {

                //you can handle the errors here
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

        Type type = new TypeToken<DashboardItems>() {
        }.getType();
        dashboardItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear the Dashboard data.
        dashboardData = null;

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (dashboardItems.getMessage().equalsIgnoreCase("Success")) {

                dashboardData = dashboardItems.getData();

                if (dashboardData == null) {
                    showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {
                    //Set MENU Grid Options.
                    setGridMenuOptions(dashboardData.getHCapExactStr());
                }
            } else {
                //If web service not respond in any case.
               // showAlertMessage(dashboardItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        hideProgress();
    }
}
