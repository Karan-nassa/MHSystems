package com.mh.systems.clubhouse.ui.activites;


import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.mh.systems.clubhouse.R;
import com.mh.systems.clubhouse.ui.adapter.BaseAdapter.AutoClubNamesAdapter;
import com.mh.systems.clubhouse.utils.constants.ApplicationGlobal;
import com.mh.systems.clubhouse.web.api.WebAPI;
import com.mh.systems.clubhouse.web.api.WebServiceMethods;
import com.mh.systems.clubhouse.web.models.clubnames.GolfClubNamesResponse;
import com.mh.systems.clubhouse.web.models.clubnames.ListOfClubsData;
import com.mh.systems.clubhouse.web.models.forgotpassword.AJsonParamsForgotPassword;
import com.mh.systems.clubhouse.web.models.forgotpassword.ForgotPasswordAPI;
import com.mh.systems.clubhouse.web.models.forgotpassword.ForgotPasswordResponse;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {

    private final String LOG_TAG = ForgotPasswordActivity.class.getSimpleName();

    @Bind(R.id.tbForgotPwd)
    Toolbar tbForgotPwd;

    @Bind(R.id.etEmailAddress)
    EditText etEmailAddress;

    @Bind(R.id.btForgotPwd)
    Button btForgotPwd;

    @Bind(R.id.etNameOfClub)
    AutoCompleteTextView etNameOfClub;

    private ForgotPasswordAPI forgotPasswordAPI;
    AJsonParamsForgotPassword aJsonParamsForgotPassword;
    ForgotPasswordResponse forgotPasswordResponse;

    Typeface tfRobotoRegular;

    private GolfClubNamesResponse golfClubNamesResponse;
    private AutoClubNamesAdapter autoClubNamesAdapter;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/

    String strClientId = "";
    private ArrayList<ListOfClubsData> listOfClubsNames = new ArrayList<>();
    private String strErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ButterKnife.bind(ForgotPasswordActivity.this);

        String jsonClubNames = getIntent().getExtras().getString("LIST_GOLF_CLUB_NAMES");
        golfClubNamesResponse = new Gson().fromJson(jsonClubNames, GolfClubNamesResponse.class);

        listOfClubsNames.addAll(golfClubNamesResponse.getData());
        autoClubNamesAdapter = new AutoClubNamesAdapter(ForgotPasswordActivity.this, listOfClubsNames);
        etNameOfClub.setAdapter(autoClubNamesAdapter);

        // show soft keyboard
        etNameOfClub.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        setSupportActionBar(tbForgotPwd);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_title_forgot_pwd));

        btForgotPwd.setEnabled(false);

        //Set Custom font style.
        setFontTypeFace();

        etEmailAddress.addTextChangedListener(mTextChangeListener);
        etNameOfClub.addTextChangedListener(mTextChangeListener);

        //Set Click listener event.
        btForgotPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btForgotPwd:

                strClientId = getClubID(etNameOfClub.getText().toString());

                if (isValid()) {
                    /**
                     *  Check internet connection before hitting server request.
                     */
                    if (isOnline(ForgotPasswordActivity.this)) {
                        requestMemberDetailService();
                    } else {
                        showAlertMessage(getString(R.string.error_no_connection));
                    }

                } else {
                    showAlertMessage(strErrorMessage);
                }

                break;
        }
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


    public TextWatcher mTextChangeListener = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            if (etEmailAddress.getText().toString().length() == 0 ||
                    etNameOfClub.getText().toString().length() == 0) {
                btForgotPwd.setEnabled(false);
                btForgotPwd.setBackground(ContextCompat.getDrawable(ForgotPasswordActivity.this, R.drawable.background_button_e8dcc9));
             } else {
                btForgotPwd.setEnabled(true);
                btForgotPwd.setBackground(ContextCompat.getDrawable(ForgotPasswordActivity.this, R.drawable.button_login_shape_c0995b));
            }
        }
    };

    /**
     * Implement a method to hit Members Detail
     * web service to get response.
     */
    public void requestMemberDetailService() {

        showPleaseWait("Loading...");

        aJsonParamsForgotPassword = new AJsonParamsForgotPassword();
        aJsonParamsForgotPassword.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsForgotPassword.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsForgotPassword.setUserId(etEmailAddress.getText().toString());

        forgotPasswordAPI = new ForgotPasswordAPI(
                strClientId, "FORGOTPASSWORD", aJsonParamsForgotPassword, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.forgotPassword(forgotPasswordAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
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
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<ForgotPasswordResponse>() {
        }.getType();
        forgotPasswordResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (forgotPasswordResponse.getMessage().equalsIgnoreCase("Success")) {

                etEmailAddress.setText("");
                showAlertOk("" + forgotPasswordResponse.getData());
            } else {
                showAlertMessage("" + forgotPasswordResponse.getMessage());
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            reportRollBarException(ForgotPasswordActivity.class.getSimpleName(), e.toString());
        }
    }

    /**
     * Implements a method to set FONT style using .ttf by putting
     * in ForecastMain\assets\fonts directory of current project.
     */
    private void setFontTypeFace() {
        tfRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        etEmailAddress.setTypeface(tfRobotoRegular);
        etNameOfClub.setTypeface(tfRobotoRegular);
        btForgotPwd.setTypeface(tfRobotoRegular);
    }

    /**
     * Implement a method to display {@link AlertDialog} with OK button. When user tap
     * on OK then go back to LOGIN screen.
     */
    private void showAlertOk(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                            onBackPressed();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
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
     * Implements a method to check whether Club name
     * entered or not?
     *
     * @return TRUE if input VALID.
     */
    private boolean isValid() {

        if (strClientId.length() == 0) {
            strErrorMessage = getResources().getString(R.string.error_club_name_required);
            return false;
        }
        return true;
    }

    private String getClubID(String strNameOfClub) {

        for (int iCount = 0; iCount < listOfClubsNames.size(); iCount++) {

            if (listOfClubsNames.get(iCount).getClubName().equalsIgnoreCase(strNameOfClub)) {
                return String.valueOf(listOfClubsNames.get(iCount).getClubId());
            }
        }
        return "";
    }
}
