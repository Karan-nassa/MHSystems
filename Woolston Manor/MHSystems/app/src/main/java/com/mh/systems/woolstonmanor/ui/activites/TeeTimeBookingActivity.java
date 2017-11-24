package com.mh.systems.woolstonmanor.ui.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.support.v7.widget.Toolbar;

import com.mh.systems.woolstonmanor.R;
import com.mh.systems.woolstonmanor.ui.fragments.ShowMonthViewFragment;
import com.mh.systems.woolstonmanor.ui.fragments.TeeTimeBookingTabFragment;
import com.mh.systems.woolstonmanor.utils.constants.ApplicationGlobal;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mh.systems.woolstonmanor.R.id.tbMyAccount;

public class TeeTimeBookingActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    public final String LOG_TAG = TeeTimeBookingActivity.class.getSimpleName();

    public static int iSelectedMonth;
    public static int iSelectedYear;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.tbTeeTimeBooking)
    Toolbar tbTeeTimeBooking;

    Fragment fragmentObj;

    Typeface tfRobotoMedium;

     /* ++ INTERNET CONNECTION PARAMETERS ++ */

    @Bind(R.id.inc_message_view)
    RelativeLayout inc_message_view;

    @Bind(R.id.ivMessageSymbol)
    ImageView ivMessageSymbol;

    @Bind(R.id.tvMessageTitle)
    TextView tvMessageTitle;

    @Bind(R.id.tvMessageDesc)
    TextView tvMessageDesc;

    private int iOpenTabPosition;

    Intent intent;

    public static boolean shouldRefresh;

    private static int iTabPosition;

     /* -- INTERNET CONNECTION PARAMETERS -- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tee_time_booking);

        //Initialize view resources.
        ButterKnife.bind(this);

        if (tbTeeTimeBooking != null) {
            setSupportActionBar(tbTeeTimeBooking);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            tbTeeTimeBooking.setNavigationIcon(R.mipmap.icon_menu);

            getSupportActionBar().setTitle(getString(R.string.text_title_tee_booking));
        }

        shouldRefresh = true;
        iTabPosition = 0;

        tfRobotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        Calendar cal = Calendar.getInstance();
        iSelectedMonth = cal.get(Calendar.MONTH) + 1;
        iSelectedYear = cal.get(Calendar.YEAR);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(shouldRefresh/*fragmentObj instanceof ShowMonthViewFragment*/){
            updateFragment(new TeeTimeBookingTabFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * Implements a method to update UI when 'No Internet connection'
     * when disconnect internet connection.
     *
     * @param isOnline : True means internet working fine.
     */
    public void updateHasInternetUI(boolean isOnline) {
        if (isOnline) {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
        }
    }

    /**
     * Implements a method to get MEMBER-ID from {@link android.content.SharedPreferences}
     */
    public String getMemberId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_MEMBERID, "10784");
    }

    /**
     * Implements a method to get CLIENT-ID from {@link android.content.SharedPreferences}
     */
    public String getClientId() {
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, ApplicationGlobal.TAG_CLIENT_ID);
    }

    /**
     * {@link Fragment} will be used to display Filter icon for FINANCE
     * tab content.
     */
    public Fragment getFragmentInstance() {
        return fragmentObj;
    }

    /**
     * Set {@link Fragment} instance which will be used to display Filter
     * icon for FINANCE tab content.
     */
    public void setFragmentInstance(Fragment fragmentObj) {
        this.fragmentObj = fragmentObj;
    }

    /**
     * Implements a method to update UI when 'No Competitions'.
     *
     * @param hasData      : True means more than 1 data.
     * @param iTabPosition : Which tab Members or friends
     */
    public void updateNoDataUI(boolean hasData, int iTabPosition) {
        if (hasData) {
            showNoBookingView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, true, iTabPosition);
        } else {
            showNoBookingView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false, iTabPosition);
        }
    }

    /**
     * Implements a method to show 'NO BOOKING FOUND' view and hide it at least one data.
     *
     * @param inc_message_view :  Whole view group for set VISIBILITY of view VISIBLE/INVISIBLE.
     * @param ivMessageSymbol  :  View to set Image at run time like CUP icon for NO COMPETITION.
     * @param tvMessageTitle   :  View to set Text title of message.
     * @param tvMessageDesc    :  View to set detail Text description of message.
     * @param hasData          :  bool used to describe which decide the functionality should happen [TRUE] or not [FALSE]?
     * @param iTabPositon      :  if iTabPosition 0 means 'No Member found' otherwise 'No Friend found'
     */
    public void showNoBookingView(RelativeLayout inc_message_view, ImageView ivMessageSymbol,
                                  TextView tvMessageTitle, TextView tvMessageDesc,
                                  boolean hasData, int iTabPositon) {

        if (hasData) {
            inc_message_view.setVisibility(View.GONE);
        } else {
            inc_message_view.setVisibility(View.VISIBLE);
            ivMessageSymbol.setImageResource(R.mipmap.ic_teetime_booking);
            if (iTabPositon == 0) {
                tvMessageTitle.setText(getResources().getString(R.string.error_no_booking));
            } else {
                tvMessageTitle.setText(getResources().getString(R.string.error_no_booking));
            }
            tvMessageDesc.setText(getResources().getString(R.string.error_try_again));
        }
    }

    public static int getiTabPosition() {
        return iTabPosition;
    }

    public static void setWhichTab(int iTabPosition) {
        TeeTimeBookingActivity.iTabPosition = iTabPosition;
    }
}
