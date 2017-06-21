package com.mh.systems.demoapp.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.ui.activites.HCapHistoryActivity;
import com.mh.systems.demoapp.ui.activites.MembersActivity;
import com.mh.systems.demoapp.utils.constants.ApplicationGlobal;
import com.mh.systems.demoapp.web.api.WebAPI;
import com.mh.systems.demoapp.web.models.contactus.AJsonParamsContactUs;
import com.mh.systems.demoapp.web.models.contactus.ContactUsAPI;
import com.mh.systems.demoapp.web.models.contactus.ContactUsResponse;
import com.mh.systems.demoapp.web.api.WebServiceMethods;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.rollbar.android.Rollbar;

import java.lang.reflect.Type;
import java.util.List;

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
public class ContactUsFragment extends Fragment implements View.OnClickListener {

    private final String LOG_TAG = ContactUsFragment.class.getSimpleName();

    @Bind(R.id.llTelephone)
    LinearLayout llTelephone;

    @Bind(R.id.llFax)
    LinearLayout llFax;

    @Bind(R.id.llEmail)
    LinearLayout llEmail;

    @Bind(R.id.llAddress)
    LinearLayout llAddress;

    @Bind(R.id.tvFax)
    TextView tvFax;

    @Bind(R.id.tvAddress)
    TextView tvAddress;

    @Bind(R.id.llDepartments)
    LinearLayout llDepartments;

    @Bind(R.id.ivAppLogo)
    ImageView ivAppLogo;

    @Bind({R.id.tvEmail, R.id.tvEmailAdministration, R.id.tvEmailRstMngr, R.id.tvEmailFoodMngr, R.id.tvEmailClubShop, R.id.tvEmailCaddieMaster,
            R.id.tvEmailGolfReservations, R.id.tvEmailGolfOpMngr, R.id.tvEmailGeneralMngr, R.id.tvEmailSecretariat})
    List<TextView> listOfEmailViews;

    @Bind({R.id.tvTelephone, R.id.tvCallAdministration, R.id.tvCallRstMngr, R.id.tvCallFoodMngr, R.id.tvCallClubShop, R.id.tvCallCaddieMaster,
            R.id.tvCallGolfReservations, R.id.tvCallGolfOpMngr, R.id.tvCallGeneralMngr, R.id.tvCallSecretariat})
    List<TextView> listOfContactViews;

    Intent callIntent = null;

    ContactUsAPI contactUsAPI;
    AJsonParamsContactUs aJsonParamsContactUs;

    ContactUsResponse contactUsResponse;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;

    String strTelephone, strFax, strEmail, strAddress;
    String strContactToCall = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewRootFragment = inflater.inflate(R.layout.fragment_contact_us, container, false);

        //Initialize butterKnife.
        ButterKnife.bind(this, viewRootFragment);

        return viewRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            ((MembersActivity) getActivity()).setiTabPosition(2);

            ((MembersActivity) getActivity()).setFragmentInstance(new ContactUsFragment());

            /**
             *  Check internet connection before hitting server request.
             */
            if (((MembersActivity) getActivity()).isOnline(getActivity())) {
                requestMemberDetailService();

                //To Hide 'No FRIEND FOUND' message
                ((MembersActivity) getActivity()).updateNoDataUI(true, 1);

            } else {
                ((MembersActivity) getActivity()).showAlertMessage(getString(R.string.error_no_connection));
            }

        }
    }

    @OnClick({R.id.tvTelephone, R.id.tvEmail,
            R.id.tvEmailAdministration, R.id.tvCallAdministration,
            R.id.tvEmailRstMngr, R.id.tvCallRstMngr,
            R.id.tvEmailFoodMngr, R.id.tvCallFoodMngr,
            R.id.tvEmailClubShop, R.id.tvCallClubShop,
            R.id.tvEmailCaddieMaster, R.id.tvCallCaddieMaster,
            R.id.tvEmailGolfReservations, R.id.tvCallGolfReservations,
            R.id.tvEmailGolfOpMngr, R.id.tvCallGolfOpMngr,
            R.id.tvEmailGeneralMngr, R.id.tvCallGeneralMngr,
            R.id.tvEmailSecretariat, R.id.tvCallSecretariat})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvTelephone:
            case R.id.tvCallAdministration:
            case R.id.tvCallRstMngr:
            case R.id.tvCallFoodMngr:
            case R.id.tvCallClubShop:
            case R.id.tvCallCaddieMaster:
            case R.id.tvCallGolfReservations:
            case R.id.tvCallGolfOpMngr:
            case R.id.tvCallGeneralMngr:
            case R.id.tvCallSecretariat:

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    strContactToCall = ((TextView) view).getText().toString();
                    ((MembersActivity) getActivity()).requestPermissions();
                    return;
                } else {
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + ((TextView) view).getText().toString()/* tvTelephone.getText().toString()*/));
                    startActivity(callIntent);
                }
                break;

            case R.id.tvEmail:
            case R.id.tvEmailAdministration:
            case R.id.tvEmailRstMngr:
            case R.id.tvEmailFoodMngr:
            case R.id.tvEmailClubShop:
            case R.id.tvEmailCaddieMaster:
            case R.id.tvEmailGolfReservations:
            case R.id.tvEmailGolfOpMngr:
            case R.id.tvEmailGeneralMngr:
            case R.id.tvEmailSecretariat:
                callSendEmail(((TextView) view).getText().toString());
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted.
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + strContactToCall/*tvTelephone.getText().toString()*/));
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

        ((MembersActivity) getActivity()).showPleaseWait("Loading...");

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
                ((MembersActivity) getActivity()).hideProgress();
                ((MembersActivity) getActivity()).showAlertMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    public String getMemberId() {
        return ((MembersActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return ((MembersActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
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

                llDepartments.setVisibility(View.VISIBLE);

                strTelephone = contactUsResponse.getData().getTelephone();
                strFax = contactUsResponse.getData().getFaxNo();
                strEmail = contactUsResponse.getData().getEmail();
                strAddress = contactUsResponse.getData().getAddress();

                //Display Logo of the App.
                ivAppLogo.setImageResource(R.mipmap.ic_home_golfclub);

                if (strTelephone.length() > 0) {
                    llTelephone.setVisibility(View.VISIBLE);
                    listOfContactViews.get(0).setText(strTelephone);
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
                    listOfEmailViews.get(0).setText(strEmail);
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
                llDepartments.setVisibility(View.GONE);
                ((MembersActivity) getActivity()).showAlertMessage(contactUsResponse.getMessage());
            }
            ((MembersActivity) getActivity()).hideProgress();
        } catch (Exception e) {
            llDepartments.setVisibility(View.GONE);
            ((MembersActivity) getActivity()).hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            ((MembersActivity)getActivity()).reportRollBarException(ContactUsFragment.class.getSimpleName(), e.toString());
        }
    }

    /**
     * Open Email App when user tap on Email
     * address.
     *
     * @param strEmailAddress : EMAIL address which selected to send email.
     */
    private void callSendEmail(String strEmailAddress) {
        String[] TO = {strEmailAddress};
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
