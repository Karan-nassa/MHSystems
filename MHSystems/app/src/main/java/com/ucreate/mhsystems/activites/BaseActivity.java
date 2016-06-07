package com.ucreate.mhsystems.activites;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.rollbar.android.Rollbar;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by karan@ucreate.co.in for base
 * of all activities on 19-02-2016.
 */
public class BaseActivity extends AppCompatActivity {

    Snackbar snackbar;
    private ProgressDialog mProgress;
    Dialog pDialog;

    static SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

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
     * Implements a method to show 'NO INTERNET CONNECTION' view
     * and hide it on access internet.
     *
     * @param inc_message_view :  Whole view group for set VISIBILITY of view VISIBLE/INVISIBLE.
     * @param ivMessageSymbol  :  View to set Image at run time like CUP icon for NO COMPETITION.
     * @param tvMessageTitle   :  View to set Text title of message.
     * @param tvMessageDesc    :  View to set detail Text description of message.
     * @param hasInternet      :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
     */
    public void showNoInternetView(RelativeLayout inc_message_view, ImageView ivMessageSymbol, TextView tvMessageTitle, TextView tvMessageDesc, boolean hasInternet) {

        if (hasInternet) {
            inc_message_view.setVisibility(View.GONE);
        } else {
            inc_message_view.setVisibility(View.VISIBLE);
            ivMessageSymbol.setImageResource(R.mipmap.ic_no_internet);
            tvMessageTitle.setText(getResources().getString(R.string.error_no_connection));
            tvMessageDesc.setText(getResources().getString(R.string.error_try_again));
        }
    }

    /**
     * Implements a method to show 'NO COMPETITIONS' view and hide it at least one Competition.
     *
     * @param inc_message_view :  Whole view group for set VISIBILITY of view VISIBLE/INVISIBLE.
     * @param ivMessageSymbol  :  View to set Image at run time like CUP icon for NO COMPETITION.
     * @param tvMessageTitle   :  View to set Text title of message.
     * @param tvMessageDesc    :  View to set detail Text description of message.
     * @param hasData      :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
     */
    public void showNoCompetitionsView(RelativeLayout inc_message_view, ImageView ivMessageSymbol, TextView tvMessageTitle, TextView tvMessageDesc, boolean hasData) {

        if (hasData) {
            inc_message_view.setVisibility(View.GONE);
        } else {
            inc_message_view.setVisibility(View.VISIBLE);
            ivMessageSymbol.setImageResource(R.mipmap.ic_home_competitions);
            tvMessageTitle.setText(getResources().getString(R.string.error_no_competitions));
            tvMessageDesc.setText(getResources().getString(R.string.error_select_different_month));
        }
    }

    /**
     * Implements a method to show 'NO DATA' view and hide it at least one data.
     *
     * @param inc_message_view :  Whole view group for set VISIBILITY of view VISIBLE/INVISIBLE.
     * @param ivMessageSymbol  :  View to set Image at run time like CUP icon for NO COMPETITION.
     * @param tvMessageTitle   :  View to set Text title of message.
     * @param tvMessageDesc    :  View to set detail Text description of message.
     * @param hasData      :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
     */
    public void showNoDataView(RelativeLayout inc_message_view, ImageView ivMessageSymbol, TextView tvMessageTitle, TextView tvMessageDesc, boolean hasData) {

        if (hasData) {
            inc_message_view.setVisibility(View.GONE);
        } else {
            inc_message_view.setVisibility(View.VISIBLE);
            ivMessageSymbol.setImageResource(R.mipmap.ic_home_members);
            tvMessageTitle.setText(getResources().getString(R.string.error_no_data));
            tvMessageDesc.setText(getResources().getString(R.string.error_try_again));
        }
    }

    /**
     * create common snackbar for all application to display
     * toast messages.
     */
    public void showSnackBarMessages(CoordinatorLayout coordinatorLayout, String sMessage) {

        snackbar = Snackbar
                .make(coordinatorLayout, sMessage, Snackbar.LENGTH_LONG);

        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(Color.GRAY);
        snackbar.show();
    }

    /**
     * Show progress "Please wait" message.
     */
    public void showPleaseWait(String sMessage) {

        try {
            if (pDialog == null) {
                pDialog = new Dialog(BaseActivity.this);
                pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pDialog.setContentView(R.layout.custom_progress_wheel);
                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ProgressWheel wheel = new ProgressWheel(BaseActivity.this);
                wheel.setBarColor(Color.RED);
                pDialog.setCancelable(false);

                if (!pDialog.isShowing()) {
                    pDialog.show();
                }
            }
        } catch (Exception e) {
            Log.e("Exception", "Progress error : " + e);
        }
    }

    /**
     * Implements a method to hide progress wheel.
     */
    public void hideProgress() {
        if (pDialog != null && pDialog.isShowing()) {
            try {
                pDialog.dismiss();
                pDialog = null;
            } catch (Exception e) {
                Log.e("hide", "" + e);
            }
        }
    }

    /**
     * Implement a method Custom showEnterCompetitionDialog
     * Alert Dialog for input user First & Last name,
     * email address and Mobile number.
     */
    public void showAlertMessage(String strAlertMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(strAlertMessage)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Implements a common method to update
     * Fragment.
     *
     * @param mFragment
     */
    public void updateFragment(Fragment mFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView, mFragment);
        fragmentTransaction.commit();
    }

    /**
     * Implements a method to RETURN the name of MONTH from
     * specific date format.
     *
     * @param strDate : Example => "2009-11-30T18:30:00Z"
     * @return strDate  : MMM [NOV]
     */
    public static String getFormateMonth(String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM");

        try {
            Date date = inputFormat.parse(strDate);
            strDate = outputFormat.format(date);
        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return strDate;
    }

    /**
     * Load Preference any string value
     *
     * @paramContext - Context of class
     * @paramKey - To get value corresponding to KEY_VALUE
     */
    public String loadPreferenceValue(
            String key, String defValue) {
        sharedpreferences = getSharedPreferences(
                ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
        return sharedpreferences.getString(key, defValue);
    }

    /**
     * Save Preference for future use.
     */
    @SuppressWarnings("static-access")
    public void savePreferenceValue(String key,
                                    String value) {
        sharedpreferences = getSharedPreferences(
                ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Reset shared-preference state.
     */
    public void clearAutoPreference() {
        sharedpreferences = getSharedPreferences(
                ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

}
