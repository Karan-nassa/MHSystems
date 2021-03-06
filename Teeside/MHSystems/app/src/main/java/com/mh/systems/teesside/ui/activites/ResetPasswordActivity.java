package com.mh.systems.teesside.ui.activites;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.teesside.R;
import com.mh.systems.teesside.utils.constants.ApplicationGlobal;
import com.mh.systems.teesside.web.models.resetpassword.AJsonParamsResetPwd;
import com.mh.systems.teesside.web.models.resetpassword.ResetPasswordAPI;
import com.mh.systems.teesside.web.models.resetpassword.ResetPasswordItems;
import com.mh.systems.teesside.web.api.WebAPI;
import com.mh.systems.teesside.web.api.WebServiceMethods;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private final String LOG_TAG = ResetPasswordActivity.class.getSimpleName();

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

    @Bind(R.id.tvCurrPwdError)
    TextView tvCurrPwdError;

    @Bind(R.id.tvNewPwdError)
    TextView tvNewPwdError;

    private ResetPasswordAPI resetPasswordAPI;
    AJsonParamsResetPwd aJsonParamsResetPwd;
    ResetPasswordItems resetPasswordItems;

    Typeface tfRobotoRegular, tfsfTextRegular;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    String strNewPassword = "", strConfirmPassword = "", strCurrentPassword = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(ResetPasswordActivity.this);

        setSupportActionBar(tbResetPassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.text_title_reset_pwd));

        btResetPassword.setEnabled(false);

        //Set Custom font style.
        setFontTypeFace();

        etCurrentPassword.addTextChangedListener(mTextChangeListener);
        etNewPassword.addTextChangedListener(mTextChangeListener);
        etConfirmPassword.addTextChangedListener(mTextChangeListener);

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

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btResetPassword.getWindowToken(), 0);

                if (strCurrentPassword.trim().length() == 0 && strNewPassword.trim().length() == 0 && strConfirmPassword.trim().length() == 0) {
                    showAlertMessage(getString(R.string.error_fields_required));
                } else {
                    if (strNewPassword.equals(strConfirmPassword)) {

                        tvNewPwdError.setVisibility(View.GONE);

                        /**
                         *  Check internet connection before hitting server request.
                         */
                        if (isOnline(ResetPasswordActivity.this)) {
                            requestMemberDetailService();
                        } else {
                            showAlertMessage(getString(R.string.error_no_connection));
                        }
                    } else {
                        tvNewPwdError.setVisibility(View.VISIBLE);

                        /**
                         * Not sure about current password is right or not because we are
                         * matching it on server.
                         */
                        tvCurrPwdError.setVisibility(View.GONE);
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

            if (etNewPassword.getText().toString().length() == 0 || etNewPassword.getText().length() < 4
                    || etConfirmPassword.getText().toString().length() == 0
                    || etCurrentPassword.getText().toString().length() == 0) {
                btResetPassword.setEnabled(false);
                btResetPassword.setBackground(ContextCompat.getDrawable(ResetPasswordActivity.this, R.drawable.background_button_e8dcc9));
            } else {
                btResetPassword.setEnabled(true);
                btResetPassword.setBackground(ContextCompat.getDrawable(ResetPasswordActivity.this, R.drawable.button_login_shape_c0995b));
            }
        }
    };

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
                tvCurrPwdError.setVisibility(View.GONE);
                tvNewPwdError.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 1000);
            } else if (resetPasswordItems.getMessage().contains("Incorrect old password!")) {
                tvCurrPwdError.setVisibility(View.VISIBLE);
            } else {
                showAlertMessage(resetPasswordItems.getMessage());
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            reportRollBarException(ResetPasswordActivity.class.getSimpleName(), e.toString());
        }
    }

    /**
     * Implements a method to set FONT style using .ttf by putting
     * in ForecastMain\assets\fonts directory of current project.
     */
    private void setFontTypeFace() {
        tfRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tfsfTextRegular = Typeface.createFromAsset(getAssets(), "fonts/SF-UI-Text-Regular.otf");

        etNewPassword.setTypeface(tfRobotoRegular);
        etConfirmPassword.setTypeface(tfRobotoRegular);
        etCurrentPassword.setTypeface(tfRobotoRegular);

        btResetPassword.setTypeface(tfRobotoRegular);

        tvCurrPwdError.setTypeface(tfsfTextRegular);
        tvNewPwdError.setTypeface(tfsfTextRegular);
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
}
