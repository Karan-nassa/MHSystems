package com.mh.systems.teesside.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.teesside.R;
import com.mh.systems.teesside.ui.activites.TeeBookingDetailActivity;
import com.mh.systems.teesside.ui.activites.TeeTimeBookingActivity;
import com.mh.systems.teesside.ui.adapter.RecyclerAdapter.TeeBookingRecyclerAdapter;
import com.mh.systems.teesside.utils.constants.ApplicationGlobal;
import com.mh.systems.teesside.web.api.WebAPI;
import com.mh.systems.teesside.web.api.WebServiceMethods;
import com.mh.systems.teesside.web.models.teetimebooking.getdaydata.AJsonParamsGetDayData;
import com.mh.systems.teesside.web.models.teetimebooking.getdaydata.GetDayDataAPI;
import com.mh.systems.teesside.web.models.teetimebooking.getdaydata.GetDayDataResponse;
import com.mh.systems.teesside.web.models.teetimebooking.getdaydata.Slot;
import com.mh.systems.teesside.web.models.teetimebooking.getmonthdata.AJsonParamsGetMonthData;
import com.mh.systems.teesside.web.models.teetimebooking.getmonthdata.Day;
import com.mh.systems.teesside.web.models.teetimebooking.getmonthdata.GetMonthDataAPI;
import com.mh.systems.teesside.web.models.teetimebooking.getmonthdata.GetMonthDataResponse;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import static com.mh.systems.teesside.ui.activites.TeeTimeBookingActivity.iSelectedMonth;
import static com.mh.systems.teesside.ui.activites.TeeTimeBookingActivity.iSelectedYear;


public class ShowMonthViewFragment extends Fragment {

    private final String LOG_TAG = ShowMonthViewFragment.class.getSimpleName();

    private final String TAG_MONTH_DATA = "GETMONTHDATA";
    private final String TAG_DAY_DATA = "GETDAYDATA";

    boolean isCalendarEnable = true;

    int iLastBookableMonth, iLastBookableYear;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;

    @Bind(R.id.ivLeftArrow)
    ImageView ivLeftArrow;

    @Bind(R.id.ivRightArrow)
    ImageView ivRightArrow;

    @Bind(R.id.tvMonthTitle)
    TextView tvMonthTitle;

    @Bind(R.id.llCalendar)
    LinearLayout llCalendar;

    @Bind(R.id.llDayView)
    LinearLayout llDayView;

    @Bind(R.id.rvMyBookingList)
    RecyclerView rvMyBookingList;

    @Bind(R.id.llCalendarView)
    LinearLayout llCalendarView;

    @Bind(R.id.ivTeeCalendar)
    ImageView ivTeeCalendar;

    Intent callIntent = null;

    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;

    private GetMonthDataAPI mGetMonthDataAPI;
    private AJsonParamsGetMonthData aJsonParamsGetMonthData;

    private GetMonthDataResponse mGetMonthDataResponse;

    private GetDayDataAPI mGetDayDataAPI;
    private AJsonParamsGetDayData aJsonParamsGetDayData;

    private GetDayDataResponse mGetDayDataResponse;

    private ArrayList<String> mSelectedDates = new ArrayList<>();

    final SimpleDateFormat formatterMonthYear = new SimpleDateFormat("MMMM yyyy");
    final SimpleDateFormat formatterDate = new SimpleDateFormat("MM/dd/yyyy");

