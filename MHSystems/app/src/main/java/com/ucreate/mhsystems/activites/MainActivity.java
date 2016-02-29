package com.ucreate.mhsystems.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rollbar.android.Rollbar;
import com.ucreate.mhsystems.R;

/**
 * Created by karan@ucreate.co.in for first
 * hello app message on 19-02-2016.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Just to test Rollbar implementation.
        Rollbar.reportMessage("A test message", "debug"); // default level is "info"
    }
}
