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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.CompetitionsActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.CompetitionsAdapter;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.utils.API.WebServiceMethods;
import com.ucreate.mhsystems.utils.pojo.CompetitionsAPI;
import com.ucreate.mhsystems.utils.pojo.CompetitionsData;
import com.ucreate.mhsystems.utils.pojo.CompetitionsJsonParams;
import com.ucreate.mhsystems.utils.pojo.CompetitionsResultItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MyEventsTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = MyEventsTabFragment.class.getSimpleName();
    ArrayList<CompetitionsData> competitionsDatas = new ArrayList<>();
    //ArrayList<CourseDiaryDataCopy> arrayCourseDataBackup = new ArrayList<>();//Used for record of complete date and day name.

    private boolean isSwipeVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View mRootView;
    CoordinatorLayout cdlCompetitions;
    //    @Bind(R.id.rvCourseDiary)
//    RecyclerView rvCourseDiary;
    Toolbar toolBar;
    TextView tvCourseSchedule;
    //    RecyclerView.Adapter recyclerViewAdapter;
    ListView lvCompetitions;

    CompetitionsAdapter competitionsAdapter;

    //Create instance of Model class CourseDiaryItems.
    CompetitionsResultItems competitionsResultItems;
    //CourseDiaryItemsCopy courseDiaryItemsCopy;
    CompetitionsJsonParams competitionsJsonParams;

    //List of type books this list will store type Book which is our data model
    private CompetitionsAPI competitionsAPI;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_competitions_list, container, false);

        cdlCompetitions = (CoordinatorLayout) mRootView.findViewById(R.id.cdlCompetitions);
        lvCompetitions = (ListView) mRootView.findViewById(R.id.lvCompetitions);

        //Course Diary events click listener.
        //  lvCompetitions.setOnItemClickListener(mCourseEventListener);

        return mRootView;
    }

    /**
     * Set COURSE DIARY events listener.
     */
    private AdapterView.OnItemClickListener mCourseEventListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            /**
             *  Handle NULL @Exception.
             */
            if (competitionsDatas.get(position) != null) {

//                if (arrayListCourseData.get(position).getSlotType() == 2) {
//
//                    //Show alert dialog.
//                    Intent mIntent = new Intent(getActivity(), CourseAlertDialog.class);
//                    startActivity(mIntent);
//                } else {
//
//                    Log.e("DAY NAME", arrayListCourseData.get(position).getDayName());
//
//                    Intent intent = new Intent(getActivity(), CourseDiaryDetailActivity.class);
//                    intent.putExtra("COURSE_TITLE", arrayListCourseData.get(position).getTitle());
//                    intent.putExtra("COURSE_EVENT_IMAGE", arrayListCourseData.get(position).getLogo());
//                  //  intent.putExtra("COURSE_EVENT_JOIN", arrayListCourseData.get(position).isJoinStatus());
//                   // intent.putExtra("COURSE_EVENT_DATE", arrayListCourseData.get(position).getCourseEventDate());
//                    intent.putExtra("COURSE_EVENT_DAY_NAME", arrayListCourseData.get(position).getDayName());
//                  //  intent.putExtra("COURSE_EVENT_PRIZE", "" + arrayListCourseData.get(position).getPrizePerGuest());
//                    intent.putExtra("COURSE_EVENT_DESCRIPTION", arrayListCourseData.get(position).getDesc());
//                    startActivity(intent);
//                }
            }
        }
    };


    /**
     * Implements a method to initialize all view resources
     * of VIEW or VIEW GROUP.
     */
    private void initializeAppResources() {

        cdlCompetitions = (CoordinatorLayout) mRootView.findViewById(R.id.cdlCompetitions);
        // toolBar = (Toolbar) mRootView.findViewById(R.id.toolBar);
        // tvCourseSchedule = (TextView) mRootView.findViewById(R.id.tvCourseSchedule);
        lvCompetitions = (ListView) mRootView.findViewById(R.id.lvCompetitions);
        // tvCourseSchedule = (TextView) mRootView.findViewById(R.id.tvCourseSchedule);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {
            callNewsWebService();
        }
    }

    /**
     * Implements a method to call News web service either call
     * initially or call from onSwipeRefresh.
     */
    private void callNewsWebService() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (((BaseActivity) getActivity()).isOnline(getActivity())) {
            //Method to hit Squads API.
            requestCompetitionsEvents();
        } else {
            ((CompetitionsActivity) getActivity()).showSnackMessage(getResources().getString(R.string.error_no_internet));
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
        competitionsJsonParams.setCallid("1456315336575");
        competitionsJsonParams.setVersion(1);
        competitionsJsonParams.setMemberId(18060);
        competitionsJsonParams.setMyEventsOnly(true);
        competitionsJsonParams.setIncludeCompletedEvents(true);
        competitionsJsonParams.setIncludeCurrentEvents(true);
        competitionsJsonParams.setIncludeFutureEvents(true);

        competitionsAPI = new CompetitionsAPI(44118078, "GetClubEventList", competitionsJsonParams, "WEBSERVICES", "Members");

       // Log.e(LOG_TAG, "competitionsAPI " + competitionsAPI.toString());

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

                ((CompetitionsActivity) getActivity()).showSnackMessage("" + error);
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

//        Type type2 = new TypeToken<CourseDiaryItemsCopy>() {
//        }.getType();
//        courseDiaryItemsCopy = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type2);

        //Clear array list before inserting items.
        competitionsDatas.clear();
        //arrayCourseDataBackup.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (competitionsResultItems.getMessage().equalsIgnoreCase("Success")) {

                competitionsDatas.addAll(competitionsResultItems.getData());
                //Take backup of List before changing to record.
                // arrayCourseDataBackup.addAll(courseDiaryItemsCopy.getData());

                if (competitionsDatas.size() == 0) {
                    ((CompetitionsActivity) getActivity()).showSnackMessage(getResources().getString(R.string.error_no_data));
                } else {

                    competitionsAdapter = new CompetitionsAdapter(getActivity(), competitionsDatas/*((CourseDiaryActivity)getActivity()).filterCourseDates(arrayCourseDataBackup)*/);
                    lvCompetitions.setAdapter(competitionsAdapter);

                    Log.e(LOG_TAG, "arrayListCourseData : " + competitionsDatas.size());
                }
            } else {
                //If web service not respond in any case.
                ((CompetitionsActivity) getActivity()).showSnackMessage(competitionsResultItems.getMessage());
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
        callNewsWebService();
    }
}