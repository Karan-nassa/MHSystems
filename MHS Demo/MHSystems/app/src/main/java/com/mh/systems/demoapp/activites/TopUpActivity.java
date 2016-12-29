package com.mh.systems.demoapp.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.mh.systems.demoapp.constants.WebAPI;
import com.mh.systems.demoapp.models.ContactUs.AJsonParamsContactUs;
import com.mh.systems.demoapp.models.ContactUs.ContactUsAPI;
import com.mh.systems.demoapp.models.ContactUs.ContactUsResponse;
import com.mh.systems.demoapp.models.TopUp.TopUpPriceListData;
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

    Typeface tfRobotoRegular;
    LinearLayoutManager linearLayoutManager;

    TopUpPriceListRecyclerAdapter topUpPriceListRecyclerAdapter;
    TopUpPricesListAPI topUpPricesListAPI;

    TopUpPriceListResponse topUpPriceListResponse;

    Intent intent;

    ArrayList<TopupList> topUpPriceListDataList = new ArrayList<>();

    String pattern = "##.00";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);

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

            intent = new Intent(TopUpActivity.this, MakePaymentWebActivity.class);
            startActivity(intent);
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

        etInputPrize.setOnEditorActionListener(mInputActionListener);

        linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCurrencyList.setLayoutManager(linearLayoutManager);

        //Initialize top up prices list Adapter.
        topUpPriceListRecyclerAdapter = new TopUpPriceListRecyclerAdapter(TopUpActivity.this, topUpPriceListDataList);
        rvCurrencyList.setAdapter(topUpPriceListRecyclerAdapter);

        btMakePayment.setOnClickListener(mMakePaymentListener);
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

                if (topUpPriceListResponse.getData().getTopupList().size() > 0) {
                    topUpPriceListDataList.addAll(topUpPriceListResponse.getData().getTopupList());
                    topUpPriceListRecyclerAdapter.notifyDataSetChanged();
                }

            } else {
                showAlertMessage(topUpPriceListResponse.getMessage());
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

    public void updatePriceUI(int iPosition) {

        etInputPrize.setText(decimalFormat.format(Integer.parseInt(topUpPriceListDataList.get(iPosition).getValue())));
        etInputPrize.setSelection(etInputPrize.getText().length());
    }
}
