package com.ucreate.mhsystems.fragments;

/**
 * Created by karan@ucreate.co.in to load and display
 * <br>NEWS
 * <br>tabs content on 12/23/2015.
 */

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

public class CompletedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = CompletedFragment.class.getSimpleName();
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

    //Create instance of Model class CourseDiaryItems.
    CompetitionsResultItems competitionsResultItems;
    CompetitionsJsonParams competitionsJsonParams;

    //List of type books this list will store type Book which is our data model
    private CompetitionsAPI competitionsAPI;


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
            callCompletedEventsWebService();
        }
    }

    /**
     * Implements a method to call News web service either call
     * initially or call from onSwipeRefresh.
     */
    private void callCompletedEventsWebService() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (((BaseActivity) getActivity()).isOnline(getActivity())) {
            //Method to hit Squads API.
            requestCompetitionsEvents();
        } else {
            ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
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

        //Set Dates to display COMPLETED functionality.
        setDatesForCompleted(CompetitionsTabFragment.iActionCalendarStates);

        competitionsJsonParams = new CompetitionsJsonParams();
        competitionsJsonParams.setCallid("1456315336575");
        competitionsJsonParams.setVersion(1);
        competitionsJsonParams.setMemberId(10784);
        competitionsJsonParams.setIncludeCompletedEvents(true);
        competitionsJsonParams.setDateto(CompetitionsTabFragment.strDateTo); // MM-DD-YYYY [END DATE]
        competitionsJsonParams.setDatefrom(CompetitionsTabFragment.strDateFrom); // MM-DD-YYYY [START DATE]
        competitionsJsonParams.setPageNo("0");
        competitionsJsonParams.setPageSize("10");
        competitionsJsonParams.setAscendingDateOrder(true);

        competitionsAPI = new CompetitionsAPI(44118078, "GetClubEventList", competitionsJsonParams, "WEBSERVICES", "Members");

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
                ((BaseActivity) getActivity()).hideProgress();
                ((BaseActivity) getActivity()).showAlertMessage("" + error);
            }
        });

    }

    /**
     * Implements a method to set DATE for
     * COMPLETED tab functionality.
     *
     * @param iActionCalendarStates
     */
    private void setDatesForCompleted(int iActionCalendarStates) {

        switch (iActionCalendarStates) {

            case ApplicationGlobal.ACTION_PREVIOUS_MONTH:
            case ApplicationGlobal.ACTION_NEXT_MONTH:
            case ApplicationGlobal.ACTION_NOTHING:

                /**
                 *  Change date because COMPLETED tab should display record of
                 *  complete MONTH.
                 */
                CompetitionsTabFragment.strDateFrom = "" + CompetitionsActivity.iMonth + "/1/" + CompetitionsActivity.iYear;
                CompetitionsTabFragment.strDateTo = "" + CompetitionsActivity.iMonth + "/" + CompetitionsActivity.iNumOfDays + "/" + CompetitionsActivity.iYear;
                break;

            case ApplicationGlobal.ACTION_TODAY:
                /**
                 *  Change date because COMPLETED tab should display record of
                 *  complete MONTH.
                 */
                CompetitionsTabFragment.strDateFrom = "" + CompetitionsActivity.iMonth + "/" + CompetitionsActivity.strDate + "/" + CompetitionsActivity.iYear;
                CompetitionsTabFragment.strDateTo = "" + CompetitionsActivity.iMonth + "/" + CompetitionsActivity.strDate + "/" + CompetitionsActivity.iYear;
                break;

            case ApplicationGlobal.ACTION_CALENDAR:
                /**
                 *  Change date because COMPLETED tab should display record of
                 *  complete MONTH.
                 */
                CompetitionsTabFragment.strDateFrom = "" + CompetitionsActivity.iMonth + "/" + CompetitionsActivity.strDate + "/" + CompetitionsActivity.iYear;
                CompetitionsTabFragment.strDateTo = "" + CompetitionsActivity.iMonth + "/" + CompetitionsActivity.strDate + "/" + CompetitionsActivity.iYear;
                break;
        }

//        Log.e("setDatesForCompleted", "START DATE : " + CompetitionsTabFragment.strDateFrom);
//        Log.e("setDatesForCompleted", "END DATE : " + CompetitionsTabFragment.strDateTo);
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

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (competitionsResultItems.getMessage().equalsIgnoreCase("Success")) {

                competitionsDatas.addAll(competitionsResultItems.getData());

                if (competitionsDatas.size() == 0) {
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {

                    competitionsAdapter = new CompetitionsAdapter(getActivity(), competitionsDatas/*((CourseDiaryActivity)getActivity()).filterCourseDates(arrayCourseDataBackup)*/);
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
        callCompletedEventsWebService();
    }
}