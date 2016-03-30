package com.ucreate.mhsystems.activites;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.fragments.CalendarPickerFragment;
import com.ucreate.mhsystems.fragments.CompetitionsTabFragment;
import com.ucreate.mhsystems.fragments.CourseDairyTabFragment;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryDataCopy;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompetitionsActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/

    public static final String LOG_TAG = CompetitionsActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.toolBarComp)
    Toolbar toolBarComp;

    @Bind(R.id.cdlCompetitions)
    CoordinatorLayout cdlCompetitions;

    @Bind(R.id.llMonthTitleComp)
    LinearLayout llMonthTitleComp;

    @Bind(R.id.tvMonthNameComp)
    TextView tvMonthNameComp;

    //Create instance of  {@link Calendar} class.
    public static Calendar mCalendarInstance;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static String strDate, strCurrentDate;
    public static int iMonth, iCurrentMonth;
    public static int iYear, iCurrentYear;

    public static int iNumOfDays;

    public String strDateFrom; //Start date.
    public String strDateTo; //End date.

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);

        //Initialize view resources.
        ButterKnife.bind(this);

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
        iNumOfDays = CompetitionsActivity.mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        //Load Default fragment of COURSE DIARY.
        updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_NOTHING));

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);

        //When user want to Select date from CALENDAR.
        llMonthTitleComp.setOnClickListener(mCalendarListener);
    }

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

                                if (tMonthofYear > iCurrentMonth) {

                                    //  String monthname = new DateFormatSymbols().getMonths()[monthOfYear];

                                    iYear = year;
                                    iMonth = tMonthofYear;
                                    strDate = "" + dayOfMonth;

                                    iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                                    updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                                } else if(tMonthofYear == iCurrentMonth) {

                                    if (dayOfMonth >= Integer.parseInt(strCurrentDate)) {

                                        //    String monthname = new DateFormatSymbols().getMonths()[monthOfYear];

                                        iYear = year;
                                        iMonth = tMonthofYear;
                                        strDate = "" + dayOfMonth;

                                        getNumberofDays();
//                                        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

                                        updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_CALENDAR));

                                    }else{
                                        showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                                    }
                                } else {
                                    showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                                }
                            } else {
                                showAlertMessage(getResources().getString(R.string.error_wrong_date_selection));
                            }
                        }
                    }, iYear, --iMonth, Integer.parseInt(strDate));
            dpd.show();

            //Set Minimum or hide dates of PREVIOUS dates of CALENDAR.
            //    dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }
    };

    /**
     * Implements a method to get TOTAL number of
     * DAYS in selected MONTH.
     */
    public static void getNumberofDays() {
        CompetitionsActivity.mCalendarInstance.set(Calendar.YEAR, CompetitionsActivity.iYear);
        CompetitionsActivity.mCalendarInstance.set(Calendar.MONTH, CompetitionsActivity.iMonth);

        CompetitionsActivity.iNumOfDays = CompetitionsActivity.mCalendarInstance.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Show snackBar message defined in BaseActivity.
     */
    public void showSnackMessage(String strSnackMessage) {
        Log.e("Snack:", strSnackMessage);
        showSnackBarMessages(cdlCompetitions, strSnackMessage);
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
     *                           <p>
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
     *                   <p>
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
        setSupportActionBar(toolBarComp);
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

        tvMonthNameComp.setText(strNameOfMonth);
    }

    /**
     * Create Menu Options
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_PREVIOUS_MONTH));
                break;

            case R.id.action_NextMonth:
                updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_NEXT_MONTH));
                break;

            case R.id.action_Today:
                updateFragment(new CompetitionsTabFragment(ApplicationGlobal.ACTION_TODAY));
                break;
        }

        //Commit and navigate to new fragment.
        tr.commit();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Implements a common method to update
     * Fragment.
     */
    public void updateFragment(Fragment mFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView, mFragment);
        fragmentTransaction.commit();
    }
}
