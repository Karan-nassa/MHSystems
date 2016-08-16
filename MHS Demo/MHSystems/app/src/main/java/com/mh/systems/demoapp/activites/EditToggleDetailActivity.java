package com.mh.systems.demoapp.activites;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.fragments.MyDetailsFragment;
import com.mh.systems.demoapp.models.EditDetailMode.AJsonParamsEditDetailMode;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeAPI;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeResponse;
import com.mh.systems.demoapp.models.MembersDetailsData;
import com.mh.systems.demoapp.models.TogglePrivacy.AJsonParamsToggle;
import com.mh.systems.demoapp.models.TogglePrivacy.TogglePrivacyAPI;
import com.mh.systems.demoapp.models.TogglePrivacy.TogglePrivacyResponse;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EditToggleDetailActivity extends BaseActivity {

    private final String LOG_TAG = EditToggleDetailActivity.class.getSimpleName();

    private final String TAG_MEMBERS = "Members";
    private final String TAG_PRIVATE = "Private";
    private final String TAG_FRIENDS = "Friends";

    Toolbar tbEditToggleDetail;
    TextView tvMobile, tvWork, tvHome, tvEmail, tvAddress;
    Button btMobMembers, btMobFriends, btMobPrivate;
    Button btWorkMembers, btWorkFriends, btWorkPrivate;
    Button btHomeMembers, btHomeFriends, btHomePrivate;
    Button btEmailMembers, btEmailFriends, btEmailPrivate;
    Button btAddressMembers, btAddressFriends, btAddressPrivate;

    MembersDetailsData membersDetailsData;

    private Intent intent;

    TogglePrivacyAPI togglePrivacyAPI;
    AJsonParamsToggle aJsonParamsToggle;

    TogglePrivacyResponse togglePrivacyResponse;

    boolean isUpdateToSave = false;
    String strMobilePrivacy, strHomePrivacy, strWorkPrivacy, strEMailPrivacy, strAddressPrivacy;
    private String strTelNoMob, strTelWork, strTelHome, strEmailOfPerson, strAddressOfPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_toggle_detail);

        initViewResources();

        //Refresh tab data when go back from this screen.
        MyDetailsFragment.shouldRefresh = true;

        if (tbEditToggleDetail != null) {
            setSupportActionBar(tbEditToggleDetail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

            case R.id.item_edit_mode:
                if (isUpdateToSave) {
                    showAlertSave();
                } else {
                    intent = new Intent(EditToggleDetailActivity.this, EditDetailsActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.action_save:
                callUpdateWebService();
                break;

            case R.id.item_toggle_mode:
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (isUpdateToSave) {
            showAlertSave();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Implements this method to initialize all view resources.
     */
    private void initViewResources() {
        //Mobile Privacy.
        btMobMembers = (Button) findViewById(R.id.btMobMembers);
        btMobFriends = (Button) findViewById(R.id.btMobFriends);
        btMobPrivate = (Button) findViewById(R.id.btMobPrivate);

        //Work Privacy.
        btWorkMembers = (Button) findViewById(R.id.btWorkMembers);
        btWorkFriends = (Button) findViewById(R.id.btWorkFriends);
        btWorkPrivate = (Button) findViewById(R.id.btWorkPrivate);

        //Home Privacy.
        btHomeMembers = (Button) findViewById(R.id.btHomeMembers);
        btHomeFriends = (Button) findViewById(R.id.btHomeFriends);
        btHomePrivate = (Button) findViewById(R.id.btHomePrivate);

        //Email Privacy.
        btEmailMembers = (Button) findViewById(R.id.btEmailMembers);
        btEmailFriends = (Button) findViewById(R.id.btEmailFriends);
        btEmailPrivate = (Button) findViewById(R.id.btEmailPrivate);

        //Address Privacy.
        btAddressMembers = (Button) findViewById(R.id.btAddressMembers);
        btAddressFriends = (Button) findViewById(R.id.btAddressFriends);
        btAddressPrivate = (Button) findViewById(R.id.btAddressPrivate);

        tbEditToggleDetail = (Toolbar) findViewById(R.id.tbEditToggleDetail);

        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvWork = (TextView) findViewById(R.id.tvWork);
        tvHome = (TextView) findViewById(R.id.tvHome);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        /**
         * Get Members detail data from SharedPreference.
         **/
        membersDetailsData = loadPreferencesJson("YOUR_DETAILS_DATA");
        updateUI();
    }

    /*************************** START OF UPDATE PRIVACY SETTINGS OF MEMBER SERVICE ***************************/

    /**
     * Implements this method to check Internet Connection and hit web service
     * if connection exists.
     */
    private void callUpdateWebService() {
        if (isOnline(this)) {
            updatePrivacySettings();
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_internet));
            hideProgress();
        }
    }

    /**
     * Implements a method to hit update Privacy Settings service.
     */
    private void updatePrivacySettings() {

        showPleaseWait("Please wait...");

        aJsonParamsToggle = new AJsonParamsToggle();
        aJsonParamsToggle.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsToggle.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsToggle.setMemberId(getMemberId());
        aJsonParamsToggle.setTelNoHomePrivacy(strHomePrivacy);
        aJsonParamsToggle.setTelNoWorkPrivacy(strWorkPrivacy);
        aJsonParamsToggle.setTelNoMobPrivacy(strMobilePrivacy);
        aJsonParamsToggle.setEMailPrivacy(strEMailPrivacy);
        aJsonParamsToggle.setAddressPrivacy(strAddressPrivacy);

        togglePrivacyAPI = new TogglePrivacyAPI(getClientId(), "UPDATEPRIVACYSETTINGS", aJsonParamsToggle, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.updatePrivacySettings(togglePrivacyAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
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

        Type type = new TypeToken<TogglePrivacyResponse>() {
        }.getType();
        togglePrivacyResponse = new Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (togglePrivacyResponse.getMessage().equalsIgnoreCase("Success")) {

                //Set False after save value.
                isUpdateToSave = false;

                showAlertUpdateResult(togglePrivacyResponse.getData());
            } else {
                //If web service not respond in any case.
                showAlertMessage(togglePrivacyResponse.getMessage());
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
        hideProgress();
    }

    /**
     * Implements a method to get MEMBER-ID from {@link SharedPreferences}
     */
    public String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /*************************** END OF UPDATE PRIVACY SETTINGS OF MEMBER SERVICE ***************************/

    /**
     * Implements to display message from Web Service.
     */
    /**
     * Implements to display message from Web Service.
     */
    public void showAlertUpdateResult(String strAlertMessage) {

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
        // Include dialog.xml file
        dialog.setContentView(R.layout.alert_edit_detail);
        // Set dialog title
        dialog.setTitle("Custom Dialog");

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
            }
        });

        tvSaveNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
                callUpdateWebService();
            }
        });
    }

    /**
     * Implements a method to get data and store to local instance.
     */
    private void updateUI() {
        strMobilePrivacy = membersDetailsData.getContactDetails().getTelNoMobPrivacy();
        strWorkPrivacy = membersDetailsData.getContactDetails().getTelNoWorkPrivacy();
        strHomePrivacy = membersDetailsData.getContactDetails().getTelNoHomePrivacy();
        strEMailPrivacy = membersDetailsData.getContactDetails().getEMailPrivacy();
        strAddressPrivacy = membersDetailsData.getContactDetails().getAddress1Privacy();

        //Set default Mobile contact privacy.
        if (strMobilePrivacy.equals(TAG_MEMBERS)) {
            onMobilePrivacy(btMobMembers);
        } else if (strMobilePrivacy.equals(TAG_FRIENDS)) {
            onMobilePrivacy(btMobFriends);
        } else {
            onMobilePrivacy(btMobPrivate);
        }

        //Set default Work contact privacy.
        if (strWorkPrivacy.equals(TAG_MEMBERS)) {
            onWorkPrivacy(btWorkMembers);
        } else if (strWorkPrivacy.equals(TAG_FRIENDS)) {
            onWorkPrivacy(btWorkFriends);
        } else {
            onWorkPrivacy(btWorkPrivate);
        }

        //Set default Home contact privacy.
        if (strHomePrivacy.equals(TAG_MEMBERS)) {
            onHomePrivacy(btHomeMembers);
        } else if (strHomePrivacy.equals(TAG_FRIENDS)) {
            onHomePrivacy(btHomeFriends);
        } else {
            onHomePrivacy(btHomePrivate);
        }

        //Set default EMAIL privacy.
        if (strEMailPrivacy.equals(TAG_MEMBERS)) {
            onEmailPrivacy(btEmailMembers);
        } else if (strEMailPrivacy.equals(TAG_FRIENDS)) {
            onEmailPrivacy(btEmailFriends);
        } else {
            onEmailPrivacy(btEmailPrivate);
        }

        //Set default ADDRESS privacy.
        if (strAddressPrivacy.equals(TAG_MEMBERS)) {
            onAddressPrivacy(btAddressMembers);
        } else if (strAddressPrivacy.equals(TAG_FRIENDS)) {
            onAddressPrivacy(btAddressFriends);
        } else {
            onAddressPrivacy(btAddressPrivate);
        }

        strTelNoMob = membersDetailsData.getContactDetails().getTelNoMob();
        strTelWork = membersDetailsData.getContactDetails().getTelNoWork();
        strTelHome = membersDetailsData.getContactDetails().getTelNoHome();
        strEmailOfPerson = membersDetailsData.getContactDetails().getEMail();
        strAddressOfPerson = membersDetailsData.getContactDetails().getAddress().getLine1();

        tvMobile.setText(strTelNoMob);
        tvWork.setText(strTelWork);
        tvHome.setText(strTelHome);
        tvEmail.setText(strEmailOfPerson);
        tvAddress.setText(strAddressOfPerson);
    }

    /**
     * Toggle privacy change functionality here for Mobile contacts.
     */
    public void onMobilePrivacy(View view) {

        isUpdateToSave = true;
        switch (view.getId()) {
            case R.id.btMobMembers:
                btMobMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btMobMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btMobFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btMobFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btMobPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btMobPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strMobilePrivacy = btMobMembers.getText().toString();
                break;

            case R.id.btMobFriends:
                btMobMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btMobMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btMobFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btMobFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btMobPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btMobPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strMobilePrivacy = btMobFriends.getText().toString();
                break;

            case R.id.btMobPrivate:
                btMobMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btMobMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btMobFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btMobFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btMobPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btMobPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                strMobilePrivacy = btMobPrivate.getText().toString();
                break;
        }
    }

    /**
     * Toggle privacy change functionality here for Work contacts.
     */
    public void onWorkPrivacy(View view) {

        isUpdateToSave = true;
        switch (view.getId()) {
            case R.id.btWorkMembers:
                btWorkMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btWorkMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btWorkFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btWorkFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btWorkPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btWorkPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strWorkPrivacy = btWorkMembers.getText().toString();
                break;

            case R.id.btWorkFriends:
                btWorkMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btWorkMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btWorkFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btWorkFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btWorkPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btWorkPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strWorkPrivacy = btWorkFriends.getText().toString();
                break;

            case R.id.btWorkPrivate:
                btWorkMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btWorkMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btWorkFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btWorkFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btWorkPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btWorkPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                strWorkPrivacy = btWorkPrivate.getText().toString();
                break;
        }
    }

    /**
     * Toggle privacy change functionality here for Home contacts.
     */
    public void onHomePrivacy(View view) {

        isUpdateToSave = true;
        switch (view.getId()) {
            case R.id.btHomeMembers:
                btHomeMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btHomeMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btHomeFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btHomeFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btHomePrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btHomePrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strHomePrivacy = btHomeMembers.getText().toString();
                break;

            case R.id.btHomeFriends:
                btHomeMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btHomeMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btHomeFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btHomeFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btHomePrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btHomePrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strHomePrivacy = btHomeFriends.getText().toString();
                break;

            case R.id.btHomePrivate:
                btHomeMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btHomeMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btHomeFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btHomeFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btHomePrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btHomePrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                strHomePrivacy = btHomePrivate.getText().toString();
                break;
        }
    }

    /**
     * Toggle privacy change functionality here for Email Privacy setting.
     */
    public void onEmailPrivacy(View view) {

        isUpdateToSave = true;
        switch (view.getId()) {
            case R.id.btEmailMembers:
                btEmailMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btEmailMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btEmailFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btEmailFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btEmailPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btEmailPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strEMailPrivacy = btEmailMembers.getText().toString();
                break;

            case R.id.btEmailFriends:
                btEmailMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btEmailMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btEmailFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btEmailFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btEmailPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btEmailPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strEMailPrivacy = btEmailFriends.getText().toString();
                break;

            case R.id.btEmailPrivate:
                btEmailMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btEmailMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btEmailFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btEmailFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btEmailPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btEmailPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                strEMailPrivacy = btEmailPrivate.getText().toString();
                break;
        }
    }

    /**
     * Toggle privacy change functionality here for Address Privacy setting.
     */
    public void onAddressPrivacy(View view) {

        isUpdateToSave = true;
        switch (view.getId()) {
            case R.id.btAddressMembers:
                btAddressMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btAddressMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btAddressFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btAddressFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btAddressPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btAddressPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strAddressPrivacy = btAddressMembers.getText().toString();
                break;

            case R.id.btAddressFriends:
                btAddressMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btAddressMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btAddressFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btAddressFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                btAddressPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btAddressPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                strAddressPrivacy = btAddressFriends.getText().toString();
                break;

            case R.id.btAddressPrivate:
                btAddressMembers.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btAddressMembers.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btAddressFriends.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorE8E2DD));
                btAddressFriends.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.color9B9B9B));

                btAddressPrivate.setBackgroundColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorC59A5C));
                btAddressPrivate.setTextColor(ContextCompat.getColor(EditToggleDetailActivity.this, R.color.colorWhiteffffff));

                strAddressPrivacy = btAddressPrivate.getText().toString();
                break;
        }
    }
}
