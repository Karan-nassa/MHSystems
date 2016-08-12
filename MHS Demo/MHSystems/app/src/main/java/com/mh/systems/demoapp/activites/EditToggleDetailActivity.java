package com.mh.systems.demoapp.activites;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.EditDetailMode.AJsonParamsEditDetailMode;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeAPI;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeResponse;
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

    Toolbar tbEditToggleDetail;
    private Intent intent;

    TogglePrivacyAPI togglePrivacyAPI;
    AJsonParamsToggle aJsonParamsToggle;

    TogglePrivacyResponse togglePrivacyResponse;

    boolean isUpdateToSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_toggle_detail);

        tbEditToggleDetail = (Toolbar) findViewById(R.id.tbEditToggleDetail);
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

    /*************************** START OF UPDATE PRIVACY SETTINGS OF MEMBER SERVICE ***************************/

    /**
     * Implements this method to check Internet Connection and hit web service
     * if connection exists.
     */
    private void callUpdateWebService() {
        if (isOnline(this)) {
            showAlertMessage("Work-in Progress.");
          //  updatePrivacySettings();
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
        aJsonParamsToggle.setTelNoHomePrivacy("Members");
        aJsonParamsToggle.setTelNoWorkPrivacy("Private");
        aJsonParamsToggle.setTelNoMobPrivacy("Private");
        aJsonParamsToggle.setEMailPrivacy("Private");
        aJsonParamsToggle.setAddressPrivacy("Private");

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
}
