package com.mh.systems.demoapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mh.systems.demoapp.R;

public class EditToggleDetailActivity extends BaseActivity {

    Toolbar tbEditToggleDetail;
    private Intent intent;

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

            case R.id.item_edit_mode:
                intent = new Intent(EditToggleDetailActivity.this, MyDetailsEditActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.item_toggle_mode:
                break;

            default:
                break;
        }
        return true;
    }
}
