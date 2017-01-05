package com.mh.systems.demoapp.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.activites.YourAccountActivity;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.activites.BaseActivity;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.AJsonParamsMembersDatail;
import com.mh.systems.demoapp.models.MembersDetailAPI;
import com.mh.systems.demoapp.models.MembersDetailsItems;
import com.mh.systems.demoapp.util.API.WebServiceMethods;

import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * The {@link MyDetailsFragment} used to display the detail of LOGIN
 * MEMBER by passing MemberId.
 *
 * @author {@link karan@ucreate.co.in}
 * @version 1.0
 * @since 17 May, 2016
 */
public class MyDetailsFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MyDetailsFragment.class.getSimpleName();
    private String strUsernameOfPerson, strStreetOfPerson, strEmailOfPerson,
            strMobileContactOfPerson, strTypeOfPerson, strNameOfPerson, strTelHome, strTelWork;

    String strTitileValues[];

    public static boolean shouldRefresh = false;

    //This bool is used to stop on Edit Detail and Privacy screen to handle crash.
    private boolean isError = false;

    //private boolean isClassVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    private TextView tvUsernameOfPerson, tvStreetOfPerson, tvEmailOfPerson,
            tvMobileContactOfPerson, tvTypeOfPerson, tvNameOfPerson, tvWorkContactOfPerson, tvHomeContactOfPerson;

    private TextView tvEmailVisibilty, tvAddressVisibilty, tvMobileVisibilty, tvWorkVisibilty, tvHomeVisibilty;

    private LinearLayout llUsernameOfPerson, llStreetOfPerson, llEmailOfPerson,
            llMobileContactOfPerson, llTypeOfPerson, llNameOfPerson, llWorkContactOfPerson, llHomeContactOfPerson;

    LinearLayout llMyDetailGroup;

    /**
     * View Group and Title Label for hide if any of the view empty.
     */
    View llViewGroup[];
    View tvTitleLabel[];

    private View mRootFragment;

    //List of type Members list will store type Book which is our data model
    private MembersDetailAPI membersDetailAPI;
    AJsonParamsMembersDatail aJsonParamsMembersDatail;
    MembersDetailsItems membersDetailItems;

    private AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootFragment = inflater.inflate(R.layout.fragment_my_details, container, false);

        //Initialize the view resources.
        initializeViewResources(mRootFragment);

        //Check Internet connection and hit web service only on first time.
       /* isClassVisible = true;
        if (isClassVisible) {
            callWebService();
            ((YourAccountActivity) getActivity()).updateFilterIcon(0);
        }*/

        llViewGroup = new View[]{llUsernameOfPerson, /*llStreetOfPerson, llEmailOfPerson,*/
                /*llMobileContactOfPerson,*/ llTypeOfPerson, llNameOfPerson};

        tvTitleLabel = new View[]{tvUsernameOfPerson, /*tvStreetOfPerson, tvEmailOfPerson,*/
                /*tvMobileContactOfPerson,*/ tvTypeOfPerson, tvNameOfPerson};

        return mRootFragment;
    }

    @Override
    public void onResume() {
        super.onResume();

       /* if (shouldRefresh) {

            ((BaseActivity) getActivity()).showPleaseWait("Loading...");

            *//**
         *  Check internet connection before hitting server request.
         *//*
            if (((BaseActivity) getActivity()).isOnline(getActivity())) {
                ((YourAccountActivity) getActivity()).updateHasInternetUI(true);
                llMyDetailGroup.setVisibility(View.VISIBLE);
                requestMemberDetailService();
            } else {
                ((BaseActivity) getActivity()).hideProgress();
                llMyDetailGroup.setVisibility(View.GONE);
                ((YourAccountActivity) getActivity()).updateHasInternetUI(false);
            }
        }*/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            ((YourAccountActivity) getActivity()).updateFilterIcon(0);
            ((YourAccountActivity) getActivity()).setiOpenTabPosition(0);

            /**
             *  Check internet connection before hitting server request.
             */
            if (((BaseActivity) getActivity()).isOnline(getActivity())) {
                //((YourAccountActivity) getActivity()).updateHasInternetUI(true);
                //llMyDetailGroup.setVisibility(View.VISIBLE);
                requestMemberDetailService();
            } else {
                //((BaseActivity) getActivity()).hideProgress();
                //llMyDetailGroup.setVisibility(View.GONE);
                //((YourAccountActivity) getActivity()).updateHasInternetUI(false);
                //   ((BaseActivity) getActivity()).updateFragment(new NoInternetFragment());
            }
        }
    }

