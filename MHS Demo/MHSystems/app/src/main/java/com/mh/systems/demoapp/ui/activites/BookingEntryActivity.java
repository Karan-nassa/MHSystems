package com.mh.systems.demoapp.ui.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mh.systems.demoapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookingEntryActivity extends AppCompatActivity {

    @Bind(R.id.tbBookingEntry)
    Toolbar tbBookingEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_entry);

        ButterKnife.bind(this);

        if (tbBookingEntry != null) {
            setSupportActionBar(tbBookingEntry);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_save, menu);
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
