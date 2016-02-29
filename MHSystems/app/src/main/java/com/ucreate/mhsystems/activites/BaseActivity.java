package com.ucreate.mhsystems.activites;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rollbar.android.Rollbar;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

/**
 * Created by karan@ucreate.co.in for base
 * of all activities on 19-02-2016.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ApplicationGlobal.isRollMessageDisplay) {
            //Initialize Roll bar.
            Rollbar.init(this, ApplicationGlobal.KEY_ROLLBAR_CLIENT_PRODUCTION, "PRODUCTION");
        }
    }
}
