package com.mh.systems.clubhouse.ui.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.clubhouse.R;
import com.mh.systems.clubhouse.ui.adapter.BaseAdapter.AutoClubNamesAdapter;
import com.mh.systems.clubhouse.utils.constants.ApplicationGlobal;
import com.mh.systems.clubhouse.web.api.WebAPI;
import com.mh.systems.clubhouse.web.api.WebServiceMethods;
import com.mh.systems.clubhouse.web.models.AJsonParamsDashboard;
import com.mh.systems.clubhouse.web.models.DashboardAPI;
import com.mh.systems.clubhouse.web.models.LoginData;
import com.mh.systems.clubhouse.web.models.LoginItems;
import com.mh.systems.clubhouse.web.models.clubnames.AJsonParamsGolfClubNames;
import com.mh.systems.clubhouse.web.models.clubnames.GolfClubNamesAPI;
import com.mh.systems.clubhouse.web.models.clubnames.GolfClubNamesResponse;
import com.mh.systems.clubhouse.web.models.clubnames.ListOfClubsData;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * The {@link LoginActivity} used for LOGIN process. Member should have entered
 * EMAIL & PASSWORD to use the app.
 *
 * @author : {@link karan@ucreate.co.in}
 * @version : 1.0
 * @since : 18 May, 2016
 */
