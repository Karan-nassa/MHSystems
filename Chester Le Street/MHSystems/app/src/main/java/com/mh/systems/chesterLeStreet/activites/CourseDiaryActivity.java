package com.mh.systems.chesterLeStreet.activites;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.chesterLeStreet.R;
import com.mh.systems.chesterLeStreet.adapter.BaseAdapter.CourseDiaryAdapter;
import com.mh.systems.chesterLeStreet.constants.ApplicationGlobal;
import com.mh.systems.chesterLeStreet.web.WebAPI;
import com.mh.systems.chesterLeStreet.models.AJsonParamsCourse;
import com.mh.systems.chesterLeStreet.models.CourseDiaryAPI;
import com.mh.systems.chesterLeStreet.models.CourseDiaryData;
import com.mh.systems.chesterLeStreet.models.CourseDiaryDataCopy;
import com.mh.systems.chesterLeStreet.models.CourseDiaryItems;
import com.mh.systems.chesterLeStreet.models.CourseDiaryItemsCopy;
import com.mh.systems.chesterLeStreet.models.CoursesData;
import com.mh.systems.chesterLeStreet.web.api.WebServiceMethods;
import com.newrelic.com.google.gson.reflect.TypeToken;

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

/**
 * The {@link CourseDiaryActivity} used to display the Course Diary events by months. First of all current month events
 * will be loaded and when user scroll down then some events of next months will be loaded and so on...
 * <p>
 * After redesign Course Diary, remove the OLD and NEW COURSE {@link android.support.v4.app.Fragment} and implements this
 * functionality in {@link PopupMenu}.
 * <p>
 * Also update PREVIOUS, NEXT, TODAY and {@link Calendar} functionality in a top bar below of {@link Toolbar}.
 *
 * @author {@link karan@ucreate.co.in}
 * @version 1.0
 * @since 12 May, 2016
 */
