package com.ucreate.mhsystems.activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.fragments.MyAccountTabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyAccountActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    public final String LOG_TAG = MyAccountActivity.class.getSimpleName();

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    @Bind(R.id.llHomeMyAccount)
    LinearLayout llHomeMyAccount;

    @Bind(R.id.toolBar)
    Toolbar tbMyAccount;

    /*@Bind(R.id.container)
    FrameLayout container;*/

     /* ++ INTERNET CONNECTION PARAMETERS ++ */

    @Bind(R.id.inc_message_view)
    RelativeLayout inc_message_view;

    @Bind(R.id.ivMessageSymbol)
    ImageView ivMessageSymbol;

    @Bind(R.id.tvMessageTitle)
    TextView tvMessageTitle;

    @Bind(R.id.tvMessageDesc)
    TextView tvMessageDesc;

     /* -- INTERNET CONNECTION PARAMETERS -- */

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
        setContentView(R.layout.activity_my_account);

        //Initialize view resources.
        ButterKnife.bind(this);

        //Let's first set up toolbar
        setupToolbar();

        //Load Default fragment of COURSE DIARY.
        updateFragment(new MyAccountTabFragment());

        //Set click listener events declaration.
        llHomeMyAccount.setOnClickListener(mHomePressListener);
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
            //inc_noInternet.setVisibility(View.GONE);
            //container.setVisibility(View.VISIBLE);
        } else {
            showNoInternetView(inc_message_view, ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
            //container.setVisibility(View.GONE);
            //inc_noInternet.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Implements a method to update UI when 'No Competitions'.
     *
     * @param hasData : True means more than 1 data.
     */
    public void updateNoCompetitionsUI(boolean hasData) {
        if (hasData) {
            //showNoCompetitionsView(inc_message_view,ivMessageSymbol, tvMessageTitle, tvMessageDesc, true);
        } else {
            showAlertMessage(getResources().getString(R.string.error_no_data));
            //showNoCompetitionsView(inc_message_view,ivMessageSymbol, tvMessageTitle, tvMessageDesc, false);
        }
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
     * Initialize tool bar to display menu bar options, app-icon and
     * navigation drawer icon.
     */
    void setupToolbar() {
        setSupportActionBar(tbMyAccount);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
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
        return loadPreferenceValue(ApplicationGlobal.KEY_CLUB_ID, "44118078");
    }
}
