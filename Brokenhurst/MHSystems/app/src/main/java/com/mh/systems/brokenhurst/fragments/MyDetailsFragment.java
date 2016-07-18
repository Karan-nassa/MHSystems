package com.mh.systems.brokenhurst.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.brokenhurst.activites.YourAccountActivity;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.mh.systems.brokenhurst.R;
import com.mh.systems.brokenhurst.activites.BaseActivity;
import com.mh.systems.brokenhurst.constants.ApplicationGlobal;
import com.mh.systems.brokenhurst.constants.WebAPI;
import com.mh.systems.brokenhurst.models.AJsonParamsMembersDatail;
import com.mh.systems.brokenhurst.models.MembersDetailAPI;
import com.mh.systems.brokenhurst.models.MembersDetailsItems;
import com.mh.systems.brokenhurst.util.API.WebServiceMethods;

import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * The {@link MyDetailsFragment} used to display the detail of LOGIN
 * MEMBER by passing MemberId.
 *
 * @author {@link karan@mh.co.in}
 * @version 1.0
 * @since 17 May, 2016
 */
public class MyDetailsFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MyDetailsFragment.class.getSimpleName();
    private String strUsernameOfPerson, /*strPostalCodeOfPerson,*/ strStreetOfPerson, /*strCityOfPerson,*/ strEmailOfPerson,
            /*strWorkContactOfPerson,*/ strMobileContactOfPerson, /*strPhoneContactOfPerson,*/ strTypeOfPerson, strNameOfPerson;

    String strTitileValues[];

    private boolean isClassVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    private TextView tvUsernameOfPerson, /*tvPostalCodeOfPerson,*/ /*tvCityOfPerson,*/ tvStreetOfPerson, tvEmailOfPerson,
            /*tvWorkContactOfPerson,*/ tvMobileContactOfPerson, /*tvPhoneContactOfPerson,*/ tvTypeOfPerson, tvNameOfPerson;

    private LinearLayout llUsernameOfPerson, /*llPostalCodeOfPerson,*/ llStreetOfPerson,/* llCityOfPerson,*/ llEmailOfPerson,
            /*llWorkContactOfPerson,*/ llMobileContactOfPerson, /*llPhoneContactOfPerson,*/ llTypeOfPerson, llNameOfPerson;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootFragment = inflater.inflate(R.layout.fragment_my_details, container, false);

        //Initialize the view resources.
        initializeViewResources(mRootFragment);

        //Check Internet connection and hit web service only on first time.
        isClassVisible = true;
        if (isClassVisible) {
            callWebService();
            ((YourAccountActivity) getActivity()).updateFilterIcon(8);
        }

        llViewGroup = new View[]{llUsernameOfPerson, /*llPostalCodeOfPerson, llStreetOfPerson,*/ llStreetOfPerson, llEmailOfPerson,
                /*llWorkContactOfPerson,*/ llMobileContactOfPerson,/* llPhoneContactOfPerson,*/ llTypeOfPerson, llNameOfPerson};

        tvTitleLabel = new View[]{tvUsernameOfPerson, /*tvPostalCodeOfPerson, llCityOfPerson,*/ tvStreetOfPerson, tvEmailOfPerson,
                /*tvWorkContactOfPerson,*/ tvMobileContactOfPerson,/* tvPhoneContactOfPerson,*/ tvTypeOfPerson, tvNameOfPerson};

        return mRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isClassVisible) {
            callWebService();
            ((YourAccountActivity) getActivity()).updateFilterIcon(8);
        }
    }

    private void callWebService(){
        /**
         *  Check internet connection before hitting server request.
         */
        if (((BaseActivity) getActivity()).isOnline(getActivity())) {
            ((YourAccountActivity) getActivity()).updateHasInternetUI(true);
            llMyDetailGroup.setVisibility(View.VISIBLE);
            requestMemberDetailService();
        } else {
            llMyDetailGroup.setVisibility(View.GONE);
            ((YourAccountActivity) getActivity()).updateHasInternetUI(false);
        }
    }

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

        Log.e(LOG_TAG, "membersDetailAPI: " + membersDetailAPI);

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

                ((BaseActivity) getActivity()).showAlertMessage("" + getResources().getString(R.string.error_please_retry));
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

                //  membersDatas.add(membersItems.getCompResultData());

                if (membersDetailItems.getData() != null) {
                    ((YourAccountActivity) getActivity()).updateHasInternetUI(true);
                    displayMembersData();
                } else {
                    ((YourAccountActivity) getActivity()).updateHasInternetUI(false);
                    //((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                }
            } else {
                ((YourAccountActivity) getActivity()).updateHasInternetUI(false);
                //If web service not respond in any case.
                //((BaseActivity) getActivity()).showAlertMessage(membersDetailItems.getMessage());
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
        /*strPostalCodeOfPerson = membersDetailItems.getCompResultData().getContactDetails().getAddress().getPostCode();*/
        strStreetOfPerson = membersDetailItems.getData().getContactDetails().getAddress().getLine2();
      /*  strCityOfPerson = membersDetailItems.getCompResultData().getContactDetails().getAddress().getCounty();*/
        strEmailOfPerson = membersDetailItems.getData().getContactDetails().getEMail();
       /* strWorkContactOfPerson = membersDetailItems.getCompResultData().getContactDetails().getTelNoWork();
        strPhoneContactOfPerson = membersDetailItems.getCompResultData().getContactDetails().getTelNoHome();*/
        strTypeOfPerson = membersDetailItems.getData().getMembershipStatus();

        //Store values to array.
        strTitileValues = new String[]{strUsernameOfPerson, /*strPostalCodeOfPerson,*/ strStreetOfPerson, /*strCityOfPerson,*/ strEmailOfPerson,
                /*strWorkContactOfPerson,*/ strMobileContactOfPerson, /*strPhoneContactOfPerson,*/ strTypeOfPerson, strNameOfPerson};
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
    /*    tvPhoneContactOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvPhoneContactOfPerson);*/
        tvMobileContactOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvMobileContactOfPerson);
       /* tvWorkContactOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvWorkContactOfPerson);*/
        tvEmailOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvEmailOfPerson);
       /* tvCityOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvCityOfPerson);*/
        tvStreetOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvStreetOfPerson);
       /* tvPostalCodeOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvPostalCodeOfPerson);*/
        tvUsernameOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvUsernameOfPerson);

        llNameOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llNameOfPerson);
        llTypeOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llTypeOfPerson);
       /* llPhoneContactOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llPhoneContactOfPerson);*/
        llMobileContactOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llMobileContactOfPerson);
      /*  llWorkContactOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llWorkContactOfPerson);*/
        llEmailOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llEmailOfPerson);
       /* llCityOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llCityOfPerson);*/
        llStreetOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llStreetOfPerson);
       /* llPostalCodeOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llPostalCodeOfPerson);*/
        llUsernameOfPerson = (LinearLayout) viewRootFragment.findViewById(R.id.llUsernameOfPerson);

        llMyDetailGroup = (LinearLayout) mRootFragment.findViewById(R.id.llMyDetailGroup);
    }
}