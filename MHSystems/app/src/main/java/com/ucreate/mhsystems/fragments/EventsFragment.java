package com.ucreate.mhsystems.fragments;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
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
import com.ucreate.mhsystems.activites.CompetitionsActivity;
import com.ucreate.mhsystems.activites.MyAccountActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.CompetitionsAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
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
 * Created by karan@ucreate.co.in to load and display
 * EVENTS of {@link CompetitionsTabFragment}
 *
 * @since on 2 JANUARY, 2016
 */
public class EventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = EventsFragment.class.getSimpleName();
    ArrayList<CompetitionsData> competitionsDatas = new ArrayList<>();

    private boolean isSwipeVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;
    CoordinatorLayout cdlCompetitions;
    Toolbar toolBar;
    TextView tvCourseSchedule;
    ListView lvCompetitions;

    CompetitionsAdapter competitionsAdapter;

    //List of type books this list will store type Book which is our data model
    private CompetitionsAPI competitionsAPI;

    //Create instance of Model class CourseDiaryItems.
    CompetitionsResultItems competitionsResultItems;
    CompetitionsJsonParams competitionsJsonParams;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_competitions_list, container, false);

        cdlCompetitions = (CoordinatorLayout) viewRootFragment.findViewById(R.id.cdlCompetitions);
        lvCompetitions = (ListView) viewRootFragment.findViewById(R.id.lvCompetitions);

        return viewRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            //Reset CALENDAR.
            ((CompetitionsActivity) getActivity()).resetCalendarEvents();

            callMyEventsWebService();
        }
    }

    /**
     * Implements a method to call News web service either call
     * initially or call from onSwipeRefresh.
     */
    private void callMyEventsWebService() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (((BaseActivity) getActivity()).isOnline(getActivity())) {
            ((CompetitionsActivity) getActivity()).updateNoInternetUI(true);
            //Method to hit Squads API.
            requestCompetitionsEvents();
        } else {
            ((CompetitionsActivity) getActivity()).updateNoInternetUI(false);
           // ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
        }
    }

    /**
     * Implement a method to hit Competitions
     * web service to get response.
     */
    public void requestCompetitionsEvents() {

        if (!isSwipeVisible) {
            ((BaseActivity) getActivity()).showPleaseWait("Loading...");
        }

        competitionsJsonParams = new CompetitionsJsonParams();
        competitionsJsonParams.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        competitionsJsonParams.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        competitionsJsonParams.setMemberId(((CompetitionsActivity) getActivity()).getMemberId());
        competitionsJsonParams.setMyEventsOnly(true);
        competitionsJsonParams.setIncludeCompletedEvents(true);
        competitionsJsonParams.setIncludeCurrentEvents(true);
        competitionsJsonParams.setIncludeFutureEvents(true);
        competitionsJsonParams.setDateto(CompetitionsTabFragment.strDateTo); // MM-DD-YYYY
        competitionsJsonParams.setDatefrom(CompetitionsTabFragment.strDateFrom); // MM-DD-YYYY
        competitionsJsonParams.setPageNo("0");
        competitionsJsonParams.setPageSize("10");
        competitionsJsonParams.setAscendingDateOrder(true);

        competitionsAPI = new CompetitionsAPI(((CompetitionsActivity) getActivity()).getClientId(), "GETCLUBEVENTLIST", competitionsJsonParams, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getCompetitionsEvents(competitionsAPI, new Callback<JsonObject>() {
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

        Type type = new TypeToken<CompetitionsResultItems>() {
        }.getType();
        competitionsResultItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        competitionsDatas.clear();
        //arrayCourseDataBackup.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (competitionsResultItems.getMessage().equalsIgnoreCase("Success")) {

                competitionsDatas.addAll(competitionsResultItems.getData());

                if (competitionsDatas.size() == 0) {
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {

                    //FALSE to set visible of JOIN button.
                    competitionsAdapter = new CompetitionsAdapter(getActivity(), competitionsDatas, false);
                    lvCompetitions.setAdapter(competitionsAdapter);

                    Log.e(LOG_TAG, "arrayListCourseData : " + competitionsDatas.size());
                }
            } else {
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(competitionsResultItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();
    }


    @Override
    public void onRefresh() {
        isSwipeVisible = true;
        callMyEventsWebService();
    }
}