    private View.OnClickListener mCalendarIconListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (caldroidFragment != null) {
                initCalendar();
                /*if (isCalendarEnable) {
                    showBookingList();
                }*/
            }
        }
    };

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewRootFragment = inflater.inflate(R.layout.fragment_show_monthview, container, false);

        //Initialize butterKnife.
        ButterKnife.bind(this, viewRootFragment);

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
        //caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {

            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, iSelectedMonth/*cal.get(Calendar.MONTH) + 1*/);
            args.putInt(CaldroidFragment.YEAR, iSelectedYear/*cal.get(Calendar.YEAR)*/);
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
                    CaldroidFragment.MONDAY); // Monday

            caldroidFragment.setArguments(args);

            ivLeftArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int iCurrentMonth  = (cal.get(Calendar.MONTH) + 1);
                    int iYear  = cal.get(Calendar.YEAR);
                    if (caldroidFragment.getMonth() > iCurrentMonth || caldroidFragment.getYear() > iYear) {
                        caldroidFragment.prevMonth();
                        iSelectedMonth = caldroidFragment.getMonth();
                        iSelectedYear = caldroidFragment.getYear();
                    }

                    updateNavIcons(iCurrentMonth, iYear);
                }
            });

            ivRightArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar cal = Calendar.getInstance();
                    int iCurrentMonth = (cal.get(Calendar.MONTH) + 1);
                    int iYear  = cal.get(Calendar.YEAR);

                    if (caldroidFragment.getMonth() < iLastBookableMonth || caldroidFragment.getYear() < iLastBookableYear) {
                        caldroidFragment.nextMonth();
                        iSelectedMonth = caldroidFragment.getMonth();
                        iSelectedYear = caldroidFragment.getYear();
                    }

                    updateNavIcons(iCurrentMonth, iYear);
                }
            });

            llCalendarView.setOnClickListener(mCalendarIconListener);
            ivTeeCalendar.setOnClickListener(mCalendarIconListener);
        }

        // Attach to the activity
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.llCalendar, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {

                /**
                 *  Check internet connection before hitting server request.
                 */
                if (((TeeTimeBookingActivity) getActivity()).isOnline(getActivity())) {
                    requestTeeBookingDataService(formatterDate.format(date), TAG_DAY_DATA);
                } else {
                    ((TeeTimeBookingActivity) getActivity()).showAlertMessage(getString(R.string.error_no_connection));
                }
            }

            @Override
            public void onChangeMonth(int month, int year, Date date) {

                tvMonthTitle.setText(formatterMonthYear.format(date));

                /**
                 *  Check internet connection before hitting server request.
                 */
                if (((TeeTimeBookingActivity) getActivity()).isOnline(getActivity())) {
                    requestTeeBookingDataService(formatterDate.format(date), TAG_MONTH_DATA);
                } else {
                    ((TeeTimeBookingActivity) getActivity()).showAlertMessage(getString(R.string.error_no_connection));
                }
            }

            @Override
            public void onLongClickDate(Date date, View view) {
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    //initCalendar();
                    updateNavIcons(iSelectedMonth, iSelectedYear);
                }
            }

            @Override
            public void onCalendarClicked(boolean visibility) {
                super.onCalendarClicked(visibility);

                if (!visibility) {
                    llDayView.setVisibility(View.GONE);
                    llCalendar.setVisibility(View.VISIBLE);
                }
            }
        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        return viewRootFragment;
    }

    private void updateNavIcons(int iCurrentMonth, int iCurrentYear) {
        if (caldroidFragment.getMonth() == iCurrentMonth
                && caldroidFragment.getYear() == iCurrentYear) {
            ivLeftArrow.setAlpha((float) 0.5);
        } else {
            ivLeftArrow.setAlpha((float) 1.0);
        }

        if (caldroidFragment.getMonth() == iLastBookableMonth
                && caldroidFragment.getYear() == iLastBookableYear) {
            ivRightArrow.setAlpha((float) 0.5);
        } else {
            ivRightArrow.setAlpha((float) 1.0);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            ((TeeTimeBookingActivity) getActivity()).setWhichTab(0);

            TeeTimeBookingActivity.shouldRefresh = true;
            ((TeeTimeBookingActivity) getActivity()).updateNoDataUI(true, 0);

            if(caldroidFragment != null) {
                initCalendar();
            }
        }
    }

    /**
     * Get Month data web service.
     */
    public void requestTeeBookingDataService(String strDate, final String strMethodName) {

        ((TeeTimeBookingActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsGetMonthData = new AJsonParamsGetMonthData();
        aJsonParamsGetMonthData.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsGetMonthData.setMemberId(getMemberId());
        aJsonParamsGetMonthData.setDatefrom(strDate);

        mGetMonthDataAPI = new GetMonthDataAPI(getClientId(),
                strMethodName,
                aJsonParamsGetMonthData,
                "MOTT",
                ApplicationGlobal.TAG_GCLUB_MEMBERS);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        api.GetMonthDataMOTT(mGetMonthDataAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                if (strMethodName.equals(TAG_MONTH_DATA)) {
                    updateMonthDataSuccess(jsonObject);
                } else {
                    updateDayDataSuccess(jsonObject);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                ((TeeTimeBookingActivity) getActivity()).hideProgress();
                ((TeeTimeBookingActivity) getActivity()).showAlertMessage(error.toString());
            }
        });
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    public String getMemberId() {
        return ((TeeTimeBookingActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return ((TeeTimeBookingActivity) getActivity()).loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateMonthDataSuccess(JsonObject jsonObject) {

        Log.e(LOG_TAG, "updateMonthDataSuccess : " + jsonObject.toString());

        Type type = new TypeToken<GetMonthDataResponse>() {
        }.getType();
        mGetMonthDataResponse = new Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             * Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (mGetMonthDataResponse.getMessage().equalsIgnoreCase("Success")) {

                String LastBookableDate = mGetMonthDataResponse.getData().getLastBookableDate();
                iLastBookableYear = Integer.parseInt(LastBookableDate.substring(0, LastBookableDate.indexOf('-')));
                iLastBookableMonth = Integer.parseInt(LastBookableDate.substring(LastBookableDate.indexOf('-') + 1,
                        LastBookableDate.lastIndexOf('-')));

                mSelectedDates.clear();
                List<Day> mDaysArr = mGetMonthDataResponse.getData().getDays();
                for (int iCount = 0; iCount < mDaysArr.size(); iCount++) {
                    if (!mDaysArr.get(iCount).getHasSlots() || mDaysArr.get(iCount).getOverQuota()) {
                        mSelectedDates.add(mDaysArr.get(iCount).getDate());
                    }
                  /*  if (mDaysArr.get(iCount).getHasSlots() && !mDaysArr.get(iCount).getOverQuota()) {
                        setCustomResourceForDates(iCount);
                    }*/
                }

                initCalendar();
                //showCalendarView();

            } else {
                ((TeeTimeBookingActivity) getActivity()).showAlertMessage(mGetMonthDataResponse.getMessage());
            }
            ((TeeTimeBookingActivity) getActivity()).hideProgress();
        } catch (Exception e) {
            ((TeeTimeBookingActivity) getActivity()).hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            ((TeeTimeBookingActivity) getActivity()).reportRollBarException(ShowMonthViewFragment.class.getSimpleName(), e.toString());
        }
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateDayDataSuccess(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<GetDayDataResponse>() {
        }.getType();
        mGetDayDataResponse = new Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             * Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (mGetDayDataResponse.getMessage().equalsIgnoreCase("Success")) {

                List<Slot> mSlotsList = mGetDayDataResponse.getGetDayData().getDays().get(0).getSlots();
                showBookingList(mSlotsList);

            } else {
                ((TeeTimeBookingActivity) getActivity()).showAlertMessage(mGetMonthDataResponse.getMessage());
            }
            ((TeeTimeBookingActivity) getActivity()).hideProgress();
        } catch (Exception e) {
            ((TeeTimeBookingActivity) getActivity()).hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            ((TeeTimeBookingActivity) getActivity()).reportRollBarException(ShowMonthViewFragment.class.getSimpleName(), e.toString());
        }
    }

    private void initCalendar() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, 0);
        Date minDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        int iNumOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.add(Calendar.DATE, iNumOfDaysInMonth);
        Date maxDate = cal.getTime();


               /* // Set selected dates
                // From Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 2);
                Date fromDate = cal.getTime();

                // To Date
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 3);
                Date toDate = cal.getTime();*/

        // Set disabled dates
               /* ArrayList<Date> disabledDates = new ArrayList<Date>();
                for (int i = 5; i < 8; i++) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, i);
                    disabledDates.add(cal.getTime());
                }*/

        // Customize
        caldroidFragment.setMinDate(minDate);
        // caldroidFragment.setMaxDate(maxDate);
        // caldroidFragment.setDisableDates(disabledDates);
        caldroidFragment.setDisableDatesFromString(mSelectedDates);
        //  caldroidFragment.setSelectedDates(fromDate, toDate);
        caldroidFragment.setShowNavigationArrows(true);//false
        caldroidFragment.setEnableSwipe(false);

        caldroidFragment.refreshView();

        // Move to date
        // cal = Calendar.getInstance();
        // cal.add(Calendar.MONTH, 12);
        // caldroidFragment.moveToDate(cal.getTime());

        llDayView.setVisibility(View.GONE);
        llCalendar.setVisibility(View.VISIBLE);

        ivTeeCalendar.setAlpha((float) 0.5);
        isCalendarEnable = true;
    }

    private void setCustomResourceForDates(int dayNo) {
        Calendar cal = Calendar.getInstance();

       /* // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();*/

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dayNo);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable green = new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark));
            // caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            // caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }

    private void showBookingList(List<Slot> mSlotsList) {

        isCalendarEnable = false;

        llDayView.setVisibility(View.VISIBLE);
        llCalendar.setVisibility(View.GONE);
        rvMyBookingList.setLayoutManager(new LinearLayoutManager(getActivity()));

        TeeBookingRecyclerAdapter teeBookingRecyclerAdapter =
                new TeeBookingRecyclerAdapter(getActivity(), mSlotsList, false);
        rvMyBookingList.setAdapter(teeBookingRecyclerAdapter);

        ivTeeCalendar.setAlpha((float) 1.0);
    }
}
