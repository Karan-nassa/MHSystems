package com.ucreate.mhsystems.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.CustomSpinnerAdapter;
import com.ucreate.mhsystems.constants.ApplicationGlobal;
import com.ucreate.mhsystems.fragments.MembersFragment;
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
                switch (position) {
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
    protected void onDestroy() {
        super.onDestroy();

        if (MembersFragment.mAdapter != null) {
            MembersFragment.mAdapter.mAsyncTaskThreadPool.cancelAllTasks(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_members, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                Log.e("onQueryTextSubmit", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                Log.e("onQueryTextChange", newText);
                performSearch(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Implements a method to Quick search bar
     * in tool bar.
     */
    public void performSearch(final String queryText) {
        MembersFragment.mAdapter.getFilter().filter(queryText);
        MembersFragment.mAdapter.setHeaderViewVisible(TextUtils.isEmpty(queryText));
        MembersFragment.mAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        String url = null;
//        switch (item.getItemId()) {
//            case R.id.menuItem_all_my_apps:
//                url = "https://play.google.com/store/apps/developer?id=AndroidDeveloperLB";
//                break;
//            case R.id.menuItem_all_my_repositories:
//                url = "https://github.com/AndroidDeveloperLB";
//                break;
//            case R.id.menuItem_current_repository_website:
//                url = "https://github.com/AndroidDeveloperLB/ListViewVariants";
//                break;
//        }
        if (url == null)
            return true;
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
        return true;
    }
}
