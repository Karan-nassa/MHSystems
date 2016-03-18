package com.ucreate.mhsystems.activites;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.fragments.CourseDairyTabFragment;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryDataCopy;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/

    public static final String LOG_TAG = CourseActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

    //Create instance to load current fragment selected from drawer.
    Fragment fragment;

    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.toolBar)
    Toolbar toolbar;

    @Bind(R.id.cdlCourse)
    CoordinatorLayout cdlCourse;

//    @Bind(R.id.ivToday)
//    ImageView ivToday;
//
//    @Bind(R.id.ivPreviousMonth)
//    ImageView ivPreviousMonth;
//
//    @Bind(R.id.ivNextMonth)
//    ImageView ivNextMonth;

    Calendar mCalendarInstance;

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

    /**
     * Today icon press handle here. When user tap on Today icon of COURSE
     * DIARY, then display content of CURRENT DATE only.
     */
    private View.OnClickListener mTodayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //setCalenderDates(true);

            Toast.makeText(CourseActivity.this, "Today pressed", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * PREVIOUS icon press handle here. When user tap on PREV icon of COURSE
     * DIARY, then display content of PREVIOUS MONTH from 1st date to last
     * day of Selected month.
     */
    private View.OnClickListener mPreviousMonthListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(CourseActivity.this, "Previous Month listener pressed", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * NEXT icon press handle here. When user tap on NEXT icon of COURSE
     * DIARY, then display content of NEXT MONTH from 1st date to last
     * day of Selected month.
     */
    private View.OnClickListener mNextMonthListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Toast.makeText(CourseActivity.this, "Next Month listener pressed", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_course);

        //Initialize view resources.
        ButterKnife.bind(this);

        //Let's first set up toolbar
        setupToolbar();

//        //Initialize the dates of CALENDER to display data according dates.
//        setCalenderDates(false); //FALSE means no call from TODAY icon pressed.

        //Load Default fragment of COURSE DIARY.
        updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_NOTHING));

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);
//        ivToday.setOnClickListener(mTodayListener);
//        ivPreviousMonth.setOnClickListener(mPreviousMonthListener);
//        ivNextMonth.setOnClickListener(mNextMonthListener);
    }

//    /**
//     * Implements a method to display calender
//     * instances.
//     */
//    private void setCalenderDates(boolean isTodayCall) {
//
//        //Initialize CALENDAR instance.
//        mCalendarInstance = Calendar.getInstance();
//
//        if (isTodayCall) {
//            strDate = "" + mCalendarInstance.get(Calendar.DATE);
//            iNumOfDays =  mCalendarInstance.get(Calendar.DATE);
//        } else {
//            strDate = "01";
//
//            //Get total number of days of selected month.
//            iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
//        }
//
//        iMonth = mCalendarInstance.get(Calendar.MONTH);
//        iYear = mCalendarInstance.get(Calendar.YEAR);
//
//        //Increment CALENDAR because MONTH start from 0.
//        iMonth++;
//
//
//        //FORMAT : MM-DD-YYYY
//        strDateFrom = strDate + "/" + iMonth + "/" + iYear;
//
//        //FORMAT : MM-DD-YYYY
//        strDateTo = "" + iNumOfDays + "/" + iMonth + "/" + iYear;
//
//        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
//        Log.e(LOG_TAG, "END DATE : " + strDateTo);
//
//        Log.e("DATA ", "DATE : " + strDate + " MONTH : " + iMonth + " YEAR : " + iYear + " NUM OF DAYS : " + iNumOfDays);
//
//        if(fragment instanceof NewCourseFragment){
//
//        }else if(fragment instanceof CourseFragmentTabsData) {
//            fragment.requestNewsService();
//        }
//    }

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

        //Used when Date format in slashes['/']. Example : dd/MM/yyyy
        //String strEventDate = strCourseEventDate.substring(strCourseEventDate.indexOf("/") + 1, strCourseEventDate.lastIndexOf("/"));

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

        TextView mTitle = (TextView) toolbar.findViewById(R.id.tvCourseSchedule);

        mTitle.setText(strNameOfMonth);
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
                updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_PREVIOUS_MONTH));
                break;

            case R.id.action_NextMonth:
                updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_NEXT_MONTH));
                break;

            case R.id.action_Today:
                updateFragment(new CourseDairyTabFragment(ApplicationGlobal.ACTION_TODAY));
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
