package com.mh.systems.sunningdale.ui.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.ui.activites.BaseActivity;
import com.mh.systems.sunningdale.ui.activites.TopUpActivity;
import com.mh.systems.sunningdale.ui.activites.YourAccountActivity;
import com.mh.systems.sunningdale.ui.adapter.RecyclerAdapter.FinanceRecycleAdapter;
import com.mh.systems.sunningdale.utils.constants.ApplicationGlobal;
import com.mh.systems.sunningdale.web.api.WebAPI;
import com.mh.systems.sunningdale.web.api.WebServiceMethods;
import com.mh.systems.sunningdale.web.models.FinanceAJsonParams;
import com.mh.systems.sunningdale.web.models.FinanceAPI;
import com.mh.systems.sunningdale.web.models.FinanceResultItems;
import com.mh.systems.sunningdale.web.models.TransactionListData;
import com.mh.systems.sunningdale.web.models.finance.FinanceFilter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by karan@ucreate.co.in to load and FINANCE
 * tab content on 12/23/2015.
 */
public class FinanceFragment extends Fragment {
    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public final String LOG_TAG = FinanceFragment.class.getSimpleName();
   // ArrayList<TransactionListData> transactionListDataArrayList = new ArrayList<>();

    /*++ Filter type for Today, 1 Week, 1 Month, 3 Months, 6 Months, 1 Year or From Start++*/
    int iFilterType = 2; //By Default 1 month will be selected.
    String strClosingBalance = "";

    private boolean isClassVisible = false;

    private boolean isToday, isWeek, isOneMonth, isThreeMonths, isSixMonths, isOneYear, isFromStart;
    private boolean isFilterOpen;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;
    TextView tvLabelCardBalance, tvCardBalance, tvDateHeading;
    TextView tvLabelYourInvoice, tvYourInvoice;
    TextView tvAmount, tvBalance;
    ImageView ivFilter;
    Intent intent;
    Button btTopUp;

    Typeface tpRobotoMedium, tpRobotoRegular;

    //Instance of Transaction listview.
    //ListView lvTransactionList;

    //FinanceAdapter financeAdapter;

    //List of type books this list will store type Book which is our data model
    private FinanceAPI financeAPI;
    FinanceAJsonParams myAccountJsonParams;

    //Create instance of Model class CourseDiaryItems.
    FinanceResultItems financeResultItems;

    LinearLayout llFinanceGroup;

    ArrayList<FinanceFilter> financeFilterArrayList = new ArrayList<>();
    List<TransactionListData> TransactionList = new ArrayList<>();
    RecyclerView rvTransactionList;
    FinanceRecycleAdapter financeRecycleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_finance, container, false);

        initViewResources();

        setFontTypeface();

//        financeAdapter = new FinanceAdapter(getActivity(), transactionListDataArrayList);
//        lvTransactionList.setAdapter(financeAdapter);

        financeRecycleAdapter = new FinanceRecycleAdapter(financeFilterArrayList, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);

        rvTransactionList.setLayoutManager(linearLayoutManager);
        rvTransactionList.setItemAnimator(new DefaultItemAnimator());
        rvTransactionList.setAdapter(financeRecycleAdapter);

        //lvTransactionList.setOnItemClickListener(mFinanceListListener);
        btTopUp.setOnClickListener(mTopUpListener);

        return viewRootFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            callFinanceWebService();
            ((YourAccountActivity) getActivity()).updateFilterIcon(0);
            ((YourAccountActivity) getActivity()).setiOpenTabPosition(2);

            YourAccountActivity.isRefreshEnable = false;
        }
    }

    /**
     * Implements a method to show Filter view.
     **/
    public void updateFilterControl(int iFilterType) {
        this.iFilterType = iFilterType;
        callFinanceWebService();
    }


