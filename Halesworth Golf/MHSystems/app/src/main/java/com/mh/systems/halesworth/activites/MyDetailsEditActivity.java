package com.mh.systems.halesworth.activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mh.systems.halesworth.R;

public class MyDetailsEditActivity extends BaseActivity {

    Toolbar tbMyDetailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details_edit);

        tbMyDetailEdit = (Toolbar) findViewById(R.id.tbMyDetailEdit);
        if (tbMyDetailEdit != null) {
            setSupportActionBar(tbMyDetailEdit);
           // getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           // tbMyDetailEdit.setTitle("Edit details");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return true;
    }
}