//   private void callWebService() {
//            /**
//             *  Check internet connection before hitting server request.
//             */
//            if (((BaseActivity) getActivity()).isOnline(getActivity())) {
//                ((YourAccountActivity) getActivity()).updateHasInternetUI(true);
//                llMyDetailGroup.setVisibility(View.VISIBLE);
//                requestMemberDetailService();
//            } else {
//                llMyDetailGroup.setVisibility(View.GONE);
//                ((YourAccountActivity) getActivity()).updateHasInternetUI(false);
//        }
//    }

    /**
     * Implement a method to hit Members Detail
     * web service to get response.
     */
    public void requestMemberDetailService() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsMembersDatail = new AJsonParamsMembersDatail();
        aJsonParamsMembersDatail.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParamsMembersDatail.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsMembersDatail.setMemberid(((YourAccountActivity) getActivity()).getMemberId());
        aJsonParamsMembersDatail.setLoginMemberId(((YourAccountActivity) getActivity()).getMemberId());

        membersDetailAPI = new MembersDetailAPI((((YourAccountActivity) getActivity()).getClientId()), "GETMEMBER", aJsonParamsMembersDatail, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

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

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                ((BaseActivity) getActivity()).hideProgress();
                showErrorMessage("" + getResources().getString(R.string.error_please_retry));
            }
        });
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

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
                    ((YourAccountActivity) getActivity()).updateHasInternetUI(true);
                    displayMembersData();
                } else {
                    ((YourAccountActivity) getActivity()).updateHasInternetUI(false);
                }
            } else {
                ((YourAccountActivity) getActivity()).updateHasInternetUI(false);
            }
            ((BaseActivity) getActivity()).hideProgress();
        } catch (Exception e) {
            ((BaseActivity) getActivity()).hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Implements a method to display data on each view
     * after getting SUCCESS from web service.
     */
    private void displayMembersData() {

        getMemberData();

        /**
         * Check value one by one and set GONE visibility if any of view
         * have empty value.
         */
        for (int iCouunter = 0; iCouunter < strTitileValues.length; iCouunter++) {
            checkHideStatus(strTitileValues[iCouunter], (TextView) tvTitleLabel[iCouunter], (LinearLayout) llViewGroup[iCouunter]);
        }
    }

    /**
     * Implements this method to HIDE/SHOW values according response
     * from Server.
     */
    private void checkHideStatus(String strValue, TextView tvTextLabel, LinearLayout llViewGroup) {
        if (strValue.length() == 0) {
            llViewGroup.setVisibility(View.GONE);
        } else {
            tvTextLabel.setText(strValue);
        }
    }

    /**
     * Implements a method to get data and store to
     * local instance.
     */
    private void getMemberData() {
        strUsernameOfPerson = membersDetailItems.getData().getUserLoginID();
        strNameOfPerson = membersDetailItems.getData().getNameRecord().getFormalName();
        strMobileContactOfPerson = membersDetailItems.getData().getContactDetails().getTelNoMob();
        strStreetOfPerson = membersDetailItems.getData().getContactDetails().getAddress().getAsLine();
        strEmailOfPerson = membersDetailItems.getData().getContactDetails().getEMail();
        strTypeOfPerson = membersDetailItems.getData().getMembershipStatus();

        //Store values to array.
        strTitileValues = new String[]{strUsernameOfPerson, /*strStreetOfPerson, strEmailOfPerson,*/
                /*strMobileContactOfPerson,*/ strTypeOfPerson, strNameOfPerson};

        //Set visibility status.
        tvEmailVisibilty.setText(getResources().getString(R.string.title_text_visible) + " " + membersDetailItems.getData().getContactDetails().getEMailPrivacy());
        tvMobileVisibilty.setText(getResources().getString(R.string.title_text_visible) + " " + membersDetailItems.getData().getContactDetails().getTelNoMobPrivacy());
        tvAddressVisibilty.setText(getResources().getString(R.string.title_text_visible) + " " + membersDetailItems.getData().getContactDetails().getAddress1Privacy());

       /*++++++++++++++++++++++++  START OF ADD HOME AND WORK NEW VIEW RESOURCES  ++++++++++++++++++++++++ */

        tvHomeVisibilty.setText(getResources().getString(R.string.title_text_visible) + " " + membersDetailItems.getData().getContactDetails().getTelNoHomePrivacy());
        tvWorkVisibilty.setText(getResources().getString(R.string.title_text_visible) + " " + membersDetailItems.getData().getContactDetails().getTelNoWorkPrivacy());

        strTelHome = membersDetailItems.getData().getContactDetails().getTelNoHome();
        strTelWork = membersDetailItems.getData().getContactDetails().getTelNoWork();

        tvHomeContactOfPerson.setText(strTelHome);
        tvWorkContactOfPerson.setText(strTelWork);
        /*++++++++++++++++++++++++  END OF ADD HOME AND WORK NEW VIEW RESOURCES  ++++++++++++++++++++++++ */

        tvEmailOfPerson.setText(strEmailOfPerson);
        tvMobileContactOfPerson.setText(strMobileContactOfPerson);
        tvStreetOfPerson.setText(strStreetOfPerson);

        SaveUserInfoToPreference();
    }

    /**
     * Implements this method to save user information to {@link android.content.SharedPreferences} so that
     * it can retrieve later for update.
     */
    private void SaveUserInfoToPreference() {
        ((YourAccountActivity) getActivity()).savePreferenceList("YOUR_DETAILS_DATA", new Gson().toJson(membersDetailItems.getData()));
    }

    /**
     * Implements a method to initialize the view
     * resources of {@link MyDetailsFragment}
     *
     * @param viewRootFragment : Used to bind and initialize each view.
     */
    private void initializeViewResources(View viewRootFragment) {
        tvNameOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvNameOfPerson);
        tvTypeOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvTypeOfPerson);
        tvMobileContactOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvMobileContactOfPerson);
        tvEmailOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvEmailOfPerson);
        tvStreetOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvStreetOfPerson);
        tvUsernameOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvUsernameOfPerson);

        tvEmailVisibilty = (TextView) viewRootFragment.findViewById(R.id.tvEmailVisibilty);
        tvAddressVisibilty = (TextView) viewRootFragment.findViewById(R.id.tvAddressVisibilty);
        tvMobileVisibilty = (TextView) viewRootFragment.findViewById(R.id.tvMobileVisibilty);

        /*++++++++++++++++++++++++  START OF ADD HOME AND WORK NEW VIEW RESOURCES  ++++++++++++++++++++++++ */

        tvWorkContactOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvWorkContactOfPerson);
        tvHomeContactOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvHomeContactOfPerson);
        tvWorkVisibilty = (TextView) viewRootFragment.findViewById(R.id.tvWorkVisibilty);
        tvHomeVisibilty = (TextView) viewRootFragment.findViewById(R.id.tvHomeVisibilty);

        /*++++++++++++++++++++++++  END OF ADD HOME AND WORK NEW VIEW RESOURCES  ++++++++++++++++++++++++ */

        llNameOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llNameOfPerson);
        llTypeOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llTypeOfPerson);
        llMobileContactOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llMobileContactOfPerson);
        llEmailOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llEmailOfPerson);
        llStreetOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llStreetOfPerson);
        llUsernameOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llUsernameOfPerson);
        llMyDetailGroup = (LinearLayout) mRootFragment.findViewById(R.id.llMyDetailGroup);
    }

    /**
     * Implement a method to show Error message
     * Alert Dialog.
     */
    public void showErrorMessage(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                            ((YourAccountActivity) getActivity()).finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}