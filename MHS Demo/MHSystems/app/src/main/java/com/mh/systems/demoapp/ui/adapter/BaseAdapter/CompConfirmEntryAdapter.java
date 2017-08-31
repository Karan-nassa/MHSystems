package com.mh.systems.demoapp.ui.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.demoapp.R;
import com.mh.systems.demoapp.ui.activites.ConfirmBookingEntryActivity;
import com.mh.systems.demoapp.ui.interfaces.OnUpdatePlayers;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Slot;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Zone;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan@ucreate.co.in for Time slots Grid options.
 *
 * @since 01-09-2016.
 */
public class CompConfirmEntryAdapter extends BaseAdapter {

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    Context context;
    private static LayoutInflater inflater = null;
    private Typeface tfRobotoRegular, tfRobotoBold;

    private ArrayList<Slot> mFilterSlotList;

    String strZoneName, strCrnSymbol;

    int iSlotNo, iPosition;
    int iTeamsPerSlot;
    int iZoneNo;

    private OnUpdatePlayers mOnUpdatePlayers;

    public CompConfirmEntryAdapter(Activity mActivity, ArrayList<Slot> mFilterSlotList,
                                   int iZoneNo, int teamsPerSlot
            , String strZoneName, String strCrnSymbol,
                                   OnUpdatePlayers mOnUpdatePlayers) {
        context = mActivity;
        this.mFilterSlotList = mFilterSlotList;
        this.strZoneName = strZoneName;
        this.iTeamsPerSlot = teamsPerSlot;
        this.iZoneNo = iZoneNo;
        this.strCrnSymbol = strCrnSymbol;
        this.mOnUpdatePlayers = mOnUpdatePlayers;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tfRobotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        tfRobotoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    }

    @Override
    public int getCount() {
        return mFilterSlotList.size();
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
        TextView tvTimeOfComp, tvZoneName;
        LinearLayout llAddConfirmPlayers;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ConfirmEntryRow confirmEntryRow = new ConfirmEntryRow();
        View rowView = inflater.inflate(R.layout.list_row_confirm_entry, null);

        confirmEntryRow.tvTimeOfComp = (TextView) rowView.findViewById(R.id.tvTimeOfComp);
        confirmEntryRow.tvZoneName = (TextView) rowView.findViewById(R.id.tvZoneName);

        confirmEntryRow.llAddConfirmPlayers = (LinearLayout) rowView.findViewById(R.id.llAddConfirmPlayers);

//        confirmEntryRow.tvTimeOfComp.setText(zoneCompEntryList.get(iZoneNo).getSlots().get(position).getTeeOffTime());
        confirmEntryRow.tvTimeOfComp.setText(mFilterSlotList.get(position).getTeeOffTime());
        confirmEntryRow.tvZoneName.setText(strZoneName);

        confirmEntryRow.tvTimeOfComp.setTypeface(tfRobotoBold);

        for (int iTeamCount = 0; iTeamCount < mFilterSlotList.get(position)
                .getTeams().size(); iTeamCount++) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View playerView = inflater.inflate(R.layout.inflate_row_add_player, null);

            final TextView tvNameOfPlayer = (TextView) playerView.findViewById(R.id.tvNameOfPlayer);
            TextView tvPriceCost = (TextView) playerView.findViewById(R.id.tvPriceCost);

            ImageView ivRemovePlayer = (ImageView) playerView.findViewById(R.id.ivRemovePlayer);
            ivRemovePlayer.setVisibility(View.VISIBLE);
            /*String strTeamName = zoneCompEntryList.get(iZoneNo)
                    .getSlots()
                    .get(position)
                    .getTeams()
                    .get(iCounter)
                    .getTeamName();*/

            tvNameOfPlayer.setText(mFilterSlotList.get(position)
                    .getTeams().get(iTeamCount).getTeamName());
            tvNameOfPlayer.setTypeface(tfRobotoBold);
            tvNameOfPlayer.setTag(iTeamCount);

            String strCostFee = "Entry fee: " +(strCrnSymbol +
                    decimalFormat.format(
                    mFilterSlotList.get(position)
                            .getTeams().get(iTeamCount)
                            .getEntryFee()));

            tvPriceCost.setText(strCostFee);
            tvPriceCost.setVisibility(View.VISIBLE);
            tvPriceCost.setTypeface(tfRobotoRegular);

            ivRemovePlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Increase Free Slot val when user remove any player.
                    mFilterSlotList.get(position).setiFreeSlotsAvail(
                            mFilterSlotList.get(position).getiFreeSlotsAvail() - 1);

                    //TODO: when user click on cross icon
                    mOnUpdatePlayers.removePlayerListener(
                            mFilterSlotList.get(position).getTeams()
                            , position
                            , Integer.parseInt(tvNameOfPlayer.getTag().toString())
                    );
                }
            });

            confirmEntryRow.llAddConfirmPlayers.addView(playerView);
        }

        return rowView;
    }
}