package com.mh.systems.demoapp.ui.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.ui.adapter.BaseAdapter.CompConfirmEntryAdapter;
import com.mh.systems.demoapp.ui.interfaces.OnUpdatePlayers;
import com.mh.systems.demoapp.utils.ExpandableHeightGridView;
import com.mh.systems.demoapp.web.models.competitionsentrynew.NewCompEntryData;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Zone;
import com.newrelic.com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookingEntryActivity extends AppCompatActivity implements
        View.OnClickListener, OnUpdatePlayers {

    @Bind(R.id.tbBookingEntry)
    Toolbar tbBookingEntry;

    @Bind(R.id.tvTitleOfComp)
    TextView tvTitleOfComp;

    @Bind(R.id.tvTimeOfComp)
    TextView tvTimeOfComp;

    @Bind(R.id.tvTotalCost)
    TextView tvTotalCost;

    @Bind(R.id.gvCompEntry)
    ExpandableHeightGridView gvCompEntry;

    @Bind(R.id.llAddPlayer)
    LinearLayout llAddPlayer;

    private NewCompEntryData newCompEntryData;
    private CompConfirmEntryAdapter compConfirmEntryAdapter;

    List<Zone> zoneCompEntryList = new ArrayList<>();
    int iZoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_entry);

        ButterKnife.bind(this);

        initalizeUI();

        updateTotalPrice(10);
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

    @Override
    public void addPlayersListener() {

    }

    @Override
    public void removePlayerListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.llAddPlayer:
                    onBackPressed();
                break;
        }
    }

    public void updateTotalPrice(int iTotalPrice) {
        tvTotalCost.setText((newCompEntryData.getCrnSymbol()
                + iTotalPrice));
    }

    private void initalizeUI() {
        if (tbBookingEntry != null) {
            setSupportActionBar(tbBookingEntry);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        iZoneNo = getIntent().getExtras().getInt("iZoneNo");
        String jsonNewCompEntryData = getIntent().getExtras().getString("RESPONSE_GET_CLUBEVENT_ENTRY_DATA");
        newCompEntryData = new Gson().fromJson(jsonNewCompEntryData, NewCompEntryData.class);

        tvTitleOfComp.setText(newCompEntryData.getEventName());
        tvTimeOfComp.setText(newCompEntryData.getEventStartDate().getFullDateStr());

        gvCompEntry.setExpanded(true);
        compConfirmEntryAdapter = new CompConfirmEntryAdapter(BookingEntryActivity.this,
                zoneCompEntryList,
                iZoneNo,
                newCompEntryData.getZones().get(iZoneNo).getTeamsPerSlot(),
                BookingEntryActivity.this);
        gvCompEntry.setAdapter(compConfirmEntryAdapter);

        llAddPlayer.setOnClickListener(this);
    }
}
