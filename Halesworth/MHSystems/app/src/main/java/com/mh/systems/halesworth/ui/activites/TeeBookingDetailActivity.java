package com.mh.systems.halesworth.ui.activites;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mh.systems.halesworth.R;
import com.mh.systems.halesworth.utils.constants.ApplicationGlobal;
import com.mh.systems.halesworth.web.api.WebAPI;
import com.mh.systems.halesworth.web.api.WebServiceMethods;
import com.mh.systems.halesworth.web.models.FinanceAJsonParams;
import com.mh.systems.halesworth.web.models.FinanceAPI;
import com.mh.systems.halesworth.web.models.FinanceResultItems;
import com.mh.systems.halesworth.web.models.teetimebooking.booking.UpdateBookingResponse;
import com.mh.systems.halesworth.web.models.teetimebooking.cancelbooking.AJsonParamsCancelBooking;
import com.mh.systems.halesworth.web.models.teetimebooking.cancelbooking.CancelBookingAPI;
import com.mh.systems.halesworth.web.models.teetimebooking.makebooking.AJsonParamsMakeBookingAPI;
import com.mh.systems.halesworth.web.models.teetimebooking.makebooking.MakeBookingAPI;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class TeeBookingDetailActivity extends BaseActivity {

    private final String LOG_TAG = TeeBookingDetailActivity.class.getSimpleName();

    String strDateAndTime, strSlotStartDateTime, strDescription, strPLU, strBuggyPLU, strCrnSymbol;
    String strDate, strTime;
    boolean isBuggyIsOptional;
    boolean isCanCancel, isBuggyValid;
    int iBookingId;
    float fPrice;
    float fBuggyPrice = 0;
    float fActualPrice;
    Integer FundsAvail = 0;
    int iMaxBuggies = 0;

    boolean fromMyBooking;
    int iBuggyQty = 0;

    private float mTopUpBalance = 0;

    NumberFormat formatter = new DecimalFormat("0.00");
    double price = 0;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.toolbarComp)
    Toolbar toolbarComp;
    @Bind(R.id.tvTeeTimeOfEvent)
    TextView tvTeeTimeOfEvent;
    @Bind(R.id.tvTeeDateOfEvent)
    TextView tvTeeDateOfEvent;
    @Bind(R.id.tvTeeTitleOfEvent)
    TextView tvTeeTitleOfEvent;
    @Bind(R.id.tvTeePriceOfEvent)
    TextView tvTeePriceOfEvent;

    @Bind(R.id.llTeeBookingGroup)
    LinearLayout llTeeBookingGroup;

    @Bind(R.id.llTeeBookingWithBuggy1)
    LinearLayout llTeeBookingWithBuggy1;

    @Bind(R.id.tvTeePriceWithBuggy1)
    TextView tvTeePriceWithBuggy1;

    @Bind(R.id.llTeeBookingWithBuggy2)
    LinearLayout llTeeBookingWithBuggy2;

    @Bind(R.id.tvTeePriceWithBuggy2)
    TextView tvTeePriceWithBuggy2;

    //Join Competition Button
    @Bind(R.id.fabJoinCompetition)
    FloatingActionButton fabJoinCompetition;

    private MakeBookingAPI mMakeBookingAPI;
    private AJsonParamsMakeBookingAPI aJsonParamsMakeBookingAPI;

    private UpdateBookingResponse mUpdateBookingResponse;

    private CancelBookingAPI mCancelBookingAPI;
    private AJsonParamsCancelBooking aJsonParamsCancelBooking;

    FinanceResultItems financeResultItems;

    FinanceAPI financeApi;
    FinanceAJsonParams financeAJsonParams;

    private View.OnClickListener mJoinOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /**
             *  Check internet connection before hitting server request.
             */
            if (isOnline(TeeBookingDetailActivity.this)) {
                if (fromMyBooking) {
                    if (isCanCancel) {
                        requestCancelBookingEntry();
                    } else {
                        showAlertMessage("You are not authorized to cancel this entry.");
                    }
                } else {
                    checkFundBalance();
                }
            } else {
                showAlertMessage(getResources().getString(R.string.error_no_internet));
            }
        }
    };

    private void checkFundBalance() {

        if (mTopUpBalance >= price) {
            if (isBuggyValid && iMaxBuggies != 0) {
                showAlertBuggyOption();
            } else {
                requestMakeBookingEntry();
            }
        } else {
            String strErrorMessage = "Sorry, you do not have sufficient funds to make this entry. The total entry fees are "
                    + strCrnSymbol + formatter.format(price) + ". Your Account balance is "
                    + strCrnSymbol + formatter.format(mTopUpBalance) +
                    ". Please top-up your account by clicking ok.";
            showAlertError(strErrorMessage);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tee_booking_detail);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(this);

        fromMyBooking = getIntent().getExtras().getBoolean("FROM_MY_BOOKING");

        strDateAndTime = getIntent().getExtras().getString("SlotStart");
        strDescription = getIntent().getExtras().getString("Description");

        strTime = strDateAndTime.substring(strDateAndTime.indexOf(' '), strDateAndTime.length());
        strDate = strDateAndTime.substring(0, strDateAndTime.indexOf(' '));
        tvTeeDateOfEvent.setText(getSimpleDateFormat(strDate));
        tvTeeTimeOfEvent.setText(strTime.trim());

        if (fromMyBooking) {
            iBookingId = getIntent().getExtras().getInt("BookingId");
            isCanCancel = getIntent().getExtras().getBoolean("CanCancel");
            fPrice = getIntent().getExtras().getInt("Price");

            price = fPrice / 100.00;
            llTeeBookingGroup.setVisibility(View.VISIBLE);
            tvTeePriceOfEvent.setText(formatter.format(price));

         /*   strTime = strDateAndTime.substring(strDateAndTime.indexOf('T') + 1, strDateAndTime.lastIndexOf(':'));
            strDate = strDateAndTime.substring(0, strDateAndTime.indexOf('T'));
            tvTeeDateOfEvent.setText(getFormateDateHyphen(strDate));
            tvTeeTimeOfEvent.setText(strTime.trim());*/
        } else {
            strSlotStartDateTime = getIntent().getExtras().getString("SlotStartDateTime");

            fPrice = fActualPrice = getIntent().getExtras().getInt("Price");
            strPLU = getIntent().getExtras().getString("PLU");
            isBuggyIsOptional = getIntent().getExtras().getBoolean("BuggyIsOptional");
            fBuggyPrice = (float) getIntent().getExtras().getInt("BuggyPrice");
            strBuggyPLU = getIntent().getExtras().getString("BuggyPLU");
            strCrnSymbol = getIntent().getExtras().getString("CrnSymbol");
            isBuggyValid = getIntent().getExtras().getBoolean("BuggyIsValid");

            iMaxBuggies = getIntent().getExtras().getInt("MaxBuggies");

            fPrice =  (float) fActualPrice;

            price = fPrice / 100.00;
            fBuggyPrice = fBuggyPrice / 100;

            llTeeBookingGroup.setVisibility(View.VISIBLE);
            tvTeePriceOfEvent.setText(formatter.format(price));

            //isBuggyValid = false;
            // isBuggyIsOptional = false;

            //isBuggySelected = isBuggyIsOptional;
           /* if (!isBuggyValid) {
                llTeeBookingWithBuggy1.setVisibility(View.GONE);
                llTeeBookingWithBuggy2.setVisibility(View.GONE);
            } else if (isBuggyValid && !isBuggyIsOptional) {
                tvTeePriceOfEvent.setText((formatter.format(price + fBuggyPrice) +
                        " " + getString(R.string.text_title_with_buggy)));
                llTeeBookingWithBuggy1.setVisibility(View.GONE);
                llTeeBookingWithBuggy2.setVisibility(View.GONE);
                *//*tvTeePriceWithBuggy.setText((formatter.format(strBuggyPrice) +
                        " " + getString(R.string.text_title_with_buggy)));*//*
            } else {
                //tvTeePriceOfEvent.setText(formatter.format(price));
                tvTeePriceWithBuggy1.setText((formatter.format(price + fBuggyPrice) +
                        " " + getString(R.string.text_title_with_buggy)));
                llTeeBookingWithBuggy1.setVisibility(View.VISIBLE);
            }*/

            if (!isBuggyValid) {
                llTeeBookingWithBuggy1.setVisibility(View.GONE);
                llTeeBookingWithBuggy2.setVisibility(View.GONE);
            }
            switch (iMaxBuggies) {
                case 0:
                    if (!isBuggyIsOptional) {
                        tvTeePriceOfEvent.setText((formatter.format(price + fBuggyPrice) +
                                " " + getString(R.string.text_with_buggy)));
                        llTeeBookingWithBuggy1.setVisibility(View.GONE);
                        llTeeBookingWithBuggy2.setVisibility(View.GONE);
                    }
                    break;

                case 1:
                    tvTeePriceWithBuggy1.setText((formatter.format(price + fBuggyPrice) +
                            " " + getString(R.string.text_with_buggy)));
                    llTeeBookingWithBuggy1.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    tvTeePriceWithBuggy1.setText((formatter.format(price + fBuggyPrice) +
                            " " + getString(R.string.text_with_buggy)));
                    llTeeBookingWithBuggy1.setVisibility(View.VISIBLE);

                    tvTeePriceWithBuggy2.setText((formatter.format(price + (fBuggyPrice * 2)) +
                            " " + getString(R.string.text_with_buggies)));
                    llTeeBookingWithBuggy2.setVisibility(View.VISIBLE);
                    break;
            }
        }

        tvTeeTitleOfEvent.setText(strDescription);

        updateFloatingButton(fromMyBooking);

        setSupportActionBar(toolbarComp);

        getSupportActionBar().

                setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().

                setDisplayShowTitleEnabled(false);
        toolbarComp.setTitle("");
        toolbarComp.setSubtitle("");

        getSupportActionBar().

                setHomeAsUpIndicator(R.mipmap.ic_close_white);

        toolbarComp.setTitleTextColor(0xFFFFFFFF);

        fabJoinCompetition.setOnClickListener(mJoinOnClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         *  Check internet connection before hitting server request.
         */
        if (isOnline(TeeBookingDetailActivity.this)) {
            if (!fromMyBooking) {
                getCardBalanceService();
            }
        } else {
            showAlertMessage(getString(R.string.error_no_internet));
        }
    }

    /**
     * Implement a method Custom showEnterCompetitionDialog
     * Alert Dialog for input user First & Last name,
     * email address and Mobile number.
     */
    public void showAlertMessageCallback(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Implements method to return date by format.
     *
     * @param strDate : Example => "2017-10-28"
     * @return strDate  : MMMM dd, yyyy
     */
    public static String getFormateDateHyphen(String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");

        try {
            Date date = inputFormat.parse(strDate);
            strDate = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return strDate;
    }

    private void updateFloatingButton(boolean isFromMyBooking) {

        fabJoinCompetition.setVisibility(View.VISIBLE);

        if (isFromMyBooking) {
            fabJoinCompetition.setImageResource(R.mipmap.ic_minus);
            fabJoinCompetition.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
        } else {
            fabJoinCompetition.setImageResource(R.mipmap.ic_plus);
            fabJoinCompetition.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C0995B")));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestCancelBookingEntry() {
        showPleaseWait("Loading...");

        aJsonParamsCancelBooking = new AJsonParamsCancelBooking();
        aJsonParamsCancelBooking.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsCancelBooking.setMemberId(getMemberId());
        aJsonParamsCancelBooking.setBookingId(iBookingId);

        mCancelBookingAPI = new CancelBookingAPI(getClientId(),
                "CANCELBOOKING",
                aJsonParamsCancelBooking,
                "MOTT",
                ApplicationGlobal.TAG_GCLUB_MEMBERS);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        api.cancelBookingMOTT(mCancelBookingAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                updateBookingSuccess(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();
                showAlertMessage(error.toString());
            }
        });
    }

    /**
     * Get Month data web service.
     */
    private void requestMakeBookingEntry() {

        showPleaseWait("Loading...");

        fActualPrice = fActualPrice + (fBuggyPrice * iBuggyQty);

        aJsonParamsMakeBookingAPI = new AJsonParamsMakeBookingAPI();
        aJsonParamsMakeBookingAPI.setVersion(ApplicationGlobal.TAG_GCLUB_VERSION);
        aJsonParamsMakeBookingAPI.setMemberId(getMemberId());
        aJsonParamsMakeBookingAPI.setSlotStart(strSlotStartDateTime);
        aJsonParamsMakeBookingAPI.setPLU(strPLU);
        aJsonParamsMakeBookingAPI.setPrice(getIntent().getExtras().getInt("Price"));
        aJsonParamsMakeBookingAPI.setIncludesBuggy(iBuggyQty);
        aJsonParamsMakeBookingAPI.setBuggyPLU(strBuggyPLU);
        aJsonParamsMakeBookingAPI.setBuggyPrice(getIntent().getExtras().getInt("BuggyPrice"));

        mMakeBookingAPI = new MakeBookingAPI(getClientId(),
                "MAKEBOOKING",
                aJsonParamsMakeBookingAPI,
                "MOTT",
                ApplicationGlobal.TAG_GCLUB_MEMBERS);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        WebServiceMethods api = adapter.create(WebServiceMethods.class);

        api.GetMakeBookingMOTT(mMakeBookingAPI, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                updateBookingSuccess(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.e(LOG_TAG, "RetrofitError : " + error);
                hideProgress();
                showAlertMessage(error.toString());
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
    private void updateBookingSuccess(JsonObject jsonObject) {

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

                /*if (fromMyBooking
                        && mUpdateBookingResponse.getData().getMessage().contains("cancel")) {
                    //Update '-' sign with '+' that indicate the booking Canceled.
                    fromMyBooking = false;
                    updateFloatingButton(fromMyBooking);
                } else if (mUpdateBookingResponse.getData().getMessage().contains("Thank you")) {
                    //Update '+' sign with '-' that indicate the booking success.
                    fromMyBooking = true;
                    updateFloatingButton(fromMyBooking);
                } */
                if (fromMyBooking) {
                    showAlertMessageCallback(mUpdateBookingResponse.getData().getMessage()
                            + " This has been removed to your ‘My Bookings’ items’.");
                } else {
                    showAlertMessageCallback(mUpdateBookingResponse.getData().getMessage()
                            + " This has been added to your ‘My Bookings’ items’.");
                }

            } else {
                showAlertMessage(mUpdateBookingResponse.getData().getMessage());
            }
            hideProgress();
        } catch (Exception e) {
            hideProgress();
            Log.e(LOG_TAG, "" + e.getMessage());
            reportRollBarException(TeeBookingDetailActivity.class.getSimpleName(), e.toString());
        }
    }

    /************************************ FINANCE WEB SERVICE [START] ************************************/

    private void getCardBalanceService() {

        showPleaseWait("Loading...");

        financeAJsonParams = new FinanceAJsonParams();
        financeAJsonParams.setCallid(ApplicationGlobal.TAG_NEW_GCLUB_CALL_ID);
        financeAJsonParams.setDateRange(2);//iFilterType
        financeAJsonParams.setMemberId(getMemberId());

        financeApi = new FinanceAPI(getClientId(), "GetAccStatement"
                , financeAJsonParams, "TRANSACTION", ApplicationGlobal.TAG_GCLUB_MEMBERS);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(WebAPI.API_BASE_URL)
                .build();

        WebServiceMethods api = adapter.create(WebServiceMethods.class);
        api.getFinanceDetail(financeApi, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                updateSuccessResponse(jsonObject);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, "RetrofitError : " + error);
                showAlertError(getString(R.string.error_no_topup_found));
                hideProgress();
            }
        });
    }

    /**
     * Implements a method to update SUCCESS
     * response of web service.
     */
    private void updateSuccessResponse(JsonObject jsonObject) {

        Log.e(LOG_TAG, "SUCCESS RESULT : " + jsonObject.toString());

        Type type = new com.newrelic.com.google.gson.reflect.TypeToken<FinanceResultItems>() {
        }.getType();
        financeResultItems = new com.newrelic.com.google.gson.Gson().fromJson(jsonObject.toString(), type);

        try {
            /**
             *  Check "Result" 1 or 0. If 1, means data received successfully.
             */
            if (financeResultItems.getMessage().equalsIgnoreCase("Success")) {

                String strClosingBalance = financeResultItems.getData().getClosingBalance();
                mTopUpBalance = Float.parseFloat(strClosingBalance.substring(1, strClosingBalance.length()));

            } else {
                showAlertMessage(financeResultItems.getMessage());
            }
        } catch (Exception e) {
            reportRollBarException(TeeBookingDetailActivity.class.getSimpleName()
                    , e.toString());
        }

        //Dismiss progress dialog.
        hideProgress();
    }

    /**
     * Implement a method Custom showEnterCompetitionDialog
     * Alert Dialog for input user First & Last name,
     * email address and Mobile number.
     */
    public void showAlertError(String strAlertMessage) {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(TeeBookingDetailActivity.this,
                                    TopUpActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("PASS_FROM", TeeBookingDetailActivity.class.getSimpleName());
                            intent.putExtra("strClosingBalance", financeResultItems.getData().getClosingBalance());
                            startActivity(intent);
                            builder = null;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /************************************ FINANCE WEB SERVICE [END] ************************************/

    public void showAlertBuggyOption() {

        final Dialog openDialog = new Dialog(TeeBookingDetailActivity.this);
        openDialog.setContentView(R.layout.alert_buggy_confirmation);
        openDialog.setTitle("");

        TextView tvAlertTitle = (TextView) openDialog.findViewById(R.id.tvAlertTitle);

        LinearLayout llOnlyTeeTime = (LinearLayout) openDialog.findViewById(R.id.llOnlyTeeTime);
        TextView tvBuggyPrice1 = (TextView) openDialog.findViewById(R.id.tvBuggyPrice1);

        LinearLayout llWithBuggy1 = (LinearLayout) openDialog.findViewById(R.id.llWithBuggy1);
        TextView tvBuggyPrice2 = (TextView) openDialog.findViewById(R.id.tvBuggyPrice2);

        LinearLayout llWithBuggy2 = (LinearLayout) openDialog.findViewById(R.id.llWithBuggy2);
        TextView tvBuggyPrice3 = (TextView) openDialog.findViewById(R.id.tvBuggyPrice3);

        TextView tvCancel = (TextView) openDialog.findViewById(R.id.tvCancel);

        tvAlertTitle.setText(("Book Tee Time on " + getSimpleDateFormat(strDate) + " at " + strTime.trim()));

        if (isBuggyIsOptional) {
            llOnlyTeeTime.setVisibility(View.VISIBLE);
            tvBuggyPrice1.setText((strCrnSymbol + (formatter.format(price))));
            llOnlyTeeTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iBuggyQty = 0;
                    requestMakeBookingEntry();
                    openDialog.dismiss();
                }
            });
        }

        if (iMaxBuggies == 1) {
            tvBuggyPrice2.setText((strCrnSymbol + (formatter.format(price + fBuggyPrice))));
            llWithBuggy1.setVisibility(View.VISIBLE);
            llWithBuggy1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iBuggyQty = 1;
                    requestMakeBookingEntry();
                    openDialog.dismiss();
                }
            });
        }

        if (iMaxBuggies == 2) {

            tvBuggyPrice2.setText((strCrnSymbol + (formatter.format(price + fBuggyPrice))));
            llWithBuggy1.setVisibility(View.VISIBLE);
            llWithBuggy1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iBuggyQty = 1;
                    requestMakeBookingEntry();
                    openDialog.dismiss();
                }
            });

            double TotalPrice = price + (fBuggyPrice * 2);
            tvBuggyPrice3.setText((strCrnSymbol + (formatter.format(TotalPrice))));
            llWithBuggy2.setVisibility(View.VISIBLE);
            llWithBuggy2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iBuggyQty = 2;
                    requestMakeBookingEntry();
                    openDialog.dismiss();
                }
            });
        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog.dismiss();
            }
        });

        openDialog.show();
    }


}
