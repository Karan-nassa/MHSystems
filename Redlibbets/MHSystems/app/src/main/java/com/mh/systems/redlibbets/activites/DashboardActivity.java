package com.mh.systems.redlibbets.activites;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.redlibbets.R;
import com.mh.systems.redlibbets.adapter.RecyclerAdapter.DashboardRecyclerAdapter;
import com.mh.systems.redlibbets.constants.ApplicationGlobal;
import com.mh.systems.redlibbets.web.WebAPI;
import com.mh.systems.redlibbets.models.DeleteToken.AJsonParamsDeleteToken;
import com.mh.systems.redlibbets.models.DeleteToken.DeleteTokenAPI;
import com.mh.systems.redlibbets.models.DeleteToken.DeleteTokenResult;
import com.mh.systems.redlibbets.models.UnreadNewsCount.AJsonParamsGetUnreadCount;
import com.mh.systems.redlibbets.models.UnreadNewsCount.GetUnreadNewsCountAPI;
import com.mh.systems.redlibbets.models.UnreadNewsCount.GetUnreadNewsResponse;
import com.mh.systems.redlibbets.models.UnreadNewsCount.UnreadNewsCountData;
import com.mh.systems.redlibbets.models.weather.WeatherApiResponse;
import com.mh.systems.redlibbets.push.QuickstartPreferences;
import com.mh.systems.redlibbets.web.api.WebServiceMethods;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * The {@link DashboardActivity} used to display {@link GridView}, Settings and
 * Logout option. Basically, it will be use as the ForecastMain screen of application
 * after Login.
 *
 * @author {@link karan@ucreate.co.in}
 * @version 1.0
 */
public class DashboardActivity extends BaseActivity {

    private final String LOG_TAG = DashboardActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

    @Bind(R.id.gvMenuOptions)
    RecyclerView gvMenuOptions;

    @Bind(R.id.llLogoutBtn)
    LinearLayout llLogoutBtn;

    @Bind(R.id.llSettings)
    LinearLayout llSettings;

    @Bind(R.id.llWeatherGroup)
    LinearLayout llWeatherGroup;

    @Bind(R.id.btSendFeedback)
    Button btSendFeedback;

    @Bind(R.id.tvTodayTemperature)
    TextView tvTodayTemperature;

    @Bind(R.id.tvNameOfLocation)
    TextView tvNameOfLocation;

    @Bind(R.id.tvWeatherDesc)
    TextView tvWeatherDesc;

    @Bind(R.id.todayIcon)
    ImageView todayIcon;

    //Instance of Grid Adapter.
    DashboardRecyclerAdapter dashboardRecyclerAdapter;
    Intent intent = null;

    //Instance of Weather api.
    WeatherApiResponse weatherApiResponse;

    /**
     * Instances of Delete Token api.
     */
    DeleteTokenAPI deleteTokenAPI;
    AJsonParamsDeleteToken aJsonParamsDeleteToken;

    DeleteTokenResult deleteTokenResult;

    /**
     * Instances of GET COUNT OF UNREAD
     * CLUB NEWS api.
     */
    GetUnreadNewsCountAPI getUnreadNewsCountAPI;
    AJsonParamsGetUnreadCount aJsonParamsGetUnreadCount;

    GetUnreadNewsResponse getUnreadNewsResponse;
    UnreadNewsCountData unreadNewsCountData;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    ArrayList<DashboardItems> dashboardItemsArrayList = new ArrayList<>();

    int iHandicapPosition = -1;
    String strNameOfWeatherLoc = "";

    /**
     * Logout user from app and navigate back to
     * Login screen.
     */
    private View.OnClickListener mLogoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Call Weather api functionality.
            if (isOnline(DashboardActivity.this)) {
                callLogoutService();
            } else {
                showAlertMessage(getString(R.string.error_no_connection));
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(DashboardActivity.this);

        setGridMenuOptions();

        //LogOut listener.
        llLogoutBtn.setOnClickListener(mLogoutListener);

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

        //See the weather of 5 days
        llWeatherGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardActivity.this, WeatherDetailActivity.class);
                intent.putExtra("WEATHER_LOC", strNameOfWeatherLoc);
                startActivity(intent);
            }
        });
