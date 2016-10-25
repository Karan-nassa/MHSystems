package com.mh.systems.porterspark.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mh.systems.porterspark.R;
import com.mh.systems.porterspark.adapter.RecyclerAdapter.DashboardRecyclerAdapter;
import com.mh.systems.porterspark.constants.ApplicationGlobal;

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

    /*********************************
     * INSTANCES OF LOCAL DATA TYPE
     *******************************/
    ArrayList<DashboardItems> dashboardItemsArrayList = new ArrayList<>();

    int iHandicapPosition = -1;

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
     * Implements a method to set Grid MENU options
     * dynamically.
     */
    private void setGridMenuOptions() {

        //Add Handicap.
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_HANDICAP_FEATURE, false)) {

            iHandicapPosition = 0;

            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_handicap_chart,
                    "Your Handicap",
                    getApplicationContext().getPackageName() + ".activites.YourAccountActivity"));
        }

        //Add Course Diary.
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_COURSE_DIARY_FEATURE, false)) {

            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_diary,
                    "Course Diary",
                    getApplicationContext().getPackageName() + ".activites.CourseDiaryActivity"));
        }

        //Add Competitions
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_COMPETITIONS_FEATURE, false)) {

            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_competitions,
                    "Competitions",
                    getApplicationContext().getPackageName() + ".activites.CompetitionsActivity"));
        }

        //Add Members
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_MEMBERS_FEATURE, false)) {
            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_members,
                    "Members",
                    getApplicationContext().getPackageName() + ".activites.MembersActivity"));
        }

        //Add Club News
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_CLUB_NEWS_FEATURE, false)) {
            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_home_clubnews,
                    "Club News",
                    getApplicationContext().getPackageName() + ".activites.ClubNewsActivity"));
        }

        //Add Finance/Your Details
        if (loadPreferenceBooleanValue(ApplicationGlobal.KEY_YOUR_ACCOUNT_FEATURE, false)) {
            dashboardItemsArrayList.add(new DashboardItems(
                    R.mipmap.ic_my_account,
                    "Your Account",
                    getApplicationContext().getPackageName() + ".activites.YourAccountActivity"));
        }

        //Set Grid options adapter.
        dashboardRecyclerAdapter = new DashboardRecyclerAdapter(this, dashboardItemsArrayList, iHandicapPosition, loadPreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, "N/A"));
        gvMenuOptions.setAdapter(dashboardRecyclerAdapter);

        setupGridLayout(dashboardItemsArrayList.size());

        // ScrollRecycleView.getListViewSize(gvMenuOptions);
    }

    /**
     * Implements this method to set Layout of dashboard
     * Grid.
     */
    private void setupGridLayout(int iGridSize) {
        // Create a grid layout with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6);

        switch (iGridSize) {
            case 3:
                // Create a custom SpanSizeLookup where the first item spans both columns
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return position == 0 ? 6 : 3;
                    }
                });
                break;

            case 4:
                // Create a custom SpanSizeLookup where the first item spans both columns
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 3;
                    }
                });
                break;

            case 5:
                // Create a custom SpanSizeLookup where the first item spans both columns
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return position == 0 || position == 1 ? 3 : 2;
                    }
                });
                break;

            default:
                // Create a custom SpanSizeLookup where the first item spans both columns
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return 2;
                    }
                });
                break;
        }


        // int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        // gvMenuOptions.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        // Layout Managers:
        gvMenuOptions.setLayoutManager(layoutManager);
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

    /**
     * {@link DashboardItems} class is used to create Model of
     * dashboard items are icon, title and path of class.
     */
    public class DashboardItems {
        int iGridIcon;
        String strTitleOfGrid;
        String strTagOfGrid;

        /**
         * @param iGridIcon      : Grid icon (drawable).
         * @param strTitleOfGrid : Name or Title of Grid item.
         * @param strTagOfGrid   : Tag or Path of destination class.
         */
        DashboardItems(int iGridIcon, String strTitleOfGrid, String strTagOfGrid) {
            this.iGridIcon = iGridIcon;
            this.strTitleOfGrid = strTitleOfGrid;
            this.strTagOfGrid = strTagOfGrid;
        }

        /**
         * @return The strTagOfGrid
         */
        public String getStrTagOfGrid() {
            return strTagOfGrid;
        }

        /**
         * @param strTagOfGrid The strTagOfGrid
         */
        public void setStrTagOfGrid(String strTagOfGrid) {
            this.strTagOfGrid = strTagOfGrid;
        }

        /**
         * @return The iGridIcon
         */
        public int getiGridIcon() {
            return iGridIcon;
        }

        /**
         * @param iGridIcon The iGridIcon
         */
        public void setiGridIcon(int iGridIcon) {
            this.iGridIcon = iGridIcon;
        }

        /**
         * @return The strTitleOfGrid
         */
        public String getStrTitleOfGrid() {
            return strTitleOfGrid;
        }

        /**
         * @param strTitleOfGrid The strTitleOfGrid
         */
        public void setStrTitleOfGrid(String strTitleOfGrid) {
            this.strTitleOfGrid = strTitleOfGrid;
        }
    }
}
