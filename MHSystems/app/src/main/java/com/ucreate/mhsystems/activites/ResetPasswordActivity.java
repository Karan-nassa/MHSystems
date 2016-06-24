package com.ucreate.mhsystems.activites;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.common.collect.Range;
import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.models.ResetPassword.AJsonParamsResetPwd;
import com.ucreate.mhsystems.models.ResetPassword.ResetPasswordAPI;
import com.ucreate.mhsystems.models.ResetPassword.ResetPasswordItems;
import com.ucreate.mhsystems.util.API.WebServiceMethods;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private final String LOG_TAG = ResetPasswordActivity.class.getSimpleName();

    private AwesomeValidation mAwesomeValidation;

    @Bind(R.id.tbResetPassword)
    Toolbar tbResetPassword;

    @Bind(R.id.etCurrentPassword)
    EditText etCurrentPassword;

    @Bind(R.id.etNewPassword)
    EditText etNewPassword;

    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;

    @Bind(R.id.btResetPassword)
    Button btResetPassword;

    @Bind(R.id.ivResetPassword)
    ImageView ivResetPassword;

    //List of type ResetPassword will store type Book which is our data model
    private ResetPasswordAPI resetPasswordAPI;
    AJsonParamsResetPwd aJsonParamsResetPwd;
    ResetPasswordItems resetPasswordItems;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strNewPassword = "", strConfirmPassword = "", strCurrentPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(ResetPasswordActivity.this);

        // Step 1: designate a style
        mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
        mAwesomeValidation.setContext(this);  // mandatory for UNDERLABEL style
        mAwesomeValidation.addValidation(ResetPasswordActivity.this, R.id.etConfirmPassword, Range.closed(strNewPassword, strConfirmPassword), R.string.error_pwd_no_match);

        setSupportActionBar(tbResetPassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.text_title_reset_pwd));

        //Set Click listener event.
        btResetPassword.setOnClickListener(this);
        ivResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btResetPassword:

                strCurrentPassword = etCurrentPassword.getText().toString();
                strNewPassword = etNewPassword.getText().toString();
                strConfirmPassword = etConfirmPassword.getText().toString();

                if (strCurrentPassword.length() == 0 && strNewPassword.length() == 0 && strConfirmPassword.length() == 0) {
                    showAlertMessage("All fields are required.");
                } else {
                    if (strNewPassword.equals(strConfirmPassword)) {
                        /**
                         *  Check internet connection before hitting server request.
                         */
                        if (isOnline(ResetPasswordActivity.this)) {
                            requestMemberDetailService();
                        } else {
                            showAlertMessage(getString(R.string.error_no_connection));
                        }
                    } else {
                        // Step 2: add validations
                        // support regex string, java.util.regex.Pattern and Guava#Range
                        // you can pass resource or string
                        mAwesomeValidation.validate();
                    }
                }
                break;

            case R.id.ivResetPassword:
                onBackPressed();
                break;
        }

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

    /**
     * Implement a method to hit Members Detail
     * web service to get response.
     */
    public void requestMemberDetailService() {

        showPleaseWait("Loading...");

        aJsonParamsResetPwd = new AJsonParamsResetPwd();
        aJsonParamsResetPwd.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsResetPwd.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsResetPwd.setMemberId(getMemberId());
        aJsonParamsResetPwd.setPassword(strCurrentPassword);
        aJsonParamsResetPwd.setNewPassword(strNewPassword);

        resetPasswordAPI = new ResetPasswordAPI(getClientId(), "RESETPASSWORD", aJsonParamsResetPwd, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.resetPassword(resetPasswordAPI, new Callback<JsonObject>() {
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

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<ResetPasswordItems>() {
        }.getType();
        resetPasswordItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (resetPasswordItems.getMessage().equalsIgnoreCase("Success")) {

                btResetPassword.setVisibility(View.GONE);
                clearAllFields();
            } else {
                mAwesomeValidation.addValidation(etCurrentPassword, "regex", resetPasswordItems.getMessage());
                mAwesomeValidation.validate();
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Implements the method to clear the fields.
     */
    private void clearAllFields() {
        etConfirmPassword.setText("");
        etNewPassword.setText("");
        etCurrentPassword.setText("");
        etCurrentPassword.requestFocus();
        mAwesomeValidation.clear();
    }

}
