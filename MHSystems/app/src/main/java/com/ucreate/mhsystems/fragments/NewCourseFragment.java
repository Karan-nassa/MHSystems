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
import com.ucreate.mhsystems.activites.MyAccountActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.CourseDiaryAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
import com.ucreate.mhsystems.models.AJsonParamsCourse;
import com.ucreate.mhsystems.models.CourseDiaryAPI;
import com.ucreate.mhsystems.models.CourseDiaryData;
import com.ucreate.mhsystems.models.CourseDiaryDataCopy;
import com.ucreate.mhsystems.models.CourseDiaryItems;
import com.ucreate.mhsystems.models.CourseDiaryItemsCopy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    boolean isDialogVisible = false;

    //Stop user to scroll or change MONTH once no data found.
    boolean isMoreToScroll;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;
    CoordinatorLayout cdlCourseDiary;
    //    @Bind(R.id.rvCourseDiary)
//    RecyclerView rvCourseDiary;
    Toolbar toolBar;
    //    RecyclerView.Adapter recyclerViewAdapter;
    ListView lvCourseDiary;

    CourseDiaryAdapter courseDiaryAdapter;

    //List of type books this list will store type Book which is our data model
    private CourseDiaryAPI courseDiaryAPI;

    //Create instance of Model class CourseDiaryItems.
    CourseDiaryItems courseDiaryItems;
    CourseDiaryItemsCopy courseDiaryItemsCopy;
    AJsonParamsCourse aJsonParams;

    int iScrollCount;

    /**
     * Implements a FIELD to scroll down to load more data to update
     * list.
     */
    private AbsListView.OnScrollListener mLoadMoreScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            /**
             *  Scroll will work after success response.
             */
            if (isMoreToScroll) {
                int threshold = 1;
                int count = lvCourseDiary.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (lvCourseDiary.getLastVisiblePosition() >= count
                            - threshold) {

                        iScrollCount = count;

                        getMoreCourseEvents();
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (arrayCourseDataBackup.size() > 0) {
                ((CourseDiaryActivity) getActivity()).setTitleBar(arrayCourseDataBackup.get(++firstVisibleItem).getMonthName());

                CourseDiaryActivity.iMonth = arrayCourseDataBackup.get(firstVisibleItem).getiMonthNum();
                CourseDiaryActivity.resetMonthsNavigationIcons();
            }

        }
    };

    /**
     * Implements a functionality to which will be called when user scroll down and LOAD more alert displaying and user
     * get more specific [ApplicationGlobal.LOAD_MORE_VALUES] having value.
     */
    private void getMoreCourseEvents() {

        //Scroll down functionality should only work for TODAY and CALENDAR date picker.
        switch (CourseDairyTabFragment.iLastCalendarAction) {
            case ApplicationGlobal.ACTION_CALENDAR:
            case ApplicationGlobal.ACTION_TODAY: {

                if (CourseDiaryActivity.iMonth == 12) {
                    ((CourseDiaryActivity) getActivity()).setNextButton(false);
                } else {
                    ((CourseDiaryActivity) getActivity()).setNextButton(true);

                    ((CourseDiaryActivity) getActivity()).showPleaseWait("Loading more...");

                    // Create a calendar object and set year and month
                    CourseDiaryActivity.mCalendarInstance = new GregorianCalendar(CourseDiaryActivity.iYear, (CourseDiaryActivity.iMonth - 1), Integer.parseInt(CourseDiaryActivity.strDate));

                    // Get the number of days in that month
                    CourseDiaryActivity.iNumOfDays = CourseDiaryActivity.mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                    CourseDiaryActivity.iLessDays = CourseDiaryActivity.iNumOfDays - Integer.parseInt(CourseDiaryActivity.strDate);

                    int iDate = Integer.parseInt(CourseDiaryActivity.strDate);

                    if (iDate >= CourseDiaryActivity.iNumOfDays) {
                        //if lessDays are greater than month lessDays
                        //CourseDiaryActivity.strDate = "01";
                        iDate = 0;
                        CourseDiaryActivity.iMonth += 1;

                        // Create a calendar object and set year and month
                        CourseDiaryActivity.mCalendarInstance = new GregorianCalendar(CourseDiaryActivity.iYear, (CourseDiaryActivity.iMonth - 1), Integer.parseInt(CourseDiaryActivity.strDate));

                        // Get the number of days in that month
                        CourseDiaryActivity.iNumOfDays = CourseDiaryActivity.mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                        CourseDiaryActivity.iLessDays = CourseDiaryActivity.iNumOfDays - Integer.parseInt(CourseDiaryActivity.strDate);
                    }


                    if (CourseDiaryActivity.iLessDays < ApplicationGlobal.LOAD_MORE_VALUES) {

                        CourseDairyTabFragment.strDateFrom = CourseDiaryActivity.iMonth + "/" + (iDate + 1)/*CourseDiaryActivity.strDate*/ + "/" + CourseDiaryActivity.iYear;

                        if (iDate == 0) {
                            CourseDiaryActivity.strDate = "" + ((iDate + ApplicationGlobal.LOAD_MORE_VALUES));
                            CourseDairyTabFragment.strDateTo = CourseDiaryActivity.iMonth + "/" + (CourseDiaryActivity.strDate) + "/" + CourseDiaryActivity.iYear;
                        } else {
                            /**
                             *  Suppose Current date is near to end of Month then increment to
                             *  Next Month.
                             */
                            CourseDiaryActivity.iMonth += 1;
                            CourseDiaryActivity.strDate = "" + ((iDate + ApplicationGlobal.LOAD_MORE_VALUES) - CourseDiaryActivity.iNumOfDays);
                            CourseDairyTabFragment.strDateTo = CourseDiaryActivity.iMonth + "/" + (CourseDiaryActivity.strDate) + "/" + CourseDiaryActivity.iYear;
                        }

                    } else {

                        CourseDairyTabFragment.strDateFrom = CourseDiaryActivity.iMonth + "/" + (iDate + 1)/*CourseDiaryActivity.strDate*/ + "/" + CourseDiaryActivity.iYear;

                        CourseDiaryActivity.strDate = "" + (iDate + ApplicationGlobal.LOAD_MORE_VALUES);

                        CourseDairyTabFragment.strDateTo = CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.strDate + "/" + CourseDiaryActivity.iYear;
                    }

                    ((CourseDiaryActivity) getActivity()).setTitleBar(CourseDiaryActivity.getMonth(Integer.parseInt(String.valueOf(CourseDiaryActivity.iMonth))) + " " + CourseDiaryActivity.iYear);

                    Log.e(LOG_TAG, "strDateFrom " + CourseDairyTabFragment.strDateFrom);
                    Log.e(LOG_TAG, "strDateTo " + CourseDairyTabFragment.strDateTo);

                    //Reload data with new date created from user.
                    callCourseWebService();
                    break;
                }
            }

            default:{
                ((CourseDiaryActivity) getActivity()).showPleaseWait("Loading more...");
                CourseDairyTabFragment.callNextMonthAction();
                //Reload data with new date created from user.
                callCourseWebService();
            }
        }

        CourseDiaryActivity.resetMonthsNavigationIcons();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_course_dairy_list, container, false);

        //Initialize app resouces of each view.
        initializeAppResources();

        //Course Diary events click listener.
        lvCourseDiary.setOnItemClickListener(mCourseEventListener);

        //Load more COURSE listener call here.
        lvCourseDiary.setOnScrollListener(mLoadMoreScrollListener);

        return viewRootFragment;
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
                    intent.putExtra("COURSE_EVENT_TIME", arrayListCourseData.get(position).getStartTime() + " - " + arrayListCourseData.get(position).getEndTime());
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

        cdlCourseDiary = (CoordinatorLayout) viewRootFragment.findViewById(R.id.cdlCourseDiary);
        toolBar = (Toolbar) viewRootFragment.findViewById(R.id.toolBar);
        lvCourseDiary = (ListView) viewRootFragment.findViewById(R.id.lvCourseDiary);
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

            resetArrayData();

            //Initially clear the scroll count instance so filter date from BASE i.e. 0.
            iScrollCount = 0;

            ((BaseActivity) getActivity()).showPleaseWait("Loading...");

            callCourseWebService();
        }
    }

    /**
     * Clear array 'NO DATA FOUND'.
     */
    private void resetArrayData() {

        //Clear array list before inserting items.
        arrayListCourseData.clear();
        arrayCourseDataBackup.clear();
        iScrollCount = 0;
        //((CourseDiaryActivity) getActivity()).setTitleBar(CourseDiaryActivity.getMonth(Integer.parseInt(String.valueOf(CourseDiaryActivity.iMonth))) + " " + CourseDiaryActivity.iYear);
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
            ((BaseActivity) getActivity()).hideProgress();
        }
    }

    /**
     * Implement a method to hit News web service to get response.
     */
    public void requestCourseService() {

        aJsonParams = new AJsonParamsCourse();
        aJsonParams.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        aJsonParams.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParams.setDateto(CourseDairyTabFragment.strDateTo); // MM-DD-YYYY
        aJsonParams.setDatefrom(CourseDairyTabFragment.strDateFrom); // MM-DD-YYYY
        aJsonParams.setPageNo("0");
        aJsonParams.setPageSize("10");
        aJsonParams.setCourseKey("1.3");

        courseDiaryAPI = new CourseDiaryAPI(aJsonParams, "COURSEDIARY", ((CourseDiaryActivity)getActivity()).getClientId(), "GetSlots", ApplicationGlobal.TAG_GCLUB_MEMBERS);

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

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    private String getMemberId() {
        return ((CourseDiaryActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    private void updateSuccessResponse(JsonObject jsonObject) {
        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<CourseDiaryItems>() {
        }.getType();
        courseDiaryItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        Type type2 = new TypeToken<CourseDiaryItemsCopy>() {
        }.getType();
        courseDiaryItemsCopy = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type2);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (courseDiaryItems.getMessage().equalsIgnoreCase("Success")) {

                //  arrayListCourseData.addAll(courseDiaryItems.getData());
                arrayListCourseData.addAll(courseDiaryItems.getData());
                //Take backup of List before changing to record.
                arrayCourseDataBackup.addAll(courseDiaryItemsCopy.getData());


                if (arrayListCourseData.size() == 0) {
                    isMoreToScroll = false;
                    resetArrayData();
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {

                    isMoreToScroll = true;

                    //Initialize Course Events Adapter.
                    courseDiaryAdapter = new CourseDiaryAdapter(getActivity(), ((CourseDiaryActivity) getActivity()).filterCourseDates(iScrollCount, arrayCourseDataBackup, arrayListCourseData));
                    lvCourseDiary.setAdapter(courseDiaryAdapter);
                }
            } else {
                isMoreToScroll = false;
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(courseDiaryItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();
        lvCourseDiary.setSelection(iScrollCount);
    }
}