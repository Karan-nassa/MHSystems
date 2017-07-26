package com.mh.systems.woolstonmanor.ui.activites;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.woolstonmanor.R;
import com.mh.systems.woolstonmanor.ui.adapter.BaseAdapter.CompetitionsAdapter;
import com.mh.systems.woolstonmanor.utils.constants.ApplicationGlobal;
import com.mh.systems.woolstonmanor.web.api.WebAPI;
import com.mh.systems.woolstonmanor.web.models.CompetitionsAPI;
import com.mh.systems.woolstonmanor.web.models.CompetitionsData;
import com.mh.systems.woolstonmanor.web.models.CompetitionsJsonParams;
import com.mh.systems.woolstonmanor.web.models.CompetitionsResultItems;
import com.mh.systems.woolstonmanor.web.api.WebServiceMethods;
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

public class CompetitionsActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    private final String LOG_TAG = CompetitionsActivity.class.getSimpleName();

    /**
     * iCourseType for categorised of Competitions. By default 'My Events' is selected.
     */
    boolean isUpcoming = true;
    boolean isJoined = false;
    boolean isCompleted = false;
    boolean isCurrent = false;

    /**
     * iPopItemPos describes the position of POP MENU selected item.
     * <br> 0 : UPCOMING
     * <br> 1 : ENTERED
     * <br> 2 : COMPLETED
     */
    int iPopItemPos = 0;

    ArrayList<CompetitionsData> competitionsDatas = new ArrayList<>();
    int iScrollCount;

    public static String strDateFrom; //Start date.
    public static String strDateTo; //End date.
    public static String strNameOfMonth = "MARCH 2016";

    int iMonthTemp, iYearTemp;
    String strDateTemp;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.tvCompType)
    TextView tvCompType;

    @Bind(R.id.llCompCategory)
    LinearLayout llCompCategory;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);

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

