package com.mh.systems.demoapp.activites;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.common.collect.Range;
import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.UpdatePassword.AJsonParamsUpdatePassword;
import com.mh.systems.demoapp.models.UpdatePassword.UpdatePassswordAPI;
import com.mh.systems.demoapp.models.UpdatePassword.UpdatePasswordResponse;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {

    private final String LOG_TAG = UpdatePasswordActivity.class.getSimpleName();

    private AwesomeValidation mAwesomeValidation;

    @Bind(R.id.etUserName)
    EditText etUserName;

    @Bind(R.id.etNewPassword)
    EditText etNewPassword;

    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;

    @Bind(R.id.btUpdatePassword)
    Button btUpdatePassword;

    @Bind(R.id.tvUpdatePwdTitle)
    TextView tvUpdatePwdTitle;

    //List of type ResetPassword will store type Book which is our data model
    private UpdatePassswordAPI updatePassswordAPI;
    private AJsonParamsUpdatePassword aJsonParamsUpdatePassword;
    private UpdatePasswordResponse updatePasswordResponse;

    Typeface tfRobotoRegular, tfRobotoMedium;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strUserName = "", strNewPassword = "", strConfirmPassword = "";

    private TextWatcher mTextChangeListener = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            if (etUserName.getText().toString().length() == 0 || etNewPassword.getText().toString().length() == 0 || etConfirmPassword.getText().toString().length() == 0) {
                btUpdatePassword.setEnabled(false);
                btUpdatePassword.setBackground(ContextCompat.getDrawable(UpdatePasswordActivity.this, R.drawable.background_button_e8dcc9));
            } else {
                btUpdatePassword.setEnabled(true);
                btUpdatePassword.setBackground(ContextCompat.getDrawable(UpdatePasswordActivity.this, R.drawable.button_login_shape_c0995b));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        ButterKnife.bind(UpdatePasswordActivity.this);

        // Step 1: designate a style
        mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
        mAwesomeValidation.setContext(this);  // mandatory for UNDERLABEL style
        mAwesomeValidation.addValidation(this, R.id.etConfirmPassword, Range.closed(strNewPassword, strConfirmPassword), R.string.error_no_match_pwd);

        btUpdatePassword.setEnabled(false);

        //Set Custom font style.
        setFontTypeFace();

        // show soft keyboard
        etUserName.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        etNewPassword.addTextChangedListener(mTextChangeListener);
        etConfirmPassword.addTextChangedListener(mTextChangeListener);
        etUserName.addTextChangedListener(mTextChangeListener);

        //Set Click listener event.
        btUpdatePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btUpdatePassword:

                strUserName = etUserName.getText().toString();
                strNewPassword = etNewPassword.getText().toString();
                strConfirmPassword = etConfirmPassword.getText().toString();

                if (strNewPassword.equals(strConfirmPassword)) {
                    /**
                     *  Check internet connection before hitting server request.
                     */
                    if (isOnline(UpdatePasswordActivity.this)) {
                        requestUpdatePasswordService();
                    } else {
                        showAlertMessage(getString(R.string.error_no_connection));
                    }
                } else {
                    // Step 2: add validations
                    // support regex string, java.util.regex.Pattern and Guava#Range
                    // you can pass resource or string
                    mAwesomeValidation.validate();
                }
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
     * Implements method to UPDATE temporary password
     * web service.
     */
    private void requestUpdatePasswordService() {

        showPleaseWait("Loading...");

        aJsonParamsUpdatePassword = new AJsonParamsUpdatePassword();
        aJsonParamsUpdatePassword.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsUpdatePassword.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsUpdatePassword.setMemberId(getMemberId());
        aJsonParamsUpdatePassword.setUserID(strUserName);
        aJsonParamsUpdatePassword.setPassword(strNewPassword);

        updatePassswordAPI = new UpdatePassswordAPI(getClientId(), "UPDATEMEMBERUSERNAMEPWD", aJsonParamsUpdatePassword, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.updatePassword(updatePassswordAPI, new Callback<JsonObject>() {
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

        Type type = new TypeToken<UpdatePasswordResponse>() {
        }.getType();
        updatePasswordResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (updatePasswordResponse.getMessage().equalsIgnoreCase("Success")) {
                clearAllFields();

                showAlertOk("" + updatePasswordResponse.getData());
            } else {
                /*mAwesomeValidation.addValidation(etUserName, "regex", updatePasswordResponse.getMessage());
                mAwesomeValidation.validate();*/
                showAlertMessage("" + updatePasswordResponse.getMessage());
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
        etUserName.setText("");
        etNewPassword.setText("");
        etConfirmPassword.setText("");
        mAwesomeValidation.clear();
    }


    /**
     * Implements a method to set FONT style using .ttf by putting
     * in main\assets\fonts directory of current project.
     */
    private void setFontTypeFace() {
        tfRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tfRobotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        etUserName.setTypeface(tfRobotoRegular);
        etNewPassword.setTypeface(tfRobotoRegular);
        etConfirmPassword.setTypeface(tfRobotoRegular);

        btUpdatePassword.setTypeface(tfRobotoMedium);
        tvUpdatePwdTitle.setTypeface(tfRobotoMedium);
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

    /**
     * Implement a method to display {@link AlertDialog} with OK button. When user tap
     * on OK then go back to LOGIN screen.
     */
    public void showAlertOk(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;

                            Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                            Bundle informacion = new Bundle();
                            informacion.putString("USERNAME", "" + etUserName.getText().toString());
                            intent.putExtras(informacion);
                            setResult(RESULT_OK, intent);
                            finish();
                            // onBackPressed();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
