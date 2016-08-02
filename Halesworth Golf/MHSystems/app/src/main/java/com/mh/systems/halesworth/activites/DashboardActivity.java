package com.mh.systems.halesworth.activites;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.JsonObject;
import com.mh.systems.halesworth.R;
import com.mh.systems.halesworth.adapter.BaseAdapter.DashboardGridAdapter;
import com.mh.systems.halesworth.constants.ApplicationGlobal;
import com.mh.systems.halesworth.constants.WebAPI;
import com.mh.systems.halesworth.models.CourseDiaryNames.AJsonParamsCourseDiaryNames;
import com.mh.systems.halesworth.models.CourseDiaryNames.CourseDiaryNamesAPI;
import com.mh.systems.halesworth.models.CourseDiaryNames.CourseDiaryNamesData;
import com.mh.systems.halesworth.models.CourseDiaryNames.CourseDiaryNamesResult;
import com.mh.systems.halesworth.util.API.WebServiceMethods;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * The {@link DashboardActivity} used to display {@link GridView}, Settings and
 * Logout option. Basically, it will be use as the main screen of application
 * after Login.
 *
 * @author {@link karan@ucreate.co.in}
 * @version 1.0
 */
public class DashboardActivity extends BaseActivity {

    private String LOG_TAG = DashboardActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

    @Bind(R.id.gvMenuOptions)
    GridView gvMenuOptions;

    @Bind(R.id.llLogoutBtn)
    LinearLayout llLogoutBtn;

    @Bind(R.id.llSettings)
    LinearLayout llSettings;

    @Bind(R.id.btSendFeedback)
    Button btSendFeedback;

    //Instance of Grid Adapter.
    DashboardGridAdapter mDashboardGridAdapter;
    Intent intent = null;

    CourseDiaryNamesAPI courseDiaryNamesAPI;
    AJsonParamsCourseDiaryNames aJsonParamsCourseDiaryNames;

    CourseDiaryNamesResult courseDiaryNamesResult;
    CourseDiaryNamesData courseDiaryNamesData;

    TypedArray gridIcons;
    TypedArray gridBackground;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/

    String gridTitles[];

    /**
     * Set click event listener of Grid Menu Options to
     * use functionality.
     */
    private AdapterView.OnItemClickListener mGridItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    intent = new Intent(DashboardActivity.this, YourAccountActivity.class);
                    intent.putExtra("iTabPosition", 1);
                    break;
                case 1:
                    /**
                     *  Check internet connection before hitting server request.
                     */
                    if (isOnline(DashboardActivity.this)) {
                        //Method to hit Squads API.
                        requestLoginService();
                    } else {
                        showAlertMessage(getResources().getString(R.string.error_no_internet));
                    }
                   /* intent = new Intent(DashboardActivity.this, CourseDiaryActivity.class);*/
                    break;
                case 2:
                    intent = new Intent(DashboardActivity.this, CompetitionsActivity.class);
                    break;
                case 3:
                    intent = new Intent(DashboardActivity.this, MembersActivity.class);
                    break;

                case 4:
                    intent = new Intent(DashboardActivity.this, ClubNewsActivity.class);
                    break;

                case 5:
                    intent = new Intent(DashboardActivity.this, YourAccountActivity.class);
                    intent.putExtra("iTabPosition", 0);
                    break;
            }

            //Check if intent not NULL then navigate to that selected screen.
            if (intent != null && position != 1) {
                startActivity(intent);
                intent = null;
            }
        }
    };

    /**
     * Logout user from app and navigate back to
     * Login screen.
     */
    private View.OnClickListener mLogoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /**
             *  Clear shared-preference memory.
             */
            clearAutoPreference();

            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(DashboardActivity.this); //Initialize facebook Fresco for round profile pic.
        setContentView(R.layout.activity_dashboard);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(DashboardActivity.this);

        setGridMenuOptions();

        //Set Menu Options click event handle.
        gvMenuOptions.setOnItemClickListener(mGridItemListener);

        //LogOut listener.
        llLogoutBtn.setOnClickListener(mLogoutListener);

        //Settings click event handle here.
        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        //Send Feedback click event here.
        btSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardActivity.this, SendFeedbackActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Implements a method to set Grid MENU options.
     */
    private void setGridMenuOptions() {

        //Setup Titles and Icons of Navigation Drawer
        gridTitles = getResources().getStringArray(R.array.homeGridItems);
        gridIcons = getResources().obtainTypedArray(R.array.HomeGridIcons);
        gridBackground = getResources().obtainTypedArray(R.array.gridBackgroundColors);

        //Set Grid options adapter.
        mDashboardGridAdapter = new DashboardGridAdapter(this, gridTitles, gridIcons, gridBackground, loadPreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, "N/A"));
        gvMenuOptions.setAdapter(mDashboardGridAdapter);

        // ScrollRecycleView.getListViewSize(gvMenuOptions);
    }

    /**
     * Implement a method to hit Login web service to
     * get response and information.
     */
    public void requestLoginService() {

        showPleaseWait("Loading...");

        aJsonParamsCourseDiaryNames = new AJsonParamsCourseDiaryNames();
        aJsonParamsCourseDiaryNames.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsCourseDiaryNames.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);

        courseDiaryNamesAPI = new CourseDiaryNamesAPI(ApplicationGlobal.TAG_CLIENT_ID, "GETCOURSEDIARIES", aJsonParamsCourseDiaryNames, "COURSEDIARY", ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getCourseDiaryNames(courseDiaryNamesAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e(LOG_TAG, "" + error.toString());

                //you can handle the errors here
                hideProgress();
                showAlertMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<CourseDiaryNamesResult>() {
        }.getType();
        courseDiaryNamesResult = new Gson().fromJson(jsonObject.toString(), type);

        //Clear the Dashboard data.
        courseDiaryNamesData = null;

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (courseDiaryNamesResult.getMessage().equalsIgnoreCase("Success")) {

                if (courseDiaryNamesResult.getData().size() == 0) {
                    showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {
                    Gson gson = new Gson();

                    //Save Courses ArrayList in Shared-preference.
                    savePreferenceList(ApplicationGlobal.KEY_COURSES, gson.toJson(courseDiaryNamesResult.getData()));

                    startActivity(new Intent(DashboardActivity.this, CourseDiaryActivity.class));
                }
            } else {
                //If web service not respond in any case.
                showAlertMessage(courseDiaryNamesResult.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        hideProgress();
    }
}
