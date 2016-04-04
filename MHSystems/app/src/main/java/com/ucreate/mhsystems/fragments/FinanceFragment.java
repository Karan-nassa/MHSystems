package com.ucreate.mhsystems.fragments;

/**
 * Created by karan@ucreate.co.in to load and display
 * <br>NEWS
 * <br>tabs content on 12/23/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.CompetitionsAdapter;
import com.ucreate.mhsystems.adapter.BaseAdapter.FinanceSectionAdapter;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.utils.API.WebServiceMethods;
import com.ucreate.mhsystems.utils.pojo.MyAccountAPI;
import com.ucreate.mhsystems.utils.pojo.MyAccountData;
import com.ucreate.mhsystems.utils.pojo.MyAccountItems;
import com.ucreate.mhsystems.utils.pojo.MyAccountJsonParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class FinanceFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = FinanceFragment.class.getSimpleName();
    ArrayList<MyAccountData> myAccountDatas = new ArrayList<>();

    private boolean isSwipeVisible = false;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View mRootView;
    //    ListView lvFinance;
    TextView tvCreditBalance, tvCurrentInvoice;

    FinanceSectionAdapter mFinanceAdapter;

    //Create instance of Model class CourseDiaryItems.
    MyAccountItems myAccountItems;
    MyAccountJsonParams myAccountJsonParams;

    //List of type books this list will store type Book which is our data model
    private MyAccountAPI myAccountAPI;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_finance, container, false);

//        lvFinance = (ListView) mRootView.findViewById(R.id.lvFinance);

        tvCreditBalance = (TextView) mRootView.findViewById(R.id.tvCreditBalance);
        tvCurrentInvoice = (TextView) mRootView.findViewById(R.id.tvCurrentInvoice);

        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            callFinanceWebService();
        }
    }

    /**
     * Implements a method to call News web service either call
     * initially or call from onSwipeRefresh.
     */
    private void callFinanceWebService() {
        /**
         *  Check internet connection before hitting server request.
         */
        if (((BaseActivity) getActivity()).isOnline(getActivity())) {
            //Method to hit Squads API.
            requestFinanceService();
        } else {
            ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_internet));
        }
    }

    /**
     * Implement a method to hit MY ACCOUNT
     * web service to get balance.
     */
    public void requestFinanceService() {

        if (!isSwipeVisible) {
            ((BaseActivity) getActivity()).showPleaseWait("Loading...");
        }

        myAccountJsonParams = new MyAccountJsonParams();
        myAccountJsonParams.setCallid("1457589857009");
        myAccountJsonParams.setVersion(1);
        myAccountJsonParams.setMemberId(10782);

        myAccountAPI = new MyAccountAPI(44118078, "AccountDetails", myAccountJsonParams, "ACCOUNT", "Members");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getMyAccount(myAccountAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                ((BaseActivity) getActivity()).hideProgress();

                ((BaseActivity) getActivity()).showAlertMessage("" + error);
            }
        });

    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<MyAccountItems>() {
        }.getType();
        myAccountItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        myAccountDatas.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (myAccountItems.getMessage().equalsIgnoreCase("Success")) {

                myAccountDatas.addAll(myAccountItems.getMyAccountData());

                if (myAccountDatas.size() == 0) {
                    ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
                } else {
                    Log.e("SIZE getMemBalance:", "" + myAccountItems.getMyAccountData().get(0).getMemBalance().size());
                    Log.e("SIZE getCurrentBills:", "" + myAccountItems.getMyAccountData().get(0).getCurrentBills().size());

                    tvCreditBalance.setText(myAccountItems.getMyAccountData().get(0).getMemBalance().get(0).getCrnSymbolStr()
                            + " " + myAccountItems.getMyAccountData().get(0).getMemBalance().get(0).getValueStr());

                    tvCurrentInvoice.setText("\u00a3"+"60 - INV/"+formatDate(myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getInvoiceDate())
                            + myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getInvoiceNo());

                    //mFinanceAdapter = new FinanceSectionAdapter(getActivity(), myAccountDatas);
                    // lvFinance.setAdapter(mFinanceAdapter);

                    Log.e(LOG_TAG, "arrayListCourseData : " + myAccountDatas.size());
                }
            } else {
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(myAccountItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();
    }

    private String formatDate(String invoiceDate) {

        String strDate =(invoiceDate.substring(0, invoiceDate.lastIndexOf("T")));

        strDate = strDate.replaceAll("-","/");

        Log.e("DATE",strDate);

        return strDate;
    }


    @Override
    public void onRefresh() {
        isSwipeVisible = true;
        callFinanceWebService();
    }
}