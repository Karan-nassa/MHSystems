package com.mh.systems.newross.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mh.systems.newross.R;
import com.mh.systems.newross.ui.activites.TeeTimeBookingActivity;
import com.mh.systems.newross.ui.adapter.RecyclerAdapter.MyBookingRecyclerAdapter;
import com.mh.systems.newross.utils.constants.ApplicationGlobal;
import com.mh.systems.newross.web.api.WebAPI;
import com.mh.systems.newross.web.api.WebServiceMethods;
import com.mh.systems.newross.web.models.teetimebooking.booking.Booking;
import com.mh.systems.newross.web.models.teetimebooking.booking.UpdateBookingResponse;
import com.mh.systems.newross.web.models.teetimebooking.getbookingdata.AJsonParamsGetBookingData;
import com.mh.systems.newross.web.models.teetimebooking.getbookingdata.GetBookingDataAPI;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Create this class to display Contact Us
 * functionality of this Golf Club.
 */
public class MyBookingsFragment extends Fragment {

    private final String LOG_TAG = MyBookingsFragment.class.getSimpleName();

    @Bind(R.id.rvMyBookingList)
    RecyclerView rvMyBookingList = null;

    GetBookingDataAPI getBookingDataAPI;
    AJsonParamsGetBookingData aJsonParamsGetBookingData;

    UpdateBookingResponse mUpdateBookingResponse;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewRootFragment = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        //Initialize butterKnife.
        ButterKnife.bind(this, viewRootFragment);

        rvMyBookingList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return viewRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

             ((TeeTimeBookingActivity) getActivity()).setWhichTab(1);

            ((TeeTimeBookingActivity) getActivity()).setFragmentInstance(new MyBookingsFragment());

            /**
             *  Check internet connection before hitting server request.
             */
            if (((TeeTimeBookingActivity) getActivity()).isOnline(getActivity())) {
                requestGetBookingDataService();

                //To Hide 'No Data FOUND' message
                ((TeeTimeBookingActivity) getActivity()).updateNoDataUI(true, 1);

            } else {
                ((TeeTimeBookingActivity) getActivity()).showAlertMessage(getString(R.string.error_no_connection));
            }

        }
    }

    /**
     * Get Booking data web service.
     */
    public void requestGetBookingDataService() {

        ((TeeTimeBookingActivity) getActivity()).showPleaseWait("Loading...");

        aJsonParamsGetBookingData = new AJsonParamsGetBookingData();
        aJsonParamsGetBookingData.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsGetBookingData.setMemberId(getMemberId());

        getBookingDataAPI = new GetBookingDataAPI(getClientId(),
                "GETBOOKINGDATA",
                aJsonParamsGetBookingData,
                "MOTT",
                ApplicationGlobal.TAG_GCLUB_MEMBERS);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        api.getBookingDataMOTT(getBookingDataAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                updateMakeBookingSuccess(jsonObject);
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
    private void updateMakeBookingSuccess(JsonObject jsonObject) {

        Log.e(LOG_TAG, "updateMakeBookingSuccess : " + jsonObject.toString());

        Type type = new TypeToken<UpdateBookingResponse>() {
        }.getType();
        mUpdateBookingResponse = new Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             * Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (mUpdateBookingResponse.getMessage().equalsIgnoreCase("Success")
                    && mUpdateBookingResponse.getData().getSuccess()) {

                List<Booking> mBookingList = mUpdateBookingResponse.getData().getBookings();
                MyBookingRecyclerAdapter myBookingRecyclerAdapter = new MyBookingRecyclerAdapter(
                        getActivity(),
                        mBookingList,
                        true,
                        mUpdateBookingResponse.getData().getFundsAvail());
                rvMyBookingList.setAdapter(myBookingRecyclerAdapter);
                if (mBookingList.size() > 0) {
                    ((TeeTimeBookingActivity) getActivity()).updateNoDataUI(true, 1);
                } else {
                    ((TeeTimeBookingActivity) getActivity()).updateNoDataUI(false, 1);
                }

            } else {
                ((TeeTimeBookingActivity) getActivity()).showAlertMessage(mUpdateBookingResponse.getData().getMessage());
            }
            ((TeeTimeBookingActivity) getActivity()).hideProgress();
        } catch (Exception e) {
            ((TeeTimeBookingActivity) getActivity()).hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            ((TeeTimeBookingActivity) getActivity()).reportRollBarException(MyBookingsFragment.class.getSimpleName(), e.toString());
        }
    }
}
