package com.ucreate.mhsystems.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.CourseDiaryAdapter;
import com.ucreate.mhsystems.adapter.RecyclerAdapter.CourseDiaryRecyclerAdapter;
import com.ucreate.mhsystems.api.volley.RequestJsonObject;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.utils.API.WebServiceMethods;
import com.ucreate.mhsystems.utils.RecycleViewDividerDecoration;
import com.ucreate.mhsystems.utils.pojo.AJsonParams_;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryAPI;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryData;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryDataCopy;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryItems;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryItemsCopy;


import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class CourseDairyActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = CourseDairyActivity.class.getSimpleName();
    ArrayList<CourseDiaryData> arrayListCourseData = new ArrayList<>();
    ArrayList<CourseDiaryDataCopy> arrayCourseDataBackup = new ArrayList<>();//Used for record of complete date and day name.

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.cdlCourseDiary)
    CoordinatorLayout cdlCourseDiary;
    //    @Bind(R.id.rvCourseDiary)
//    RecyclerView rvCourseDiary;
    @Bind(R.id.toolBar)
    Toolbar toolbar;
    @Bind(R.id.tvCourseSchedule)
    TextView tvCourseSchedule;
    @Bind(R.id.tvMonthName)
    TextView tvMonthName;
//    RecyclerView.Adapter recyclerViewAdapter;

    @Bind(R.id.lvCourseDiary)
    ListView lvCourseDiary;

    CourseDiaryAdapter courseDiaryAdapter;

    //Create instance of Model class CourseDiaryItems.
    CourseDiaryItems courseDiaryItems;
    CourseDiaryItemsCopy courseDiaryItemsCopy;
    AJsonParams_ aJsonParams;

    //List of type books this list will store type Book which is our data model
    private CourseDiaryAPI courseDiaryAPI;

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

                Intent intent = new Intent(CourseDairyActivity.this, CourseDiaryDetailActivity.class);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_dairy);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(this);

        //Let's first set up toolbar
        setupToolbar();

//        RecyclerView.ItemDecoration itemDecoration =
//                new RecycleViewDividerDecoration(CourseDairyActivity.this, LinearLayoutManager.VERTICAL);
//        rvCourseDiary.addItemDecoration(itemDecoration);
//
//        /**
//         *It is must to set a Layout Manager For Recycler View
//         *As per docs ,
//         *RecyclerView allows client code to provide custom layout arrangements for child views.
//         *These arrangements are controlled by the RecyclerView.LayoutManager.
//         *A LayoutManager must be provided for RecyclerView to function.
//         */
//        rvCourseDiary.setLayoutManager(new LinearLayoutManager(CourseDairyActivity.this));

        //Set Course Diary Recycler Adapter.
//        recyclerViewAdapter = new CourseDiaryRecyclerAdapter(CourseDairyActivity.this, filterCourseDates(arrayListCourseData));
//        rvCourseDiary.setAdapter(recyclerViewAdapter);


        lvCourseDiary.setOnItemClickListener(mCourseEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(CourseDairyActivity.this)) {
            //Method to hit Squads API.
            requestCourseDiaryService();
        } else {
            showSnackBarMessages(cdlCourseDiary, getResources().getString(R.string.error_no_internet));
        }
    }

    /**
     * Initialize tool bar to display UI title with
     * EVENTS date and Back button.
     */
    void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Implement a method to hit News web service to get response.
     */
    private void requestCourseDiaryService() {

        aJsonParams = new AJsonParams_();
        aJsonParams.setCallid("1456315336575");
        aJsonParams.setVersion("1");
        aJsonParams.setDateto("03-23-2016");
        aJsonParams.setDatefrom("03-04-2016");
        aJsonParams.setPageNo("0");
        aJsonParams.setPageSize("10");

        courseDiaryAPI = new CourseDiaryAPI(aJsonParams, "COURSEDIARY", "44118078", "GetSlots", "Members");

        showPleaseWait("Please wait...");

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
                hideProgress();

                showSnackBarMessages(cdlCourseDiary, "" + error);
            }
        });

    }

    private void updateSuccessResponse(JsonObject jsonObject) {
        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<CourseDiaryItems>() {
        }.getType();
        courseDiaryItems = new Gson().fromJson(jsonObject.toString(), type);

        Type type2 = new TypeToken<CourseDiaryItemsCopy>() {
        }.getType();
        courseDiaryItemsCopy = new Gson().fromJson(jsonObject.toString(), type2);

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
                    showSnackBarMessages(cdlCourseDiary, getResources().getString(R.string.error_no_data));
                } else {

                    Log.e(LOG_TAG, "arrayCourseDataBackup : " + arrayCourseDataBackup.get(0).getDayName());
                    Log.e(LOG_TAG, "arrayListCourseData : " + arrayListCourseData.get(0).getDayName());

                    //Set Course Diary Recycler Adapter.
//                    recyclerViewAdapter = new CourseDiaryRecyclerAdapter(CourseDairyActivity.this, filterCourseDates(arrayListCourseData));
//                    rvCourseDiary.setAdapter(recyclerViewAdapter);

                    courseDiaryAdapter = new CourseDiaryAdapter(CourseDairyActivity.this, filterCourseDates(arrayCourseDataBackup));
                    lvCourseDiary.setAdapter(courseDiaryAdapter);

                    Log.e(LOG_TAG, "arrayCourseDataBackup : " + arrayCourseDataBackup.get(0).getDayName());
                    Log.e(LOG_TAG, "arrayListCourseData : " + arrayListCourseData.get(0).getDayName());
                    Log.e(LOG_TAG, "arrayListCourseData : " + arrayListCourseData.size());

                    //Set Name of Month selected in CALENDER or record from api of COURSE DIARY.
                    tvMonthName.setText(arrayListCourseData.get(0).getMonthName());
                }
            } else {
                //If web service not respond in any case.
                showSnackBarMessages(cdlCourseDiary, getResources().getString(R.string.error_please_retry));
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        hideProgress();
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

}
