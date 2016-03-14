package com.ucreate.mhsystems.activites;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.GridAdapter;
import com.ucreate.mhsystems.utils.ScrollRecycleView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.gvMenuOptions)
    GridView gvMenuOptions;

    Context context;

    public static String[] prgmNameList = {"Your Handicap", "Course Diary", "Competitions", "Members", "About The Club", "My Account"
            ,"Your Handicap", "Course Diary", "Competitions"};
    public static int[] prgmImages = {R.drawable.ic_handicap_red,
            R.drawable.ic_home_diary,
            R.drawable.ic_home_competitions,
            R.drawable.ic_home_members,
            R.drawable.icon_home_golfclub,
            R.drawable.ic_home_myaccount
    , R.drawable.ic_home_members,
            R.drawable.icon_home_golfclub,
            R.drawable.ic_home_myaccount};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         * Annotate fields with @Bind and a view ID for Butter Knife to find and
         * automatically cast the corresponding view in your layout.
         */
        ButterKnife.bind(this);

        //Set Grid options adapter.
        gvMenuOptions.setAdapter(new GridAdapter(this, prgmNameList, prgmImages));
        ScrollRecycleView.getListViewSize(gvMenuOptions);
    }
}
