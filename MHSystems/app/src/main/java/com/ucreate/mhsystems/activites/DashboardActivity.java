package com.ucreate.mhsystems.activites;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.JsonObject;
import com.newrelic.com.google.gson.reflect.TypeToken;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.GridAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.constants.WebAPI;
import com.ucreate.mhsystems.models.AJsonParamsDashboard;
import com.ucreate.mhsystems.models.DashboardAPI;
import com.ucreate.mhsystems.models.LoginData;
import com.ucreate.mhsystems.models.LoginItems;
import com.ucreate.mhsystems.util.API.WebServiceMethods;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class DashboardActivity extends BaseActivity {

    private static final String LOG_TAG = DashboardActivity.class.getSimpleName();

    @Bind(R.id.gvMenuOptions)
    GridView gvMenuOptions;

    //Instance of Grid Adapter.
    GridAdapter mGridAdapter;
    Intent intent = null;

    TypedArray gridIcons;
    String gridTitles[];
    TypedArray gridBackground;

    //List of type books this list will store type Book which is our data model
    private DashboardAPI dashboardAPI;
    AJsonParamsDashboard aJsonParamsDashboard;

    LoginItems dashboardItems;
    LoginData dashboardData;

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
        mGridAdapter = new GridAdapter(this, gridTitles, gridIcons, gridBackground, loadPreferenceValue(ApplicationGlobal.KEY_HCAP_EXACT_STR, "N/A"));
        gvMenuOptions.setAdapter(mGridAdapter);

        // ScrollRecycleView.getListViewSize(gvMenuOptions);
    }
}
