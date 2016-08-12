package com.mh.systems.demoapp.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.adapter.RecyclerAdapter.HCapHistoryRecyclerAdapter;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.EditDetailMode.AJsonParamsEditDetailMode;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeAPI;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeResponse;
import com.mh.systems.demoapp.models.HCapHistory.AJsonParamsHcapHistory;
import com.mh.systems.demoapp.models.HCapHistory.HCapHistoryAPI;
import com.mh.systems.demoapp.models.HCapHistory.HCapHistoryResult;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class EditDetailsActivity extends BaseActivity implements View.OnClickListener {

    private final String LOG_TAG = EditDetailsActivity.class.getSimpleName();

    Toolbar tbMyDetailEdit;

    EditText etEditEmail, etEditMobile, etEditWork, etEditHome,
            etEditAddress1, etEditAddress2, etEditAddress3, etEditTown, etCounty, etEditPostCode, etEditCountry;
    Typeface tfRobotoRegular;

    CountryPicker countryPicker;

    EditDetailModeAPI editDetailModeAPI;
    AJsonParamsEditDetailMode aJsonParamsEditDetailMode;

    EditDetailModeResponse editDetailModeResponse;

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

        countryPicker = CountryPicker.newInstance("Select Country");

        countryPicker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                countryPicker.dismiss();
                etEditCountry.setText(name);
                countryPicker.getSearchEditText().setText("");
            }
        });

        etEditCountry.setOnClickListener(this);
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

            case R.id.action_save:
                /**
                 *  Check internet connection before hitting server request.
                 */
                if (isOnline(this)) {
                    updateMemberDetails();
                } else {
                    showAlertMessage(getResources().getString(R.string.error_no_internet));
                    hideProgress();
                }
                break;

            case R.id.item_edit_mode:
                break;

            case R.id.item_toggle_mode:
                intent = new Intent(EditDetailsActivity.this, EditToggleDetailActivity.class);
                startActivity(intent);
                finish();
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

    /*************************** START OF SAVE AND UPDATE MEMBER DETAIL SERVICE ***************************/

    /**
     * Implements a method to hit update members detail service.
     */
    private void updateMemberDetails() {

        showPleaseWait("Please wait...");

        aJsonParamsEditDetailMode = new AJsonParamsEditDetailMode();
        aJsonParamsEditDetailMode.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsEditDetailMode.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsEditDetailMode.setMemberId(getMemberId());
        aJsonParamsEditDetailMode.setTelNoHome("01727 798481");
        aJsonParamsEditDetailMode.setTelNoWork("");
        aJsonParamsEditDetailMode.setTelNoMob("07884 572612");
        aJsonParamsEditDetailMode.setEMail("halmstadt@btinternet.com");
        aJsonParamsEditDetailMode.setLine1("6 Campion Road");
        aJsonParamsEditDetailMode.setLine2("");
        aJsonParamsEditDetailMode.setLine3("");
        aJsonParamsEditDetailMode.setTown("Herts");
        aJsonParamsEditDetailMode.setCounty("Herts");
        aJsonParamsEditDetailMode.setPostCode("EN6 1NZ");
        aJsonParamsEditDetailMode.setCountry("");

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

        Type type = new TypeToken<EditDetailModeResponse>() {
        }.getType();
        editDetailModeResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (editDetailModeResponse.getMessage().equalsIgnoreCase("Success")) {

                showAlertMessage(editDetailModeResponse.getData());
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
    public String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
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

    /*************************** START OF WEB SERVICE IMPLEMENTATION TO GET MEMBER DETAIL AND SET IN INPUT FIELDS ***************************/


    /*************************** END OF WEB SERVICE IMPLEMENTATION TO GET MEMBER DETAIL AND SET IN INPUT FIELDS ***************************/
}
