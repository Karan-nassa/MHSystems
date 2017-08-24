package com.mh.systems.demoapp.ui.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.ui.activites.BookingEntryActivity;
import com.mh.systems.demoapp.ui.activites.CompetitionEntryActivity;
import com.mh.systems.demoapp.ui.interfaces.OnUpdatePlayers;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Slot;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Zone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan@ucreate.co.in for Time slots Grid options.
 *
 * @since 01-09-2016.
 */
public class CompConfirmEntryAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;
    private Typeface tfRobotoRegular, tfRobotoBold;

    List<Zone> zoneCompEntryList;
    private Button lastSelectedView = null;

    int iSlotNo, iPosition;
    int iTeamsPerSlot;
    int iZoneNo;

    private OnUpdatePlayers mOnUpdatePlayers;

    public CompConfirmEntryAdapter(Activity mActivity, List<Zone> zoneCompEntryList, int iZoneNo, int teamsPerSlot, OnUpdatePlayers mOnUpdatePlayers) {
        context = mActivity;
        this.zoneCompEntryList = zoneCompEntryList;
        this.iTeamsPerSlot = teamsPerSlot;
        this.iZoneNo = iZoneNo;
        this.mOnUpdatePlayers = mOnUpdatePlayers;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        tfRobotoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    }

    @Override
    public int getCount() {
        return /*slotArrayList.size();*/1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Declares a view holder class to create view resources.
     */
    private class ConfirmEntryRow {
        TextView tvTimeOfComp, tvZoneOfComp;
        LinearLayout llAddConfirmPlayers;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ConfirmEntryRow confirmEntryRow = new ConfirmEntryRow();
        View rowView =  inflater.inflate(R.layout.list_row_confirm_entry, null);

        confirmEntryRow.tvTimeOfComp = (TextView) rowView.findViewById(R.id.tvTimeOfComp);
        confirmEntryRow.tvZoneOfComp = (TextView) rowView.findViewById(R.id.tvZoneOfComp);

        confirmEntryRow.llAddConfirmPlayers = (LinearLayout) rowView.findViewById(R.id.llAddConfirmPlayers);

//        confirmEntryRow.tvTimeOfComp.setText(zoneCompEntryList.get(iZoneNo).getSlots().get(position).getTeeOffTime());
        confirmEntryRow.tvTimeOfComp.setText("07:00 AM");
        confirmEntryRow.tvTimeOfComp.setTypeface(tfRobotoBold);

        for (int iCounter = 0; iCounter < iTeamsPerSlot; iCounter++) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View playerView = inflater.inflate(R.layout.inflate_row_add_player, null);

            TextView tvNameOfPlayer = (TextView) playerView.findViewById(R.id.tvNameOfPlayer);
            TextView tvPriceCost = (TextView) playerView.findViewById(R.id.tvPriceCost);

            ImageView ivRemovePlayer = (ImageView) playerView.findViewById(R.id.ivRemovePlayer);
            ivRemovePlayer.setVisibility(View.VISIBLE);
            /*String strTeamName = zoneCompEntryList.get(iZoneNo)
                    .getSlots()
                    .get(position)
                    .getTeams()
                    .get(iCounter)
                    .getTeamName();*/

            tvNameOfPlayer.setText("Greg Hadala (+5)"/*strTeamName*/);
            tvNameOfPlayer.setTypeface(tfRobotoBold);

            tvPriceCost.setVisibility(View.VISIBLE);
            tvPriceCost.setTypeface(tfRobotoRegular);

            ivRemovePlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: when user click on cross icon

                    mOnUpdatePlayers.removePlayerListener();
                }
            });

            confirmEntryRow.llAddConfirmPlayers.addView(playerView);
        }

        return rowView;
    }
}