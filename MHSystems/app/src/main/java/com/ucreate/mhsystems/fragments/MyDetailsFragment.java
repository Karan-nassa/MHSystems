package com.ucreate.mhsystems.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.CompetitionsAdapter;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.models.AJsonParamsMembersDatail;
import com.ucreate.mhsystems.models.MembersDetailAPI;
import com.ucreate.mhsystems.models.MembersDetailsItems;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
import com.ucreate.mhsystems.models.CompetitionsAPI;
import com.ucreate.mhsystems.models.CompetitionsData;
import com.ucreate.mhsystems.models.CompetitionsJsonParams;
import com.ucreate.mhsystems.models.CompetitionsResultItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    private TextView tvUsernameOfPerson, tvPostalCodeOfPerson, tvStreetOfPerson, tvCityOfPerson, tvEmailOfPerson,
            tvWorkContactOfPerson, tvMobileContactOfPerson, tvPhoneContactOfPerson, tvTypeOfPerson, tvNameOfPerson;
    private View viewRootFragment;
    ListView lvFriends;

    CompetitionsAdapter competitionsAdapter;

    //List of type Members list will store type Book which is our data model
    private MembersDetailAPI membersDetailAPI;
    AJsonParamsMembersDatail aJsonParamsMembersDatail;
    MembersDetailsItems membersDetailItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_my_details, container, false);

        //Initialize the view resources.
        initializeViewResources(viewRootFragment);

        return viewRootFragment;
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
        tvPhoneContactOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvPhoneContactOfPerson);
        tvMobileContactOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvMobileContactOfPerson);
        tvWorkContactOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvWorkContactOfPerson);
        tvEmailOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvEmailOfPerson);
        tvCityOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvCityOfPerson);
        tvStreetOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvStreetOfPerson);
        tvPostalCodeOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvPostalCodeOfPerson);
        tvUsernameOfPerson = (TextView) viewRootFragment.findViewById(R.id.tvUsernameOfPerson);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            /**
             *  Check internet connection before hitting server request.
             */
            if (((BaseActivity) getActivity()).isOnline(getActivity())) {
                //Method to hit Members list API.
                requestMemberDetailService();
            } else {
                ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
            }
        }
    }

    /**
     * Implement a method to hit Members Detail
     * web service to get response.
     */
    public void requestMemberDetailService() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsMembersDatail = new AJsonParamsMembersDatail();
        aJsonParamsMembersDatail.setCallid("1456315336575");
        aJsonParamsMembersDatail.setVersion(1);
        aJsonParamsMembersDatail.setMemberid(10784);
        aJsonParamsMembersDatail.setLoginMemberId(10784);

        membersDetailAPI = new MembersDetailAPI(44118078, "GETMEMBER", aJsonParamsMembersDatail, "WEBSERVICES", "Members");

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

                //  membersDatas.add(membersItems.getData());

                if (membersDetailItems.getData() != null) {

                    displayMembersData();
                } else {
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                }
            } else {
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(membersDetailItems.getMessage());
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

        tvUsernameOfPerson.setText(membersDetailItems.getData().getUserLoginID());
        tvNameOfPerson.setText(membersDetailItems.getData().getNameRecord().getFormalName());
        tvMobileContactOfPerson.setText(membersDetailItems.getData().getContactDetails().getTelNoMob());
        tvPhoneContactOfPerson.setText(membersDetailItems.getData().getContactDetails().getTelNoHome());
        tvWorkContactOfPerson.setText(membersDetailItems.getData().getContactDetails().getTelNoWork());
        tvEmailOfPerson.setText(membersDetailItems.getData().getContactDetails().getEMail());
        tvTypeOfPerson.setText(membersDetailItems.getData().getMembershipStatus());
        tvCityOfPerson.setText(membersDetailItems.getData().getContactDetails().getAddress().getCounty());
        tvStreetOfPerson.setText(membersDetailItems.getData().getContactDetails().getAddress().getLine2());
        tvPostalCodeOfPerson.setText(membersDetailItems.getData().getContactDetails().getAddress().getPostCode());
    }
}