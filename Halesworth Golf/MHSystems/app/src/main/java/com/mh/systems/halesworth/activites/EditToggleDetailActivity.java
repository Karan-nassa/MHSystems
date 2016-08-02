package com.mh.systems.halesworth.activites;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mh.systems.halesworth.R;

public class EditToggleDetailActivity extends BaseActivity {

    Toolbar tbEditToggleDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_toggle_detail);

        tbEditToggleDetail = (Toolbar) findViewById(R.id.tbEditToggleDetail);
        if (tbEditToggleDetail != null) {
            setSupportActionBar(tbEditToggleDetail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_privacy, menu);
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
