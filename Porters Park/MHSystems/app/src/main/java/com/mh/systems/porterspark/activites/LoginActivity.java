package com.mh.systems.porterspark.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.mh.systems.porterspark.R;
import com.mh.systems.porterspark.constants.ApplicationGlobal;
import com.mh.systems.porterspark.constants.WebAPI;
import com.mh.systems.porterspark.models.AJsonParamsDashboard;
import com.mh.systems.porterspark.models.DashboardAPI;
import com.mh.systems.porterspark.models.LoginData;
import com.mh.systems.porterspark.models.LoginItems;
import com.mh.systems.porterspark.util.API.WebServiceMethods;

import java.lang.reflect.Type;

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
    public static final String LOG_TAG = LoginActivity.class.getSimpleName();
    String strErrorMessage = "";
    String strUserName, strPassword;

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

    Typeface tfRobotoRegular, tfRobotoLight, getTfRobotoMedium;

    //List of type books this list will store type Book which is our data model
    private DashboardAPI dashboardAPI;
    AJsonParamsDashboard aJsonParamsDashboard;

    LoginItems dashboardItems;
    LoginData dashboardData;

    /**
     * Define a constant field called when user press on LOGIN
     * {@link Button}.
     */
    private View.OnClickListener mLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Get input to local instance.
            strUserName = etUserName.getText().toString();
            strPassword = etPassword.getText().toString();

            if (isValid()) {
                //Call LOGIN API if UserName & Password correctly filled.
                /**
                 *  Check internet connection before hitting server request.
                 */
                if (isOnline(LoginActivity.this)) {
                    //Method to hit Squads API.
                    requestLoginService();
                } else {
                    showAlertMessage(getResources().getString(R.string.error_no_internet));
                }

            } else {
                showAlertMessage(strErrorMessage);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize Butter knife.
        ButterKnife.bind(this);

//        etUserName.setText("TONYP1952");
//        etPassword.setText("WINCHESTER1952");

        btLogin.setOnClickListener(mLoginListener);
    }

    /**
     * Implements a method to check whether Login and Password
     * field filled or not?
     *
     * @return TRUE if input VALID.
     */
    private boolean isValid() {

        if (strUserName.length() == 0 && strPassword.length() == 0) {
            strErrorMessage = getResources().getString(R.string.error_all_required);
            return false;
        }
        return true;
    }

    /**
     * Implement a method to hit Login web service to
     * get response and information.
     */
    public void requestLoginService() {

        showPleaseWait("Loading...");

        aJsonParamsDashboard = new AJsonParamsDashboard();
        aJsonParamsDashboard.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsDashboard.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsDashboard.setUserID(strUserName/*"NABECASIS"*/);
        aJsonParamsDashboard.setPassword(strPassword/*"BILLABONG1"*/);

        dashboardAPI = new DashboardAPI(44071043, "AuthenticateMember", aJsonParamsDashboard, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

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
                    savePreferenceValue(ApplicationGlobal.KEY_CLUB_ID, "" + dashboardData.getClubID());
                    savePreferenceValue(ApplicationGlobal.KEY_MEMBERID, "" + dashboardData.getMemberID());
                    savePreferenceValue(ApplicationGlobal.KEY_USER_LOGINID, dashboardData.getUserLoginID());
                    savePreferenceValue(ApplicationGlobal.KEY_PASSWORD, "" + strPassword);
                    savePreferenceValue(ApplicationGlobal.KEY_HCAP_TYPE_STR, dashboardData.getHCapTypeStr());
                    savePreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, dashboardData.getHCapExactStr());

                   /* Gson gson = new Gson();

                    //Save Courses ArrayList in Shared-preference.
                    savePreferenceList(ApplicationGlobal.KEY_COURSES, gson.toJson(dashboardData.getCourses()));*/

                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    this.finish();
                }
            } else {
                //If web service not respond in any case.
                showAlertMessage(dashboardItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        hideProgress();
    }

    /**
     * Implements a method to set FONT style using .ttf by putting
     * in main\assets\fonts directory of current project.
     */
    private void setFontTypeFace() {
        tfRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        getTfRobotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        tfRobotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        etUserName.setTypeface(tfRobotoRegular);
        etPassword.setTypeface(tfRobotoRegular);

        tvLoginTitle.setTypeface(getTfRobotoMedium);
        btLogin.setTypeface(getTfRobotoMedium);

        tvCopyRight.setTypeface(tfRobotoLight);
    }
}
