package com.ucreate.mhsystems.activites;

import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

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
import com.ucreate.mhsystems.utils.RecycleViewDividerDecoration;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryData;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryItems;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseDairyActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = CourseDairyActivity.class.getSimpleName();
    ArrayList<CourseDiaryData> arrayListCourseData = new ArrayList<>();
    Map<String, String> params;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.cdlCourseDiary)
    CoordinatorLayout cdlCourseDiary;
    @Bind(R.id.rvCourseDiary)
    RecyclerView rvCourseDiary;
    @Bind(R.id.toolBar)
    Toolbar toolbar;
    @Bind(R.id.tvCourseSchedule)
    TextView tvCourseSchedule;
    @Bind(R.id.tvCourseTitle)
    TextView tvCourseTitle;
    @Bind(R.id.tvMonthName)
    TextView tvMonthName;
    RecyclerView.Adapter recyclerViewAdapter;
    //Create instance of Model class CourseDiaryItems.
    CourseDiaryItems courseDiaryItems;


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

        RecyclerView.ItemDecoration itemDecoration =
                new RecycleViewDividerDecoration(CourseDairyActivity.this, LinearLayoutManager.VERTICAL);
        rvCourseDiary.addItemDecoration(itemDecoration);

        /**
         *It is must to set a Layout Manager For Recycler View
         *As per docs ,
         *RecyclerView allows client code to provide custom layout arrangements for child views.
         *These arrangements are controlled by the RecyclerView.LayoutManager.
         *A LayoutManager must be provided for RecyclerView to function.
         */
        rvCourseDiary.setLayoutManager(new LinearLayoutManager(CourseDairyActivity.this));

        //Set Course Diary Recycler Adapter.
        recyclerViewAdapter = new CourseDiaryRecyclerAdapter(CourseDairyActivity.this, arrayListCourseData);
        rvCourseDiary.setAdapter(recyclerViewAdapter);
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
        tvCourseTitle.setText(getResources().getString(R.string.title_course_diary));
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Implement a method to hit News web service to get response.
     */
    private void requestCourseDiaryService() {

        showPleaseWait("Please wait...");

        //Some of the params pass as JSON.
        Map<String, String> params2 = new HashMap<String, String>();
        params2.put("version", "1");
        params2.put("datefrom", "03/04/2016");
        params2.put("dateto", "03/23/2016");
//        params2.put("pageNo", "1");
//        params2.put("pageSize", "15");
        params2.put("callid", "1456315336575");

        //Add data to params.
        params = new HashMap<String, String>();
        params.put("aClientId", "44118078");
        params.put("aCommand", "GetSlots");
        params.put("aJsonParams", String.valueOf(params2));
        params.put("aModuleId", "COURSEDIARY");
        params.put("aUserClass", "Members");

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(CourseDairyActivity.this);
            RequestJsonObject jsObjRequest = new RequestJsonObject(Request.Method.POST,
                    WebAPI.URL_COURSE_DIARY, params,
                    createErrorListener(),
                    createSuccessListener()
            );
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Implement Volley error listener here.
     */
    public Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.e(LOG_TAG, "VolleyError Listener : " + volleyError);

                //If any Volley error occurs, then hide progress.
                hideProgress();

                /**
                 *  Handle network or time out error.
                 */
                if (volleyError.networkResponse == null) {
                    if (volleyError.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        showSnackBarMessages(cdlCourseDiary, getResources().getString(R.string.error_please_retry));
                    }
                }
            }
        };
    }

    /**
     * Implement success listener on execute api url.
     */
    public Response.Listener<JSONObject> createSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e(LOG_TAG, "SUCCESS RESULT : " + response.toString());

                Type type = new TypeToken<CourseDiaryItems>() {
                }.getType();
                courseDiaryItems = new Gson().fromJson(response.toString(), type);

                //Clear array list before inserting items.
                arrayListCourseData.clear();

                try {
                    /**
                     *  Check "Result" 1 or 0. If 1, means data received successfully.
                     */
                    if (courseDiaryItems.getResult().equals("1")) {

                        arrayListCourseData.addAll(courseDiaryItems.getData());

                        if (arrayListCourseData.size() == 0) {
                            showSnackBarMessages(cdlCourseDiary, getResources().getString(R.string.error_no_data));
                        } else {

                            //Set Course Diary Recycler Adapter.
                            recyclerViewAdapter = new CourseDiaryRecyclerAdapter(CourseDairyActivity.this, filterCourseDates(arrayListCourseData));
                            rvCourseDiary.setAdapter(recyclerViewAdapter);
                            //recyclerViewAdapter.notifyDataSetChanged();

                            Log.e(LOG_TAG, "" + arrayListCourseData.size());

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
        };
    }

    /**
     * Implements a method to filter or set date and name of Day
     * one time for all course events having same date and day.
     */
    public ArrayList<CourseDiaryData> filterCourseDates(ArrayList<CourseDiaryData> arrayListCourseData) {
        ArrayList<CourseDiaryData> courseDiaryDataArrayList = new ArrayList<>();
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
     *                           <p>
     *                           Exapmle: 2016-03-04T00:00:00
     * @Return : 04
     */
    private String formatDateOfEvent(String strCourseEventDate) {

        String strEventDate = strCourseEventDate.substring(strCourseEventDate.lastIndexOf("-") + 1, strCourseEventDate.lastIndexOf("T"));

        return strEventDate;
    }

    /**
     * @param strDayName <br>
     *                   Implements a method to return the format the day of
     *                   event.
     *                   <p>
     *                   Exapmle: NAME OF DAY : Friday
     * @Return : Fri
     */
    private String formatDayOfEvent(String strDayName) {
        return (strDayName.substring(0, 3));
    }

}
