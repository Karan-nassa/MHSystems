package com.ucreate.mhsystems.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.activites.BaseActivity;
import com.ucreate.mhsystems.activites.DetailInvoiceActivity;
import com.ucreate.mhsystems.activites.MyAccountActivity;
import com.ucreate.mhsystems.adapter.BaseAdapter.FinanceAdapter;
import com.ucreate.mhsystems.adapter.BaseAdapter.FinanceSectionAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.util.API.WebServiceMethods;
import com.ucreate.mhsystems.models.MyAccountAPI;
import com.ucreate.mhsystems.models.MyAccountData;
import com.ucreate.mhsystems.models.MyAccountItems;
import com.ucreate.mhsystems.models.MyAccountJsonParams;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
    public static final String LOG_TAG = FinanceFragment.class.getSimpleName();
    ArrayList<MyAccountData> myAccountDatas = new ArrayList<>();

    String strInvoiceTitle, strInvoiceNo, strInvoiceValue, strInvoiceTax, strInvoiceDate, strInvoiceDesc, strInvoiceBillFrom,
            strInvoiceBillTo, strInvoiceTotalPayable, strInvoiceStatus;

    private boolean isClassVisible = false;

    private boolean isToday, isWeek, isOneMonth, isThreeMonths, isSixMonths, isOneYear, isFromStart;
    private boolean isFilterOpen;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;
    //    ListView lvFinance;
    TextView tvCreditBalance, tvCurrentInvoice, tvRecentTransaction;
    TextView tvLabelCardBalance, tvCardBalance;
    TextView tvLabelYourInvoice, tvYourInvoice;
    ImageView ivFilter;
    Intent mIntent;

    Button btShowAll;
    Button btToday, btWeek, btOneMonth, btThreeMonths, btSixMonths, btOneYear, btFromStart;
    Button btLastView;
    Typeface tpRobotoMedium, tpRobotoRegular;

    LinearLayout llBalanceGroup, llFilterGroup;

    //Instance of Transaction listview.
    ListView lvTransactionList;

    FinanceAdapter financeAdapter;

    FinanceSectionAdapter mFinanceAdapter;

    //List of type books this list will store type Book which is our data model
    private MyAccountAPI myAccountAPI;

    //Create instance of Model class CourseDiaryItems.
    MyAccountItems myAccountItems;
    MyAccountJsonParams myAccountJsonParams;

    /**
     * Declares the constant fields to display
     * INVOICE detail screen.
     */
    private View.OnClickListener mInvoiceDetailListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (myAccountItems.getMyAccountData().get(0).getCurrentBills().size() > 0) {
                getCurrentInvoiceData();

                /**
                 *  Navigate to Detail Invoice Screen.
                 */
                mIntent = new Intent(getActivity(), DetailInvoiceActivity.class);
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_TITLE, strInvoiceTitle);
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_NUMBER, strInvoiceNo);
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_VALUE, strInvoiceValue);
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_TAX, strInvoiceTax);
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_TOTAL_PAYABLE, strInvoiceTotalPayable);
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_DATE, strInvoiceDate);
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_DESCRIPTION, strInvoiceDesc);
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_BILL_FROM, /*formatDate(*/strInvoiceBillFrom)/*.replaceAll("/", "-"))*/;
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_BILL_TO, /*formatDate(*/strInvoiceBillTo)/*.replaceAll("/", "-"))*/;
                mIntent.putExtra(ApplicationGlobal.KEY_INVOICE_STATUS_STR, strInvoiceStatus);
                startActivity(mIntent);
            } else {
                ((BaseActivity) getActivity()).showAlertMessage(getResources().getString(R.string.error_no_data));
            }
        }
    };

    /**
     * Implements this method to update Filter view
     * Group.
     */
    private View.OnClickListener mFilterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btToday:
                    updateFilterUI((Button) view);
                    break;

                case R.id.btWeek:
                    updateFilterUI((Button) view);
                    break;

                case R.id.btOneMonth:
                    updateFilterUI((Button) view);
                    break;

                case R.id.btThreeMonths:
                    updateFilterUI((Button) view);
                    break;

                case R.id.btSixMonths:
                    updateFilterUI((Button) view);
                    break;

                case R.id.btOneYear:
                    updateFilterUI((Button) view);
                    break;

                case R.id.btFromStart:
                    updateFilterUI((Button) view);
                    break;
            }

            llFilterGroup.setVisibility(View.GONE);
            llBalanceGroup.setVisibility(View.VISIBLE);

            btLastView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_shape_white));
            btLastView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhiteffffff));

            isFilterOpen = false;

            btLastView = (Button) view;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_finance, container, false);

        initialzeViewResources();

        btLastView = btToday;

        //Set click events here.
        btToday.setOnClickListener(mFilterListener);
        btWeek.setOnClickListener(mFilterListener);
        btOneMonth.setOnClickListener(mFilterListener);
        btThreeMonths.setOnClickListener(mFilterListener);
        btSixMonths.setOnClickListener(mFilterListener);
        btOneYear.setOnClickListener(mFilterListener);
        btFromStart.setOnClickListener(mFilterListener);

        setFontTypeface();

        /*isClassVisible = true;
        if (isClassVisible) {
            callFinanceWebService();
            ((MyAccountActivity) getActivity()).updateFilterIcon(0);
        }*/

        setDefaultTransactions();

       /* tvCreditBalance = (TextView) viewRootFragment.findViewById(R.id.tvCreditBalance);
        tvCurrentInvoice = (TextView) viewRootFragment.findViewById(R.id.tvCurrentInvoice);
        tvRecentTransaction = (TextView) viewRootFragment.findViewById(R.id.tvRecentTransaction);

        btShowAll = (Button) viewRootFragment.findViewById(R.id.btShowAll);
        tpRobotoMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
        btShowAll.setTypeface(tpRobotoMedium);

        //Set click event handle here.
        btShowAll.setOnClickListener(mInvoiceDetailListener);
*/
        return viewRootFragment;
    }

    /**
     * Implements a method to show Filter view.
     */
    public void updateFilterControl() {
        if (isFilterOpen) {
            isFilterOpen = false;
        } else {
            llFilterGroup.setVisibility(View.VISIBLE);
            llBalanceGroup.setVisibility(View.GONE);
            isFilterOpen = true;
        }
    }

    /**
     * I
     */
    private void setDefaultTransactions() {

        //Initialize the Finance adapter.
        financeAdapter = new FinanceAdapter(getActivity());

        for (int iCounter = 0; iCounter < 4; iCounter++) {

            switch (iCounter) {
                case 0:
                    financeAdapter.addSectionHeaderItem("Today");
                    break;

                case 2:
                    financeAdapter.addSectionHeaderItem("Last week");
                    break;
            }
            financeAdapter.addItem("Pint of Carslberg");
        }

        lvTransactionList.setAdapter(financeAdapter);
        financeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser /*&& isClassVisible*/) {
            callFinanceWebService();

            ((MyAccountActivity) getActivity()).updateFilterIcon(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //Check Internet connection and hit web service only on first time.
        isClassVisible = true;
        if (isClassVisible) {
            requestFinanceService();
        }
    }

    /**
     * Implements this method to initialize all
     * view resources.
     */
    private void initialzeViewResources() {
        btToday = (Button) viewRootFragment.findViewById(R.id.btToday);
        btWeek = (Button) viewRootFragment.findViewById(R.id.btWeek);
        btOneMonth = (Button) viewRootFragment.findViewById(R.id.btOneMonth);
        btThreeMonths = (Button) viewRootFragment.findViewById(R.id.btThreeMonths);
        btSixMonths = (Button) viewRootFragment.findViewById(R.id.btSixMonths);
        btOneYear = (Button) viewRootFragment.findViewById(R.id.btOneYear);
        btFromStart = (Button) viewRootFragment.findViewById(R.id.btFromStart);

        tvLabelCardBalance = (TextView) viewRootFragment.findViewById(R.id.tvLabelCardBalance);
        tvCardBalance = (TextView) viewRootFragment.findViewById(R.id.tvCardBalance);
        tvLabelYourInvoice = (TextView) viewRootFragment.findViewById(R.id.tvLabelYourInvoice);
        tvYourInvoice = (TextView) viewRootFragment.findViewById(R.id.tvYourInvoice);

        llBalanceGroup = (LinearLayout) viewRootFragment.findViewById(R.id.llBalanceGroup);
        llFilterGroup = (LinearLayout) viewRootFragment.findViewById(R.id.llFilterGroup);

        ivFilter = (ImageView) viewRootFragment.findViewById(R.id.ivFilter);

        lvTransactionList = (ListView) viewRootFragment.findViewById(R.id.lvTransactionList);
    }

    /**
     * Implements a method to update FILTER buttons according selection. Set
     * as unselected if isSelected TRUE and wise-versa.
     *
     * @param btView     : Instance of View to update background.
     * @param isSelected : TRUE means to selected Otherwise unselected.
     */
    private void updateFilterUI(Button btView/*, boolean isSelected*/) {

       /* if (isSelected) {*/
            btView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_shape_c0995b));
            btView.setTextColor(ContextCompat.getColor(getActivity(), R.color.color030303));
       /* } else {
            btView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.border_shape_white));
            btView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhiteffffff));
        }*/
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
            ((MyAccountActivity) getActivity()).updateHasInternetUI(true);
            // btShowAll.setVisibility(View.VISIBLE);
        } else {
            ((MyAccountActivity) getActivity()).updateHasInternetUI(false);
            //btShowAll.setVisibility(View.GONE);
        }
    }

    /**
     * Implement a method to hit MY ACCOUNT
     * web service to get balance.
     */
    public void requestFinanceService() {

        Log.e(LOG_TAG, "requestFinanceService");

        ((BaseActivity) getActivity()).showPleaseWait("Loading...");

        myAccountJsonParams = new MyAccountJsonParams();
        myAccountJsonParams.setCallid(ApplicationGlobal.TAG_GCLUB_CALL_ID);
        myAccountJsonParams.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        myAccountJsonParams.setMemberId(((MyAccountActivity) getActivity()).getMemberId());

        myAccountAPI = new MyAccountAPI((((MyAccountActivity) getActivity()).getClientId()), "AccountDetails", myAccountJsonParams, ApplicationGlobal.TAG_GCLUB_WEBSERVICES, ApplicationGlobal.TAG_GCLUB_MEMBERS);

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
                // Log.e(LOG_TAG, "RetrofitError : " + error);
                ((BaseActivity) getActivity()).hideProgress();
                //((BaseActivity) getActivity()).showAlertMessage("" + error);
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

                    tvCreditBalance.setText(myAccountItems.getMyAccountData().get(0).getMemBalance().get(0).getCrnSymbolStr()
                            + myAccountItems.getMyAccountData().get(0).getMemBalance().get(0).getValueStr());

                    //Get INVOICE number.
                    strInvoiceTitle = "INV/" + myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getStrInvoiceDate()
                            + "/" + myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getInvoiceNo();

                    tvCurrentInvoice.setText(myAccountItems.getMyAccountData().get(0).getMemBalance().get(0).getCrnSymbolStr() + myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getTotalPayable() + " - " + strInvoiceTitle);

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

    /**
     * Implements a method to get all CURRENT INVOICE data
     * to pass for DetailInvoiceActivity.
     */
    private void getCurrentInvoiceData() {

        strInvoiceNo = myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getInvoiceNo();
        strInvoiceDate = /*formatDate(*/myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getStrInvoiceDate()/*).replaceAll("/", "-")*/;
        strInvoiceStatus = myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getInvoiceStatusStr();
        strInvoiceTotalPayable = "" + myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getTotalPayable();

        strInvoiceBillFrom = myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getBillParts().get(0).getLines().get(0).getStrBilledFrom();
        strInvoiceBillTo = myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getBillParts().get(0).getLines().get(0).getStrBilledTo();
        strInvoiceDesc = myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getBillParts().get(0).getLines().get(0).getDescription();
        strInvoiceValue = "" + myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getBillParts().get(0).getLines().get(0).getValueX();
        strInvoiceTax = "" + myAccountItems.getMyAccountData().get(0).getCurrentBills().get(0).getBillParts().get(0).getLines().get(0).getTax();
    }

    /**
     * Implements a method to set Font style.
     */
    private void setFontTypeface() {
        tpRobotoRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        tpRobotoMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");

        btToday.setTypeface(tpRobotoMedium);
        btWeek.setTypeface(tpRobotoMedium);
        btOneMonth.setTypeface(tpRobotoMedium);
        btThreeMonths.setTypeface(tpRobotoMedium);
        btSixMonths.setTypeface(tpRobotoMedium);
        btOneYear.setTypeface(tpRobotoMedium);
        btFromStart.setTypeface(tpRobotoMedium);

        tvLabelCardBalance.setTypeface(tpRobotoRegular);
        tvLabelYourInvoice.setTypeface(tpRobotoRegular);
        tvCardBalance.setTypeface(tpRobotoRegular);
        tvYourInvoice.setTypeface(tpRobotoRegular);
    }
}