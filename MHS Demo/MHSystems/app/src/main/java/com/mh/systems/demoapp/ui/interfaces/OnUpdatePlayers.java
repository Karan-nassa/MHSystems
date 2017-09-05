package com.mh.systems.demoapp.ui.interfaces;

import com.mh.systems.demoapp.web.models.competitionsentrynew.Player;
import com.mh.systems.demoapp.web.models.competitionsentrynew.Team;

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
    void addMaxPlayersAsTeamsize(ArrayList<Team> teams, int position, int iTeamsPerSlot, int iTeamPlayerPos, Integer slotIdx);

    /**
     * Team size = 3, max entry 1 team.  _1 team per slot_
     * <p>
     * Team size = 4, max entry 1 team.  _1 team per slot_
     */
    void addorRemoveUpdateMaxTeam(List<Player> mPlayersArr, ArrayList<Team> teamArrayList, int iSlotPos, int iTeamsPerSlot, int iTeamPosition, int iSlotIdx, int iPlayerCount, int actionCallFromRemove);
}
