package com.ucreate.mhsystems.activites;

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

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public String strDate;
    public int iMonth;
    public int iYear;

    //To record total number of days.
    int iNumOfDays;

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

//            CalendarPickerFragment pickerFrag = new CalendarPickerFragment();
//            pickerFrag.setCallback(mFragmentCallback);
//
//            // Options
//            Pair<Boolean, SublimeOptions> optionsPair = getOptions();
//
//            if (!optionsPair.first) { // If options are not valid
//                Toast.makeText(CompetitionsActivity.this, "No pickers activated",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Valid options
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
//            pickerFrag.setArguments(bundle);
//
//            pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
//            pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
        }
    };


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

    /**
     * CALENDAR call listener implements
     * here.
     */
    CalendarPickerFragment.Callback mFragmentCallback = new CalendarPickerFragment.Callback() {
        @Override
        public void onCancelled() {
            Toast.makeText(CompetitionsActivity.this, "No date selected.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {

            Log.e("selectedDate:", "" + selectedDate.toString());
            Log.e("mHour:", "" + hourOfDay);
            Log.e("mMinute:", "" + minute);

            Toast.makeText(CompetitionsActivity.this, "Selected Date : " + selectedDate, Toast.LENGTH_LONG).show();
        }
    };

    Pair<Boolean, SublimeOptions> getOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;

        // if (cbDatePicker.isChecked()) {

        // }

        // if (cbTimePicker.isChecked()) {
        //   displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;
        //}
        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;


        // if (rbDatePicker.getVisibility() == View.VISIBLE && rbDatePicker.isChecked()) {

        // } else if (rbTimePicker.getVisibility() == View.VISIBLE && rbTimePicker.isChecked()) {
        //  options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
        // }
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);

   /* else if (rbRecurrencePicker.getVisibility() == View.VISIBLE && rbRecurrencePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
        }*/

        options.setDisplayOptions(displayOptions);


        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

       /* Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 3, 28);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 12, 31);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }
}
