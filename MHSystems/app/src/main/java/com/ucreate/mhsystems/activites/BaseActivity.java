package com.ucreate.mhsystems.activites;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.rollbar.android.Rollbar;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

/**
 * Created by karan@ucreate.co.in for base
 * of all activities on 19-02-2016.
 */
public class BaseActivity extends AppCompatActivity {

    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ApplicationGlobal.isRollMessageDisplay) {
            //Initialize Roll bar.
            Rollbar.init(this, ApplicationGlobal.KEY_ROLLBAR_CLIENT_TESTING, "TEST");
        }
    }

    /**
     * @return True if internet is working, Otherwise @return
     * False.
     */
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(BaseActivity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /**
     * create common snackbar for all application to display
     * toast messages.
     */
    protected void showSnackBarMessages(CoordinatorLayout coordinatorLayout, String sMessage) {

        snackbar = Snackbar
                .make(coordinatorLayout, sMessage, Snackbar.LENGTH_LONG);

        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(Color.GRAY);
        snackbar.show();
    }
}
