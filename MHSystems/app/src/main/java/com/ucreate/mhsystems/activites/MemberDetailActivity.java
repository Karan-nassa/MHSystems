package com.ucreate.mhsystems.activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.constants.ApplicationGlobal;

public class MemberDetailActivity extends AppCompatActivity {

    public static final String LOG_TAG = MemberDetailActivity.class.getSimpleName();

    LinearLayout llMembersDetailBack;

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
        setContentView(R.layout.activity_member_detail);

        llMembersDetailBack = (LinearLayout) findViewById(R.id.llMembersDetailBack);

        Log.e(LOG_TAG, ""+getIntent().getExtras().getString(ApplicationGlobal.KEY_MEMBER_ID));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Set click listener events declaration.
        llMembersDetailBack.setOnClickListener(mHomePressListener);
    }

}