//    public AdapterView.OnItemClickListener mFinanceListListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            intent = new Intent(getActivity(), FinanceDetailWebActivity.class);
//            intent.putExtra("IsTopup", transactionListDataArrayList.get(position).getIsTopup());
//            intent.putExtra("iTransactionId", transactionListDataArrayList.get(position).getTransactionId());
//            intent.putExtra("strMemberId", ((YourAccountActivity) getActivity()).getMemberId());
//            intent.putExtra("titleOfScreen", transactionListDataArrayList.get(position).getTitle());
//            startActivity(intent);
//        }
//    };

    public View.OnClickListener mTopUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (financeResultItems != null) {
                intent = new Intent(getActivity(), TopUpActivity.class);
                intent.putExtra("strClosingBalance", strClosingBalance);
                startActivity(intent);
            }
        }
    };

    /**
     * Implements this method to initialize all
     * view resources.
     */
    private void initViewResources() {
        tvCardBalance = (TextView) viewRootFragment.findViewById(R.id.tvCardBalance);
        tvDateHeading = (TextView) viewRootFragment.findViewById(R.id.tvDateHeading);

        tvLabelCardBalance = (TextView) viewRootFragment.findViewById(R.id.tvLabelCardBalance);
        tvLabelYourInvoice = (TextView) viewRootFragment.findViewById(R.id.tvLabelYourInvoice);
        tvYourInvoice = (TextView) viewRootFragment.findViewById(R.id.tvYourInvoice);

        tvAmount = (TextView) viewRootFragment.findViewById(R.id.tvAmount);
        tvBalance = (TextView) viewRootFragment.findViewById(R.id.tvBalance);

        ivFilter = (ImageView) viewRootFragment.findViewById(R.id.ivFilter);

        rvTransactionList = (RecyclerView) viewRootFragment.findViewById(R.id.rvTransactionList);

        llFinanceGroup = (LinearLayout) viewRootFragment.findViewById(R.id.llFinanceGroup);

        btTopUp = (Button) viewRootFragment.findViewById(R.id.btTopUp);
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
            requestFinanceService();
        }
    }

    /**
     * Implement a method to hit MY ACCOUNT
     * web service to get balance.
     */
    private void requestFinanceService() {

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        myAccountJsonParams = new FinanceAJsonParams();
        myAccountJsonParams.setCallid(ApplicationGlobal.TAG_NEW_GCLUB_CALL_ID);
        myAccountJsonParams.setDateRange(iFilterType);
        myAccountJsonParams.setMemberId(((YourAccountActivity) getActivity()).getMemberId());

        financeAPI = new FinanceAPI((((YourAccountActivity) getActivity()).getClientId()), "GetAccStatement", myAccountJsonParams, "TRANSACTION", ApplicationGlobal.TAG_GCLUB_MEMBERS);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getFinanceDetail(financeAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {

                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                ((BaseActivity) getActivity()).hideProgress();
            }
        });
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<FinanceResultItems>() {
        }.getType();
        financeResultItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        //Clear array list before inserting items.
        //transactionListDataArrayList.clear();
        financeFilterArrayList.clear();
        TransactionList.clear();

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (financeResultItems.getMessage().equalsIgnoreCase("Success")) {

                TransactionList = financeResultItems.getData().getTransactionList();
                String strLastDate = "";

                for (int iCount = 0; iCount < TransactionList.size(); iCount++) {

                    if(!strLastDate.equals(TransactionList.get(iCount).getDateStr())){
                        financeFilterArrayList.add(new FinanceFilter(FinanceFilter.TYPE_DATE, TransactionList.get(iCount).getDateStr(),null));
                        strLastDate = TransactionList.get(iCount).getDateStr();
                    }
                    financeFilterArrayList.add(new FinanceFilter(FinanceFilter.TYPE_DATA, "", TransactionList.get(iCount)));
                }

                //transactionListDataArrayList.addAll(financeResultItems.getData().getTransactionList());

                if (financeFilterArrayList.size() == 0) {
                    ((BaseActivity) getActivity()).showAlertMessage("No Transaction Found.");
                }

                financeRecycleAdapter.notifyDataSetChanged();

                setTransactionListTitle();

                strClosingBalance = financeResultItems.getData().getClosingBalance();
                tvCardBalance.setText(strClosingBalance);

            } else {
                //If web service not respond in any case.
                ((BaseActivity) getActivity()).showAlertMessage(financeResultItems.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e.getMessage());
            ((YourAccountActivity) getActivity()).reportRollBarException(FinanceFragment.class.getSimpleName(), e.toString());
        }

        //Dismiss progress dialog.
        ((BaseActivity) getActivity()).hideProgress();
    }

    /**
     * Implements a method to set Transaction filter type.
     */
    private void setTransactionListTitle() {
        switch (iFilterType) {
            case 0:
                tvDateHeading.setText("Today");
                break;

            case 1:
                tvDateHeading.setText("1 week");
                break;

            case 2:
                tvDateHeading.setText("1 month");
                break;

            case 3:
                tvDateHeading.setText("3 months");
                break;

            case 4:
                tvDateHeading.setText("6 months");
                break;

            case 5:
                tvDateHeading.setText("1 year");
                break;

            case 6:
                tvDateHeading.setText("From Start");
                break;
        }

    }

    /**
     * Implements a method to set Font style.
     */
    private void setFontTypeface() {
        tpRobotoRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        tpRobotoMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");

        tvLabelCardBalance.setTypeface(tpRobotoRegular);
        tvLabelYourInvoice.setTypeface(tpRobotoRegular);
        tvCardBalance.setTypeface(tpRobotoMedium);
        tvYourInvoice.setTypeface(tpRobotoMedium);

        tvDateHeading.setTypeface(tpRobotoMedium);
        tvAmount.setTypeface(tpRobotoMedium);
        tvBalance.setTypeface(tpRobotoMedium);
    }
}