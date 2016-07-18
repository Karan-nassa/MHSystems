package com.mh.systems.hartsbourne.activites;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.constants.ApplicationGlobal;

/**
 * The {@link SplashActivity} used to load the SPLASH/LOADING
 * screen.
 *
 * @author : {@link karan@ucreate.co.in}
 * @version : 1.0
 * @since : 18 May, 2016
 */
public class SplashActivity extends BaseActivity {

    private final int SPLASH_TIME_OUT = 1500;

    String strUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        strUserName = loadPreferenceValue(ApplicationGlobal.KEY_USER_LOGINID, "");

        /**
         *  SPLASH FUNCTIONALITY.
         */
        new Handler().postDelayed(new Runnable() {
            /*
                * Showing splash screen with a timer. This will be useful when you
                * want to show case your app logo / company
                */
            @Override
            public void run() {
                if (strUserName.equalsIgnoreCase("")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
