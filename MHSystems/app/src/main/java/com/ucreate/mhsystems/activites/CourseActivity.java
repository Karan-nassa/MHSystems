package com.ucreate.mhsystems.activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.fragments.CourseDairyTabFragment;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryDataCopy;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseActivity extends BaseActivity {

    public static final String LOG_TAG = CourseActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

    //Create instance to load current fragment selected from drawer.
    Fragment fragment;

    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

    @Bind(R.id.ivToday)
    ImageView ivToday;

    @Bind(R.id.ivPreviousMonth)
    ImageView ivPreviousMonth;

    @Bind(R.id.ivNextMonth)
    ImageView ivNextMonth;

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

            setCalenderDates(true);

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

        //Initialize the dates of CALENDER to display data according dates.
        setCalenderDates(false); //FALSE means no call from TODAY icon pressed.

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = new CourseDairyTabFragment();
        fragmentTransaction.replace(R.id.containerView, fragment);
        fragmentTransaction.commit();

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);
        ivToday.setOnClickListener(mTodayListener);
        ivPreviousMonth.setOnClickListener(mPreviousMonthListener);
        ivNextMonth.setOnClickListener(mNextMonthListener);
    }

    /**
     * Implements a method to display calender
     * instances.
     */
    private void setCalenderDates(boolean isTodayCall) {

        //Initialize CALENDAR instance.
        mCalendarInstance = Calendar.getInstance();

        if (isTodayCall) {
            strDate = "" + mCalendarInstance.get(Calendar.DATE);
        } else {
            strDate = "01";
        }

        iMonth = mCalendarInstance.get(Calendar.MONTH);
        iYear = mCalendarInstance.get(Calendar.YEAR);

        //Increment CALENDAR because MONTH start from 0.
        iMonth++;

        //Get total number of days of selected month.
        iNumOfDays = mCalendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);

        //FORMAT : MM-DD-YYYY
        strDateFrom = strDate + "/" + iMonth + "/" + iYear;

        //FORMAT : MM-DD-YYYY
        strDateTo = "" + iNumOfDays + "/" + iMonth + "/" + iYear;

        Log.e(LOG_TAG, "START DATE : " + strDateFrom);
        Log.e(LOG_TAG, "END DATE : " + strDateTo);

        Log.e("DATA ", "DATE : " + strDate + " MONTH : " + iMonth + " YEAR : " + iYear + " NUM OF DAYS : " + iNumOfDays);
    }

    /**
     * Show snackBar message defined in BaseActivity.
     */
    public void showSnackMessage(String strSnackMessage) {
        Log.e("Snack:", strSnackMessage);
        //BaseActivity.showsn(cdlCourse, strSnackMessage);
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


}
