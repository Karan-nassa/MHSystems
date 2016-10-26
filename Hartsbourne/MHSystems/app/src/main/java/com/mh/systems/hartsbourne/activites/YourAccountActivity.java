package com.mh.systems.hartsbourne.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.constants.ApplicationGlobal;
import com.mh.systems.hartsbourne.fragments.FinanceFragment;
import com.mh.systems.hartsbourne.fragments.MyAccountTabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YourAccountActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/
    public final String LOG_TAG = YourAccountActivity.class.getSimpleName();

    private boolean isFilterOpen;

    private int iTabPosition;

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    /*@Bind(R.id.llHomeMyAccount)
    LinearLayout llHomeMyAccount;*/

    /*@Bind(R.id.container)
    FrameLayout container;*/

   /* @Bind(R.id.tvMyAccountTitle)
    TextView tvMyAccountTitle;*/

    @Bind(R.id.tbMyAccount)
    Toolbar tbMyAccount;

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

    @Bind(R.id.ivFilter)
    ImageView ivFilter;

    //Pop Menu to show Categories of Course Diary.
    PopupMenu popupMenu;

    Intent intent;

     /* -- INTERNET CONNECTION PARAMETERS -- */

    /**
     * Declares the click event handling FIELD to set categories
     * of Your Account Finance {@link Fragment}.
     */
    public PopupMenu.OnMenuItemClickListener mCourseTypeListener =
            new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.item_today:
                            ((FinanceFragment) getFragmentInstance()).updateFilterControl(0);
                            break;

                        case R.id.item_a_week:
                            ((FinanceFragment) getFragmentInstance()).updateFilterControl(1);
                            break;

                        case R.id.item_one_month:
                            ((FinanceFragment) getFragmentInstance()).updateFilterControl(2);
                            break;

                        case R.id.item_three_months:
                            ((FinanceFragment) getFragmentInstance()).updateFilterControl(3);
                            break;

                        case R.id.item_six_months:
                            ((FinanceFragment) getFragmentInstance()).updateFilterControl(4);
                            break;

                        case R.id.item_a_year:
                            ((FinanceFragment) getFragmentInstance()).updateFilterControl(5);
                            break;

                        case R.id.item_from_start:
                            ((FinanceFragment) getFragmentInstance()).updateFilterControl(6);
                            break;

                        case R.id.item_toggle_mode:
                            intent = new Intent(YourAccountActivity.this, EditToggleDetailActivity.class);
                            startActivity(intent);

                            break;

                        case R.id.item_edit_mode:
                            intent = new Intent(YourAccountActivity.this, EditDetailsActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //Initialize view resources.
        ButterKnife.bind(this);

        if (tbMyAccount != null) {
            setSupportActionBar(tbMyAccount);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            tbMyAccount.setNavigationIcon(R.mipmap.icon_menu);

            getSupportActionBar().setTitle("Your Account");
        }

        tfRobotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        //tvMyAccountTitle.setTypeface(tfRobotoMedium);

        //Load Default fragment of COURSE DIARY.
        updateFragment(new MyAccountTabFragment());

        initFianaceCategory();

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        popupMenu.setOnMenuItemClickListener(mCourseTypeListener);
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
     * Implements a method to initialize Finance categories in pop-up menu.
     */
    private void initFianaceCategory() {

        /**
         * Step 1: Create a new instance of popup menu
         */
        popupMenu = new PopupMenu(this, ivFilter);

        boolean isHandicapFeature = loadPreferenceBooleanValue(ApplicationGlobal.KEY_HANDICAP_FEATURE, false);

        if(isHandicapFeature){
            /**
             * Step 2: Inflate the menu resource. Here the menu resource is
             * defined in the res/menu project folder
             */
            switch (getWhichTab()) {
                case 2:
                    ivFilter.setImageResource(R.mipmap.ic_event);
                    popupMenu.inflate(R.menu.finance_menu);
                    break;

                case 0:
                case 1:
                    ivFilter.setImageResource(R.mipmap.ic_mode_edit);
                    popupMenu.inflate(R.menu.my_details_menu);
                    break;
            }
        }else{
            /**
             * Step 2: Inflate the menu resource. Here the menu resource is
             * defined in the res/menu project folder
             */
            switch (getWhichTab()) {
                case 1:
                    ivFilter.setImageResource(R.mipmap.ic_event);
                    popupMenu.inflate(R.menu.finance_menu);
                    break;

                case 0:
                    ivFilter.setImageResource(R.mipmap.ic_mode_edit);
                    popupMenu.inflate(R.menu.my_details_menu);
                    break;
            }
        }

        popupMenu.setOnMenuItemClickListener(mCourseTypeListener);

        /*//Initially display title at position 0 of R.menu.course_menu.
        tvCourseType.setText("" + popupMenu.getMenu().getItem(0));*/
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

    public void setWhichTab(int iTabPosition) {
        this.iTabPosition = iTabPosition;

        if (iTabPosition == 0 || iTabPosition == 2) {
            updateFilterIcon(View.VISIBLE);
        } else {
            updateFilterIcon(View.GONE);
        }
    }

    private int getWhichTab() {
        return this.iTabPosition;
    }

    public void updateFilterIcon(int iVisibleType) {
        ivFilter.setVisibility(iVisibleType);

        initFianaceCategory();
    }
}
