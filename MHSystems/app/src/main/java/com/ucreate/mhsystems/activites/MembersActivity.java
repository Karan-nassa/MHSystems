package com.ucreate.mhsystems.activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.CustomSpinnerAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.fragments.MembersTabFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MembersActivity extends BaseActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/


    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    private Toolbar toolbar;
    private Spinner spinner_nav;

    @Bind(R.id.llHomeMembers)
    LinearLayout llHomeMembers;

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
        setContentView(R.layout.activity_members);

        //Initialize view resources.
        ButterKnife.bind(this);

        /**
         *  Setup Tool bar of Members screen with DROP-DOWN [SPINNER]
         *  and SEARCH bar icon.
         */
        setupToolBar();

        //Load Default fragment of Members Activity.
        updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_MEMBERS_ALL));

        //Set click listener events declaration.
        llHomeMembers.setOnClickListener(mHomePressListener);
    }

    /**
     * Implements a method to define SPINNER and
     * SEARCH bar icon.
     */
    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBarMembers);
        spinner_nav = (Spinner) findViewById(R.id.spinner_nav);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        addItemsToSpinner();
    }

    /**
     * Implements a method to setup default Spinner items
     * with All, Ladies and Gentlemen's options.
     */
    public void addItemsToSpinner() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("All");
        list.add("Ladies");
        list.add("Gentlemens");

        // Custom ArrayAdapter with spinner item layout to set popup background

        CustomSpinnerAdapter spinAdapter = new CustomSpinnerAdapter(
                getApplicationContext(), list);


        // Default ArrayAdapter with default spinner item layout, getting some
        // view rendering problem in lollypop device, need to test in other
        // devices

      /* ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this,
      android.R.layout.simple_spinner_item, list);
      spinAdapter.setDropDownViewResource
      (android.R.layout.simple_spinner_dropdown_item);
        */
        spinner_nav.setAdapter(spinAdapter);

        spinner_nav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();
                switch (position){
                    case ApplicationGlobal.ACTION_MEMBERS_ALL:
                        updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_MEMBERS_ALL));
                        break;

                    case ApplicationGlobal.ACTION_MEMBERS_LADIES:
                        updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_MEMBERS_LADIES));
                        break;

                    case ApplicationGlobal.ACTION_MEMBERS_GENTLEMENS:
                        updateFragment(new MembersTabFragment(ApplicationGlobal.ACTION_MEMBERS_GENTLEMENS));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_members, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
