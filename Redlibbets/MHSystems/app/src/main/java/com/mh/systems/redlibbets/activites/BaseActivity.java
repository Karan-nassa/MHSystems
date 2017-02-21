package com.mh.systems.redlibbets.activites;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mh.systems.redlibbets.R;
import com.mh.systems.redlibbets.constants.ApplicationGlobal;
import com.mh.systems.redlibbets.models.CoursesData;
import com.mh.systems.redlibbets.models.MembersDetailsData;
import com.newrelic.com.google.gson.Gson;
import com.rollbar.android.Rollbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created {@link BaseActivity} to handle {@link SharedPreferences}, {@link AlertDialog}
 * on 19-02-2016.
 *
 * @author Karan Nassa
 */
public class BaseActivity extends AppCompatActivity {

    public final int PERMISSIONS_REQUEST_CALL_PHONE = 101;

    Snackbar snackbar;
    private ProgressDialog mProgress;
    Dialog pDialog;

    AlertDialog.Builder builder;

    static SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    static Fragment fragmentInstance;

    //TOKEN of TestFairy.
    private final String KEY_TEST_FAIRY = "607132019102f58e6620f8be506322315fad2aa9";

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
     * @param hasData          :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
     */
    public void showNoEventView(RelativeLayout inc_message_view, ImageView ivMessageSymbol, TextView tvMessageTitle, TextView tvMessageDesc, boolean hasData, String strMessageTitle) {

        if (hasData) {
            inc_message_view.setVisibility(View.GONE);
        } else {
            inc_message_view.setVisibility(View.VISIBLE);
            ivMessageSymbol.setImageResource(R.mipmap.ic_home_competitions);
            tvMessageTitle.setText(strMessageTitle);
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
     * @param hasData          :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
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
     * Implements a method to show 'NO MEMBER FOUND' view and hide it at least one data.
     *
     * @param inc_message_view :  Whole view group for set VISIBILITY of view VISIBLE/INVISIBLE.
     * @param ivMessageSymbol  :  View to set Image at run time like CUP icon for NO COMPETITION.
     * @param tvMessageTitle   :  View to set Text title of message.
     * @param tvMessageDesc    :  View to set detail Text description of message.
     * @param hasData          :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
     * @param iTabPositon      :  if iTabPosition 0 means 'No Member found' otherwise 'No Friend found'
     */
    public void showNoMemberView(RelativeLayout inc_message_view, ImageView ivMessageSymbol, TextView tvMessageTitle, TextView tvMessageDesc, boolean hasData, int iTabPositon) {

        if (hasData) {
            inc_message_view.setVisibility(View.GONE);
        } else {
            inc_message_view.setVisibility(View.VISIBLE);
            ivMessageSymbol.setImageResource(R.mipmap.ic_home_members);
            if (iTabPositon == 0) {
                tvMessageTitle.setText(getResources().getString(R.string.error_no_member));
            } else {
                tvMessageTitle.setText(getResources().getString(R.string.error_no_friend));
            }
            tvMessageDesc.setText(getResources().getString(R.string.error_try_again));
        }
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
                // ProgressWheel wheel = new ProgressWheel(BaseActivity.this);
                // wheel.setBarColor(Color.RED);
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

        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage(strAlertMessage)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            builder = null;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Implements a common method to update
     * Fragment.
     *
     * @param mFragment
     */
    public void updateFragment(Fragment mFragment) {

        this.fragmentInstance = mFragment;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView, fragmentInstance);
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
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
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
     * Load Preference any Boolean value
     *
     * @paramContext - Context of class
     * @paramKey - To get value corresponding to KEY_VALUE
     */
    public boolean loadPreferenceBooleanValue(
            String key, boolean defValue) {
        sharedpreferences = getSharedPreferences(
                ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
        return sharedpreferences.getBoolean(key, defValue);
    }

    /**
     * Save boolean value in Preference for future use.
     */
    @SuppressWarnings("static-access")
    public void savePreferenceBooleanValue(String key,
                                           boolean value) {
        sharedpreferences = getSharedPreferences(
                ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
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
     * Save {@link java.util.ArrayList} in {@link SharedPreferences} in
     * Gson form.
     */
    @SuppressWarnings("static-access")
    public void savePreferenceList(String key, String json) {
        sharedpreferences = getSharedPreferences(
                ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(key, json);
        editor.commit();
    }

    public ArrayList loadPreferencesList(Context context) {
        List arrayList = null;
        sharedpreferences = getSharedPreferences(
                ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
        if (sharedpreferences.contains(ApplicationGlobal.KEY_COURSES)) {
            String jsonFavorites = sharedpreferences.getString(ApplicationGlobal.KEY_COURSES, null);
            Gson gson = new Gson();
            CoursesData[] favoriteItems = gson.fromJson(jsonFavorites, CoursesData[].class);
            arrayList = Arrays.asList(favoriteItems);
            arrayList = new ArrayList(arrayList);
        } else
            return null;
        return (ArrayList) arrayList;
    }

    /**
     * Implements this to get {@link com.mh.systems.redlibbets.models.MembersData}.
     */
    public MembersDetailsData loadPreferencesJson(String strKeyValue) {
        MembersDetailsData membersDetailsData;
        sharedpreferences = getSharedPreferences(
                ApplicationGlobal.SHARED_PREF, MODE_PRIVATE);
        if (sharedpreferences.contains(strKeyValue)) {
            String jsonFavorites = sharedpreferences.getString(strKeyValue, null);
            Gson gson = new Gson();
            membersDetailsData = gson.fromJson(jsonFavorites, MembersDetailsData.class);
        } else
            return null;
        return membersDetailsData;
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

     /* ++++++++++++++++++++ REQUEST PERMISSION FOR MARSHMALLOW PROGRAMMATICALLY ++++++++++++++++++++ */

    /**
     * Implements this method to get CALL PERMISSION
     * for marshmallow.
     */
    public void requestPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(BaseActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }

            //And finally ask for the permission
            ActivityCompat.requestPermissions(BaseActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    /**
     * Implements this method to check if user already granted
     * for the permission or not?
     *
     * @return TRUE : If granted otherwise FALSE.
     */
    public boolean isCallPermissionAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }
}
