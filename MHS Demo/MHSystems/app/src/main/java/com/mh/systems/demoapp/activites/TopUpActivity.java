package com.mh.systems.demoapp.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.adapter.RecyclerAdapter.TopUpPriceListRecyclerAdapter;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.models.TopUp.TopUpPriceListResponse;
import com.mh.systems.demoapp.models.TopUp.TopUpPricesListAPI;
import com.mh.systems.demoapp.models.TopUp.TopupList;
import com.mh.systems.demoapp.util.API.WebServiceMethods;
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

    Intent intent;

    Typeface tfRobotoRegular;
    LinearLayoutManager linearLayoutManager;

    TopUpPriceListRecyclerAdapter topUpPriceListRecyclerAdapter;
    TopUpPricesListAPI topUpPricesListAPI;

    TopUpPriceListResponse topUpPriceListResponse;

    ArrayList<TopupList> topUpPriceListDataList = new ArrayList<>();

    String strOutputPattern = "##.00";
    DecimalFormat decimalFormat = new DecimalFormat(strOutputPattern);

    int iMaxTopup, iMinTopup, iTopUpPrize = 0;
    int iCardBalance;
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

    private View.OnClickListener mMakePaymentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (iTopUpPrize >= iMinTopup && iTopUpPrize <= iMaxTopup) {
                intent = new Intent(TopUpActivity.this, MakePaymentWebActivity.class);
                intent.putExtra("iTopUpPrize", iTopUpPrize);
                startActivity(intent);
            } else {
                showAlertMessage("Top Up range should remain between " + strMinTopup + " and " + strMaxTopup + ".");
            }
        }
    };

    private TextWatcher mPrizeChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Log.e(LOG_TAG, "beforeTextChanged : " + s.toString());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Log.e(LOG_TAG, "onTextChanged : " + s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

            etInputPrize.removeTextChangedListener(mPrizeChangeListener);

            iTopUpPrize = Float.valueOf(s.toString()).intValue();
            etInputPrize.setText(decimalFormat.format(iTopUpPrize));
            etInputPrize.setSelection(etInputPrize.getText().length() - 3);

            //Update price description label.
            updatePriceDecsription();

            topUpPriceListRecyclerAdapter.markAsUnselected();

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

        //Get Closing balance.
        strClosingBalance = getIntent().getExtras().getString("strClosingBalance");
        if (strClosingBalance.length() > 0) {
            tvYourBalance.setText((getString(R.string.text_title_your_balance)
                    + " " + strClosingBalance + ".00"));

            iCardBalance = Integer.parseInt(strClosingBalance.substring(1, strClosingBalance.length()));
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
            requestTopUpPriceListService();
        } else {
            showAlertMessage(getString(R.string.error_no_connection));
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
                        showAlertMessage("" + getResources().getString(R.string.error_please_retry));
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

                    rvCurrencyList.setVisibility(View.VISIBLE);
                    topUpPriceListDataList.clear();

                    topUpPriceListDataList.addAll(topUpPriceListResponse.getData().getTopupList());
                    topUpPriceListRecyclerAdapter.notifyDataSetChanged();

                    rvCurrencyList.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rvCurrencyList.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.btTimeSlot).performClick();
                        }
                    }, 100);
                }
            } else {
                showAlertMessage(topUpPriceListResponse.getMessage());
                rvCurrencyList.setVisibility(View.GONE);//Hide if Topup List empty.
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
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

        etInputPrize.removeTextChangedListener(mPrizeChangeListener);

        iTopUpPrize = Integer.parseInt(topUpPriceListDataList.get(iPosition).getValue());

        etInputPrize.setText(decimalFormat.format(iTopUpPrize));
        etInputPrize.setSelection(etInputPrize.getText().length());

        //Update price description label.
        updatePriceDecsription();

        etInputPrize.addTextChangedListener(mPrizeChangeListener);
    }

    /**
     * Implements this method to update Top
     * Up price description.
     */
    private void updatePriceDecsription() {
        tvYourBalance.setText((getString(R.string.text_title_your_balance)
                + " " + tvCurrencySign.getText().toString()
                + (iCardBalance + iTopUpPrize) + ".00"));
    }

    /* +++++++++++++++++++++++++++++ DECIMAL FLOAT WATCHER FOR PRICE ++++++++++++++++++++++++++++++*/

   /* public class DecimalTextWatcher implements TextWatcher {

        private final DecimalFormat df;
        private final DecimalFormat dfnd;
        private final EditText et;
        private boolean hasFractionalPart;
        private int trailingZeroCount;

        private DecimalTextWatcher(EditText editText, String pattern, String strOutputPattern) {
            df = new DecimalFormat(pattern);
            df.setDecimalSeparatorAlwaysShown(true);
            dfnd = new DecimalFormat(strOutputPattern);
            this.et = editText;
            hasFractionalPart = false;
        }

        @Override
        public void afterTextChanged(Editable s) {
            et.removeTextChangedListener(this);

            if (etInputPrize.getText().length() > 0) {
                btMakePayment.setEnabled(true);
                btMakePayment.setBackground(ContextCompat.getDrawable(TopUpActivity.this, R.drawable.button_login_shape_c0995b));
            } else {
                btMakePayment.setEnabled(false);
                btMakePayment.setBackground(ContextCompat.getDrawable(TopUpActivity.this, R.drawable.background_button_e8dcc9));
            }

            //Update price desciption label.
            updatePriceDecsription();

//            topUpPriceListRecyclerAdapter.markAsUnselected();

            if (s != null && !s.toString().isEmpty()) {
                try {
                    int inilen, endlen;
                    inilen = et.getText().length();
                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "")*//*.replace("$", "")*//*;

                    iTopUpPrize = Float.valueOf(s.toString()).intValue();

                    Number n = df.parse(v);
                    int cp = et.getSelectionStart();
                    if (hasFractionalPart) {
                        StringBuilder trailingZeros = new StringBuilder();
                        while (trailingZeroCount-- > 0)
                            trailingZeros.append('0');
                        et.setText(df.format(n) + trailingZeros.toString());
                    } else {
                        et.setText(dfnd.format(n));
                    }
                    et.setText("".concat(et.getText().toString()));
                    endlen = et.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel < et.getText().length()) {
                        et.setSelection(sel);
                    } else if (trailingZeroCount > -1) {
                        et.setSelection(et.getText().length() - 3);
                    } else {
                        et.setSelection(et.getText().length());
                    }
                } catch (NumberFormatException | ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }

            et.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int index = s.toString().indexOf(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()));
            trailingZeroCount = 0;

            if (index > -1) {
                for (index++; index < s.length(); index++) {
                    if (s.charAt(index) == '0')
                        trailingZeroCount++;
                    else {
                        trailingZeroCount = 0;
                    }
                }
                hasFractionalPart = true;
            } else {
                hasFractionalPart = false;
            }
        }
    }*/
}
