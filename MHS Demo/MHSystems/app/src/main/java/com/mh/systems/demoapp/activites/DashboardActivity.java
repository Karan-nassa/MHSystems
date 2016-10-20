package com.mh.systems.demoapp.activites;

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
import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.adapter.BaseAdapter.DashboardGridAdapter;
import com.mh.systems.demoapp.adapter.RecyclerAdapter.DashboardRecyclerAdapter;
import com.mh.systems.demoapp.constants.ApplicationGlobal;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * The {@link DashboardActivity} used to display {@link GridView}, Settings and
 * Logout option. Basically, it will be use as the main screen of application
 * after Login.
 *
 * @author {@link karan@ucreate.co.in}
 * @version 1.0
 */
public class DashboardActivity extends BaseActivity {

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/

    @Bind(R.id.gvMenuOptions)
    RecyclerView gvMenuOptions;

    @Bind(R.id.llLogoutBtn)
    LinearLayout llLogoutBtn;

    @Bind(R.id.llSettings)
    LinearLayout llSettings;

    @Bind(R.id.btSendFeedback)
    Button btSendFeedback;

    //Instance of Grid Adapter.
    DashboardRecyclerAdapter dashboardRecyclerAdapter;
    Intent intent = null;

    TypedArray gridBackground;

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    ArrayList<DashboardItems> dashboardItemsArrayList = new ArrayList<>();

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
                return 2;
            }
        });

        // int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        // gvMenuOptions.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        setGridMenuOptions();

        // Layout Managers:
        gvMenuOptions.setLayoutManager(layoutManager);

        //LogOut listener.
        llLogoutBtn.setOnClickListener(mLogoutListener);

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

        //Add Handicap.
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_HANDICAP_FEATURE, false)) {

            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_handicap_chart,
                    "Your Handicap",
                    "com.mh.systems.demoapp.activites.YourAccountActivity"));
        }

        //Add Course Diary.
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_COURSE_DIARY_FEATURE, false)) {

            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_diary,
                    "Course Diary",
                    "com.mh.systems.demoapp.activites.CourseDiaryActivity"));
        }

        //Add Competitions
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_COMPETITIONS_FEATURE, false)) {

            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_competitions,
                    "Competitions",
                    "com.mh.systems.demoapp.activites.CompetitionsActivity"));
        }

        //Add Members
        dashboardItemsArrayList.add(new DashboardItems(
                R.mipmap.ic_home_members,
                "Members",
                "com.mh.systems.demoapp.activites.MembersActivity"));

        //Add Club News
        dashboardItemsArrayList.add(new DashboardItems(
                R.mipmap.ic_home_clubnews,
                "Club News",
                "com.mh.systems.demoapp.activites.ClubNewsActivity"));

        //Add Finance/Your Details
        dashboardItemsArrayList.add(new DashboardItems(
                R.mipmap.ic_my_account,
                "Your Account",
                "com.mh.systems.demoapp.activites.YourAccountActivity"));

        //Setup Titles and Icons of Navigation Drawer
        //gridTitles = getResources().getStringArray(R.array.homeGridItems);
        // gridIcons = getResources().obtainTypedArray(R.array.HomeGridIcons);
        //gridBackground = getResources().obtainTypedArray(R.array.gridBackgroundColors);

        //Set Grid options adapter.
        dashboardRecyclerAdapter = new DashboardRecyclerAdapter(this, dashboardItemsArrayList, loadPreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, "N/A"));
        gvMenuOptions.setAdapter(dashboardRecyclerAdapter);

        // ScrollRecycleView.getListViewSize(gvMenuOptions);
    }

    /**
     * Create this class to decorate Dashboard Grid items spacing.
     * Because above two grid items should be in center of below
     * three one.
     */
   /* public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
           // outRect.left = space;
            //outRect.right = space;
           // outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.left = space;
            } else if (parent.getChildLayoutPosition(view) == 1)  {
                outRect.right = space;
            }
        }
    }*/

    public class DashboardItems {
        int iGridIcon;
        String strTitleOfGrid;
        String strTagOfGrid;

        public DashboardItems(int iGridIcon, String strTitleOfGrid, String strTagOfGrid) {
            this.iGridIcon = iGridIcon;
            this.strTitleOfGrid = strTitleOfGrid;
            this.strTagOfGrid = strTagOfGrid;
        }

        public String getStrTagOfGrid() {
            return strTagOfGrid;
        }

        public void setStrTagOfGrid(String strTagOfGrid) {
            this.strTagOfGrid = strTagOfGrid;
        }

        public int getiGridIcon() {
            return iGridIcon;
        }

        public void setiGridIcon(int iGridIcon) {
            this.iGridIcon = iGridIcon;
        }

        public String getStrTitleOfGrid() {
            return strTitleOfGrid;
        }

        public void setStrTitleOfGrid(String strTitleOfGrid) {
            this.strTitleOfGrid = strTitleOfGrid;
        }
    }
}
