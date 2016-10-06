package com.mh.systems.demoapp.activites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.constants.ApplicationGlobal;
import com.mh.systems.demoapp.push.QuickstartPreferences;
import com.mh.systems.demoapp.push.RegistrationIntentService;

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

    BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //  mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.e("sentToken:", "" + sentToken);
                } else {
                    Log.e("sentToken else :", "" + sentToken);
                }
            }
        };

        Log.e("checkPlayServices():", "" + checkPlayServices());
        if (checkPlayServices()) {
            Log.e("IF", "CALLING");
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


        strMemberID = loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "");

        /**
         *  SPLASH FUNCTIONALITY.
         */
        new Handler().postDelayed(new Runnable() {
              /**
                * Showing splash screen with a timer. This will be useful when you
                * want to show case your app logo / company
                */
            @Override
            public void run() {
                if (strMemberID.equalsIgnoreCase("")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }
}
