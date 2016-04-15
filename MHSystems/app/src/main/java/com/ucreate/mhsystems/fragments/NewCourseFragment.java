package com.ucreate.mhsystems.fragments;

/**
 * Created by karan@ucreate.co.in to load and display
 * <br>NEWS
 * <br>tabs content on 12/23/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.CourseDiaryActivity;
import com.ucreate.mhsystems.activites.CourseDiaryDetailActivity;
import com.ucreate.mhsystems.activites.CustomAlertDialogActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.CourseDiaryAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
import com.ucreate.mhsystems.util.pojo.AJsonParams_;
import com.ucreate.mhsystems.util.pojo.CourseDiaryAPI;
import com.ucreate.mhsystems.util.pojo.CourseDiaryData;
import com.ucreate.mhsystems.util.pojo.CourseDiaryDataCopy;
import com.ucreate.mhsystems.util.pojo.CourseDiaryItems;
import com.ucreate.mhsystems.util.pojo.CourseDiaryItemsCopy;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class NewCourseFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = NewCourseFragment.class.getSimpleName();
    ArrayList<CourseDiaryData> arrayListCourseData = new ArrayList<>();
    ArrayList<CourseDiaryDataCopy> arrayCourseDataBackup = new ArrayList<>();//Used for record of complete date and day name.

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View mRootView;
    CoordinatorLayout cdlCourseDiary;
    //    @Bind(R.id.rvCourseDiary)
//    RecyclerView rvCourseDiary;
    Toolbar toolBar;
    //    RecyclerView.Adapter recyclerViewAdapter;
    ListView lvCourseDiary;

    CourseDiaryAdapter courseDiaryAdapter;

    //Create instance of Model class CourseDiaryItems.
    CourseDiaryItems courseDiaryItems;
    CourseDiaryItemsCopy courseDiaryItemsCopy;
    AJsonParams_ aJsonParams;

    //List of type books this list will store type Book which is our data model
    private CourseDiaryAPI courseDiaryAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_course_dairy_list, container, false);

        //Initialize app resouces of each view.
        initializeAppResources();

        //Course Diary events click listener.
        lvCourseDiary.setOnItemClickListener(mCourseEventListener);

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
            if (arrayListCourseData.get(position) != null) {

                if (arrayListCourseData.get(position).getSlotType() == 2) {

                    //Show alert dialog.
                    Intent mIntent = new Intent(getActivity(), CustomAlertDialogActivity.class);
                    //Pass theme green color.
                    mIntent.putExtra(ApplicationGlobal.TAG_POPUP_THEME, "#AFD9A1");
                    mIntent.putExtra(ApplicationGlobal.TAG_CALL_FROM, ApplicationGlobal.POSITION_COURSE_DIARY);
                    startActivity(mIntent);
                } else {

                    Intent intent = new Intent(getActivity(), CourseDiaryDetailActivity.class);
                    intent.putExtra("COURSE_TITLE", arrayListCourseData.get(position).getTitle());
                    intent.putExtra("COURSE_EVENT_IMAGE", arrayListCourseData.get(position).getLogo());
                    intent.putExtra("COURSE_EVENT_JOIN", arrayListCourseData.get(position).isJoinStatus());
                    intent.putExtra("COURSE_EVENT_DATE", arrayListCourseData.get(position).getCourseEventDate());
                    intent.putExtra("COURSE_EVENT_DAY_NAME", arrayListCourseData.get(position).getDayName());
                    intent.putExtra("COURSE_EVENT_PRIZE", "" + arrayListCourseData.get(position).getPrizePerGuest());
                    intent.putExtra("COURSE_EVENT_DESCRIPTION", arrayListCourseData.get(position).getDesc());
                    startActivity(intent);
                }
            }
        }
    };


    /**
     * Implements a method to initialize all view resources
     * of VIEW or VIEW GROUP.
     */
    private void initializeAppResources() {

        cdlCourseDiary = (CoordinatorLayout) mRootView.findViewById(R.id.cdlCourseDiary);
        toolBar = (Toolbar) mRootView.findViewById(R.id.toolBar);
        lvCourseDiary = (ListView) mRootView.findViewById(R.id.lvCourseDiary);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            /**
             * TODO : Hide RESET CALENDAR functionality because its showing whole month data on select single date or TODAY.
             *
             */
            //Reset CALENDAR.
//            ((CourseDiaryActivity) getActivity()).resetCalendarEvents();

            callCourseWebService();
        }
    }

    /**
     * Implements a method to call News web service either call
     * initially or call from onSwipeRefresh.
     */
    private void callCourseWebService() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (((BaseActivity) getActivity()).isOnline(getActivity())) {
            //Method to hit Squads API.
            requestCourseService();
        } else {
            ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
        }
    }

    /**
     * Implement a method to hit News web service to get response.
     */
    public void requestCourseService() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParams = new AJsonParams_();
        aJsonParams.setCallid("1456315336575");
        aJsonParams.setVersion("1");
        aJsonParams.setDateto(CourseDairyTabFragment.strDateTo); // MM-DD-YYYY
        aJsonParams.setDatefrom(CourseDairyTabFragment.strDateFrom); // MM-DD-YYYY
        aJsonParams.setPageNo("0");
        aJsonParams.setPageSize("10");
        aJsonParams.setCourseKey("1.3");

        courseDiaryAPI = new CourseDiaryAPI(aJsonParams, "COURSEDIARY", "44118078", "GetSlots", "Members");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getCourseDiaryEvents(courseDiaryAPI, new Callback<JsonObject>() {
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

    private void updateSuccessResponse(JsonObject jsonObject) {
        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<CourseDiaryItems>() {
        }.getType();
        courseDiaryItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        Type type2 = new TypeToken<CourseDiaryItemsCopy>() {
        }.getType();
        courseDiaryItemsCopy = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type2);

        //Clear array list before inserting items.
        arrayListCourseData.clear();
        arrayCourseDataBackup.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (courseDiaryItems.getMessage().equalsIgnoreCase("Success")) {

                arrayListCourseData.addAll(courseDiaryItems.getData());
                //Take backup of List before changing to record.
                arrayCourseDataBackup.addAll(courseDiaryItemsCopy.getData());

                if (arrayListCourseData.size() == 0) {
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {

                    //Set Course Diary Recycler Adapter.
//                    recyclerViewAdapter = new CourseDiaryRecyclerAdapter(CourseDairyTabFragment.this, filterCourseDates(arrayListCourseData));
//                    rvCourseDiary.setAdapter(recyclerViewAdapter);

                    courseDiaryAdapter = new CourseDiaryAdapter(getActivity(), ((CourseDiaryActivity) getActivity()).filterCourseDates(arrayCourseDataBackup));
                    lvCourseDiary.setAdapter(courseDiaryAdapter);

                    Log.e(LOG_TAG, "arrayListCourseData : " + arrayListCourseData.size());
                }
            } else {
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(courseDiaryItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();
    }
}