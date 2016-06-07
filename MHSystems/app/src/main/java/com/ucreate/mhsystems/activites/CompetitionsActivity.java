package com.ucreate.mhsystems.activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.CompetitionsAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.models.CompetitionsAPI;
import com.ucreate.mhsystems.models.CompetitionsData;
import com.ucreate.mhsystems.models.CompetitionsJsonParams;
import com.ucreate.mhsystems.models.CompetitionsResultItems;
import com.ucreate.mhsystems.models.CourseDiaryDataCopy;
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

public class CompetitionsActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    private final String LOG_TAG = CompetitionsActivity.class.getSimpleName();

    /**
     * iCourseType for categorised of Competitions. By default 'My Events' is selected.
     */
    boolean isMyEvent = true;
    boolean isUpcoming = true;
    boolean isPast = true;
    boolean isCompleted = true;

    int iPopItemPos = 0;

    ArrayList<CompetitionsData> competitionsDatas = new ArrayList<>();
    int iScrollCount;

    public static String strDateFrom; //Start date.
    public static String strDateTo; //End date.
    public static String strNameOfMonth = "MARCH 2016";

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.tvCompType)
    TextView tvCompType;

    @Bind(R.id.llMonthTitle)
    LinearLayout llMonthTitle;

    @Bind(R.id.lvCompetitions)
    ListView lvCompetitions;

    @Bind(R.id.ivPrevMonthComp)
    ImageView ivPrevMonthComp;

    @Bind(R.id.ivNextMonthComp)
    ImageView ivNextMonthComp;

    @Bind(R.id.ivCalendarComp)
    ImageView ivCalendarComp;

    @Bind(R.id.tvNameOfMonthComp)
    TextView tvNameOfMonthComp;

    @Bind(R.id.tvTodayComp)
    TextView tvTodayComp;

    //Pop Menu to show Categories of Course Diary.
    PopupMenu popupMenu;

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

    //Create instance of  {@link Calendar} class.
    public static Calendar mCalendarInstance;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public String strDate, strCurrentDate;
    public int iMonth, iCurrentMonth;
    public int iYear, iCurrentYear;

    public int iNumOfDays;

    CompetitionsAdapter competitionsAdapter;

    //List of type books this list will store type Book which is our data model
    private CompetitionsAPI competitionsAPI;

    //Create instance of Model class CourseDiaryItems.
    CompetitionsResultItems competitionsResultItems;
    CompetitionsJsonParams competitionsJsonParams;

    /**
     * Implements HOME icons press listener.
     */
    private View.OnClickListener mHomePressListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    /**
     * Display CALENDAR view on tap of Month Title.
     */
    private View.OnClickListener mCalendarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(CompetitionsActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            int tMonthofYear = ++monthOfYear;

                            if (year == iCurrentYear) {

                                if (iPopItemPos == 2) {

                                    iYear = year;
                                    iMonth = tMonthofYear;
                                    strDate = "" + dayOfMonth;

                                    //updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                                } else {

                                    if (tMonthofYear > iCurrentMonth) {

                                        iYear = year;
                                        iMonth = tMonthofYear;
                                        strDate = "" + dayOfMonth;

                                        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                                       // updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                                    } else if (tMonthofYear == iCurrentMonth) {

                                        if (dayOfMonth >= Integer.parseInt(strCurrentDate)) {

                                            iYear = year;
                                            iMonth = tMonthofYear;
                                            strDate = "" + dayOfMonth;

                                            getNumberofDays();

                                           // updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                                        } else {
                                            resetCalendar();
                                            showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                                        }
                                    } else {
                                        resetCalendar();
                                        showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                                    }
                                }
                            } else {
                                resetCalendar();
                                showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                            }
                        }
                    }, iYear, --iMonth, Integer.parseInt(strDate));
            //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
            resetMonthsNavigationIcons();
            dpd.show();

            //Set Minimum or hide dates of PREVIOUS dates of CALENDAR.
            //    dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
    };

    /**
     * Declares the click event handling FIELD to set categories
     * of COURSE DIARY.
     */
    private PopupMenu.OnMenuItemClickListener mCompetitionsTypeLitener =
            new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    tvCompType.setText(item.getTitle());

                    switch (item.getItemId()) {
                        case R.id.item_My_Events:
                            iPopItemPos = 0;
                            isMyEvent = true; isUpcoming = true; isPast = true; isCompleted = true;
                            break;

                        case R.id.item_Upcoming:
                            iPopItemPos = 1;
                            isMyEvent = false; isUpcoming = true; isPast = false; isCompleted = false;
                            break;

                        case R.id.item_Past:
                            iPopItemPos = 2;
                            isMyEvent = false; isUpcoming = false; isPast = true; isCompleted = false;
                            break;

                        case R.id.item_Completed:
                            iPopItemPos = 3;
                            isMyEvent = false; isUpcoming = false; isPast = false; isCompleted = true;
                            break;
                    }

                    resetArrayData();

                    //Show progress dialog during call web service.
                    showPleaseWait("Loading...");
                    callCompetitionsWebService();
                    return true;
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions_new);

        //Initialize view resources.
        ButterKnife.bind(this);

        competitionsAdapter = new CompetitionsAdapter(this);

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
        iNumOfDays = CompetitionsActivity.mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        initCompetitionsCategory();
        callCompetitionsWebService();

        //Load Default fragment of COURSE DIARY.
        //updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_NOTHING));

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);

        //When user want to Select date from CALENDAR.
        //llMonthTitleComp.setOnClickListener(mCalendarListener);

        llMonthTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });

        popupMenu.setOnMenuItemClickListener(mCompetitionsTypeLitener);
    }

    /**
     * Implements a method to call News web service either call
     * initially or call from onSwipeRefresh.
     */
    private void callCompetitionsWebService() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(CompetitionsActivity.this)) {
           updateNoInternetUI(true);
            requestCompetitionsEvents();
        } else {
            updateNoInternetUI(false);
        }
    }

    /**
     * Implement a method to hit Competitions
     * web service to get response.
     */
    public void requestCompetitionsEvents() {

        showPleaseWait("Loading...");

        competitionsJsonParams = new CompetitionsJsonParams();
        competitionsJsonParams.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        competitionsJsonParams.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        competitionsJsonParams.setMemberId(getMemberId());
        competitionsJsonParams.setMyEventsOnly(isMyEvent);
        competitionsJsonParams.setIncludeCompletedEvents(isPast);
        competitionsJsonParams.setIncludeCurrentEvents(isCompleted);
        competitionsJsonParams.setIncludeFutureEvents(isUpcoming);
        competitionsJsonParams.setDateto(strDateTo); // MM-DD-YYYY
        competitionsJsonParams.setDatefrom(strDateFrom); // MM-DD-YYYY
        competitionsJsonParams.setPageNo("0");
        competitionsJsonParams.setPageSize("10");
        competitionsJsonParams.setAscendingDateOrder(true);

        competitionsAPI = new CompetitionsAPI(getClientId(), "GETCLUBEVENTLIST", competitionsJsonParams, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

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
                hideProgress();

                showAlertMessage("" + error);
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

        //Clear array list before inserting items.
        competitionsDatas.clear();
        //arrayCourseDataBackup.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (competitionsResultItems.getMessage().equalsIgnoreCase("Success")) {

                competitionsDatas.addAll(competitionsResultItems.getData());

                if (competitionsDatas.size() == 0) {
                    // ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                   updateNoCompetitionsUI(false);
                } else {

                    updateNoCompetitionsUI(true);

                   /* //TRUE to set visible of JOIN button.
                    competitionsAdapter = new CompetitionsAdapter(CompetitionsActivity.this, competitionsDatas, true);
                    lvCompetitions.setAdapter(competitionsAdapter);*/
                    competitionsAdapter = new CompetitionsAdapter(CompetitionsActivity.this, competitionsDatas, true);
                    lvCompetitions.setAdapter(competitionsAdapter);
                    competitionsAdapter.notifyDataSetChanged();

                    Log.e(LOG_TAG, "arrayListCourseData : " + competitionsDatas.size());
                }
            } else {
                updateNoCompetitionsUI(false);
                //If web service not respond in any case.
                //((BaseActivity) getActivity()).showAlertMessage(competitionsResultItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        hideProgress();
    }

    /**
     * Implements a method to clear array on
     * 'NO DATA FOUND' message.
     */
    private void resetArrayData() {
        competitionsDatas.clear();
        iScrollCount = 0;

        competitionsAdapter.compititionsDatas.clear();
        competitionsAdapter.notifyDataSetChanged();
    }


    /**
     * Declares the quick navigation of top bar icons like Previous/Next Month, Today or
     * Calendar navigation to month randomly.
     */
    public void onClick(View view) {

        if (isOnline(CompetitionsActivity.this)) {
            showNoInternetView(inc_message_view,ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
            switch (view.getId()) {

                case ApplicationGlobal.ACTION_NOTHING:

                    break;

                case R.id.ivPrevMonth:
                    //IF COMPLETED TAB SELECTED THEN DISPLAY DATA FROM 1st JAN of current year.
                    if (iPopItemPos == 2) {

                        if (iMonth == Calendar.JANUARY) {

                            setPreviousButton(false);
                        } else {

                            iMonth--;

                            //Do nothing. Just load data according current date.
                            strDate = "01";

                           getNumberofDays();
                        }
                    } else {

                        /**
                         *  User cannot navigate back to current
                         *  month.
                         */
                        if (/*iMonth == 1 ||*/ iMonth > iCurrentMonth) {
                            iMonth--;

                            if (iMonth == iCurrentMonth) {
                                //Do nothing. Just load data according current date.
                                strDate = strCurrentDate;
                            } else {
                                //Do nothing. Just load data according current date.
                                strDate = "01";
                            }

                            getNumberofDays();
                        }else{
                            setPreviousButton(false);
                        }
                    }
                    break;

                case R.id.ivNextMonth:
                    if (iMonth == 12) {

                    } else {
                        iMonth++;

                        if(iMonth > iCurrentMonth){
                            setPreviousButton(true);
                        }

                        //Do nothing. Just load data according current date.
                        strDate = "01";

                        getNumberofDays();
                    }
                    break;

                case R.id.tvToday:
                    mCalendarInstance.set(Calendar.YEAR, iCurrentYear);
                    mCalendarInstance.set(Calendar.MONTH, (iCurrentMonth - 1));
                    mCalendarInstance.set(Calendar.DATE, Integer.parseInt(strCurrentDate));

                    //Initialize the dates of CALENDER to display data according dates.
                    strDate = "" + mCalendarInstance.get(Calendar.DATE);
                    iNumOfDays = mCalendarInstance.get(Calendar.DATE);

                    //Get MONTH and YEAR.
                    iMonth = (mCalendarInstance.get(Calendar.MONTH) + 1);
                    break;

                case R.id.ivCalendar:
                    iNumOfDays = Integer.parseInt(strDate);
                    break;
            }

            //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
            resetMonthsNavigationIcons();
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
        }

        //FORMAT : MM-DD-YYYY
        strDateFrom = "" + iMonth + "/" + strDate + "/" + iYear;

        //FORMAT : MM-DD-YYYY
        strDateTo = "" + iMonth + "/" + iNumOfDays + "/" + iYear;

        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
        Log.e(LOG_TAG, "END DATE : " + strDateTo);

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(iMonth))) + " " + iYear;

        Log.e(LOG_TAG, strNameOfMonth);
        Log.e("DATA ", "DATE : " + strDate + " MONTH : " + iMonth + " YEAR : " + iYear + " NUM OF DAYS : " + iNumOfDays);

    }

    /**
     * Implements a method to update UI when 'No Internet connection'
     * when disconnect internet connection.
     *
     * @param isOnline : True means internet working fine.
     */
    public void updateNoInternetUI(boolean isOnline) {
        if (isOnline) {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
            //inc_noInternet.setVisibility(View.GONE);
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
            //inc_noInternet.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Implements a method to update UI when 'No Competitions'.
     *
     * @param hasData : True means more than 1 data.
     */
    public void updateNoCompetitionsUI(boolean hasData) {
        if (hasData) {
            showNoCompetitionsView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
        } else {
            showNoCompetitionsView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
        }
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
    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
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
     * Implements a method to update name of MONTH.
     * <p/>
     * EXAMPLE: 06 for JUNE.
     */
    public void setTitleBar(String strNameOfMonth) {
        tvNameOfMonthComp.setText(strNameOfMonth);
    }

//    /**
//     * Create Menu Options
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        menuInstance = menu;
//
//        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
//        resetMonthsNavigationIcons();
//
//        return true;
//    }

//    /**
//     * Handle click event on Menu bar icons
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        //Here we change the fragment
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction tr = fm.beginTransaction();
//
//        switch (item.getItemId()) {
//            case R.id.action_PrevMonth:
//                /**
//                 *  User can navigate back till current MONTH but can back to JANUARY MONTH for
//                 *  COMPLETED tab.
//                 */
//                if (CompetitionsTabFragment.iLastTabPosition == 2) {
//
//                    if (iMonth > 1) {
//                        updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_PREVIOUS_MONTH));
////                        menuInstance.getItem(0).setIcon(ContextCompat.getDrawable(CompetitionsActivity.this, R.mipmap.ic_arrow_left_blur));
////                        showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
//                    } else {
//                        setPreviousButton(false);
//                    }
//                } else {
//                    if (iMonth > iCurrentMonth) {
//                        updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_PREVIOUS_MONTH));
//                    }
//                }
//                break;
//
//            case R.id.action_NextMonth:
//                updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_NEXT_MONTH));
//                break;
//
//            case R.id.action_Today:
//                updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_TODAY));
//                break;
//        }
//
//        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
//        resetMonthsNavigationIcons();
//
//        //Commit and navigate to new fragment.
//        tr.commit();
//        return super.
//
//                onOptionsItemSelected(item);
//
//    }

    /**
     * Implements this method to reset CALENDAR PREV, NEXT and TODAY icon.
     */
    public void resetMonthsNavigationIcons() {
        /**
         *  To disable or display blur previous icon.
         */
        if (iPopItemPos == 2) {

            if (iMonth <= 1) {
                setPreviousButton(false);
            } else {
                setPreviousButton(true);
            }
        } else {
            if (iMonth <= iCurrentMonth) {
                setPreviousButton(false);
            } else {
                setPreviousButton(true);
            }
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
            strDateFrom = "" + iMonth + "/" + strDate + "/" + iYear;

            //FORMAT : MM-DD-YYYY
            strDateTo = "" + iMonth + "/" + iNumOfDays + "/" + iYear;

            //Set MONTH title.
            setTitleBar(getMonth(Integer.parseInt(String.valueOf(iMonth))) + " " + iYear);

//            Log.e(LOG_TAG, "START DATE : " + CompetitionsTabFragment.strDateFrom);
//            Log.e(LOG_TAG, "END DATE : " + CompetitionsTabFragment.strDateTo);
        }
    }

    /**
     * Implements a method to ENABLE/DISABLE previous
     * MONTH arrow.
     */
    public void setPreviousButton(boolean isEnable) {

        if (isEnable) {
            ivPrevMonthComp.setAlpha((float) 1.0);
        } else {
            ivPrevMonthComp.setAlpha((float) 0.3);
        }
    }

    /**
     * Implements a method to ENABLE/DISABLE NEXT
     * MONTH arrow.
     */
    public void setNextButton(boolean isEnable) {

        if (isEnable) {
            ivNextMonthComp.setAlpha((float) 1.0);
        } else {
            ivNextMonthComp.setAlpha((float) 0.3);
        }
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    public String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, "44118078");
    }

    /**
     * Implements a method to initialize Competitions category in pop-up menu like <b>My Events</b>, <b>Upcoming</b>, <b>Past</b>
     * and <b>Completed</b> for Sunningdales golf club.
     */
    private void initCompetitionsCategory() {

        /**
         * Step 1: Create a new instance of popup menu
         */
        popupMenu = new PopupMenu(this, tvNameOfMonthComp);
        /**
         * Step 2: Inflate the menu resource. Here the menu resource is
         * defined in the res/menu project folder
         */
        popupMenu.inflate(R.menu.competitions_menu);

        //Initially display title at position 0 of R.menu.course_menu.
        tvCompType.setText("" + popupMenu.getMenu().getItem(0));
    }
}
