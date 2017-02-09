package com.mh.systems.york.activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.york.R;
import com.mh.systems.york.adapter.RecyclerAdapter.TopUpPriceListRecyclerAdapter;
import com.mh.systems.york.constants.ApplicationGlobal;
import com.mh.systems.york.models.TopUp.TopUpPriceListResponse;
import com.mh.systems.york.models.TopUp.TopUpPricesListAPI;
import com.mh.systems.york.models.TopUp.TopupList;
import com.mh.systems.york.web.api.WebServiceMethods;
import com.newrelic.com.google.gson.Gson;
import com.newrelic.com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Create {@link TopUpActivity} is used to implements the FSI
 * payment Gateway. Top up balance sheet coming from AWS Server.
 *
 * @author karan@ucreate.co.in
 */
public class TopUpActivity extends BaseActivity {

    private final String LOG_TAG = TopUpActivity.class.getSimpleName();

    private final int ACTION_MAKE_PAYMENT = 111;

    @Bind(R.id.tbTopUp)
    Toolbar tbTopUp;

    @Bind(R.id.tvCurrencySign)
    TextView tvCurrencySign;

    @Bind(R.id.etInputPrize)
    EditText etInputPrize;

    @Bind(R.id.rvCurrencyList)
    RecyclerView rvCurrencyList;

    @Bind(R.id.btMakePayment)
    Button btMakePayment;

    @Bind(R.id.tvYourBalance)
    TextView tvYourBalance;

    @Bind(R.id.llMainGroup)
    LinearLayout llMainGroup;

      /* ++ INTERNET CONNECTION PARAMETERS ++ */

    @Bind(R.id.inc_message_view)
    RelativeLayout inc_message_view;

    @Bind(R.id.ivMessageSymbol)
    ImageView ivMessageSymbol;

    @Bind(R.id.tvMessageTitle)
    TextView tvMessageTitle;

    @Bind(R.id.tvMessageDesc)
    TextView tvMessageDesc;

     /* -- INTERNET CONNECTION PARAMETERS -- */

    Intent intent;

    Typeface tfRobotoRegular;
    LinearLayoutManager linearLayoutManager;

    TopUpPriceListRecyclerAdapter topUpPriceListRecyclerAdapter;
    TopUpPricesListAPI topUpPricesListAPI;

    TopUpPriceListResponse topUpPriceListResponse;

    InputMethodManager inputMethodManager;

    ArrayList<TopupList> topUpPriceListDataList = new ArrayList<>();

    String strOutputPattern = "##.00";
    DecimalFormat decimalFormat = new DecimalFormat(strOutputPattern);

    int iMaxTopup = 0, iMinTopup = 0;
    float fTopUpPrize = 0;
    float fCardBalance;
    String strMinTopup, strMaxTopup;
    String strClosingBalance;

