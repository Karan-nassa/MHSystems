package com.mh.systems.demoapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class EditToggleDetailActivity extends BaseActivity {

    private final String LOG_TAG = EditToggleDetailActivity.class.getSimpleName();

    Toolbar tbEditToggleDetail;
    private Intent intent;

    TogglePrivacyAPI togglePrivacyAPI;
    AJsonParamsToggle aJsonParamsToggle;

    TogglePrivacyResponse togglePrivacyResponse;

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
                finish();
                break;

            case R.id.item_edit_mode:
                intent = new Intent(EditToggleDetailActivity.this, EditDetailsActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.action_save:
                /**
                 *  Check internet connection before hitting server request.
                 */
                if (isOnline(this)) {
                    updatePrivacySettings();
                } else {
                    showAlertMessage(getResources().getString(R.string.error_no_internet));
                    hideProgress();
                }
                break;

            case R.id.item_toggle_mode:
                break;

            default:
                break;
        }
        return true;
    }

    /*************************** START OF UPDATE PRIVACY SETTINGS OF MEMBER SERVICE ***************************/

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
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

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
        togglePrivacyResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (togglePrivacyResponse.getMessage().equalsIgnoreCase("Success")) {

                showAlertMessage(togglePrivacyResponse.getData());
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

    /*************************** END OF UPDATE PRIVACY SETTINGS OF MEMBER SERVICE ***************************/
}
