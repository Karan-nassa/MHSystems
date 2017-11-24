package com.mh.systems.dunstabledowns.ui.adapter.BaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mh.systems.dunstabledowns.R;
import com.mh.systems.dunstabledowns.ui.activites.ConfirmBookingEntryActivity;
import com.mh.systems.dunstabledowns.ui.interfaces.OnUpdatePlayers;
import com.mh.systems.dunstabledowns.utils.constants.ApplicationGlobal;
import com.mh.systems.dunstabledowns.web.models.competitionsentrynew.Player;
import com.mh.systems.dunstabledowns.web.models.competitionsentrynew.Slot;
import com.mh.systems.dunstabledowns.web.models.competitionsentrynew.Team;

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
    int iTeamSize;

    private OnUpdatePlayers mOnUpdatePlayers;

    public CompConfirmEntryAdapter(Activity mActivity, ArrayList<Slot> mFilterSlotList,
                                   int iZoneNo, int teamsPerSlot
            , String strZoneName, String strCrnSymbol,
                                   int iTeamSize, OnUpdatePlayers mOnUpdatePlayers) {
        context = mActivity;
        this.mFilterSlotList = mFilterSlotList;
        this.strZoneName = strZoneName;
        this.iTeamsPerSlot = teamsPerSlot;
        this.iZoneNo = iZoneNo;
        this.strCrnSymbol = strCrnSymbol;
        this.mOnUpdatePlayers = mOnUpdatePlayers;

        this.iTeamSize = iTeamSize;

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

        //confirmEntryRow.tvTimeOfComp.setText(zoneCompEntryList.get(iZoneNo).getSlots().get(position).getTeeOffTime());
        confirmEntryRow.tvTimeOfComp.setText(mFilterSlotList.get(position).getTeeOffTime());
        confirmEntryRow.tvZoneName.setText(strZoneName);

        confirmEntryRow.tvTimeOfComp.setTypeface(tfRobotoBold);

        LinearLayout llAddTeamsRow;
        final ArrayList<Team> mTeamArrayList = mFilterSlotList.get(position).getTeams();

        for (int iTeamCount = 0; iTeamCount < mTeamArrayList.size(); iTeamCount++) {

            View addTeamView = LayoutInflater.from(context).inflate(R.layout.inflate_row_add_teams, null);

            llAddTeamsRow = (LinearLayout) addTeamView.findViewById(R.id.llAddTeamsRow);

            String strTeamName = mTeamArrayList.get(iTeamCount).getTeamName();
            final List<Player> mPlayersArr = mTeamArrayList.get(iTeamCount).getPlayers();

           /* if (mPlayersArr.size() == 1) {*/
            if (iTeamSize == 1) {
                View viewSinglePlayer = LayoutInflater.from(context).inflate(R.layout.inflate_add_more_players, null);

                LinearLayout llAddMoreContainer = (LinearLayout) viewSinglePlayer.findViewById(R.id.llAddMoreContainer);

                TextView tvPlayerName = (TextView) viewSinglePlayer.findViewById(R.id.tvPlayerName);
                TextView tvPriceCost = (TextView) viewSinglePlayer.findViewById(R.id.tvPriceCost);
                final TextView tvAddTeam = (TextView) viewSinglePlayer.findViewById(R.id.tvAddPlayer);

                ImageView ivPlayerRemove = (ImageView) viewSinglePlayer.findViewById(R.id.ivPlayerRemove);

                tvPlayerName.setText(((ConfirmBookingEntryActivity) context).
                        getMemberNameFromID(Integer.parseInt(mPlayersArr.get(0).getMemberId())));

                String strCostFee = "Entry fee: " + (strCrnSymbol +
                        decimalFormat.format(
                                mFilterSlotList.get(position)
                                        .getTeams().get(iTeamCount)
                                        .getEntryFee()));

                tvPriceCost.setText(strCostFee);
                tvPriceCost.setVisibility(View.VISIBLE);
                tvPriceCost.setTypeface(tfRobotoRegular);

                if ((iTeamSize == 4 || iTeamSize == 3) && iTeamsPerSlot == 1) {
                    tvAddTeam.setText(context.getString(R.string.text_add_players));
                } else {
                    tvAddTeam.setText(context.getString(R.string.text_add_player));
                }

                //Store Add Player position as Tag for use later.
                tvAddTeam.setTag(iTeamCount);
                ivPlayerRemove.setVisibility(View.VISIBLE);

                ivPlayerRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Increase Free Slot val when user remove any player.
                        mFilterSlotList.get(position).setiFreeSlotsAvail(
                                mFilterSlotList.get(position).getiFreeSlotsAvail() - 1);

                        mOnUpdatePlayers.removePlayerListener(
                                mFilterSlotList.get(position).getTeams()
                                , position
                                , Integer.parseInt(tvAddTeam.getTag().toString())
                        );
                    }
                });

                llAddTeamsRow.addView(llAddMoreContainer);
                // mFilterSlotList.get(position).setiFreeSlotsAvail(iFreeSlotsAvail);
            } else {
                for (int iPlayerCount = 0; iPlayerCount < mPlayersArr.size(); iPlayerCount++) {
                    LayoutInflater mInflatorAddPlayers = LayoutInflater.from(context);
                    View viewAddMorePlayer = mInflatorAddPlayers.inflate(R.layout.inflate_add_more_players, null);

                    final TextView tvPlayerName = (TextView) viewAddMorePlayer.findViewById(R.id.tvPlayerName);
                    final ImageView ivPlayerRemove = (ImageView) viewAddMorePlayer.findViewById(R.id.ivPlayerRemove);

                    tvPlayerName.setText(((ConfirmBookingEntryActivity) context).
                            getMemberNameFromID(Integer.parseInt(mPlayersArr.get(iPlayerCount).getMemberId())));
                    tvPlayerName.setTag(iTeamCount);

                    ivPlayerRemove.setVisibility(View.VISIBLE);

                    final int finalITeamCount = iTeamCount;
                    final int finalIPlayerCount = iPlayerCount;
                    ivPlayerRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if ((iTeamSize == 4 || iTeamSize == 3) && iTeamsPerSlot == 1) {

                                mOnUpdatePlayers.addorChangePlayerUpdateMaxTeam(
                                        mPlayersArr
                                        , mFilterSlotList.get(position).getTeams()
                                        , position //Slot Position
                                        , iTeamsPerSlot
                                        , Integer.parseInt(tvPlayerName.getTag().toString()) //team pos
                                        , mFilterSlotList.get(position).getTeams().get(finalITeamCount).getSlotIdx() //SlotIdx
                                        , finalIPlayerCount
                                        , ApplicationGlobal.ACTION_CALL_FROM_REMOVE //Call from
                                );
                            } else {

                                //Increase Free Slot val when user remove any player.
                                mFilterSlotList.get(position).setiFreeSlotsAvail(
                                        mFilterSlotList.get(position).getiFreeSlotsAvail() - 1);

                                //Increase Free Slot val when user remove any player.
                                mOnUpdatePlayers.removePlayerListener(
                                        mFilterSlotList.get(position).getTeams()
                                        , position
                                        , finalITeamCount
                                );
                            }
                        }
                    });

                    llAddTeamsRow.addView(viewAddMorePlayer);
                }
            }

            View view = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 2);
            params.setMargins(0, 10, 0, 0);
            view.setLayoutParams(params);
            view.setBackgroundColor(Color.parseColor("#AAAAAA"));
            llAddTeamsRow.addView(view);

            confirmEntryRow.llAddConfirmPlayers.addView(llAddTeamsRow);
        }
        return rowView;
    }
}