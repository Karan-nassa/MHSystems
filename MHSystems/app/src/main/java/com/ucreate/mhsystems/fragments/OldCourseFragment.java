package com.ucreate.mhsystems.fragments;

/**
 * Created by karan@ucreate.co.in to load and display
 * <br>NEWS
 * <br>tabs content on 12/23/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.CourseDiaryActivity;
import com.ucreate.mhsystems.activites.CustomAlertDialogActivity;
import com.ucreate.mhsystems.activites.CourseDiaryDetailActivity;
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

public class OldCourseFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = OldCourseFragment.class.getSimpleName();
    ArrayList<CourseDiaryData> arrayListCourseData = new ArrayList<>();
    ArrayList<CourseDiaryDataCopy> arrayCourseDataBackup = new ArrayList<>();//Used for record of complete date and day name.

    boolean isDialogVisible = false;

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

    /**
     * Implements a FIELD to scroll down to load more data to update
     * list.
     */
    private AbsListView.OnScrollListener mLoadMoreScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            int threshold = 1;
            int count = lvCourseDiary.getCount();

            Log.e(LOG_TAG, "List Count on Scroll down :" + count);

            if (scrollState == SCROLL_STATE_IDLE) {
                if (lvCourseDiary.getLastVisiblePosition() >= count
                        - threshold) {

                    getMoreCourseEvents();

                    //((CourseDiaryActivity) getActivity()).showPleaseWait("Loading More...");
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };

    /**
     * Implements a functionality to which will be called when
     * user scroll down and LOAD more alert displaying and user
     * get more specific [ApplicationGlobal.LOAD_MORE_VALUES]
     * having value.
     */
    private void getMoreCourseEvents() {
        if (CourseDiaryActivity.iMonth == 12)
        {
            ((CourseDiaryActivity)getActivity()).setPreviousButton(false);
        }
        else
        {
            ((CourseDiaryActivity)getActivity()).setPreviousButton(true);

            ((CourseDiaryActivity)getActivity()).showAlertMessage("Under process...");

//            let dateComponents = NSDateComponents()
//            dateComponents.year = self.components.year
//            dateComponents.month = CounterMonth
//
//            let calendar = NSCalendar.currentCalendar()
//            let date = calendar.dateFromComponents(dateComponents)!
//
//                let range = calendar.rangeOfUnit(.Day, inUnit: .Month, forDate: date)
//
//            let numDays = range.length
//
//            let lessDays = numDays - scrollingDaysforFooter
//
//
//
//            print("monthSymbol  : \(scrollingDaysforFooter)")
//            if scrollingDaysforFooter >= numDays {
//            //if lessDays are greater than month lessDays
//            scrollingDaysforFooter = 1
//        }
//
//
//            if lessDays < 12 {
//
//            DateFrom    =  "\(CounterMonth)/\(scrollingDaysforFooter+1)/\(components.year)"
//            CounterMonth += 1
//
//            dateTo   =  "\(CounterMonth)/\((scrollingDaysforFooter+scrollingDaysfortoday) - numDays)/\(components.year)"
//
//            //scrollingDaysforFooter = scrollingDaysforFooter+12-scrollingDaysfortoday+1
//
//            scrollingDaysforFooter =  (scrollingDaysforFooter+scrollingDaysfortoday) - numDays // 12-scrollingDaysfortoday+1
//        }
//            else
//            {
//
//
//                DateFrom = "\(CounterMonth)/\(scrollingDaysforFooter+1)/\(components.year)"
//                dateTo = "\(CounterMonth)/\(scrollingDaysforFooter+scrollingDaysfortoday)/\(components.year)"
//
//                scrollingDaysforFooter = scrollingDaysforFooter+scrollingDaysfortoday
//                print("monthSymbol  : \(scrollingDaysforFooter)")
//
//            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_course_dairy_list, container, false);

        //Initialize app resouces of each view.
        initializeAppResources();

        //Course Diary events click listener.
        lvCourseDiary.setOnItemClickListener(mCourseEventListener);

        //Load more COURSE listener call here.
        lvCourseDiary.setOnScrollListener(mLoadMoreScrollListener);

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

                    if (!isDialogVisible) {
                        //Show alert dialog.
                        Intent mIntent = new Intent(getActivity(), CustomAlertDialogActivity.class);
                        //Pass theme green color.
                        mIntent.putExtra(ApplicationGlobal.TAG_POPUP_THEME, "#AFD9A1");
                        mIntent.putExtra(ApplicationGlobal.TAG_CALL_FROM, ApplicationGlobal.POSITION_COURSE_DIARY);
                        startActivity(mIntent);

                        isDialogVisible = true;
                    } else {
                        //Don't display again if already display Alert dialog.
                        isDialogVisible = false;
                    }
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
            //   ((CourseDiaryActivity) getActivity()).resetCalendarEvents();

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
        aJsonParams.setCourseKey("1.1");

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