package com.mh.systems.sunningdale.ui.activites;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.mh.systems.sunningdale.R;
import com.mh.systems.sunningdale.utils.constants.ApplicationGlobal;

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

    String strMemberID;
    boolean isFirstTimeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        strMemberID = loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "");
        isFirstTimeLogin = loadPreferenceBooleanValue(ApplicationGlobal.KEY_FIRST_TIME_LOGIN, true);

        /**
         *  If we already have MemberID and FirstLogin bool value means user already
         *  Logged-in
         * */
        new Handler().postDelayed(new Runnable() {
            /*
                * Showing splash screen with a timer. This will be useful when you
                * want to show case your app logo / company
                */
            @Override
            public void run() {
                if (strMemberID.equalsIgnoreCase("") || isFirstTimeLogin) {
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
