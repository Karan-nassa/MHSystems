package com.ucreate.mhsystems.activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.CourseDiaryAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.fragments.CourseDairyTabFragment;
import com.ucreate.mhsystems.models.AJsonParamsCourse;
import com.ucreate.mhsystems.models.CourseDiaryAPI;
import com.ucreate.mhsystems.models.CourseDiaryData;
import com.ucreate.mhsystems.models.CourseDiaryDataCopy;
import com.ucreate.mhsystems.models.CourseDiaryItems;
import com.ucreate.mhsystems.models.CourseDiaryItemsCopy;
import com.ucreate.mhsystems.util.API.WebServiceMethods;

import java.lang.reflect.Type;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class CourseDiaryActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    public final String LOG_TAG = CourseDiaryActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.llMonthTitle)
    LinearLayout llMonthTitle;

    @Bind(R.id.toolBar)
    Toolbar toolbar;

    @Bind(R.id.cdlCourse)
    CoordinatorLayout cdlCourse;

    @Bind(R.id.tvMonthsName)
    TextView tvMonthsName;

    @Bind(R.id.lvCourseDiary)
    ListView lvCourseDiary;

    @Bind(R.id.ivPrevMonth)
    ImageView ivPrevMonth;

    @Bind(R.id.ivNextMonth)
    ImageView ivNextMonth;

    @Bind(R.id.ivCalendar)
    ImageView ivCalendar;

    @Bind(R.id.tvNameOfMonth)
    TextView tvNameOfMonth;

    @Bind(R.id.tvToday)
    TextView tvToday;

    //Create instance of  {@link Calendar} class.
    public static Calendar mCalendarInstance;

    //Create instance of Menu to change PREV, NEXT and TODAY icon.
    private static Menu menuInstance;

    private static Context mActivityContext;

    CourseDiaryAdapter courseDiaryAdapter;

    //List of type books this list will store type Book which is our data model
    private CourseDiaryAPI courseDiaryAPI;

    //Create instance of Model class CourseDiaryItems.
    CourseDiaryItems courseDiaryItems;
    CourseDiaryItemsCopy courseDiaryItemsCopy;
    AJsonParamsCourse aJsonParams;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static String strDate, strCurrentDate;
    public static int iMonth, iCurrentMonth;
    public static int iYear, iCurrentYear;

    public static String strDateFrom; //Start date.
    public static String strDateTo; //End date.
    public static String strNameOfMonth = "MARCH 2016";

    /**
     * +++++ START OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++
     **/
    public static int iLessDays;
    /**
     * +++++ END OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++
     **/

    boolean isDialogVisible = false;

    public static int iNumOfDays;

    int iScrollCount;

    //Stop user to scroll or change MONTH once no data found.
    boolean isMoreToScroll;

    static int iMonthTemp, iYearTemp;
    static String strDateTemp;

    ArrayList<CourseDiaryData> arrayListCourseData = new ArrayList<>();
    ArrayList<CourseDiaryDataCopy> arrayCourseDataBackup = new ArrayList<>();//Used for record of complete date and day name.

    /**
     * Implements HOME icons press
     * listener.
     */
    private View.OnClickListener mHomePressListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

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
                setTitleBar(arrayCourseDataBackup.get(++firstVisibleItem).getMonthName());

                CourseDiaryActivity.iMonth = arrayCourseDataBackup.get(firstVisibleItem).getiMonthNum();
                resetMonthsNavigationIcons();
            }

        }
    };

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
                        Intent mIntent = new Intent(CourseDiaryActivity.this, CustomAlertDialogActivity.class);
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

                    Intent intent = new Intent(CourseDiaryActivity.this, CourseDiaryDetailActivity.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_diary_new);

        //Initialize view resources.
        ButterKnife.bind(this);

        //Get Context
        mActivityContext = CourseDiaryActivity.this;

        /**
         *  If user back press on 'New Course' tab then app should
         *  open 'Old Course' by default when opening 'COURSE DIARY'.
         */
        CourseDairyTabFragment.iLastTabPosition = 0;

        //Let's first set up toolbar
        setupToolbar();

        //Initialize CALENDAR instance.
        mCalendarInstance = Calendar.getInstance();

        iYear = mCalendarInstance.get(Calendar.YEAR);
        iMonth = (mCalendarInstance.get(Calendar.MONTH) + 1);
        strDate = "" + mCalendarInstance.get(Calendar.DAY_OF_MONTH);

        //Store to check use cannot navigate to previous dates of CALENDAR.
        strCurrentDate = strDate;
        iCurrentYear = iYear;
        iCurrentMonth = iMonth;

        //Get total number of days of selected month.
        iNumOfDays = CourseDiaryActivity.mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Load Default fragment of COURSE DIARY.
        //  updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_NOTHING));

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);

        //When user want to Select date from CALENDAR.
        //   llMonthTitle.setOnClickListener(mCalendarListener);

        //Course Diary events click listener.
        lvCourseDiary.setOnItemClickListener(mCourseEventListener);

        //Load more COURSE listener call here.
        lvCourseDiary.setOnScrollListener(mLoadMoreScrollListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        resetArrayData();

        //Initially clear the scroll count instance so filter date from BASE i.e. 0.
        iScrollCount = 0;

        showPleaseWait("Loading...");

        callCourseDiaryWebService();
    }

    /**
     * Declares the quick navigation of top bar icons like Previous/Next Month, Today or
     * Calendar navigation to month randomly.
     */
    public void onClick(View view) {

        switch (view.getId()) {

            case ApplicationGlobal.ACTION_NOTHING:

                createDateForData();
                break;

            case R.id.ivPrevMonth:
                if (iMonth > iCurrentMonth) {
                    callPrevMonthAction();
                }
                break;

            case R.id.tvNameOfMonth:
                Log.e(LOG_TAG, "tvNameOfMonth : ");
                break;

            case R.id.ivNextMonth:
                if (iMonth < 12) {
                    callNextMonthAction();
                }
                break;

            case R.id.tvToday:
               // updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_TODAY));
                //Reset To current date.
//                CourseDiaryActivity.mCalendarInstance.set(Calendar.YEAR, CourseDiaryActivity.iCurrentYear);
//                CourseDiaryActivity.mCalendarInstance.set(Calendar.MONTH, (CourseDiaryActivity.iCurrentMonth - 1));
//                CourseDiaryActivity.mCalendarInstance.set(Calendar.DATE, Integer.parseInt(CourseDiaryActivity.strCurrentDate));

                CourseDiaryActivity.resetCalendar();

                /** +++++ START OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++ **/
                callTodayScrollEvents();
                /** +++++ END OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++ **/
                break;

            case R.id.ivCalendar:
                showCalendar();
                Log.e(LOG_TAG, "ivCalendar : ");
                break;
        }

        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
        resetMonthsNavigationIcons();
    }

    /**
     * Implements a method call for NEXT MONTH action called on
     * NEXT icon navigation and on SCROLL down to load
     * more COURSE EVENTS.
     */
    public void callPrevMonthAction() {
        /**
         *  User cannot navigate back to current
         *  month.
         */
        if (/*iMonth == 1 ||*/ CourseDiaryActivity.iMonth > CourseDiaryActivity.iCurrentMonth) {
            CourseDiaryActivity.iMonth--;

            if (CourseDiaryActivity.iMonth == CourseDiaryActivity.iCurrentMonth) {
                //Do nothing. Just load data according current date.
                CourseDiaryActivity.strDate = CourseDiaryActivity.strCurrentDate;
            } else {
                //Do nothing. Just load data according current date.
                CourseDiaryActivity.strDate = "01";
            }

            getNumberofDays();
        }

        createDateForData();
    }

    /**
     * Implements a method call for NEXT MONTH action called on
     * NEXT icon navigation and on SCROLL down to load
     * more COURSE EVENTS.
     */
    public void callNextMonthAction() {
        if (CourseDiaryActivity.iMonth == 12) {

        } else {
            CourseDiaryActivity.iMonth++;

            if (CourseDiaryActivity.iMonth > CourseDiaryActivity.iCurrentMonth) {
                setPreviousButton(true);
            }

            //Do nothing. Just load data according current date.
            CourseDiaryActivity.strDate = "01";

            getNumberofDays();
        }

        createDateForData();
    }

    /**
     * Finally, create DATE to get data and for CALENDAR, PREVIOUS/NEXT MONTH
     * functionality.
     */
    public void createDateForData() {
        //FORMAT : MM-DD-YYYY
        strDateFrom = CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.strDate + "/" + CourseDiaryActivity.iYear;

        //FORMAT : MM-DD-YYYY
        strDateTo = "" + CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.iNumOfDays + "/" + CourseDiaryActivity.iYear;

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(CourseDiaryActivity.iMonth))) + " " + CourseDiaryActivity.iYear;

        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
        Log.e(LOG_TAG, "END DATE : " + strDateTo);
        Log.e(LOG_TAG, "NAME OF MONTH : " + strNameOfMonth);

        callCourseDiaryWebService();
    }

    /**
     * Implements a method to call News web service either call
     * initially or call from onSwipeRefresh.
     */
    private void callCourseDiaryWebService() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(CourseDiaryActivity.this)) {

            //Method to hit Squads API.
            requestCourseService();
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_internet));
            hideProgress();
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

        courseDiaryAPI = new CourseDiaryAPI(aJsonParams, "COURSEDIARY", getClientId(), "GetSlots", ApplicationGlobal.TAG_GCLUB_MEMBERS);

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
                hideProgress();

                showAlertMessage("" + error);
            }
        });

    }

    /**
     * Implements this method to UPDATE the data from webservice in
     * COURSE DIARY list if get SUCCESS.
     */
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
                    showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {

                    isMoreToScroll = true;

                    //Initialize Course Events Adapter.
                    courseDiaryAdapter = new CourseDiaryAdapter(CourseDiaryActivity.this,/* filterCourseDates(iScrollCount,*/ arrayCourseDataBackup/*, arrayListCourseData)*/);
                    lvCourseDiary.setAdapter(courseDiaryAdapter);
                }
            } else {
                isMoreToScroll = false;
                //If web service not respond in any case.
                showAlertMessage(courseDiaryItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        hideProgress();
        lvCourseDiary.setSelection(iScrollCount);
    }

    /**
     * Implements a method to RESET CALENDAR state
     * or set as initial state.
     */
    public static void resetCalendar() {

        strDate = strCurrentDate;
        iMonth = iCurrentMonth;
        iYear = iCurrentYear;
    }

    /**
     * Implements a method to RESET CALENDAR state
     * or set as initial state.
     */
    public static void resetCalendarPicker() {

        strDate = strDateTemp;
        iMonth = iMonthTemp;
        iYear = iYearTemp;
    }

    /**
     * Implements a method to get TOTAL number of
     * DAYS in selected MONTH.
     */
    public static void getNumberofDays() {

        // Create a calendar object and set year and month
        mCalendarInstance = new GregorianCalendar(iYear, (iMonth - 1), Integer.parseInt(strDate));

        // Get the number of days in that month
        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Declares a method to get NAME of MONTH by passing
     * month value.
     */
    public static String getMonth(int month) {

        return new DateFormatSymbols().getMonths()[month - 1];
    }

    /**
     * Show snackBar message defined in BaseActivity.
     */
    public void showSnackMessage(String strSnackMessage) {
        Log.e("Snack:", strSnackMessage);
        showSnackBarMessages(cdlCourse, strSnackMessage);
    }

    /**
     * Implements a method to filter or set date and name of Day
     * one time for all course events having same date and day.
     */
    public ArrayList<CourseDiaryDataCopy> filterCourseDates(int iStart, ArrayList<CourseDiaryDataCopy> arrayListCourseDataCopies, ArrayList<CourseDiaryData> arrayListCourseData) {
        ArrayList<CourseDiaryDataCopy> courseDiaryDataArrayList = new ArrayList<>();
        courseDiaryDataArrayList.clear();
        String strLastDate = "";
        String strNameOfMonth;
        int iMonthNum; //For update navigation icon to enable, disable.

        /**
         *  Loop filter till end of Course Diary events.
         */
        for (int iCounter = 0; iCounter < arrayListCourseDataCopies.size(); iCounter++) {

            /**
             *Place NAME of Month for all events to set as Title bar when scroll down or up.
             */
            strNameOfMonth = arrayListCourseData.get(iCounter).getCourseEventDate();
            iMonthNum = Integer.parseInt(strNameOfMonth.substring((strNameOfMonth.indexOf("-") + 1), strNameOfMonth.lastIndexOf("-")));

            strNameOfMonth = strNameOfMonth.substring(0, strNameOfMonth.indexOf("-"));

            arrayListCourseDataCopies.get(iCounter).setMonthName(arrayListCourseData.get(iCounter).getMonthName() + " " + strNameOfMonth);

            arrayListCourseDataCopies.get(iCounter).setiMonthNum(iMonthNum);

            if (iCounter < iStart) {
                courseDiaryDataArrayList.add(arrayListCourseDataCopies.get(iCounter));
            } else {
                String strDateOfEvent = formatDateOfEvent(arrayListCourseDataCopies.get(iCounter).getCourseEventDate());

                /**
                 * Check if same date or not of Course Diary event If yes then just
                 * display date and day name once otherwise skip.
                 */
                if (strLastDate.equalsIgnoreCase(strDateOfEvent)) {

                    arrayListCourseDataCopies.get(iCounter).setCourseEventDate("");
                    arrayListCourseDataCopies.get(iCounter).setDayName("");

                } else {
                    strLastDate = strDateOfEvent;

                    arrayListCourseDataCopies.get(iCounter).setCourseEventDate(strDateOfEvent);
                    arrayListCourseDataCopies.get(iCounter).setDayName(formatDayOfEvent(arrayListCourseDataCopies.get(iCounter).getDayName()));
                }

                //Add final to new arrat list.
                courseDiaryDataArrayList.add(arrayListCourseDataCopies.get(iCounter));
            }
        }
        // Log.e("filterCourseDates:", "" + courseDiaryDataArrayList.size());
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
     * Initialize tool bar to display menu bar options, app-icon and
     * navigation drawer icon.
     */
    void setupToolbar() {
        setSupportActionBar(toolbar);
        //toolbar.setLogo(R.mipmap.ic_home_menu);
        // toolbar.setTitle("March 2016");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    /**
     * Implements a method to update
     * name of MONTH.
     */
    public void setTitleBar(String strNameOfMonth) {

        tvNameOfMonth.setText(strNameOfMonth);
    }

    /**
     * Create Menu Options with previous, next, Today icons.
     *
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        menuInstance = menu;
//
//        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
//        resetMonthsNavigationIcons();

        return true;
    }

    /**
     * Handle click event on Menu bar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Here we change the fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();

        switch (item.getItemId()) {
            case R.id.action_PrevMonth:
                if (iMonth > iCurrentMonth) {
                    updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_PREVIOUS_MONTH));
                }
                break;

            case R.id.action_NextMonth:
                updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_NEXT_MONTH));
                break;

            case R.id.action_Today:
                updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_TODAY));
                break;
        }

        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
        resetMonthsNavigationIcons();

        //Commit and navigate to new fragment.
        tr.commit();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Implements Today functionality to display CURRENT date to
     * next specific dates.
     */
    private void callTodayScrollEvents() {

        // Create a calendar object and set year and month
        CourseDiaryActivity.mCalendarInstance = new GregorianCalendar(CourseDiaryActivity.iYear, (CourseDiaryActivity.iMonth - 1), Integer.parseInt(CourseDiaryActivity.strDate));

        // Get the number of days in that month
        CourseDiaryActivity.iNumOfDays = CourseDiaryActivity.mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        CourseDiaryActivity.iLessDays = CourseDiaryActivity.iNumOfDays - Integer.parseInt(CourseDiaryActivity.strDate);

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(CourseDiaryActivity.iMonth))) + " " + CourseDiaryActivity.iYear;

        Log.e(LOG_TAG, "callTodayScrollEvents : " + strNameOfMonth);
        int iDate;
        if (CourseDiaryActivity.iLessDays < ApplicationGlobal.LOAD_MORE_VALUES) {

            strDateFrom = CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.strDate + "/" + CourseDiaryActivity.iYear;
            /**
             *  Suppose Current date is near to end of Month then increment to
             *  Next Month.
             */
            CourseDiaryActivity.iMonth += 1;
            CourseDiaryActivity.strDate = "" + (ApplicationGlobal.LOAD_MORE_VALUES - CourseDiaryActivity.iLessDays);
            strDateTo = CourseDiaryActivity.iMonth + "/" + (CourseDiaryActivity.strDate) + "/" + CourseDiaryActivity.iYear;
        } else {
            strDateFrom = CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.strDate + "/" + CourseDiaryActivity.iYear;

            iDate = Integer.parseInt(CourseDiaryActivity.strDate);
            CourseDiaryActivity.strDate = "" + (iDate + ApplicationGlobal.LOAD_MORE_VALUES);

            strDateTo = CourseDiaryActivity.iMonth + "/" + CourseDiaryActivity.strDate + "/" + CourseDiaryActivity.iYear;
        }


        Log.e(LOG_TAG, "strDate " + strDateFrom);
        Log.e(LOG_TAG, "strDateTo " + strDateTo);
    }

    /**
     * Implements this method to reset CALENDAR PREV, NEXT icon.
     */
    public void resetMonthsNavigationIcons() {
        /**
         *  To disable or display blur previous icon.
         */
        if (iMonth <= iCurrentMonth) {
            setPreviousButton(false);
        } else {
            setPreviousButton(true);
        }
    }

    /**
     * User can navigate back to 1st JAN of current YEAR for COMPLETED tab so reset or navigate
     * to current MONTH if past MONTH/DATE selected via PREVIOUS MONTH icon.
     */
    public void resetCalendarEvents() {

        if (iMonth <= iCurrentMonth) {

            //Reset to current MONTH.
            resetCalendar();

            // Create a calendar object and set year and month
            mCalendarInstance = new GregorianCalendar(iYear, (iMonth - 1), Integer.parseInt(strDate));

            // Get the number of days in that month
            iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

            //FORMAT : MM-DD-YYYY
            CourseDairyTabFragment.strDateFrom = "" + iMonth + "/" + strDate + "/" + iYear;

            //FORMAT : MM-DD-YYYY
            CourseDairyTabFragment.strDateTo = "" + iMonth + "/" + iNumOfDays + "/" + iYear;

            //Set MONTH title.
            setTitleBar(getMonth(Integer.parseInt(String.valueOf(iMonth))) + " " + iYear);

            Log.e(LOG_TAG, "START DATE : " + CourseDairyTabFragment.strDateFrom);
            Log.e(LOG_TAG, "END DATE : " + CourseDairyTabFragment.strDateTo);
        }
    }

    /**
     * Implements a method to ENABLE/DISABLE previous
     * MONTH arrow.
     *
     * @param isEnable : TRUE means VISIBLE otherwise FALSE.
     */
    public void setPreviousButton(boolean isEnable) {

        if (isEnable) {
            ivPrevMonth.setAlpha(1);
//            menuInstance.getItem(0).setIcon(ContextCompat.getDrawable(mActivityContext, R.mipmap.ic_arrow_left));
        } else {
            ivPrevMonth.setAlpha((float) 0.3);
            //menuInstance.getItem(0).setIcon(ContextCompat.getDrawable(mActivityContext, R.mipmap.ic_arrow_left_blur));
        }
    }

    /**
     * Implements a method to ENABLE/DISABLE NEXT
     * MONTH arrow.
     */
    public void setNextButton(boolean isEnable) {

        if (isEnable) {
            ivNextMonth.setAlpha(1);
            //menuInstance.getItem(1).setIcon(ContextCompat.getDrawable(mActivityContext, R.mipmap.ic_arrow_right));
        } else {
            ivNextMonth.setAlpha((float) 0.3);
          //  menuInstance.getItem(1).setIcon(ContextCompat.getDrawable(mActivityContext, R.mipmap.ic_arrow_right_blur));
        }
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, "44118078");
    }

    /**
     * Implements a method to clear array on
     * 'NO DATA FOUND' message.
     */
    private void resetArrayData() {
        arrayListCourseData.clear();
        arrayCourseDataBackup.clear();
        iScrollCount = 0;
    }

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
                    setNextButton(false);
                } else {
                    setNextButton(true);

                    showPleaseWait("Loading more...");

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

                   setTitleBar(CourseDiaryActivity.getMonth(Integer.parseInt(String.valueOf(CourseDiaryActivity.iMonth))) + " " + CourseDiaryActivity.iYear);

                    Log.e(LOG_TAG, "strDateFrom " + CourseDairyTabFragment.strDateFrom);
                    Log.e(LOG_TAG, "strDateTo " + CourseDairyTabFragment.strDateTo);

                    //Reload data with new date created from user.
                    callCourseDiaryWebService();
                    break;
                }
            }

            default:{
                showPleaseWait("Loading more...");
                CourseDairyTabFragment.callNextMonthAction();
                //Reload data with new date created from user.
                callCourseDiaryWebService();
            }
        }

        resetMonthsNavigationIcons();
    }

    /**
     * Display CALENDAR view on tap of Month Title.
     */
    private void showCalendar(){

        iMonthTemp = iMonth;
        iYearTemp = iYear;
        strDateTemp = strDate;

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(CourseDiaryActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        int tMonthofYear = ++monthOfYear;

                        if (year == iCurrentYear) {

                            if (tMonthofYear > iCurrentMonth) {

                                iYear = year;
                                iMonth = tMonthofYear;
                                strDate = "" + dayOfMonth;

                                iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                                //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
                                resetMonthsNavigationIcons();

                                //setTitleBar(getMonth(Integer.parseInt(String.valueOf(iMonth))) + " " + iYear);

                                //updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                                callCourseDiaryWebService();
                            } else if (tMonthofYear == iCurrentMonth) {

                                if (dayOfMonth >= Integer.parseInt(strCurrentDate)) {

                                    iYear = year;
                                    iMonth = tMonthofYear;
                                    strDate = "" + dayOfMonth;

                                    getNumberofDays();

                                    //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
                                    resetMonthsNavigationIcons();

                                    //  setTitleBar(getMonth(Integer.parseInt(String.valueOf(iMonth))) + " " + iYear);

                                    //updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_CALENDAR));
                                    callCourseDiaryWebService();
                                } else {
                                    resetCalendarPicker();
                                    showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                                }
                            } else {
                                resetCalendarPicker();
                                showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                            }
                        } else {
                            resetCalendarPicker();
                            showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                        }
                    }
                }, iYear, --iMonth, Integer.parseInt(strDate));

        dpd.setButton(
                DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            resetCalendarPicker();
                        }
                    }
                });
        dpd.show();

        //Set Minimum or hide dates of PREVIOUS dates of CALENDAR.
        //    dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }


}
