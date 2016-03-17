package com.ucreate.mhsystems.activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.fragments.CourseDairyTabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseActivity extends BaseActivity {

    //Create instance to load current fragment seleted from drawer.
    Fragment fragment;

    @Bind(R.id.llHomeIcon)
    LinearLayout llHomeIcon;

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
        setContentView(R.layout.activity_content_course);

        //Initialize view resources.
        ButterKnife.bind(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = new CourseDairyTabFragment();
        fragmentTransaction.replace(R.id.containerView, fragment);
        fragmentTransaction.commit();

        //HOME icons press listener.
        llHomeIcon.setOnClickListener(mHomePressListener);
    }

    /**
     * Show snackBar message defined in BaseActivity.
     */
    public void showSnackMessage(String strSnackMessage) {
        Log.e("Snack:", strSnackMessage);
        //BaseActivity.showsn(cdlCourse, strSnackMessage);
    }

}
