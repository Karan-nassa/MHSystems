package com.mh.systems.demoapp.ui.adapter.BaseAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
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
import com.mh.systems.demoapp.ui.activites.CompetitionEntryActivity;
import com.mh.systems.demoapp.ui.activites.NewCompAddPlayersActivity;
import com.mh.systems.demoapp.ui.interfaces.OnUpdatePlayers;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan@ucreate.co.in for Time slots Grid options.
 *
 * @since 01-09-2016.
 */
public class CompTimeSlotsAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;
    private Typeface tfRobotoMedium, tfRobotoBold;

    private List<Slot> slotArrayList = new ArrayList<>();
    private Button lastSelectedView = null;

    int iSlotNo, iPosition;
    int iTeamsPerSlot;

    private OnUpdatePlayers mOnUpdatePlayers;

    public CompTimeSlotsAdapter(CompetitionEntryActivity mainActivity, List<Slot> slotArrayList
            , int iSlotNo, int iTeamsPerSlot
            , OnUpdatePlayers mOnUpdatePlayers) {

        context = mainActivity;
        this.slotArrayList = slotArrayList;
        this.iSlotNo = iSlotNo;
        this.iTeamsPerSlot = iTeamsPerSlot;

        this.mOnUpdatePlayers = mOnUpdatePlayers;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tfRobotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        tfRobotoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    }

    @Override
    public int getCount() {
        return slotArrayList.size();
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
    private class Holder {
        TextView tvTimeOfSlot;
        LinearLayout llViewAddTeams;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();
        View rowView = null;
        rowView = inflater.inflate(R.layout.grid_new_comp_zone, null);

        holder.tvTimeOfSlot = (TextView) rowView.findViewById(R.id.tvTimeOfSlot);
        holder.llViewAddTeams = (LinearLayout) rowView.findViewById(R.id.llViewAddTeams);


        holder.tvTimeOfSlot.setText(slotArrayList.get(position).getTeeOffTime());
        holder.tvTimeOfSlot.setTypeface(tfRobotoBold);

        for (int iCounter = 0; iCounter < iTeamsPerSlot; iCounter++) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View playerView = inflater.inflate(R.layout.inflate_row_add_player, null);

            TextView tvNameOfPlayer = (TextView) playerView.findViewById(R.id.tvNameOfPlayer);
            ImageView ivRemovePlayer = (ImageView) playerView.findViewById(R.id.ivRemovePlayer);
            TextView tvAddPlayer = (TextView) playerView.findViewById(R.id.tvAddPlayer);

            String strTeamName = slotArrayList.get(position)
                    .getTeams()
                    .get(iCounter).getTeamName();

            tvNameOfPlayer.setText(strTeamName);

            if (strTeamName.equalsIgnoreCase("(Free)")) {
                tvAddPlayer.setVisibility(View.VISIBLE);
                ivRemovePlayer.setVisibility(View.GONE);
            } else {
                tvAddPlayer.setVisibility(View.GONE);
                ivRemovePlayer.setVisibility(View.VISIBLE);
            }

            ivRemovePlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: when user click on cross icon
                }
            });

            tvAddPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: ADD Players and oepn member screen.

                    mOnUpdatePlayers.addPlayersListener();
                }
            });

            holder.llViewAddTeams.addView(playerView);
        }

        /*if (slotArrayList.get(position).getTeeOffTime()) {
            holder.btTimeSlot.setAlpha((float) 0.1);
        }*/

        /*if (slotArrayList.get(position).getSlotNo() == iSlotNo) {
            setSlotSelected(holder.btTimeSlot);
            lastSelectedView = holder.btTimeSlot;
            iPosition = position;
        }*/

      /*  holder.btTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!slotArrayList.get(position).getIsSlotReserved()) {
                    iSlotNo = (position + 1);
                    notifyDataSetChanged();

                    //Update Tee Time Slot value.
                    ((CompetitionEntryActivity) context).updateTeeTimeValue(position);
                }
            }
        });*/

        return rowView;
    }

    /**
     * Implements this method set Time slot as
     * selected.
     */
    private void setSlotSelected(Button btTimeSlot) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            btTimeSlot.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_time_buttonc0995b));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btTimeSlot.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_time_buttonc0995b));
        }
        btTimeSlot.setTextColor(Color.parseColor("#ffffff"));
    }
}