public class LoginActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *********************************/
    public final String LOG_TAG = LoginActivity.class.getSimpleName();
    String strErrorMessage = "";
    String strUserName, strPassword, strClientId;

    /*********************************
     * INSTANCES OF CLASSES
     ********************************/
    @Bind(R.id.tvLoginTitle)
    TextView tvLoginTitle;

    @Bind(R.id.btLogin)
    Button btLogin;

    @Bind(R.id.etUserName)
    EditText etUserName;

    @Bind(R.id.etPassword)
    EditText etPassword;

    @Bind(R.id.tvCopyRight)
    TextView tvCopyRight;

    @Bind(R.id.tvForgotPWD)
    TextView tvForgotPWD;

    @Bind(R.id.etNameOfClub)
    AutoCompleteTextView etNameOfClub;

    AutoClubNamesAdapter autoClubNamesAdapter = null;
    ArrayList<ListOfClubsData> arrNameOfClubs = new ArrayList<>();

    Typeface tfRobotoRegular, tfRobotoLight, getTfRobotoMedium;

    //List of type books this list will store type Book which is our data model
    private DashboardAPI dashboardAPI;
    AJsonParamsDashboard aJsonParamsDashboard;

    private GolfClubNamesAPI golfClubNamesAPI;
    AJsonParamsGolfClubNames aJsonParamsGolfClubNames;

    GolfClubNamesResponse golfClubNamesResponse;

    LoginItems dashboardItems;
    LoginData dashboardData;

    Intent intent;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize Butter knife.
        ButterKnife.bind(this);

        setFontTypeFace();

        if (isOnline(LoginActivity.this)) {
            getAllClubsListRequest();
        }else{
            showAlertGoBack(getString(R.string.error_check_internet));
        }

        btLogin.setOnClickListener(mLoginListener);

        tvForgotPWD.setOnClickListener(mForgotPwdListener);
    }

    /**
     * Define a constant field called when user press on LOGIN
     * {@link Button}.
     */
    private View.OnClickListener mLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Get input to local instance.
            strClientId = getClubID(etNameOfClub.getText().toString());
            strUserName = etUserName.getText().toString();
            strPassword = etPassword.getText().toString();

            if (isValid()) {
                //Call LOGIN api if UserName & Password correctly filled.
               /* *
                 *  Check internet connection before hitting server request.
                 */
                if (isOnline(LoginActivity.this)) {
                    //Method to hit Squads api.
                    requestLoginService();
                } else {
                    showAlertMessage(getResources().getString(R.string.error_no_internet));
                }

            } else {
                showAlertMessage(strErrorMessage);
            }
        }
    };

    private String getClubID(String strNameOfClub) {

        for (int iCount = 0; iCount < arrNameOfClubs.size(); iCount++) {

            if (arrNameOfClubs.get(iCount).getClubName().equalsIgnoreCase(strNameOfClub)) {
                return String.valueOf(arrNameOfClubs.get(iCount).getClubId());
            }
        }
        return "";
    }

    /**
     * Implements this method to invoke when user tap on Forgot Password
     * text to recover Password on regitered EMAIL.
     */
    private View.OnClickListener mForgotPwdListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            etPassword.setText("");

            intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            intent.putExtra("LIST_GOLF_CLUB_NAMES", new Gson().toJson(golfClubNamesResponse));
            startActivity(intent);
        }
    };

    /**
     * Implements a method to check whether Login and Password
     * field filled or not?
     *
     * @return TRUE if input VALID.
     */
    private boolean isValid() {

        if (strClientId.length() == 0) {
            strErrorMessage = getResources().getString(R.string.error_club_name_required);
            return false;
        } else if (strUserName.length() == 0
                || strPassword.length() == 0) {
            strErrorMessage = getResources().getString(R.string.error_all_required);
            return false;
        }
        return true;
    }

    /**
     * Implement a method to hit Login web service to
     * get response and information.
     */
    private void requestLoginService() {

        showPleaseWait("Loading...");

        aJsonParamsDashboard = new AJsonParamsDashboard();
        aJsonParamsDashboard.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsDashboard.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsDashboard.setUserID(strUserName);
        aJsonParamsDashboard.setPassword(strPassword);

        dashboardAPI = new DashboardAPI(strClientId, "AUTHENTICATEMEMBER", aJsonParamsDashboard, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

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

                Log.e(LOG_TAG, "" + error.toString());

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

        Type type = new TypeToken<LoginItems>() {
        }.getType();
        dashboardItems = new Gson().fromJson(jsonObject.toString(), type);

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

                    savePreferenceValue(ApplicationGlobal.KEY_MEMBERID, "" + dashboardData.getMemberID());

                    if (dashboardData.getFirstTimeLogin()) {
                        intent = new Intent(LoginActivity.this, UpdatePasswordActivity.class);
                        startActivityForResult(intent, 1);
                    } else {

                        savePreferenceBooleanValue(ApplicationGlobal.KEY_FIRST_TIME_LOGIN, dashboardData.getFirstTimeLogin());
                        savePreferenceValue(ApplicationGlobal.KEY_CLUB_ID, "" + dashboardData.getClubID());
                        savePreferenceValue(ApplicationGlobal.KEY_USER_LOGINID, dashboardData.getUserLoginID());
                        savePreferenceValue(ApplicationGlobal.KEY_PASSWORD, "" + strPassword);
                        savePreferenceValue(ApplicationGlobal.KEY_HCAP_TYPE_STR, dashboardData.getHCapTypeStr());
                        savePreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, dashboardData.getHCapExactStr());

                        Gson gson = new Gson();

                        //Save Courses ArrayList in Shared-preference.
                        savePreferenceList(ApplicationGlobal.KEY_COURSES, gson.toJson(dashboardData.getCourses()));

                        intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        this.finish();
//                        etPassword.setText("");
//                        etUserName.setText("");
                    }
                }
            } else {
                //If web service not respond in any case.
                showAlertMessage(dashboardItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            reportRollBarException(LoginActivity.class.getSimpleName(), e.toString());
        }

        //Dismiss progress dialog.
        hideProgress();
    }

    /**
     * Implements a method to set FONT style using .ttf by putting
     * in ForecastMain\assets\fonts directory of current project.
     */
    private void setFontTypeFace() {

        tfRobotoRegular = Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf");
        getTfRobotoMedium = Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Medium.ttf");
        tfRobotoLight = Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Light.ttf");

        etUserName.setTypeface(tfRobotoRegular);
        etPassword.setTypeface(tfRobotoRegular);
        etNameOfClub.setTypeface(tfRobotoRegular);

        btLogin.setTypeface(getTfRobotoMedium);

        tvCopyRight.setTypeface(tfRobotoLight);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            etUserName.setText("" + data.getStringExtra("USERNAME"));
        }
    }

    /********************* +++++++++ GOLF CLUBS NAME LIST ++++++++++++++ ***************************/
    /**
     * Implement a method to hit Login web service to
     * get response and information.
     */
    private void getAllClubsListRequest() {

        showPleaseWait("Loading...");

        aJsonParamsGolfClubNames = new AJsonParamsGolfClubNames();
        aJsonParamsGolfClubNames.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsGolfClubNames.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);

        golfClubNamesAPI = new GolfClubNamesAPI(44071043, "GETALLCLUBS", aJsonParamsGolfClubNames, "CLUBINFO", ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getAllClubNames(golfClubNamesAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateClubsNamesSuccess(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e(LOG_TAG, "" + error.toString());
                hideProgress();
                showAlertGoBack(error.toString());
            }
        });
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateClubsNamesSuccess(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<GolfClubNamesResponse>() {
        }.getType();
        golfClubNamesResponse = new Gson().fromJson(jsonObject.toString(), type);

        arrNameOfClubs.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (golfClubNamesResponse.getMessage().equalsIgnoreCase("Success")) {

                arrNameOfClubs.addAll(golfClubNamesResponse.getData());

                if (arrNameOfClubs.size() > 0) {

                    autoClubNamesAdapter = new AutoClubNamesAdapter(LoginActivity.this, arrNameOfClubs);
                    autoClubNamesAdapter = new AutoClubNamesAdapter(this, arrNameOfClubs);
                    etNameOfClub.setAdapter(autoClubNamesAdapter);
                }
            } else {
                //If web service not respond in any case.
                showAlertMessage(dashboardItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
        }

        //Dismiss progress dialog.
        hideProgress();
    }

    /********************* +++++++++ GOLF CLUBS NAME LIST ++++++++++++++ ***************************/
}
