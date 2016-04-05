package com.ucreate.mhsystems.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

public class DetailInvoiceActivity extends AppCompatActivity {


    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    public static final String LOG_TAG = DetailInvoiceActivity.class.getSimpleName();

    String strInvoiceNo, strInvocieDate, strInvoiceDesc, strInvoiceBillFrom,
            strInvoiceBillTo, strInvoiceTotalTax, strInvoiceStatus;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    TextView tvInvoiceTitle;
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

        tvInvoiceTitle.setText(strInvoiceNo);

        //Set click listener events declaration.
        llHomeIcon.setOnClickListener(mHomePressListener);
    }

    /**
     * Implements a method to initialize all resources that are
     * using in Detail Invoice Activity.
     */
    private void initializeResources() {
        tvInvoiceTitle = (TextView) findViewById(R.id.tvInvoiceTitle);

        llHomeIcon = (LinearLayout) findViewById(R.id.llHomeIcon);
    }

    /**
     * Implements a method to Initialize all the
     * instances from INTENT.
     */
    private void initializeIntentData() {
        strInvoiceNo = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_NUMBER);
        strInvocieDate = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_DATE);
        strInvoiceDesc = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_DESCRIPTION);
        strInvoiceBillFrom = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_BILL_FROM);
        strInvoiceBillTo = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_BILL_TO);
        strInvoiceTotalTax = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_TOTAL_TAX);
        strInvoiceStatus = getIntent().getExtras().getString(ApplicationGlobal.KEY_INVOICE_STATUS_STR);
    }
}
