package com.ucreate.mhsystems.activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.fragments.CourseDairyTabFragment;

import butterknife.ButterKnife;

public class CourseActivity extends BaseActivity {

    //Create instance to load current fragment seleted from drawer.
    Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_course);

        ButterKnife.bind(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment = new CourseDairyTabFragment();
        fragmentTransaction.replace(R.id.containerView, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Show snackBar message defined in BaseActivity.
     */
    public void showSnackMessage(String strSnackMessage) {
        Log.e("Snack:", strSnackMessage);
        //BaseActivity.showsn(cdlCourse, strSnackMessage);
    }

}