public class CourseDiaryActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    public final String LOG_TAG = CourseDiaryActivity.class.getSimpleName();

    ArrayList<CoursesData> coursesDataArrayList = new ArrayList<>();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.llCourseCategory)
    LinearLayout llCourseCategory;

    @Bind(R.id.tvCourseType)
    TextView tvCourseType;

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
    public Calendar mCalendarInstance;

    //Create instance of Menu to change PREV, NEXT and TODAY icon.
    private Menu menuInstance;

    //Pop Menu to show Categories of Course Diary.
    PopupMenu popupMenu;

    //Declares the instance of Course Diary Adapter.
    CourseDiaryAdapter courseDiaryAdapter;

    //List of type books this list will store type Book which is our data model
    private CourseDiaryAPI courseDiaryAPI;

    //Create instance of Model class CourseDiaryItems.
    CourseDiaryItems courseDiaryItems;
    CourseDiaryItemsCopy courseDiaryItemsCopy;
    AJsonParamsCourse aJsonParams;

    /* ++ INTERNET CONNECTION PARAMETERS ++ */

    @Bind(R.id.inc_message_view)
    RelativeLayout inc_message_view;

    @Bind(R.id.ivMessageSymbol)
    ImageView ivMessageSymbol;

    @Bind(R.id.tvMessageTitle)
    TextView tvMessageTitle;

    @Bind(R.id.tvMessageDesc)
    TextView tvMessageDesc;

     /* -- INTERNET CONNECTION PARAMETERS -- */

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public String strDate, strCurrentDate;
    public int iMonth, iCurrentMonth;
    public int iYear, iCurrentYear;

    private int iLastCalendarAction;

    public String strDateFrom = ""; //Start date.
    public String strDateTo = ""; //End date.
    public String strNameOfMonth = "JUNE 2016";

    String strLastDateFrom;
    String strLastDateTo;

    /**
     * +++++ START OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++
     **/
    public int iLessDays;

    /**
     * +++++ END OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++
     **/

    public int iNumOfDays;

    int iScrollCount;

    //Stop user to scroll or change MONTH once no data found.
    boolean isMoreToScroll;

    int iMonthTemp, iYearTemp;
    String strDateTemp;

    String strLastCourseEventDate = "";

    /**
     * iCourseType for categorised like Old or New Course.
     * By default Old Course is selected.
     */
    String strCourseType = "1.1";

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

                        if (isMoreToScroll) {
                            getMoreCourseEvents();
                        }
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (courseDiaryAdapter.CourseDiaryData.size() > 0) {
                setTitleBar(courseDiaryAdapter.CourseDiaryData.get(firstVisibleItem).getMonthName());

                iMonth = courseDiaryAdapter.CourseDiaryData.get(firstVisibleItem).getiMonthNum();
                resetMonthsNavigationIcons();
            }
        }
    };

    /**
     * Declares the click event handling FIELD to set categories
     * of COURSE DIARY.
     */
    private PopupMenu.OnMenuItemClickListener mCourseTypeListener =
            new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    tvCourseType.setText(item.getTitle());

                    switch (item.getItemId()) {
                        case R.id.item_tonbridge:
                            strCourseType = "1.1";
                            break;

                        case R.id.item_pembury:
                            strCourseType = "1.2";
                            break;
                    }

                    resetArrayData();

                    //Show progress dialog during call web service.
                    showPleaseWait("Loading...");
                    callCourseDiaryWebService();
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_diary);

        //Initialize view resources.
        ButterKnife.bind(this);

        //Initialize Course Events Adapter.
        courseDiaryAdapter = new CourseDiaryAdapter(this);

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
        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Load Default fragment of COURSE DIARY.
        createDateForData();
        //resetMonthsNavigationIcons();

        initializeCourseCategory();

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);

        //Load more COURSE listener call here.
        lvCourseDiary.setOnScrollListener(mLoadMoreScrollListener);

        llCourseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        popupMenu.setOnMenuItemClickListener(mCourseTypeListener);
    }

    /**
     * Implements a method to initialize Course Diary categories in pop-up menu like
     * <b>Old Course</b> and <b>New Course</b> for Sunningdales golf club.
     */
    private void initializeCourseCategory() {

        /**
         * Step 1: Create a new instance of popup menu
         */
        popupMenu = new PopupMenu(this, tvCourseType);
        /**
         * Step 2: Inflate the menu resource. Here the menu resource is
         * defined in the res/menu project folder
         */
        popupMenu.inflate(R.menu.course_menu);

        //Initially display title at position 0 of R.menu.course_menu.
        tvCourseType.setText("" + popupMenu.getMenu().getItem(0));
    }

    /**
     * Declares the quick navigation of top bar icons like Previous/Next Month, Today or
     * Calendar navigation to month randomly.
     */
    public void onClick(View view) {

        if (isOnline(CourseDiaryActivity.this)) {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
            //  inc_message_view.setVisibility(View.GONE);
            switch (view.getId()) {

                case ApplicationGlobal.ACTION_NOTHING:
                    resetArrayData();
                    createDateForData();
                    break;

                case R.id.ivPrevMonth:
                    if (iMonth > iCurrentMonth) {
                        resetArrayData();
                        callPrevMonthAction();
                    } else if (iYear > iCurrentYear) {
                        resetArrayData();
                        callPrevMonthAction();
                    }
                    break;

                case R.id.ivNextMonth:
                    //      if (iMonth < 12) {
                    resetArrayData();
                    callNextMonthAction();
                    //     }

                    break;

                case R.id.tvToday:
                    resetArrayData();

                    iLastCalendarAction = ApplicationGlobal.ACTION_TODAY;

                    //Reset To current date.
//                 mCalendarInstance.set(Calendar.YEAR,  iCurrentYear);
//                 mCalendarInstance.set(Calendar.MONTH, ( iCurrentMonth - 1));
//                 mCalendarInstance.set(Calendar.DATE, Integer.parseInt( strCurrentDate));

                    resetCalendar();

                    /** +++++ START OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++ **/
                    callTodayScrollEvents();
                    /** +++++ END OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++ **/
                    break;

                case R.id.ivCalendar:
                    resetArrayData();
                    iLastCalendarAction = ApplicationGlobal.ACTION_CALENDAR;

                    showCalendar();
                    break;
            }

            //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
            resetMonthsNavigationIcons();
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
        }
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
        if (/*iMonth == 1 ||*/  iMonth > iCurrentMonth) {
            iMonth--;

            if (iMonth == iCurrentMonth) {
                //Do nothing. Just load data according current date.
                strDate = strCurrentDate;
            } else {
                if (iMonth == 1) {
                    iMonth = 12;
                    iYear = (iYear - 1);
                } else {
                    iMonth--;
                }

                //Do nothing. Just load data according current date.
                strDate = "01";
            }

            getNumberofDays();
        } else {
            if (iMonth == 1) {
                iMonth = 12;
                iYear = (iYear - 1);
            } else {
                iMonth--;
            }

            //Do nothing. Just load data according current date.
            strDate = "01";
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
        if (iMonth == 12) {
            iMonth = 1;
            strDate = "01";
            iYear = (iYear + 1);

            getNumberofDays();
        } else {

            iMonth++;

            if (iMonth > iCurrentMonth) {
                setPreviousButton(true);
            }

            //Do nothing. Just load data according current date.
            strDate = "01";

            getNumberofDays();
        }

        createDateForData();
    }

    /**
     * Finally, create DATE to get data and for CALENDAR, PREVIOUS/NEXT MONTH
     * functionality.
     */
    public void createDateForData() {

        strLastDateFrom = strDateFrom;
        strLastDateTo = strDateTo;

        //FORMAT : MM-DD-YYYY
        strDateFrom = iMonth + "/" + strDate + "/" + iYear;

        //FORMAT : MM-DD-YYYY
        strDateTo = "" + iMonth + "/" + iNumOfDays + "/" + iYear;

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(iMonth))) /*+ " " +  iYear*/;

        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
        Log.e(LOG_TAG, "END DATE : " + strDateTo);
        Log.e(LOG_TAG, "NAME OF MONTH : " + strNameOfMonth);

        if (strLastDateFrom.equals(strDateFrom) && strLastDateTo.equals(strDateTo)) {
            isMoreToScroll = false;
            hideProgress();
        } else {
            //Show progress dialog during call web service.
            showPleaseWait("Loading...");
            callCourseDiaryWebService();
        }
    }

    /**
     * Implements a method to call News web service either call
     * initially or call from onSwipeRefresh.
     */
    private void callCourseDiaryWebService() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(this)) {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
            // inc_message_view.setVisibility(View.GONE);
            //Method to hit Squads API.
            requestCourseService();
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
            // inc_message_view.setVisibility(View.VISIBLE);
            //showAlertMessage(getResources().getString(R.string.error_no_internet));
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
        aJsonParams.setDateto(strDateTo); // MM-DD-YYYY
        aJsonParams.setDatefrom(strDateFrom); // MM-DD-YYYY
        aJsonParams.setPageNo("0");
        aJsonParams.setPageSize("10");
        aJsonParams.setCourseKey(strCourseType);

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
                showAlertMessage("" + getResources().getString(R.string.error_please_retry));
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

                // arrayListCourseData.addAll(courseDiaryItems.getCompResultData());
                //Take backup of List before changing to record.
                arrayCourseDataBackup.addAll(courseDiaryItemsCopy.getData());

                if (arrayCourseDataBackup.size() == 0) {
                    isMoreToScroll = false;
                    resetArrayData();
                    showNoCourseView(false);
                    // showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {

                    showNoCourseView(true);

                    isMoreToScroll = true;

                    //Set Course Diary Adapter after create section title of Course Events.
                    filterCourseDates(courseDiaryItems.getData());

                    lvCourseDiary.setAdapter(courseDiaryAdapter);
                    courseDiaryAdapter.notifyDataSetChanged();
                }
            } else {
                isMoreToScroll = false;
                setTitleBar(getMonth(iMonth));

                showNoCourseView(false);
                //If web service not respond in any case.
                //  showAlertMessage(courseDiaryItems.getMessage());
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
    public void resetCalendar() {
        strDate = strCurrentDate;
        iMonth = iCurrentMonth;
        iYear = iCurrentYear;
    }

    /**
     * Implements a method to RESET CALENDAR state
     * or set as initial state.
     */
    public void resetCalendarPicker() {
        strDate = strDateTemp;
        iMonth = iMonthTemp;
        iYear = iYearTemp;
    }

    /**
     * Implements a method to get TOTAL number of
     * DAYS in selected MONTH.
     */
    public void getNumberofDays() {

        // Create a calendar object and set year and month
        mCalendarInstance = new GregorianCalendar(iYear, (iMonth - 1), Integer.parseInt(strDate));

        // Get the number of days in that month
        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Declares a method to get NAME of MONTH by passing
     * month value.
     */
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    /**
     * Implements this method to filter or create section and item rows
     * of Course Diary events.
     */
    public ArrayList<CourseDiaryData> filterCourseDates(ArrayList<CourseDiaryData> arrayListCourseData) {
        ArrayList<CourseDiaryData> diaryDataArrayList = new ArrayList<>();
        diaryDataArrayList.clear();
        String strNameOfMonth;
        int iMonthNum; //For update navigation icon to enable, disable.

        /**
         *  Loop filter till end of Course Diary events.
         */
        for (int iCounter = 0; iCounter < arrayListCourseData.size(); iCounter++) {

            /**
             *Place NAME of Month for all events to set as Title bar when scroll down or up.
             */
            strNameOfMonth = arrayListCourseData.get(iCounter).getCourseEventDate();
            iMonthNum = Integer.parseInt(strNameOfMonth.substring((strNameOfMonth.indexOf("-") + 1), strNameOfMonth.lastIndexOf("-")));

            arrayListCourseData.get(iCounter).setiMonthNum(iMonthNum);

            String strCourseEventDate = arrayListCourseData.get(iCounter).getStrCourseEventDate();

            /**
             *  Add section title of Course Event if not added before.
             */
            if (!(strLastCourseEventDate.equalsIgnoreCase(strCourseEventDate))) {
                courseDiaryAdapter.addSectionHeaderItem(arrayListCourseData.get(iCounter));
                strLastCourseEventDate = strCourseEventDate;
            }
            courseDiaryAdapter.addItem(arrayListCourseData.get(iCounter));

            //Add final to new array-list.
            diaryDataArrayList.add(arrayListCourseData.get(iCounter));
        }
        return diaryDataArrayList;
    }

    /**
     * Implements a method to update
     * name of MONTH.
     */
    public void setTitleBar(String strNameOfMonth) {
        tvNameOfMonth.setText(strNameOfMonth + " " + iYear);
    }

    /**
     * Implements Today functionality to display CURRENT date to
     * next specific dates.
     */
    private void callTodayScrollEvents() {

        // Create a calendar object and set year and month
        mCalendarInstance = new GregorianCalendar(iYear, (iMonth - 1), Integer.parseInt(strDate));

        // Get the number of days in that month
        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        iLessDays = iNumOfDays - Integer.parseInt(strDate);

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(iMonth))) + " " + iYear;

        int iDate;
        if (iLessDays < ApplicationGlobal.LOAD_MORE_VALUES) {

            strDateFrom = iMonth + "/" + strDate + "/" + iYear;
            /**
             *  Suppose Current date is near to end of Month then increment to
             *  Next Month.
             */
            iMonth += 1;
            strDate = "" + (ApplicationGlobal.LOAD_MORE_VALUES - iLessDays);
            strDateTo = iMonth + "/" + (strDate) + "/" + iYear;
        } else {
            strDateFrom = iMonth + "/" + strDate + "/" + iYear;

            iDate = Integer.parseInt(strDate);
            strDate = "" + (iDate + ApplicationGlobal.LOAD_MORE_VALUES);

            strDateTo = iMonth + "/" + strDate + "/" + iYear;
        }


        Log.e(LOG_TAG, "strDate " + strDateFrom);
        Log.e(LOG_TAG, "strDateTo " + strDateTo);

        //Show progress dialog during call web service.
        showPleaseWait("Loading...");
        callCourseDiaryWebService();
    }

    /**
     * Implements this method to reset Previous and Next months
     * navigation icons ENABLE or DISABLE state.
     */
    public void resetMonthsNavigationIcons() {

        if (iMonth == iCurrentMonth) {
            setPreviousButton(false);
            setNextButton(true);
        } else if (iMonth == 12) {
            setPreviousButton(true);
            setNextButton(true);
        } else if (iYear > iCurrentYear) {
            setPreviousButton(true);
            setNextButton(true);
        } else {
            setPreviousButton(true);
            setNextButton(true);
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
            ivPrevMonth.setAlpha((float) 1.0);
        } else {
            ivPrevMonth.setAlpha((float) 0.3);
        }
    }

    /**
     * Implements a method to ENABLE/DISABLE NEXT
     * MONTH arrow.
     */
    public void setNextButton(boolean isEnable) {

        if (isEnable) {
            ivNextMonth.setAlpha((float) 1.0);
        } else {
            ivNextMonth.setAlpha((float) 0.3);
        }
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements a method to clear array on
     * 'NO DATA FOUND' message.
     */
    private void resetArrayData() {
        arrayListCourseData.clear();
        arrayCourseDataBackup.clear();
        iScrollCount = 0;

        courseDiaryAdapter.CourseDiaryData.clear();
        courseDiaryAdapter.sectionHeader.clear();
        courseDiaryAdapter.notifyDataSetChanged();
    }

    /**
     * Implements a functionality to which will be called when user scroll down and LOAD more alert displaying and user
     * get more specific [ApplicationGlobal.LOAD_MORE_VALUES] having value.
     */
    private void getMoreCourseEvents() {

        strLastDateFrom = strDateFrom;
        strLastDateTo = strDateTo;

        //Scroll down functionality should only work for TODAY and CALENDAR date picker.
        switch (iLastCalendarAction) {
            case ApplicationGlobal.ACTION_CALENDAR:
            case ApplicationGlobal.ACTION_TODAY: {

                if (iMonth == 12) {
                    setNextButton(false);
                } else {
                    setNextButton(true);

                    showPleaseWait("Loading more...");

                    // Create a calendar object and set year and month
                    mCalendarInstance = new GregorianCalendar(iYear, (iMonth - 1), Integer.parseInt(strDate));

                    // Get the number of days in that month
                    iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                    iLessDays = iNumOfDays - Integer.parseInt(strDate);

                    int iDate = Integer.parseInt(strDate);

                    if (iDate >= iNumOfDays) {
                        //if lessDays are greater than month lessDays
                        // strDate = "01";
                        iDate = 0;
                        iMonth += 1;

                        // Create a calendar object and set year and month
                        mCalendarInstance = new GregorianCalendar(iYear, (iMonth - 1), Integer.parseInt(strDate));

                        // Get the number of days in that month
                        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                        iLessDays = iNumOfDays - Integer.parseInt(strDate);
                    }


                    if (iLessDays < ApplicationGlobal.LOAD_MORE_VALUES) {

                        strDateFrom = iMonth + "/" + (iDate + 1)/* strDate*/ + "/" + iYear;

                        if (iDate == 0) {
                            strDate = "" + ((iDate + ApplicationGlobal.LOAD_MORE_VALUES));
                            strDateTo = iMonth + "/" + (strDate) + "/" + iYear;
                        } else {
                            /**
                             *  Suppose Current date is near to end of Month then increment to
                             *  Next Month.
                             */
                            iMonth += 1;
                            strDate = "" + ((iDate + ApplicationGlobal.LOAD_MORE_VALUES) - iNumOfDays);
                            strDateTo = iMonth + "/" + (strDate) + "/" + iYear;
                        }

                    } else {

                        strDateFrom = iMonth + "/" + (iDate + 1)/* strDate*/ + "/" + iYear;

                        strDate = "" + (iDate + ApplicationGlobal.LOAD_MORE_VALUES);

                        strDateTo = iMonth + "/" + strDate + "/" + iYear;
                    }

                    if (strLastDateFrom.equals(strDateFrom) && strLastDateTo.equals(strDateTo)) {
                        isMoreToScroll = false;
                        hideProgress();
                    } else {
                        //setTitleBar(getMonth(Integer.parseInt(String.valueOf(iMonth)))/* + " " + iYear*/);

                        Log.e(LOG_TAG, "strDateFrom " + strDateFrom);
                        Log.e(LOG_TAG, "strDateTo " + strDateTo);

                        //Show progress dialog during call web service.
                        showPleaseWait("Loading...");
                        callCourseDiaryWebService();
                    }
                    break;
                }
            }

            default: {
                showPleaseWait("Loading more...");
                callNextMonthAction();
            }
        }

        resetMonthsNavigationIcons();
    }

//    /**
//     * Display CALENDAR view on tap of CALENDAR icon.
//     */
//    private void showCalendar() {
//
//        iMonthTemp = iMonth;
//        iYearTemp = iYear;
//        strDateTemp = strDate;
//
//        // Launch Date Picker Dialog
//        DatePickerDialog dpd = new DatePickerDialog(this,
//                new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year,
//                                          int monthOfYear, int dayOfMonth) {
//
//                        int tMonthofYear = ++monthOfYear;
//
//                        if (year == iCurrentYear) {
//
//                            if (tMonthofYear > iCurrentMonth) {
//
//                                iYear = year;
//                                iMonth = tMonthofYear;
//                                strDate = "" + dayOfMonth;
//
//                                iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//                                //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
//                                resetMonthsNavigationIcons();
//
//                                // iNumOfDays = Integer.parseInt( strDate);
//                                callTodayScrollEvents();
//                            } else if (tMonthofYear == iCurrentMonth) {
//
//                                if (dayOfMonth >= Integer.parseInt(strCurrentDate)) {
//
//                                    iYear = year;
//                                    iMonth = tMonthofYear;
//                                    strDate = "" + dayOfMonth;
//
//                                    getNumberofDays();
//
//                                    //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
//                                    resetMonthsNavigationIcons();
//
//                                    // iNumOfDays = Integer.parseInt( strDate);
//                                    callTodayScrollEvents();
//                                } else {
//                                    resetCalendarPicker();
//                                    showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
//                                }
//                            } else {
//                                resetCalendarPicker();
//                                showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
//                            }
//                        } else {
//                            resetCalendarPicker();
//                            showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
//                        }
//                    }
//                }, iYear, --iMonth, Integer.parseInt(strDate));
//
//        dpd.setButton(
//                DialogInterface.BUTTON_NEGATIVE, "Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == DialogInterface.BUTTON_NEGATIVE) {
//                            resetCalendarPicker();
//                        }
//                    }
//                });
//        dpd.show();
//
//        //Set Minimum or hide dates of PREVIOUS dates of CALENDAR.
//        //    dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//    }

    /**
     * Implements a method to display {@link Calendar}.
     */
    private void showCalendar() {

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
                        iYear = year;
                        iMonth = tMonthofYear;
                        strDate = "" + dayOfMonth;
                        //    if (year == iCurrentYear) {
                        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                        if (iCurrentYear < iYear) {

                            getNumberofDays();
                            createDateForData();

                        } else if (iCurrentYear == iYear) {

                            if (iCurrentMonth < iMonth) {

                                getNumberofDays();
                                createDateForData();
                            } else if (iCurrentMonth == iMonth) {


                                if (Integer.parseInt(strCurrentDate) <= dayOfMonth) {

                                    getNumberofDays();
                                    createDateForData();

                                } else {
                                    resetCalendarPicker();
                                    showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                                    return;
                                }
                            } else {
                                resetCalendarPicker();
                                showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                                return;
                            }
                        }


                        //    if (year == iCurrentYear) {

                        //     if (iPopItemPos == 2) {

//                            iYear = year;
//                            iMonth = tMonthofYear;
//                            strDate = "" + dayOfMonth;
//
//                            iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//                            createDateForData();

                        //updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                        //                } else {

//                                if (tMonthofYear > iCurrentMonth) {
//
//                                    iYear = year;
//                                    iMonth = tMonthofYear;
//                                    strDate = "" + dayOfMonth;
//
//                                    iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//                                    createDateForData();

                        // updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                        //  } else if (tMonthofYear == iCurrentMonth) {

                        if (dayOfMonth >= Integer.parseInt(strCurrentDate)) {

                            iYear = year;
                            iMonth = tMonthofYear;
                            strDate = "" + dayOfMonth;

                            iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                            getNumberofDays();

                            createDateForData();

                            // updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_CALENDAR));
                        } else {
                            iYear = year;
                            iMonth = tMonthofYear;
                            strDate = "" + dayOfMonth;

                            iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                            getNumberofDays();

                            createDateForData();

                        }
//                                    } else {
//                                        resetCalendarPicker();
//                                        showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
//                                    }
//                                } else {
//                                    resetCalendarPicker();
//                                    showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
//                                }
                    }
//                        } else {
//                            resetCalendarPicker();
//                            showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
//                        }
                    //    }
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

        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
        resetMonthsNavigationIcons();
        dpd.show();

        //Set Minimum or hide dates of PREVIOUS dates of CALENDAR.
        //    dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    /**
     * Implements a method to show 'NO COURSE' view and hide it at least one Course event.
     *
     * @param hasData :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
     */
    public void showNoCourseView(boolean hasData) {
        if (hasData) {
            inc_message_view.setVisibility(View.GONE);
        } else {
            inc_message_view.setVisibility(View.VISIBLE);
            ivMessageSymbol.setImageResource(R.mipmap.ic_home_diary);
            tvMessageTitle.setText(getResources().getString(R.string.error_no_course));
            tvMessageDesc.setText(getResources().getString(R.string.error_select_different_month));
        }
    }
}
