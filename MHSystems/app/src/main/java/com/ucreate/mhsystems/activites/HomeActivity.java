package com.ucreate.mhsystems.activites;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.GridAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.gvMenuOptions)
    GridView gvMenuOptions;

    //Instance of Grid Adapter.
    GridAdapter mGridAdapter;
    Intent intent;

    TypedArray gridIcons;
    String gridTitles[];
    TypedArray gridBackground;

    /**
     * Set click event listener of
     * Grid Menu Options.
     */
    private AdapterView.OnItemClickListener mGridItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 1:
                    intent = new Intent(HomeActivity.this, CourseActivity.class);
                    //Navigate to ACTIVITY.
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(HomeActivity.this); //Initialize facebook Fresco for round profile pic.
        setContentView(R.layout.activity_home);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(HomeActivity.this);

        //Set MENU Grid Options.
        setGridMenuOptions();

        //Set Menu Options click event handle.
        gvMenuOptions.setOnItemClickListener(mGridItemListener);
    }

    /**
     * Implements a method to set Grid
     * MENU options.
     */
    private void setGridMenuOptions() {

        //Setup Titles and Icons of Navigation Drawer
        gridTitles = getResources().getStringArray(R.array.navDrawerItems);
        gridIcons = getResources().obtainTypedArray(R.array.navDrawerIcons);
        gridBackground = getResources().obtainTypedArray(R.array.gridBackgroundColors);

        //Set Grid options adapter.
        mGridAdapter = new GridAdapter(this, gridTitles, gridIcons, gridBackground);
        gvMenuOptions.setAdapter(mGridAdapter);
        // ScrollRecycleView.getListViewSize(gvMenuOptions);
    }
}
