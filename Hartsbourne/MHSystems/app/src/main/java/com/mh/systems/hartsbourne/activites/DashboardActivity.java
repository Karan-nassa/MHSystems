package com.mh.systems.hartsbourne.activites;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mh.systems.hartsbourne.R;
import com.mh.systems.hartsbourne.adapter.RecyclerAdapter.DashboardRecyclerAdapter;
import com.mh.systems.hartsbourne.constants.ApplicationGlobal;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * The {@link DashboardActivity} used to display {@link GridView}, Settings and
 * Logout option. Basically, it will be use as the main screen of application
 * after Login.
 *
 * @author {@link karan@mh.co.in}
 * @version 1.0
 */
public class DashboardActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

   /* @Bind(R.id.gvMenuOptions)
    GridView gvMenuOptions;*/
    @Bind(R.id.gvMenuOptions)
    RecyclerView gvMenuOptions;

    @Bind(R.id.llLogoutBtn)
    LinearLayout llLogoutBtn;

    @Bind(R.id.llSettings)
    LinearLayout llSettings;

    @Bind(R.id.btSendFeedback)
    Button btSendFeedback;

    //Instance of Grid Adapter.
  //  DashboardGridAdapter mDashboardGridAdapter;
   DashboardRecyclerAdapter dashboardRecyclerAdapter;
    Intent intent = null;

    TypedArray gridIcons;
    TypedArray gridBackground;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/

    String gridTitles[];

    /**
     * Set click event listener of Grid Menu Options to
     * use functionality.
     */
    private AdapterView.OnItemClickListener mGridItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    intent = new Intent(DashboardActivity.this, YourAccountActivity.class);
                    intent.putExtra("iTabPosition", 1);
                    break;
                case 1:
                    intent = null;
                    /*intent = new Intent(DashboardActivity.this, CourseDiaryActivity.class);*/
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
                    intent = new Intent(DashboardActivity.this, YourAccountActivity.class);
                    intent.putExtra("iTabPosition", 0);
                    break;
            }

            //Check if intent not NULL then navigate to that selected screen.
            if (intent != null) {
                startActivity(intent);
                intent = null;
            }else{
                showAlertMessage("Course Diary not active");
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

        // Create a grid layout with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6);

        // Create a custom SpanSizeLookup where the first item spans both columns
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 || position == 1 ? 3 : 2;
            }
        });

        setGridMenuOptions();

        // Layout Managers:
        //gvMenuOptions.setLayoutManager(new LinearLayoutManager(this));
        gvMenuOptions.setLayoutManager(layoutManager);
        // Item Decorator:
      //  gvMenuOptions.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));

       /* layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                             @Override
                                             public int getSpanSize(int position) {
                                                 if (dashboardRecyclerAdapter.getItemCount() <= 2) {
                                                     return 2;
                                                 } else *//*if (dashboardRecyclerAdapter.getItemCount() == 2) {*//*
                                                     return 3;
                                                *//* } else if (dashboardRecyclerAdapter.getItemCount() == 3) {
                                                     if (position == 0) {
                                                         return 2;
                                                     } else {
                                                         return 1;
                                                     }
                                                 } else {

                                                     return 1;

                                                 }*//*
                                             }});*/



        //Set Menu Options click event handle.
      //  gvMenuOptions.setOnItemClickListener(mGridItemListener);

        //LogOut listener.
        llLogoutBtn.setOnClickListener(mLogoutListener);

        //Settings click event handle here.
        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        //Send Feedback click event here.
        btSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DashboardActivity.this, SendFeedbackActivity.class);
                startActivity(intent);
            }
        });
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
       // mDashboardGridAdapter = new DashboardGridAdapter(this, gridTitles, gridIcons, gridBackground, loadPreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, "N/A"));
        //gvMenuOptions.setAdapter(mDashboardGridAdapter);

        dashboardRecyclerAdapter = new DashboardRecyclerAdapter(this, gridTitles, gridIcons, gridBackground, loadPreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, "N/A"));
        gvMenuOptions.setAdapter(dashboardRecyclerAdapter);

        // ScrollRecycleView.getListViewSize(gvMenuOptions);
    }
}