//        createDateForData();
        //callCompetitionsWebService();

        initCompetitionsCategory();

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);

        llCompCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        popupMenu.setOnMenuItemClickListener(mCompetitionsTypeLitener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createDateForData();
    }

    /**
     * Declares the quick navigation of top bar icons like Previous/Next Month, Today or
     * Calendar navigation to month randomly.
     */
    public void onClick(View view) {

        if (isOnline(CompetitionsActivity.this)) {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
            switch (view.getId()) {

                case ApplicationGlobal.ACTION_NOTHING:

                    break;

                case R.id.ivPrevMonthComp:
                    /**
                     *  User can navigate back till current MONTH but can back to JANUARY MONTH for
                     *  COMPLETED tab.
                     */
                    if (iPopItemPos == 2) {

                        if (iMonth >= 1) {
                            actionPreviousMonth();
                            createDateForData();
                            //updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_PREVIOUS_MONTH));
                        } else {
                            setPreviousButton(false);
                            setNextButton(true);
                        }
                    } else {
                        Log.e("yaer", "" + Calendar.YEAR);
                        if (iMonth > iCurrentMonth) {
                            actionPreviousMonth();
                            setNextButton(true);
                            createDateForData();
                            // updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_PREVIOUS_MONTH));
                        } else {
                            if (iMonth == 1) {
                                iMonth = 12;
                                iYear = (iYear - 1);
                                Log.e("iYear", "" + iYear);
                                strDate = "01";
                                createDateForData();
                            } else if (iMonth < iCurrentMonth && iCurrentYear < iYear) {
                                Log.e("iYear", "" + iYear);
                                iMonth--;
                                strDate = "01";
                                createDateForData();
                            }


                        }
                    }
                    break;

                case R.id.ivNextMonthComp:
                    if (iMonth == 12) {
                        iMonth = 1;
                        iYear = (iYear + 1);
                        Log.e("iYear", "" + iYear);
                        strDate = "01";

                        getNumberofDays();
                        setPreviousButton(true);
                        createDateForData();
                    } else {
                        iMonth++;

                        if (iMonth > iCurrentMonth) {
                            setPreviousButton(true);
                        } else if (iMonth == 12) {
                            setNextButton(false);
                        }

                        //Do nothing. Just load data according current date.
                        strDate = "01";

                        getNumberofDays();

                        createDateForData();
                    }
                    break;

                case R.id.tvTodayComp:
                    resetCalendar();

//                    mCalendarInstance.set(Calendar.YEAR, iCurrentYear);
//                    mCalendarInstance.set(Calendar.MONTH, (iCurrentMonth - 1));
//                    mCalendarInstance.set(Calendar.DATE, Integer.parseInt(strCurrentDate));
//
//                    //Initialize the dates of CALENDER to display data according dates.
//                    strDate = "" + mCalendarInstance.get(Calendar.DATE);
//                    iNumOfDays = mCalendarInstance.get(Calendar.DATE);
//
//                    //Get MONTH and YEAR.
//                    iMonth = (mCalendarInstance.get(Calendar.MONTH) + 1);

                    createDateForData();
                    break;

                case R.id.ivCalendarComp:
                    showCalendar();
                    break;
            }
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
        }
    }

    /**
     * Implement a method to hit Competitions
     * web service to get response.
     */
    public void requestCompetitionsEvents() {

        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
        resetMonthsNavigationIcons();

        showPleaseWait("Loading...");

        competitionsJsonParams = new CompetitionsJsonParams();
        competitionsJsonParams.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        competitionsJsonParams.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        competitionsJsonParams.setMemberId(getMemberId());
        competitionsJsonParams.setMyEventsOnly(isJoined);
        competitionsJsonParams.setIncludeCompletedEvents(isCompleted);
        competitionsJsonParams.setIncludeCurrentEvents(isCurrent);
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

                showAlertMessage("" + getResources().getString(R.string.error_server_problem));
            }
        });
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
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
        }
    }

    /**
     * Implements a method to update UI when 'No Competitions'.
     *
     * @param hasData : True means more than 1 data.
     */
    public void updateNoCompetitionsUI(boolean hasData) {
        if (hasData) {
            showNoEventView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true, "");
        } else {
            showNoEventView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false, getResources().getString(R.string.error_no_competitions));
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
     * Implements a method to update name of MONTH.
     * <p/>
     * EXAMPLE: 06 for JUNE.
     */
    public void setTitleBar(String strNameOfMonth) {
        tvNameOfMonthComp.setText(strNameOfMonth + " " + iYear);
    }


    /**
     * Implements this method to reset CALENDAR PREV, NEXT and TODAY icon.
     */
    public void resetMonthsNavigationIcons() {
        /**
         *  To disable or display blur previous icon.
         */

        if (iPopItemPos == 2) {

            if (iMonth < 1) {
                setPreviousButton(false);
            } else {
                setPreviousButton(true);
            }
        } else {
            if (iMonth <= iCurrentMonth && iCurrentYear == iYear) {
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
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

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
     * Declares the click event handling FIELD to set categories
     * of COURSE DIARY.
     */
    private PopupMenu.OnMenuItemClickListener mCompetitionsTypeLitener =
            new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    tvCompType.setText(item.getTitle());

                    switch (item.getItemId()) {
                       /* case R.id.item_My_Events:
                            iPopItemPos = 0;
                            isMyEvent = true;
                            isUpcoming = true;
                            isPast = true;
                            isCompleted = true;
                            break;*/

                        case R.id.item_Upcoming:
                            iPopItemPos = 0;
                            isUpcoming = true;
                            isJoined = false;
                            isCompleted = false;
                            isCurrent = false;
                            break;

                        case R.id.item_Joined:
                            iPopItemPos = 1;
                            isUpcoming = true;
                            isJoined = true;
                            isCompleted = true;
                            isCurrent = true;
                            break;

                        case R.id.item_Completed:
                            iPopItemPos = 2;
                            isUpcoming = false;
                            isJoined = true;
                            isCompleted = true;
                            isCurrent = false;
                            break;
                    }

                    resetArrayData();

                    if (iPopItemPos < 2) {
                        resetCalendar();
                        //createDateForData();
                    } else {
                        strDate = "01";
                        iMonth = iCurrentMonth;
                        iYear = iCurrentYear;
                        //createDateForData();

                        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
                        //resetMonthsNavigationIcons();
                        //callCompetitionsWebService();
                    }

                    mCalendarInstance = new GregorianCalendar(iYear, (iMonth - 1), Integer.parseInt(strDate));
                    iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
                    createDateForData();
                    return true;
                }
            };


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

                    competitionsAdapter = new CompetitionsAdapter(CompetitionsActivity.this, competitionsDatas, true, iPopItemPos);
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
            reportRollBarException(CompetitionsActivity.class.getSimpleName(), e.toString());
        }

        //Update Month title name.
        setTitleBar(getMonth(iMonth));

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

        /*if (competitionsAdapter.compititionsDatas.size() > 0) {*/
        competitionsAdapter.compititionsDatas.clear();
        competitionsAdapter.notifyDataSetChanged();
        /*}*/
    }

    /**
     * Implements a method to RESET CALENDAR state
     * or set as initial state.
     */
    private void resetCalendarPicker() {
        strDate = strDateTemp;
        iMonth = iMonthTemp;
        iYear = iYearTemp;
    }

    /**
     * Implements a method to initialize Competitions category in pop-up menu like <b>My Events</b>, <b>Upcoming</b>, <b>Past</b>
     * and <b>Completed</b> for Sunningdales golf club.
     */
    private void initCompetitionsCategory() {

        /**
         * Step 1: Create a new instance of popup menu
         */
        popupMenu = new PopupMenu(this, tvCompType);
        /**
         * Step 2: Inflate the menu resource. Here the menu resource is
         * defined in the res/menu project folder
         */
        popupMenu.inflate(R.menu.competitions_menu);

        //Initially display title at position 0 of R.menu.course_menu.
        tvCompType.setText("" + popupMenu.getMenu().getItem(0));
    }

    /**
     * Implements a method to create date to display for
     * selected month, year and date.
     */
    private void createDateForData() {
        //FORMAT : MM-DD-YYYY
        strDateFrom = "" + iMonth + "/" + strDate + "/" + iYear;

        //FORMAT : MM-DD-YYYY
        strDateTo = "" + iMonth + "/" + iNumOfDays + "/" + iYear;

        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
        Log.e(LOG_TAG, "END DATE : " + strDateTo);

        strNameOfMonth = getMonth(Integer.parseInt(String.valueOf(iMonth))) + " " + iYear;

        Log.e(LOG_TAG, strNameOfMonth);
        Log.e("DATA ", "DATE : " + strDate + " MONTH : " + iMonth + " YEAR : " + iYear + " NUM OF DAYS : " + iNumOfDays);

        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
        // resetMonthsNavigationIcons();

        callCompetitionsWebService();
    }

    /**
     * Implements a method to perform PREVIOUS month
     * action.
     */
    private void actionPreviousMonth() {
        //IF COMPLETED TAB SELECTED THEN DISPLAY DATA FROM 1st JAN of current year.
        if (iPopItemPos == 2) {

            if (iMonth == Calendar.JANUARY) {

                setPreviousButton(false);
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
            } else {

                setPreviousButton(false);
            }
        }
    }

    /**
     * Implements a method to display {@link Calendar}.
     */
    private void showCalendar() {

        iMonthTemp = iMonth;
        iYearTemp = iYear;
        strDateTemp = strDate;

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(CompetitionsActivity.this,
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
                        if (tvCompType.getText().equals("Completed")) {
                            iYear = year;
                            iMonth = tMonthofYear;
                            strDate = "" + dayOfMonth;

                            iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                            getNumberofDays();
                            createDateForData();

                        } else {

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

                            if (iPopItemPos == 2) {

                                iYear = year;
                                iMonth = tMonthofYear;
                                strDate = "" + dayOfMonth;

                                iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                                createDateForData();

                                //updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                            } else {

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

        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
        resetMonthsNavigationIcons();
        dpd.show();

        //Set Minimum or hide dates of PREVIOUS dates of CALENDAR.
        //    dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }
}