    /**
     * Implements this method to perform the {@link EditorInfo.IME_ACTION_DONE}
     * when user tap on tick icon in soft input keyboard.
     */
    private TextView.OnEditorActionListener mInputActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_DONE) {

                btMakePayment.performClick();
            }
            return false;
        }
    };

    /**
     * Implements this method to pass data to FSI payment gateway
     * for Top Up balance.
     */
    private View.OnClickListener mMakePaymentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(etInputPrize.getText().toString().length() > 0) {

                if (isOnline(TopUpActivity.this)) {

                    if (fTopUpPrize >= iMinTopup && fTopUpPrize <= iMaxTopup) {
                        intent = new Intent(TopUpActivity.this, MakePaymentWebActivity.class);
                        intent.putExtra("fTopUpPrize", fTopUpPrize);
                        intent.putExtra("fCardBalance", fCardBalance);
                        intent.putExtra("strCurrencySign", tvCurrencySign.getText().toString());
                        startActivityForResult(intent, ACTION_MAKE_PAYMENT);
                    } else {
                        showAlertMessage("Top Up range should remain between " + strMinTopup + " and " + strMaxTopup + ".");
                    }
                } else {
                    //showAlertMessage(getString(R.string.error_no_connection));
                    showAlertMessage(getString(R.string.error_no_internet));
                }
            }else{
                showAlertMessage(getString(R.string.error_enter_topup));
            }
        }
    };

    private TextWatcher mPrizeChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            etInputPrize.removeTextChangedListener(mPrizeChangeListener);

            fTopUpPrize = Float.valueOf(s.toString()).intValue();
            etInputPrize.setText(decimalFormat.format(fTopUpPrize));
            etInputPrize.setSelection(etInputPrize.getText().length() - 3);

            //Update price description label.
            updatePriceDecsription();

            //topUpPriceListRecyclerAdapter.markAsUnselected();

            etInputPrize.addTextChangedListener(mPrizeChangeListener);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        ButterKnife.bind(TopUpActivity.this);

        setSupportActionBar(tbTopUp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.text_title_topup));

        //Set Font style.
        setupFontStyle();

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Get Closing balance.
        strClosingBalance = getIntent().getExtras().getString("strClosingBalance");
        if (strClosingBalance.length() > 0) {
            tvYourBalance.setText((getString(R.string.text_title_your_balance)
                    + " " + strClosingBalance));

            fCardBalance = Float.parseFloat(strClosingBalance.substring(1, strClosingBalance.length()));
        }

        etInputPrize.setOnEditorActionListener(mInputActionListener);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCurrencyList.setLayoutManager(linearLayoutManager);

        //Initialize top up prices list Adapter.
        topUpPriceListRecyclerAdapter = new TopUpPriceListRecyclerAdapter(TopUpActivity.this, topUpPriceListDataList);
        rvCurrencyList.setAdapter(topUpPriceListRecyclerAdapter);

        btMakePayment.setOnClickListener(mMakePaymentListener);

        etInputPrize.addTextChangedListener(mPrizeChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isOnline(TopUpActivity.this)) {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
            llMainGroup.setVisibility(View.VISIBLE);
            requestTopUpPriceListService();
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
            llMainGroup.setVisibility(View.GONE);
        }

        etInputPrize.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * If PAYMENT status SUCCESS then navigate user to {@link com.mh.systems.york.fragments.FinanceFragment}
         * Otherwise, retain on this screen.
         */
        if (resultCode == ACTION_MAKE_PAYMENT) {
            try {
                boolean isPaymentSuccess = data.getExtras().getBoolean("Is_PAYMENT_SUCCESS");
                if (isPaymentSuccess) {
                    onBackPressed();
                }
            } catch (Exception exp) {
                Log.e(LOG_TAG, "" + exp.toString());
            }
        }
    }

    /**
     * Implement a method to hit Members Detail
     * web service to get response.
     */
    public void requestTopUpPriceListService() {

        showPleaseWait("Loading...");

        topUpPricesListAPI = new TopUpPricesListAPI();
        topUpPricesListAPI.setAClientId(getClientId());
        topUpPricesListAPI.setAMemberId(getMemberId());

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://staging.mhsystems.co.uk/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Creating an object of our api interface
        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        //Defining the method
        api.getTopUpPricesList(getClientId(),
                getMemberId()
                , new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, retrofit.client.Response response) {

                        updateSuccessResponse(jsonObject);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //you can handle the errors here
                        Log.e(LOG_TAG, "RetrofitError : " + error);
                        hideProgress();
                        //showAlertMessage("" + getResources().getString(R.string.error_please_retry));
                        showNoTopUpView(false);
                    }
                });
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    public String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new TypeToken<TopUpPriceListResponse>() {
        }.getType();
        topUpPriceListResponse = new Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (topUpPriceListResponse.getMessage().equalsIgnoreCase("Success")) {

                iMinTopup = topUpPriceListResponse.getData().getMinTopup();
                iMaxTopup = topUpPriceListResponse.getData().getMaxTopup();

                strMinTopup = topUpPriceListResponse.getData().getMinTopupStr();
                strMaxTopup = topUpPriceListResponse.getData().getMaxTopupStr();

                tvCurrencySign.setText(topUpPriceListResponse.getData().getCrnSym());


                if (topUpPriceListResponse.getData().getTopupList().size() > 0) {

                    showNoTopUpView(true);
                    topUpPriceListDataList.clear();

                    topUpPriceListDataList.addAll(topUpPriceListResponse.getData().getTopupList());
                    TopupList topupListOthers = new TopupList("Others", "");
                    topUpPriceListDataList.add(topupListOthers);
                    topUpPriceListRecyclerAdapter.notifyDataSetChanged();

                    rvCurrencyList.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (rvCurrencyList.findViewHolderForAdapterPosition(0) != null) {
                                rvCurrencyList.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.btTimeSlot).performClick();
                            }
                        }
                    }, 100);
                }
            } else {
                showNoTopUpView(false);
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            showNoTopUpView(false);
        }
    }

    /**
     * Implements this method to set Font style
     * programmatically.
     */
    private void setupFontStyle() {
        tfRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tvCurrencySign.setTypeface(tfRobotoRegular);
        etInputPrize.setTypeface(tfRobotoRegular);
    }

    /**
     * Implements this method to update Top Up
     * price.
     */
    public void updatePriceUI(int iPosition) {

        if (iPosition == (topUpPriceListDataList.size() - 1)) {
            etInputPrize.removeTextChangedListener(mPrizeChangeListener);
            etInputPrize.setText("");
            etInputPrize.addTextChangedListener(mPrizeChangeListener);

            etInputPrize.setEnabled(true);
            inputMethodManager.showSoftInput(etInputPrize, InputMethodManager.SHOW_IMPLICIT);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etInputPrize.getWindowToken(), 0);
            etInputPrize.removeTextChangedListener(mPrizeChangeListener);

            fTopUpPrize = Float.parseFloat(topUpPriceListDataList.get(iPosition).getValue());

            etInputPrize.setText(decimalFormat.format(fTopUpPrize));
            etInputPrize.setSelection(etInputPrize.getText().length());

            //Update price description label.
            updatePriceDecsription();
            etInputPrize.addTextChangedListener(mPrizeChangeListener);

            etInputPrize.setEnabled(false);
        }
    }

    /**
     * Implements this method to update Top
     * Up price description.
     */
    private void updatePriceDecsription() {
        tvYourBalance.setText((getString(R.string.text_title_your_balance)
                + " " + tvCurrencySign.getText().toString()
                + (fCardBalance + fTopUpPrize) + ""));
    }

    /**
     * Implements a method to show hint to user when 'NO TOP UP'
     * range found.
     *
     * @param hasData :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
     */
    public void showNoTopUpView(boolean hasData) {
        if (hasData) {
            llMainGroup.setVisibility(View.VISIBLE);
            inc_message_view.setVisibility(View.GONE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } else {
            llMainGroup.setVisibility(View.GONE);
            inc_message_view.setVisibility(View.VISIBLE);
            ivMessageSymbol.setImageResource(R.mipmap.ic_my_account);
            tvMessageTitle.setText(getResources().getString(R.string.error_no_top_up));
            tvMessageDesc.setText(getResources().getString(R.string.error_try_again));

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }
}
