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
import com.ucreate.mhsystems.activites.CourseActivity;
import com.ucreate.mhsystems.activites.CourseDiaryDetailActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.CourseDiaryAdapter;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.utils.API.WebServiceMethods;
import com.ucreate.mhsystems.utils.pojo.AJsonParams_;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryAPI;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryData;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryDataCopy;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryItems;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryItemsCopy;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = NewsFragment.class.getSimpleName();
    ArrayList<CourseDiaryData> arrayListCourseData = new ArrayList<>();
    ArrayList<CourseDiaryDataCopy> arrayCourseDataBackup = new ArrayList<>();//Used for record of complete date and day name.

    private boolean isSwipeVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View mRootView;
    CoordinatorLayout cdlCourseDiary;
    //    @Bind(R.id.rvCourseDiary)
//    RecyclerView rvCourseDiary;
    Toolbar toolBar;
    TextView tvCourseSchedule;
    TextView tvMonthName;
    //    RecyclerView.Adapter recyclerViewAdapter;
    ListView lvCourseDiary;

    CourseDiaryAdapter courseDiaryAdapter;

    //Create instance of Model class CourseDiaryItems.
    CourseDiaryItems courseDiaryItems;
    CourseDiaryItemsCopy courseDiaryItemsCopy;
    AJsonParams_ aJsonParams;

    //List of type books this list will store type Book which is our data model
    private CourseDiaryAPI courseDiaryAPI;

    //Swipe Refresh Instance.
    private SwipeRefreshLayout SwipeRefreshNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_course_dairy, container, false);

        //Initialize app resouces of each view.
        initializeAppResources();
        SwipeRefreshNews = (SwipeRefreshLayout) mRootView.findViewById(R.id.SwipeRefreshNews);
        // Configure the refreshing colors
        SwipeRefreshNews.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //Set Swipe Refresh listener.
        SwipeRefreshNews.setOnRefreshListener(this);

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

                Log.e("DAY NAME", arrayListCourseData.get(position).getDayName());

                Intent intent = new Intent(getActivity(), CourseDiaryDetailActivity.class);
                intent.putExtra("COURSE_TITLE", arrayListCourseData.get(position).getTitle());
                intent.putExtra("COURSE_EVENT_IMAGE", arrayListCourseData.get(position).getLogo());
                intent.putExtra("COURSE_EVENT_JOIN", arrayListCourseData.get(position).isJoinStatus());
                intent.putExtra("COURSE_EVENT_DATE", arrayListCourseData.get(position).getCourseEventDate());
                intent.putExtra("COURSE_EVENT_DAY_NAME", arrayListCourseData.get(position).getDayName());
                intent.putExtra("COURSE_EVENT_PRIZE", arrayListCourseData.get(position).getPrizePerGuest());
                intent.putExtra("COURSE_EVENT_DESCRIPTION", arrayListCourseData.get(position).getDesc());
                startActivity(intent);
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
        tvCourseSchedule = (TextView) mRootView.findViewById(R.id.tvCourseSchedule);
        tvMonthName = (TextView) mRootView.findViewById(R.id.tvMonthName);
        lvCourseDiary = (ListView) mRootView.findViewById(R.id.lvCourseDiary);
        tvCourseSchedule = (TextView) mRootView.findViewById(R.id.tvCourseSchedule);
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
        if (((BaseActivity)getActivity()).isOnline(getActivity())) {
            //Method to hit Squads API.
            requestNewsService();
        } else {
            ((CourseActivity) getActivity()).showSnackMessage(getResources().getString(R.string.error_no_internet));
            updateSwipeRefresh();
        }
    }

    /**
     * Implement a method to hit News web service to get response.
     */
    private void requestNewsService() {

        if (!isSwipeVisible) {
            ((BaseActivity) getActivity()).showPleaseWait("Loading...");
        }

        aJsonParams = new AJsonParams_();
        aJsonParams.setCallid("1456315336575");
        aJsonParams.setVersion("1");
        aJsonParams.setDateto("03-23-2016");
        aJsonParams.setDatefrom("03-04-2016");
        aJsonParams.setPageNo("0");
        aJsonParams.setPageSize("10");
        aJsonParams.setCourseKey("1.1");

        courseDiaryAPI = new CourseDiaryAPI(aJsonParams, "COURSEDIARY", "44118078", "GetSlots", "Members");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.URL_COURSE_DIARY)
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

                ((CourseActivity) getActivity()).showSnackMessage("" + error);
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
            if (courseDiaryItems.getResult().equals("1")) {

                arrayListCourseData.addAll(courseDiaryItems.getData());
                //Take backup of List before changing to record.
                arrayCourseDataBackup.addAll(courseDiaryItemsCopy.getData());

                if (arrayListCourseData.size() == 0) {
                    ((CourseActivity) getActivity()).showSnackMessage(getResources().getString(R.string.error_no_data));
                } else {

                    //Set Course Diary Recycler Adapter.
//                    recyclerViewAdapter = new CourseDiaryRecyclerAdapter(CourseDairyActivity.this, filterCourseDates(arrayListCourseData));
//                    rvCourseDiary.setAdapter(recyclerViewAdapter);

                    courseDiaryAdapter = new CourseDiaryAdapter(getActivity(), filterCourseDates(arrayCourseDataBackup));
                    lvCourseDiary.setAdapter(courseDiaryAdapter);

                    Log.e(LOG_TAG, "arrayListCourseData : " + arrayListCourseData.size());

                    //Set Name of Month selected in CALENDER or record from api of COURSE DIARY.
                    tvMonthName.setText(arrayListCourseData.get(0).getMonthName());
                }
            } else {
                //If web service not respond in any case.
                ((CourseActivity) getActivity()).showSnackMessage(getResources().getString(R.string.error_please_retry));
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();
    }

    /**
     * Implements a method to filter or set date and name of Day
     * one time for all course events having same date and day.
     */
    public ArrayList<CourseDiaryDataCopy> filterCourseDates(ArrayList<CourseDiaryDataCopy> arrayListCourseData) {
        ArrayList<CourseDiaryDataCopy> courseDiaryDataArrayList = new ArrayList<>();
        courseDiaryDataArrayList.clear();
        String strLastDate = "";

        /**
         *  Loop filter till end of Course
         *  Diary events.
         */
        for (int iCounter = 0; iCounter < arrayListCourseData.size(); iCounter++) {

            String strDateOfEvent = formatDateOfEvent(arrayListCourseData.get(iCounter).getCourseEventDate());

            /**
             * Check if same date or not of Course Diary event If yes then just
             * display date and day name once otherwise skip.
             */
            if (strLastDate.equalsIgnoreCase(strDateOfEvent)) {

                arrayListCourseData.get(iCounter).setCourseEventDate("");
                arrayListCourseData.get(iCounter).setDayName("");

            } else {
                strLastDate = strDateOfEvent;

                arrayListCourseData.get(iCounter).setCourseEventDate(strDateOfEvent);
                arrayListCourseData.get(iCounter).setDayName(formatDayOfEvent(arrayListCourseData.get(iCounter).getDayName()));
            }

            //Add final to new arrat list.
            courseDiaryDataArrayList.add(arrayListCourseData.get(iCounter));
        }
        return courseDiaryDataArrayList;
    }

    /**
     * @param strCourseEventDate <br>
     *                           Implements a method to return the format the day of
     *                           event.
     *                           <p/>
     *                           Exapmle: 2016-03-04T00:00:00
     * @Return : 04
     */
    private String formatDateOfEvent(String strCourseEventDate) {

        //Used when Date format in Hyphen['-']. Example : dd-MM-yyyy
        String strEventDate = strCourseEventDate.substring(strCourseEventDate.lastIndexOf("-") + 1, strCourseEventDate.lastIndexOf("T"));

        //Used when Date format in slashes['/']. Example : dd/MM/yyyy
        //String strEventDate = strCourseEventDate.substring(strCourseEventDate.indexOf("/") + 1, strCourseEventDate.lastIndexOf("/"));

        return strEventDate;
    }

    /**
     * @param strDayName <br>
     *                   Implements a method to return the format the day of
     *                   event.
     *                   <p/>
     *                   Exapmle: NAME OF DAY : Friday
     * @Return : Fri
     */
    private String formatDayOfEvent(String strDayName) {
        return (strDayName.substring(0, 3));
    }


    /**
     * Update Swipe refresh view.
     */
    private void updateSwipeRefresh() {
        if (isSwipeVisible) {

            if (courseDiaryAdapter != null) {
                courseDiaryAdapter.notifyDataSetChanged();
            }

            isSwipeVisible = false;//For next time.
            // stopping swipe refresh
            SwipeRefreshNews.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        isSwipeVisible = true;
        callNewsWebService();
    }
}