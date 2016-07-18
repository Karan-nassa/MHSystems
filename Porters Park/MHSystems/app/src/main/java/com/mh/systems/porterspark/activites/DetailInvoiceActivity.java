package com.mh.systems.porterspark.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.porterspark.R;
import com.mh.systems.porterspark.constants.ApplicationGlobal;

public class DetailInvoiceActivity extends AppCompatActivity {


    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = DetailInvoiceActivity.class.getSimpleName();

    String strInvoiceTitle, strInvoiceNo, strInvoiceValue, strInvoiceTax, strInvoiceDate, strInvoiceDesc, strInvoiceBillFrom,
            strInvoiceBillTo, strInvoiceTotalPayable, strInvoiceStatus;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    TextView tvInvoiceTitle;
    TextView tvInvoiceNo, tvInvoiceDate, tvInvoiceStatus, tvInvoiceDesc, tvInvoiceFrom, tvInvoiceTo, tvInvoiceValue, tvInvoiceTax, tvInvoiceTotal;
    LinearLayout llHomeIcon;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_invoice);

        initializeResources();

        initializeIntentData();

        //Set data on each view.
        setInvoiceData();

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);
    }

    /**
     * Implements a method to display all INVOICE data pass from last
     * screen.
     */
    private void setInvoiceData() {

        tvInvoiceTitle.setText(strInvoiceTitle);
        tvInvoiceNo.setText(strInvoiceNo);
        tvInvoiceDate.setText(strInvoiceDate);
        tvInvoiceStatus.setText(strInvoiceStatus);
        tvInvoiceDesc.setText(strInvoiceDesc);
        tvInvoiceFrom.setText(strInvoiceBillFrom);
        tvInvoiceTo.setText(strInvoiceBillTo);
        tvInvoiceValue.setText(strInvoiceValue);
        tvInvoiceTax.setText(strInvoiceTax);
        tvInvoiceTotal.setText(strInvoiceTotalPayable);


    }

    /**
     * Implements a method to initialize all resources that are
     * using in Detail Invoice Activity.
     */
    private void initializeResources() {
        tvInvoiceTitle = (TextView) findViewById(R.id.tvInvoiceTitle);

        tvInvoiceNo = (TextView) findViewById(R.id.tvInvoiceNo);
        tvInvoiceDate = (TextView) findViewById(R.id.tvInvoiceDate);
        tvInvoiceStatus = (TextView) findViewById(R.id.tvInvoiceStatus);
        tvInvoiceDesc = (TextView) findViewById(R.id.tvInvoiceDesc);
        tvInvoiceFrom = (TextView) findViewById(R.id.tvInvoiceFrom);
        tvInvoiceTo = (TextView) findViewById(R.id.tvInvoiceTo);
        tvInvoiceValue = (TextView) findViewById(R.id.tvInvoiceValue);
        tvInvoiceTax = (TextView) findViewById(R.id.tvInvoiceTax);
        tvInvoiceTotal = (TextView) findViewById(R.id.tvInvoiceTotal);

        llHomeIcon = (LinearLayout) findViewById(R.id.llHomeIcon);
    }

    /**
     * Implements a method to Initialize all the
     * instances from INTENT.
     */
    private void initializeIntentData() {
        strInvoiceTitle = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_TITLE);
        strInvoiceNo  = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_NUMBER);
        strInvoiceTax  = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_TAX);
        strInvoiceTotalPayable  = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_TOTAL_PAYABLE);
        strInvoiceDate = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_DATE);
        strInvoiceDesc = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_DESCRIPTION);
        strInvoiceBillFrom = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_BILL_FROM);
        strInvoiceBillTo = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_BILL_TO);
        strInvoiceStatus = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_STATUS_STR);
        strInvoiceValue = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_VALUE);
    }
}
