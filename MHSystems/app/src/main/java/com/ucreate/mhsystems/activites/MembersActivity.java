package com.ucreate.mhsystems.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ucreate.mhsystems.R;
import com.ucreate.mhsystems.adapter.BaseAdapter.AlphabetListAdapter;
import com.ucreate.mhsystems.adapter.BaseAdapter.CustomSpinnerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class MembersActivity extends AppCompatActivity {

    /*********************************
     * DECLARATION OF CONSTANTS
     *******************************/


    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    private Toolbar toolbar;
    private Spinner spinner_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);


        setupToolBar();
    }

    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBarMembers);
        spinner_nav = (Spinner) findViewById(R.id.spinner_nav);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        addItemsToSpinner();
    }

    // add items into spinner dynamically
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

  /*
   * ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this,
   * android.R.layout.simple_spinner_item, list);
   * spinAdapter.setDropDownViewResource
   * (android.R.layout.simple_spinner_dropdown_item);
   */

        spinner_nav.setAdapter(spinAdapter);

        spinner_nav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();

                // Showing selected spinner item
//                Toast.makeText(getApplicationContext(), "Selected  : " + item,
//                        Toast.LENGTH_LONG).show();
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
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_today) {
//            Toast.makeText(getApplicationContext(), "Settings Clicked",
//                    Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (id == R.id.action_search) {
//            Toast.makeText(getApplicationContext(), "Search Clicked",
//                    Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (id == R.id.action_add) {
//            Toast.makeText(getApplicationContext(), "Add Clicked",
//                    Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (id == R.id.action_delete) {
//            Toast.makeText(getApplicationContext(), "Delete Clicked",
//                    Toast.LENGTH_SHORT).show();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
