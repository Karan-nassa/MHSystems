package com.mh.systems.hartsbourne.activites;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.constants.ApplicationGlobal;
import com.mh.systems.hartsbourne.constants.WebAPI;
import com.mh.systems.hartsbourne.models.ContactUs.AJsonParamsContactUs;
import com.mh.systems.hartsbourne.models.ContactUs.ContactUsAPI;
import com.mh.systems.hartsbourne.models.ContactUs.ContactUsResponse;
import com.mh.systems.hartsbourne.util.API.WebServiceMethods;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.rollbar.android.Rollbar;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Create this class to display Contact Us
 * functionality of this Golf Club.
 */
public class ContactUsActivity extends BaseActivity implements View.OnClickListener {

    private final String LOG_TAG = ContactUsActivity.class.getSimpleName();

    @Bind(R.id.tbContactUs)
    Toolbar tbContactUs;

    @Bind(R.id.llTelephone)
    LinearLayout llTelephone;

    @Bind(R.id.llFax)
    LinearLayout llFax;

    @Bind(R.id.llEmail)
    LinearLayout llEmail;

    @Bind(R.id.llAddress)
    LinearLayout llAddress;

    @Bind(R.id.tvTelephone)
    TextView tvTelephone;

    @Bind(R.id.tvFax)
    TextView tvFax;

    @Bind(R.id.tvEmail)
    TextView tvEmail;

    @Bind(R.id.tvAddress)
    TextView tvAddress;

    Intent callIntent = null;

    ContactUsAPI contactUsAPI;
    AJsonParamsContactUs aJsonParamsContactUs;

    ContactUsResponse contactUsResponse;

    String strTelephone, strFax, strEmail, strAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ButterKnife.bind(ContactUsActivity.this);

        //Set Tool bar.
        setSupportActionBar(tbContactUs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.text_title_contact_us));

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(ContactUsActivity.this)) {
            requestMemberDetailService();
        } else {
            showAlertMessage(getString(R.string.error_no_connection));
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


    @OnClick({R.id.tvTelephone, R.id.tvEmail})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvTelephone:

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions();
                    return;
                } else {
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + tvTelephone.getText().toString()));
                    startActivity(callIntent);
                }
                break;

            case R.id.tvEmail:
                callSendEmail();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted.
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + tvTelephone.getText().toString()));
                    startActivity(callIntent);
                } else {

                    // permission denied.
                }
                return;
            }
        }
    }

    /**
     * Implement a method to hit Members Detail
     * web service to get response.
     */
    public void requestMemberDetailService() {

        showPleaseWait("Loading...");

        aJsonParamsContactUs = new AJsonParamsContactUs();
        aJsonParamsContactUs.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);

        contactUsAPI = new ContactUsAPI(getClientId(), "GETCLUBCONTACTDETAILBYID", aJsonParamsContactUs, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.contactUs(contactUsAPI, new Callback<JsonObject>() {
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
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<ContactUsResponse>() {
        }.getType();
        contactUsResponse = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (contactUsResponse.getMessage().equalsIgnoreCase("Success")) {

                strTelephone = contactUsResponse.getData().getTelephone();
                strFax = contactUsResponse.getData().getFaxNo();
                strEmail = contactUsResponse.getData().getEmail();
                strAddress = contactUsResponse.getData().getAddress();

                if (strTelephone.length() > 0) {
                    llTelephone.setVisibility(View.VISIBLE);
                    tvTelephone.setText(strTelephone);
                } else {
                    llTelephone.setVisibility(View.GONE);
                }

                if (strFax.length() > 0) {
                    llFax.setVisibility(View.VISIBLE);
                    tvFax.setText(strFax);
                } else {
                    llFax.setVisibility(View.GONE);
                }

                if (strEmail.length() > 0) {
                    llEmail.setVisibility(View.VISIBLE);
                    tvEmail.setText(strEmail);
                } else {
                    llEmail.setVisibility(View.GONE);
                }

                if (strAddress.length() > 0) {
                    llAddress.setVisibility(View.VISIBLE);
                    tvAddress.setText(strAddress);
                } else {
                    llAddress.setVisibility(View.GONE);
                }

            } else {
                showAlertMessage(contactUsResponse.getMessage());
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Open Email App when user tap on Email
     * address.
     */
    protected void callSendEmail() {
        String[] TO = {tvEmail.getText().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, Html.fromHtml("<B>Choose an Email Client:</B>")));
        } catch (android.content.ActivityNotFoundException ex) {
            Rollbar.reportMessage("There is no email client installed.");
        }
    }
}
