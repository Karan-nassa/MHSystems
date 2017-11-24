package com.mh.systems.dunstabledowns.ui.interfaces;

import com.mh.systems.dunstabledowns.web.models.competitionsentrynew.Player;
import com.mh.systems.dunstabledowns.web.models.competitionsentrynew.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 24-08-2017.
 */

public interface OnUpdatePlayers {

    void addPlayersListener(ArrayList<Team> teams, int slotPosition, int iTeamPerSlot, int iAddPlayerPosition, int iAlreadyBookSlotIdx, boolean isAlertUpdate);
    void removePlayerListener(ArrayList<Team> teams, int iSlotPosition, int iAddPlayerPosition);

    /**
     * Team size = 3, max entry 1 team.  _1 team per slot_
     * <p>
     * Team size = 4, max entry 1 team.  _1 team per slot_
     */
    void addorChangePlayerUpdateMaxTeam(List<Player> mPlayersArr, ArrayList<Team> teamArrayList, int iSlotPos, int iTeamsPerSlot, int iTeamPosition, int iSlotIdx, int iPlayerCount, int actionCallFromRemove);
    void confirmRemoveTeam(List<Player> mPlayersArr, ArrayList<Team> teamArrayList, int iSlotPos, int iTeamsPerSlot, int iTeamPosition, int iSlotIdx, int iPlayerCount, int actionCallFromRemove);
}
