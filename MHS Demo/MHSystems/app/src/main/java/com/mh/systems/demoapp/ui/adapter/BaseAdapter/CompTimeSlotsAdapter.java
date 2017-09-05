package com.mh.systems.demoapp.ui.adapter.BaseAdapter;

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
import com.mh.systems.demoapp.ui.activites.CompetitionEntryActivity;
import com.mh.systems.demoapp.ui.interfaces.OnUpdatePlayers;
import com.mh.systems.demoapp.utils.constants.ApplicationGlobal;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Player;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Slot;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Team;

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

    private ArrayList<Slot> slotArrayList = new ArrayList<>();
    private Button lastSelectedView = null;

    int iSlotNo, iPosition;
    int iTeamsPerSlot;
    int iMaxTeamAdded, iMaxTeamCount;
    int iTeamSize;
    int iAlreadyBookSlotIdx;

    private OnUpdatePlayers mOnUpdatePlayers;

    public CompTimeSlotsAdapter(CompetitionEntryActivity mainActivity, ArrayList<Slot> slotArrayList
            , int iSlotNo, int iTeamsPerSlot
            , int MaxTeamAdded, int MaxTeamCount
            , int iAlreadyBookSlotIdx, Integer iTeamSize
            , OnUpdatePlayers mOnUpdatePlayers) {

        context = mainActivity;
        this.slotArrayList = slotArrayList;
        this.iSlotNo = iSlotNo;
        this.iTeamsPerSlot = iTeamsPerSlot;

        this.iMaxTeamAdded = MaxTeamAdded;
        this.iMaxTeamCount = MaxTeamCount;

        this.iAlreadyBookSlotIdx = iAlreadyBookSlotIdx;
        this.iTeamSize = iTeamSize;

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

        int iFreeSlotsAvail = 0;

        LinearLayout llAddTeamsRow;

        for (int iTeamCount = 0; iTeamCount < iTeamsPerSlot; iTeamCount++) {

            View addTeamView = LayoutInflater.from(context).inflate(R.layout.inflate_row_add_teams, null);

            llAddTeamsRow = (LinearLayout) addTeamView.findViewById(R.id.llAddTeamsRow);

            final ArrayList<Team> mTeamArrayList = slotArrayList.get(position).getTeams();

            String strTeamName = mTeamArrayList.get(iTeamCount).getTeamName();
            final List<Player> mPlayersArr = mTeamArrayList.get(iTeamCount).getPlayers();

            if (mPlayersArr.size() == 0) {
                View viewSinglePlayer = LayoutInflater.from(context).inflate(R.layout.inflate_add_more_players, null);

                LinearLayout llAddMoreContainer = (LinearLayout) viewSinglePlayer.findViewById(R.id.llAddMoreContainer);

                TextView tvPlayerName = (TextView) viewSinglePlayer.findViewById(R.id.tvPlayerName);
                ImageView ivPlayerRemove = (ImageView) viewSinglePlayer.findViewById(R.id.ivPlayerRemove);
                final TextView tvAddTeam = (TextView) viewSinglePlayer.findViewById(R.id.tvAddPlayer);

                tvPlayerName.setText(strTeamName);
                iFreeSlotsAvail++;

                if((iTeamSize == 4|| iTeamSize == 3) && iTeamsPerSlot == 1){
                    tvAddTeam.setText(context.getString(R.string.text_add_players));
                }else{
                    tvAddTeam.setText(context.getString(R.string.text_add_player));
                }

                /**
                 * EntryStatus equal to
                 * 0, If not booked
                 * 1, if booked by someone else
                 * 2, If booked by itself
                 */
                switch (mTeamArrayList.get(iTeamCount).getEntryStatus()) {
                    case 0:
                        if (mTeamArrayList.get(iTeamCount).isAnyUpdated()) {
                            tvAddTeam.setVisibility(View.GONE);
                            ivPlayerRemove.setVisibility(View.VISIBLE);
                        } else {
                            tvAddTeam.setVisibility(View.VISIBLE);
                            ivPlayerRemove.setVisibility(View.GONE);
                        }
                        break;

                    case 1:
                        tvAddTeam.setVisibility(View.GONE);
                        ivPlayerRemove.setVisibility(View.GONE);
                        break;

                    case 2:
                        tvAddTeam.setVisibility(View.GONE);
                        ivPlayerRemove.setVisibility(View.VISIBLE);
                        break;

                    default:

                }

                //Store Add Player position as Tag for use later.
                tvAddTeam.setTag(iTeamCount);

                ivPlayerRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mOnUpdatePlayers.removePlayerListener(
                                slotArrayList.get(position).getTeams()
                                , position
                                , Integer.parseInt(tvAddTeam.getTag().toString())
                        );

                        //Increase Free Slot val when user remove any player.
                        slotArrayList.get(position).setiFreeSlotsAvail(
                                slotArrayList.get(position).getiFreeSlotsAvail() - 1);
                    }
                });

                tvAddTeam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int iTeamPlayerPos = Integer.parseInt(tvAddTeam.getTag().toString());

                        if (iAlreadyBookSlotIdx == -1 ||
                                slotArrayList.get(position).getTeams().get(iTeamPlayerPos).getSlotIdx() == iAlreadyBookSlotIdx) {

                            if(((iTeamSize == 4|| iTeamSize == 3) && iTeamsPerSlot == 1)
                                    || (iTeamSize == 2 && iTeamsPerSlot == 2)){

                                /*mOnUpdatePlayers.addMaxPlayersAsTeamsize(
                                        slotArrayList.get(position).getTeams()
                                        , position
                                        , iTeamsPerSlot
                                        , iTeamPlayerPos
                                        , slotArrayList.get(position).getTeams().get(iTeamPlayerPos).getSlotIdx()
                                );*/
                                mOnUpdatePlayers.addorRemoveUpdateMaxTeam(
                                        mPlayersArr
                                        , slotArrayList.get(position).getTeams()
                                        , position //Slot Position
                                        , iTeamsPerSlot
                                        , Integer.parseInt(tvAddTeam.getTag().toString()) //team pos
                                        , slotArrayList.get(position).getTeams().get(iTeamPlayerPos).getSlotIdx() //SlotIdx
                                        , 0
                                        , ApplicationGlobal.ACTION_CALL_FROM_ADD //Call from
                                       );

                                iAlreadyBookSlotIdx = slotArrayList.get(position).getTeams().get(iTeamPlayerPos).getSlotIdx();

                            }else if (iMaxTeamAdded < iMaxTeamCount) {

                                mOnUpdatePlayers.addPlayersListener(slotArrayList.get(position).getTeams()
                                        , position
                                        , iTeamsPerSlot
                                        , iTeamPlayerPos
                                        , slotArrayList.get(position).getTeams().get(iTeamPlayerPos).getSlotIdx()
                                        , true);

                                iAlreadyBookSlotIdx = slotArrayList.get(position).getTeams().get(iTeamPlayerPos).getSlotIdx();

                            } else {
                                ((CompetitionEntryActivity) context).showAlertMessageOk(CompetitionEntryActivity.ACTION_TYPE_DEFAULT
                                        , context.getString(R.string.text_alert_max_limit)
                                        , context.getString(R.string.alert_title_alert));
                            }
                        } else {
                            ((CompetitionEntryActivity) context).
                                    showAlertMessageOk(CompetitionEntryActivity.ACTION_TYPE_DEFAULT,
                                            "Sorry, You can add new member at " +
                                                    slotArrayList.get(iAlreadyBookSlotIdx).getTeeOffTime() + " slot only."
                                            , context.getString(R.string.alert_title_alert));
                        }
                    }
                });

                llAddTeamsRow.addView(llAddMoreContainer);
                slotArrayList.get(position).setiFreeSlotsAvail(iFreeSlotsAvail);
            } else {
                for (int iPlayerCount = 0; iPlayerCount < mPlayersArr.size(); iPlayerCount++) {
                    LayoutInflater mInflatorAddPlayers = LayoutInflater.from(context);
                    View viewAddMorePlayer = mInflatorAddPlayers.inflate(R.layout.inflate_add_more_players, null);

                    final TextView tvPlayerName = (TextView) viewAddMorePlayer.findViewById(R.id.tvPlayerName);
                    final ImageView ivPlayerRemove = (ImageView) viewAddMorePlayer.findViewById(R.id.ivPlayerRemove);

                    tvPlayerName.setText(((CompetitionEntryActivity) context).
                            getMemberNameFromID(Integer.parseInt(mPlayersArr.get(iPlayerCount).getMemberId())));
                    tvPlayerName.setTag(iTeamCount);

                    //ivPlayerRemove.setVisibility(View.VISIBLE);
                    /**
                     * EntryStatus equal to
                     * 0, If not booked
                     * 1, if booked by someone else
                     * 2, If booked by itself
                     */
                    switch (mTeamArrayList.get(iTeamCount).getEntryStatus()) {
                        case 0:
                            if (mTeamArrayList.get(iTeamCount).isAnyUpdated()) {
                                ivPlayerRemove.setVisibility(View.VISIBLE);
                            } else {
                                ivPlayerRemove.setVisibility(View.GONE);
                            }
                            break;

                        case 1:
                            ivPlayerRemove.setVisibility(View.GONE);
                            break;

                        case 2:
                            ivPlayerRemove.setVisibility(View.VISIBLE);
                            break;

                        default:

                    }


                    final int finalITeamCount = iTeamCount;
                    final int finalIPlayerCount = iPlayerCount;
                    ivPlayerRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if((iTeamSize == 4|| iTeamSize == 3) && iTeamsPerSlot == 1) {

                                mOnUpdatePlayers.addorRemoveUpdateMaxTeam(
                                        mPlayersArr
                                        , slotArrayList.get(position).getTeams()
                                        , position //Slot Position
                                        , iTeamsPerSlot
                                        , Integer.parseInt(tvPlayerName.getTag().toString()) //team pos
                                        , slotArrayList.get(position).getTeams().get(finalITeamCount).getSlotIdx() //SlotIdx
                                        , finalIPlayerCount
                                        , ApplicationGlobal.ACTION_CALL_FROM_REMOVE //Call from
                                );
                            }else{
                                mOnUpdatePlayers.removePlayerListener(
                                        slotArrayList.get(position).getTeams()
                                        , position
                                        , finalITeamCount
                                );

                                //Increase Free Slot val when user remove any player.
                                slotArrayList.get(position).setiFreeSlotsAvail(
                                        slotArrayList.get(position).getiFreeSlotsAvail() - 1);
                            }
                        }
                    });

                    llAddTeamsRow.addView(viewAddMorePlayer);
                }
            }
            holder.llViewAddTeams.addView(llAddTeamsRow);
        }

        return rowView;
    }
}