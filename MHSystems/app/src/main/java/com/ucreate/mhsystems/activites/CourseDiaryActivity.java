package com.ucreate.mhsystems.activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.fragments.CourseDairyTabFragment;
import com.ucreate.mhsystems.models.CourseDiaryData;
import com.ucreate.mhsystems.models.CourseDiaryDataCopy;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseDiaryActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    public static final String LOG_TAG = CourseDiaryActivity.class.getSimpleName();

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

    //Create instance of  {@link Calendar} class.
    public static Calendar mCalendarInstance;

    //Create instance of Menu to change PREV, NEXT and TODAY icon.
    private static Menu menuInstance;

    private static Context mActivityContext;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static String strDate, strCurrentDate;
    public static int iMonth, iCurrentMonth;
    public static int iYear, iCurrentYear;

    /**
     * +++++ START OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++
     **/
    public static int iLessDays;
    /**
     * +++++ END OF SCROLL DOWN TO LOAD MORE FUNCTIONALITY +++++
     **/


    public static int iNumOfDays;


    static int iMonthTemp, iYearTemp;
    static String strDateTemp;
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
     * Display CALENDAR view on tap of Month Title.
     */
    private View.OnClickListener mCalendarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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

                                    updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                                } else if (tMonthofYear == iCurrentMonth) {

                                    if (dayOfMonth >= Integer.parseInt(strCurrentDate)) {

                                        iYear = year;
                                        iMonth = tMonthofYear;
                                        strDate = "" + dayOfMonth;

                                        getNumberofDays();

                                        //Set ENABLE/DISABLE state of ICONS on change tab or pressed.
                                        resetMonthsNavigationIcons();

                                        //  setTitleBar(getMonth(Integer.parseInt(String.valueOf(iMonth))) + " " + iYear);

                                        updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_CALENDAR));

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
         *  Loop filter till end of Course
         *  Diary events.
         */
        for (int iCounter = 0; iCounter < arrayListCourseDataCopies.size(); iCounter++) {

            /**
             *Place NAME of Month for all events to set as Title bar when scroll down or up.
             */
            strNameOfMonth = arrayListCourseData.get(iCounter).getCourseEventDate();
            iMonthNum = Integer.parseInt(strNameOfMonth.substring((strNameOfMonth.indexOf("-")+1), strNameOfMonth.lastIndexOf("-")));

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

        tvMonthsName.setText(strNameOfMonth);
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
     * Implements this method to reset CALENDAR PREV, NEXT icon.
     */
    public static void resetMonthsNavigationIcons() {
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
    public static void setPreviousButton(boolean isEnable) {

        if (isEnable) {
            menuInstance.getItem(0).setIcon(ContextCompat.getDrawable(mActivityContext, R.mipmap.ic_arrow_left));
        } else {
            menuInstance.getItem(0).setIcon(ContextCompat.getDrawable(mActivityContext, R.mipmap.ic_arrow_left_blur));
        }
    }

    /**
     * Implements a method to ENABLE/DISABLE NEXT
     * MONTH arrow.
     */
    public static void setNextButton(boolean isEnable) {

        if (isEnable) {
            menuInstance.getItem(1).setIcon(ContextCompat.getDrawable(mActivityContext, R.mipmap.ic_arrow_right));
        } else {
            menuInstance.getItem(1).setIcon(ContextCompat.getDrawable(mActivityContext, R.mipmap.ic_arrow_right_blur));
        }
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, "44118078");
    }
}
