package com.ucreate.mhsystems.activites;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.DashboardGridAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import butterknife.Bind;
import butterknife.ButterKnife;

public class DashboardActivity extends BaseActivity {

    @Bind(R.id.gvMenuOptions)
    GridView gvMenuOptions;

    @Bind(R.id.llLogoutBtn)
    LinearLayout llLogoutBtn;

    //Instance of Grid Adapter.
    DashboardGridAdapter mDashboardGridAdapter;
    Intent intent = null;

    TypedArray gridIcons;
    String gridTitles[];
    TypedArray gridBackground;

    /**
     * Set click event listener of Grid Menu Options to
     * use functionality.
     */
    private AdapterView.OnItemClickListener mGridItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    intent = new Intent(DashboardActivity.this, MyAccountActivity.class);
                    intent.putExtra("iTabPosition", 1);
                    break;
                case 1:
                    intent = new Intent(DashboardActivity.this, CourseDiaryActivity.class);
                    break;
                case 2:
                    intent = new Intent(DashboardActivity.this, CompetitionsActivity.class);
                    break;
                case 3:
                    intent = new Intent(DashboardActivity.this, MembersActivity.class);
                    break;

                case 4:
                    intent = new Intent(DashboardActivity.this, ClubNewsActivity.class);
                    break;

                case 5:
                    intent = new Intent(DashboardActivity.this, MyAccountActivity.class);
                    intent.putExtra("iTabPosition", 0);
                    break;
            }

            //Check if intent not NULL then navigate to that selected screen.
            if (intent != null) {
                startActivity(intent);
                intent = null;
            }
        }
    };

    /**
     * Logout user from app and navigate back to
     * Login screen.
     */
    private View.OnClickListener mLogoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /**
             *  Clear shared-preference memory.
             */
            clearAutoPreference();

            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(DashboardActivity.this); //Initialize facebook Fresco for round profile pic.
        setContentView(R.layout.activity_dashboard);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(DashboardActivity.this);

        setGridMenuOptions();

        //Set Menu Options click event handle.
        gvMenuOptions.setOnItemClickListener(mGridItemListener);

        //LogOut listener.
        llLogoutBtn.setOnClickListener(mLogoutListener);
    }

    /**
     * Implements a method to set Grid MENU options.
     */
    private void setGridMenuOptions() {

        //Setup Titles and Icons of Navigation Drawer
        gridTitles = getResources().getStringArray(R.array.homeGridItems);
        gridIcons = getResources().obtainTypedArray(R.array.HomeGridIcons);
        gridBackground = getResources().obtainTypedArray(R.array.gridBackgroundColors);

        //Set Grid options adapter.
        mDashboardGridAdapter = new DashboardGridAdapter(this, gridTitles, gridIcons, gridBackground, loadPreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, "N/A"));
        gvMenuOptions.setAdapter(mDashboardGridAdapter);

        // ScrollRecycleView.getListViewSize(gvMenuOptions);
    }
}