//        String temp = loadPreferenceValue(ApplicationGlobal.KEY_TEMPKEY_TEMPERATURE, "");
//          if (temp.equals("")){
//        callWeatherService();
//          }
//    else{
//            llWeatherGroup.setVisibility(View.VISIBLE);
//            tvTodayTemperature.setText(loadPreferenceValue(ApplicationGlobal.KEY_TEMPKEY_TEMPERATURE, ""));
//            tvWeatherDesc.setText(loadPreferenceValue(ApplicationGlobal.KEY_TEMPKEY_WEATHER, ""));
//            //        tvNameOfLocation.setText(weatherData.getName() + ", " + weatherData.getSys().getCountry());
//            tvNameOfLocation.setText(loadPreferenceValue(ApplicationGlobal.KEY_TEMPKEY_LOCATION, ""));
//        //    todayIcon.setImageURI(Uri.parse("http://openweathermap.org/img/w/" + weatherData.getWeather().get(0).getIcon() + ".png"));
//            Resources res=getResources();
//            int resID = res.getIdentifier(loadPreferenceValue(ApplicationGlobal.KEY_TEMPKEY_IMAGE, ""), "mipmap", getPackageName());
//            Drawable drawable = res.getDrawable(resID);
//            todayIcon.setImageDrawable(drawable);
//
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Call Weather api functionality.
        if (isOnline(DashboardActivity.this)) {
            callWeatherService();
            getUnreadNewsCountService();
        }
    }

    /**
     * Implements a method to set Grid MENU options
     * dynamically.
     */
    private void setGridMenuOptions() {

        //Add Handicap.
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_HANDICAP_FEATURE, false)) {

            iHandicapPosition = 0;

            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_handicap_chart,
                    "Your Handicap",
                    getApplicationContext().getPackageName() + ".activites.YourAccountActivity"));
        }

        //Add Course Diary.
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_COURSE_DIARY_FEATURE, false)) {

            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_diary,
                    "Course Diary",
                    getApplicationContext().getPackageName() + ".activites.CourseDiaryActivity"));
        }

        //Add Competitions
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_COMPETITIONS_FEATURE, false)) {

            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_competitions,
                    "Competitions",
                    getApplicationContext().getPackageName() + ".activites.CompetitionsActivity"));
        }

        //Add Members
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_MEMBERS_FEATURE, false)) {
            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_members,
                    "Members",
                    getApplicationContext().getPackageName() + ".activites.MembersActivity"));
        }

        //Add Club News
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_CLUB_NEWS_FEATURE, false)) {
            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_clubnews,
                    "Club News",
                    getApplicationContext().getPackageName() + ".activites.ClubNewsActivity"));
        }

        //Add Finance/Your Details
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_YOUR_ACCOUNT_FEATURE, false)) {
            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_my_account,
                    "Your Account",
                    getApplicationContext().getPackageName() + ".activites.YourAccountActivity"));
        }

        //Set Grid options adapter.
        dashboardRecyclerAdapter = new DashboardRecyclerAdapter(this, dashboardItemsArrayList, iHandicapPosition, loadPreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, "N/A"));
        gvMenuOptions.setAdapter(dashboardRecyclerAdapter);

        setupGridLayout(dashboardItemsArrayList.size());

        // ScrollRecycleView.getListViewSize(gvMenuOptions);
    }

    /**
     * Implements this method to set Layout of dashboard
     * Grid.
     */
    private void setupGridLayout(int iGridSize) {
        // Create a grid layout with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6);

        switch (iGridSize) {
            case 3:
                // Create a custom SpanSizeLookup where the first item spans both columns
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return position == 0 ? 6 : 3;
                    }
                });
                break;

            case 4:
                // Create a custom SpanSizeLookup where the first item spans both columns
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });
                break;

            case 5:
                // Create a custom SpanSizeLookup where the first item spans both columns
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return position == 0 || position == 1 ? 3 : 2;
                    }
                });
                break;

            default:
                // Create a custom SpanSizeLookup where the first item spans both columns
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 2;
                    }
                });
                break;
        }


        // int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        // gvMenuOptions.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        // Layout Managers:
        gvMenuOptions.setLayoutManager(layoutManager);
    }

    /**
     * Create this class to decorate Dashboard Grid items spacing.
     * Because above two grid items should be in center of below
     * three one.
     */
   /* public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
           // outRect.left = space;
            //outRect.right = space;
           // outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.left = space;
            } else if (parent.getChildLayoutPosition(view) == 1)  {
                outRect.right = space;
            }
        }
    }*/

    /**
     * {@link DashboardItems} class is used to create Model of
     * dashboard items are icon, title and path of class.
     */
    public class DashboardItems {
        int iGridIcon;
        String strTitleOfGrid;
        String strTagOfGrid;

        /**
         * @param iGridIcon      : Grid icon (drawable).
         * @param strTitleOfGrid : Name or Title of Grid item.
         * @param strTagOfGrid   : Tag or Path of destination class.
         */
        DashboardItems(int iGridIcon, String strTitleOfGrid, String strTagOfGrid) {
            this.iGridIcon = iGridIcon;
            this.strTitleOfGrid = strTitleOfGrid;
            this.strTagOfGrid = strTagOfGrid;
        }

        /**
         * @return The strTagOfGrid
         */
        public String getStrTagOfGrid() {
            return strTagOfGrid;
        }

        /**
         * @param strTagOfGrid The strTagOfGrid
         */
        public void setStrTagOfGrid(String strTagOfGrid) {
            this.strTagOfGrid = strTagOfGrid;
        }

        /**
         * @return The iGridIcon
         */
        public int getiGridIcon() {
            return iGridIcon;
        }

        /**
         * @param iGridIcon The iGridIcon
         */
        public void setiGridIcon(int iGridIcon) {
            this.iGridIcon = iGridIcon;
        }

        /**
         * @return The strTitleOfGrid
         */
        public String getStrTitleOfGrid() {
            return strTitleOfGrid;
        }

        /**
         * @param strTitleOfGrid The strTitleOfGrid
         */
        public void setStrTitleOfGrid(String strTitleOfGrid) {
            this.strTitleOfGrid = strTitleOfGrid;
        }
    }

    /****************** ++ WEATHER api FEATURE ++ ******************/

    /**
     * Implements this method to hit weather web
     * service.
     */
    private void callWeatherService() {

        String strDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        api.weatherAPI("Weather",
                ApplicationGlobal.TAG_CLIENT_ID,
                strDate,
                new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, retrofit.client.Response response) {

                        updateSuccessResponse(jsonObject);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Log.e(LOG_TAG, "RetrofitError : " + error);
                    }
                });
    }

    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "Weather response : " + jsonObject.toString());

        Type type = new TypeToken<WeatherApiResponse>() {
        }.getType();
        weatherApiResponse = new Gson().fromJson(jsonObject.toString(), type);

        if (weatherApiResponse.getData() != null) {

            llWeatherGroup.setVisibility(View.VISIBLE);

            //Get Data to local instances.
            String desc = weatherApiResponse.getData().getWeather().get(0).getDescription();
            tvTodayTemperature.setText("" + ((int) (weatherApiResponse.getData().getMain().getTemp() - 273.15f)) + "°C");
            tvWeatherDesc.setText(("Current, " + (desc.substring(0, 1).toUpperCase() + desc.substring(1))));
            tvNameOfLocation.setText(weatherApiResponse.getData().getName());
            //todayIcon.setImageURI(Uri.parse("http://openweathermap.org/img/w/" + weatherApiResponse.getData().getWeather().get(0).getIcon() + ".png"));

            strNameOfWeatherLoc = weatherApiResponse.getData().getName();

            int resID = getResources().getIdentifier("e" + weatherApiResponse.getData().getWeather().get(0).getIcon(), "mipmap", getPackageName());
            Drawable drawable = ContextCompat.getDrawable(DashboardActivity.this, resID);
            todayIcon.setImageDrawable(drawable);

//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_TEMPERATURE, ("" + ((int) (weatherData.getMain().getTemp() - 273.15f)) + "°C"));
//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_WEATHER, ("Today, "+(desc.substring(0, 1).toUpperCase() + desc.substring(1))));
//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_LOCATION, weatherData.getName());
//            savePreferenceValue(ApplicationGlobal.KEY_TEMPKEY_IMAGE, ("e"+weatherData.getWeather().get(0).getIcon()));

        } else {
            //  Toast.makeText(DashboardActivity.this, weatherApiResponse.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, weatherApiResponse.getMessage());
            //callWeatherService();
        }
    }

    /****************** ~~ WEATHER api FEATURE ~~ ******************/

    /*~~~~~~~~~~~~~~~~~ START OF LOGOUT FEATURE  ~~~~~~~~~~~~~~~~~*/

    /**
     * Implements this method to call the
     * Logout web service.
     */
    private void callLogoutService() {

        showPleaseWait("Loading...");

        aJsonParamsDeleteToken = new AJsonParamsDeleteToken();
        aJsonParamsDeleteToken.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsDeleteToken.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsDeleteToken.setDeviceId(loadPreferenceValue(QuickstartPreferences.TAG_DEVICE_ID, "N/A"));

        deleteTokenAPI = new DeleteTokenAPI(getClientId(), "DELETEMEMBERDEVICE", aJsonParamsDeleteToken, "PUSHNOTIFICATION", ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.deleteToken(deleteTokenAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateTokenSuccessResponse(jsonObject);
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
     * Implements a method which called when token
     * deleted from web server successfully.
     */
    private void updateTokenSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new com.newrelic.com.google.gson.reflect.TypeToken<DeleteTokenResult>() {
        }.getType();
        deleteTokenResult = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (deleteTokenResult.getMessage().equalsIgnoreCase("Success") || deleteTokenResult.getData() == 1) {
                /**
                 *  Clear shared-preference memory.
                 */
                clearAutoPreference();

                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
            } else {
                /*mAwesomeValidation.addValidation(etUserName, "regex", updatePasswordResponse.getMessage());
                mAwesomeValidation.validate();*/
                showAlertMessage("" + deleteTokenResult.getMessage());
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
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

     /*~~~~~~~~~~~~~~~~~ END OF LOGOUT FEATURE  ~~~~~~~~~~~~~~~~~*/

    /****************** ++ GET CLUB NEWS UNREAD COUNT FUNCTIONALITY ++ ******************/

    /**
     * Implements this method to hit weather web
     * service get count of UNREAD club news.
     */
    private void getUnreadNewsCountService() {

        aJsonParamsGetUnreadCount = new AJsonParamsGetUnreadCount();
        aJsonParamsGetUnreadCount.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsGetUnreadCount.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsGetUnreadCount.setLoginMemberId(getMemberId());

        getUnreadNewsCountAPI = new GetUnreadNewsCountAPI(getClientId(), "GETUNREADCLUBNEWS", aJsonParamsGetUnreadCount, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getUnreadClubNewsCount(getUnreadNewsCountAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateGetUnreadCountResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();

                //getUnreadNewsCountService();
            }
        });
    }

    /**
     * Implements a method which called to get count of
     * UNREAD club news.
     */
    private void updateGetUnreadCountResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<GetUnreadNewsResponse>() {
        }.getType();
        getUnreadNewsResponse = new Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (getUnreadNewsResponse.getMessage().equalsIgnoreCase("Success")) {

                int iUnReadCount = getUnreadNewsResponse.getData().getUnRead();

                if (iUnReadCount > 0) {
                    dashboardRecyclerAdapter.updateBadgerCount(getUnreadNewsResponse.getData().getUnRead());
                }
            } else {
                /*mAwesomeValidation.addValidation(etUserName, "regex", updatePasswordResponse.getMessage());
                mAwesomeValidation.validate();*/
                showAlertMessage("" + getUnreadNewsResponse.getMessage());
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    /****************** ++ GET CLUB NEWS UNREAD COUNT FUNCTIONALITY ++ ******************/
}