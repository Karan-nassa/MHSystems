package com.mh.systems.york.ui.activites;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.york.R;
import com.mh.systems.york.utils.constants.ApplicationGlobal;
import com.mh.systems.york.web.api.WebAPI;
import com.mh.systems.york.web.models.AJsonParamsMembersDatail;
import com.mh.systems.york.web.models.editdetailmode.AJsonParamsEditDetailMode;
import com.mh.systems.york.web.models.editdetailmode.EditDetailModeAPI;
import com.mh.systems.york.web.models.editdetailmode.EditDetailModeResponse;
import com.mh.systems.york.web.models.MembersDetailAPI;
import com.mh.systems.york.web.models.MembersDetailsData;
import com.mh.systems.york.web.models.MembersDetailsItems;
import com.mh.systems.york.web.api.WebServiceMethods;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class EditDetailsActivity extends BaseActivity implements View.OnClickListener {

    private final String LOG_TAG = EditDetailsActivity.class.getSimpleName();

    private String strEmailOfPerson, strMobileContactOfPerson, strTelWork, strTelHome, strAddressLine1, strAddressLine2, strAddressLine3,
            strTown, strCounty, strPostalCode, strCountry;
    boolean isUpdateToSave = false;
    String strErrorMessage = "";

    Toolbar tbMyDetailEdit;

    EditText etEditEmail, etEditMobile, etEditWork, etEditHome,
            etEditAddress1, etEditAddress2, etEditAddress3, etEditTown, etCounty, etEditPostCode, etEditCountry;

    LinearLayout llEditDetailGroup;

    Typeface tfRobotoRegular;

    CountryPicker countryPicker;

    EditDetailModeAPI editDetailModeAPI;
    AJsonParamsEditDetailMode aJsonParamsEditDetailMode;

    EditDetailModeResponse editDetailModeResponse;

    MembersDetailsData membersDetailsData;
    MembersDetailAPI membersDetailAPI;
    AJsonParamsMembersDatail aJsonParamsMembersDatail;
    MembersDetailsItems membersDetailItems;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        tbMyDetailEdit = (Toolbar) findViewById(R.id.tbMyDetailEdit);
        if (tbMyDetailEdit != null) {
            setSupportActionBar(tbMyDetailEdit);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();

        //Initially hide whole view group.
        llEditDetailGroup.setVisibility(View.GONE);

        countryPicker = CountryPicker.newInstance("Select Country");

        countryPicker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                countryPicker.dismiss();
                etEditCountry.setText(name);
                countryPicker.getSearchEditText().setText("");
            }
        });

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(EditDetailsActivity.this)) {
            getMemberDetailService();
        } else {
            showErrorMessage(getString(R.string.error_server_problem));
        }

        etEditCountry.setOnClickListener(this);

        etEditEmail.setOnFocusChangeListener(mFocusListener);
        etEditMobile.setOnFocusChangeListener(mFocusListener);
        etEditWork.setOnFocusChangeListener(mFocusListener);
        etEditHome.setOnFocusChangeListener(mFocusListener);
        etEditAddress1.setOnFocusChangeListener(mFocusListener);
        etEditAddress2.setOnFocusChangeListener(mFocusListener);
        etEditAddress3.setOnFocusChangeListener(mFocusListener);
        etEditTown.setOnFocusChangeListener(mFocusListener);
        etCounty.setOnFocusChangeListener(mFocusListener);
        etEditPostCode.setOnFocusChangeListener(mFocusListener);
        etEditCountry.setOnFocusChangeListener(mFocusListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_privacy, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (isUpdateToSave) {
                    showAlertSave();
                } else {
                    finish();
                }
                break;

            case R.id.action_save:
                callUpdateWebService();
                break;

            case R.id.item_edit_mode:
                break;

            case R.id.item_toggle_mode:
                if (isUpdateToSave) {
                    showAlertSave();
                } else {
                    intent = new Intent(EditDetailsActivity.this, EditToggleDetailActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * On Click Listener
     **/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etEditCountry:
                countryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isUpdateToSave) {
            showAlertSave();
        } else {
            super.onBackPressed();
        }
    }

    private View.OnFocusChangeListener mFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            isUpdateToSave = true;
        }
    };


    /*************************** START OF SAVE AND UPDATE MEMBER DETAIL SERVICE ***************************/

    /**
     * Implements this method to check Internet Connection and hit web service
     * if connection exists.
     */
    private void callUpdateWebService() {

        if (isValidInput()) {
            if (isOnline(this)) {
                updateMemberDetails();
            } else {
                showAlertMessage(getResources().getString(R.string.error_no_internet));
                hideProgress();
            }
        } else {
            showAlertMessage(strErrorMessage);
        }
    }

    /**
     * Implements a method to hit update members detail service.
     */
    private void updateMemberDetails() {

        showPleaseWait("Please wait...");

        aJsonParamsEditDetailMode = new AJsonParamsEditDetailMode();
        aJsonParamsEditDetailMode.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsEditDetailMode.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsEditDetailMode.setMemberId(getMemberId());
        aJsonParamsEditDetailMode.setTelNoHome(strTelHome);
        aJsonParamsEditDetailMode.setTelNoWork(strTelWork);
        aJsonParamsEditDetailMode.setTelNoMob(strMobileContactOfPerson);
        aJsonParamsEditDetailMode.setEMail(strEmailOfPerson);
        aJsonParamsEditDetailMode.setLine1(strAddressLine1);
        aJsonParamsEditDetailMode.setLine2(strAddressLine2);
        aJsonParamsEditDetailMode.setLine3(strAddressLine3);
        aJsonParamsEditDetailMode.setTown(strTown);
        aJsonParamsEditDetailMode.setCounty(strCounty);
        aJsonParamsEditDetailMode.setPostCode(strPostalCode);
        aJsonParamsEditDetailMode.setCountry(strCountry);

        editDetailModeAPI = new EditDetailModeAPI(getClientId(), "UPDATEMEMBERCONTACTDETAILS", aJsonParamsEditDetailMode, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.updateMemberDetails(editDetailModeAPI, new Callback<JsonObject>() {
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

        Type type = new TypeToken<EditDetailModeResponse>() {
        }.getType();
        editDetailModeResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (editDetailModeResponse.getMessage().equalsIgnoreCase("Success")) {

                //Set False after save value.
                isUpdateToSave = false;

                showAlertUpdateResult(editDetailModeResponse.getData());
            } else {
                //If web service not respond in any case.
                showAlertMessage(editDetailModeResponse.getMessage());
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
    private String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    private String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /*************************** END OF SAVE AND UPDATE MEMBER DETAIL SERVICE ***************************/

    /**
     * Method to initialize views
     **/
    private void initializeViews() {
        tfRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        etEditEmail = (EditText) findViewById(R.id.etEditEmail);
        etEditMobile = (EditText) findViewById(R.id.etEditMobile);
        etEditWork = (EditText) findViewById(R.id.etEditWork);
        etEditHome = (EditText) findViewById(R.id.etEditHome);
        etEditAddress1 = (EditText) findViewById(R.id.etEditAddress1);
        etEditAddress2 = (EditText) findViewById(R.id.etEditAddress2);
        etEditAddress3 = (EditText) findViewById(R.id.etEditAddress3);
        etEditTown = (EditText) findViewById(R.id.etEditTown);
        etCounty = (EditText) findViewById(R.id.etCounty);
        etEditPostCode = (EditText) findViewById(R.id.etEditPostCode);
        etEditCountry = (EditText) findViewById(R.id.etEditCountry);

        llEditDetailGroup = (LinearLayout) findViewById(R.id.llEditDetailGroup);

        setTypeFace();
    }

    /**
     * Implements method to set custom font style of any view.
     */
    private void setTypeFace() {
        etEditEmail.setTypeface(tfRobotoRegular);
        etEditMobile.setTypeface(tfRobotoRegular);
        etEditWork.setTypeface(tfRobotoRegular);
        etEditHome.setTypeface(tfRobotoRegular);
        etEditAddress1.setTypeface(tfRobotoRegular);
        etEditAddress2.setTypeface(tfRobotoRegular);
        etEditAddress3.setTypeface(tfRobotoRegular);
        etEditTown.setTypeface(tfRobotoRegular);
        etCounty.setTypeface(tfRobotoRegular);
        etEditPostCode.setTypeface(tfRobotoRegular);
        etEditCountry.setTypeface(tfRobotoRegular);
    }

    /**
     * Implements to display message from Web Service.
     */
    private void showAlertUpdateResult(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Implements this method to Save information if anything updated.
     */
    private void showAlertSave() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        dialog.setContentView(R.layout.alert_edit_detail);
        // Set dialog title
        dialog.setTitle("");

        // set values for custom dialog components - text, image and button
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
        TextView tvSaveNow = (TextView) dialog.findViewById(R.id.tvSaveNow);

        dialog.show();

        // if decline button is clicked, close the custom dialog
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
                finish();
            }
        });

        tvSaveNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callUpdateWebService();
            }
        });
    }

    /**
     * Implements a method to get data and store to
     * local instance.
     */
    private void updateUI() {
        strEmailOfPerson = membersDetailsData.getContactDetails().getEMail();
        strMobileContactOfPerson = membersDetailsData.getContactDetails().getTelNoMob();
        strTelWork = membersDetailsData.getContactDetails().getTelNoWork();
        strTelHome = membersDetailsData.getContactDetails().getTelNoHome();

        //ADDRESS
        strAddressLine1 = membersDetailsData.getContactDetails().getAddress().getLine1();
        strAddressLine2 = membersDetailsData.getContactDetails().getAddress().getLine2();
        strAddressLine3 = membersDetailsData.getContactDetails().getAddress().getLine3();

        strTown = membersDetailsData.getContactDetails().getAddress().getTown();
        strCounty = membersDetailsData.getContactDetails().getAddress().getCounty();
        strPostalCode = membersDetailsData.getContactDetails().getAddress().getPostCode();
        strCountry = membersDetailsData.getContactDetails().getAddress().getCountry();

        /////////////////  SET DATA TO INPUT FIELDS PROGRAMMATICALLY  /////////////////

        etEditEmail.setText(strEmailOfPerson);
        etEditMobile.setText(strMobileContactOfPerson);
        etEditWork.setText(strTelWork);
        etEditHome.setText(strTelHome);
        etEditAddress1.setText(strAddressLine1);
        etEditAddress2.setText(strAddressLine2);
        etEditAddress3.setText(strAddressLine3);
        etEditTown.setText(strTown);
        etCounty.setText(strCounty);
        etEditPostCode.setText(strPostalCode);
        etEditCountry.setText(strCountry);
    }

    /**
     * validate Email Address format.
     * Ex-akhi@mani.com 
     */
    private boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Check whether input is valid or not.
     *
     * @return TRUE if valid, otherwise FALSE.
     */
    private boolean isValidInput() {

        strEmailOfPerson = etEditEmail.getText().toString();
        strMobileContactOfPerson = etEditMobile.getText().toString();
        strTelWork = etEditWork.getText().toString();
        strTelHome = etEditHome.getText().toString();

        strAddressLine1 = etEditAddress1.getText().toString();
        strAddressLine2 = etEditAddress2.getText().toString();
        strAddressLine3 = etEditAddress3.getText().toString();

        strTown = etEditTown.getText().toString();
        strCounty = etCounty.getText().toString();
        strCountry = etEditCountry.getText().toString();
        strPostalCode = etEditPostCode.getText().toString();

        if (strEmailOfPerson.length() > 0) {
            if (emailValidator(strEmailOfPerson)) {
                return true;
            } else {
                strErrorMessage = getResources().getString(R.string.error_valid_email);
                return false;
            }
        }
        return true;
    }

    /*************************** START OF GET MEMBER DETAIL SERVICE ***************************/

    /**
     * Implement a method to hit Members Detail
     * web service to get response.
     */
    private void getMemberDetailService() {

        showPleaseWait("Loading...");

        aJsonParamsMembersDatail = new AJsonParamsMembersDatail();
        aJsonParamsMembersDatail.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsMembersDatail.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsMembersDatail.setMemberid(getMemberId());
        aJsonParamsMembersDatail.setLoginMemberId(getMemberId());

        membersDetailAPI = new MembersDetailAPI((getClientId()),
                "GETMEMBER",
                aJsonParamsMembersDatail,
                ApplicationGlobal.TAG_GCLUB_WEBSERVICES,
                ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getMembersDetail(membersDetailAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                getDetailSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();
                showErrorMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void getDetailSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<MembersDetailsItems>() {
        }.getType();
        membersDetailItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (membersDetailItems.getMessage().equalsIgnoreCase("Success")) {

                if (membersDetailItems.getData() != null) {
                    llEditDetailGroup.setVisibility(View.VISIBLE);

                    membersDetailsData = membersDetailItems.getData();

                    updateUI();
                } else {
                    llEditDetailGroup.setVisibility(View.GONE);
                    showErrorMessage(getString(R.string.error_server_problem));
                }
            } else {
                llEditDetailGroup.setVisibility(View.GONE);
                showErrorMessage(membersDetailItems.getMessage());
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
            llEditDetailGroup.setVisibility(View.GONE);
        }
    }

    /*************************** END OF GET MEMBER DETAIL SERVICE ***************************/

    /**
     * Implement a method to show Error message
     * Alert Dialog.
     */
    private void showErrorMessage(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(EditDetailsActivity.this);
            builder.setTitle("");
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
