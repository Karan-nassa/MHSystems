package com.ucreate.mhsystems.fragments;

/**
 * Created by karan@ucreate.co.in to load and display
 * <br>NEWS
 * <br>tabs content on 12/23/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.MembersActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.CompetitionsAdapter;
import com.ucreate.mhsystems.adapter.BaseAdapter.MembersAdapter;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.utils.API.WebServiceMethods;
import com.ucreate.mhsystems.utils.pojo.AJsonParamsMembers;
import com.ucreate.mhsystems.utils.pojo.CompetitionsAPI;
import com.ucreate.mhsystems.utils.pojo.CompetitionsJsonParams;
import com.ucreate.mhsystems.utils.pojo.CompetitionsResultItems;
import com.ucreate.mhsystems.utils.pojo.MembersAPI;
import com.ucreate.mhsystems.utils.pojo.MembersData;
import com.ucreate.mhsystems.utils.pojo.MembersItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MembersFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MembersFragment.class.getSimpleName();
    ArrayList<MembersData> membersDatas = new ArrayList<>();

    private boolean isSwipeVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View mRootView;
    ListView lvMembers;

    MembersAdapter membersAdapter;

    //Create instance of Model class MembersItems.
    MembersItems membersItems;
    AJsonParamsMembers aJsonParamsMembers;

    //List of type books this list will store type Book which is our data model
    private MembersAPI membersAPI;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_members, container, false);

        lvMembers = (ListView) mRootView.findViewById(R.id.lvMembers);

        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {

            /**
             *  Check internet connection before hitting server request.
             */
            if (((BaseActivity) getActivity()).isOnline(getActivity())) {
                //Method to hit Squads API.
                requestMemberService();
            } else {
                ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
            }
        }
    }


    /**
     * Implement a method to hit Members
     * web service to get response.
     */
    public void requestMemberService() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsMembers = new AJsonParamsMembers();
        aJsonParamsMembers.setCallid("1456315336575");
        aJsonParamsMembers.setVersion(1);
        aJsonParamsMembers.setMemberid(10784);
        aJsonParamsMembers.setGenderFilter(MembersTabFragment.iMemberType);

        membersAPI = new MembersAPI(44118078, "GetAllMembers", aJsonParamsMembers, "WEBSERVICES", "Members");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getMembers(membersAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                ((BaseActivity) getActivity()).hideProgress();

                ((BaseActivity) getActivity()).showAlertMessage("" + error);
            }
        });

    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<MembersItems>() {
        }.getType();
        membersItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        membersDatas.clear();
        //arrayCourseDataBackup.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (membersItems.getMessage().equalsIgnoreCase("Success")) {

                membersDatas.add(membersItems.getData());

                if (membersDatas.size() == 0) {
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {

                    membersAdapter = new MembersAdapter(getActivity(), membersDatas.get(0).getMembersList());
                    lvMembers.setAdapter(membersAdapter);

                    Log.e(LOG_TAG, "getMembersList : " + membersDatas.get(0).getMembersList().size());
                    Log.e(LOG_TAG, "getMember().getDisplayName : " + membersDatas.get(0).getMember().getDisplayName());
                }
            } else {
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(membersItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();
    }
}