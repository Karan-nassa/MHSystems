package com.mh.systems.demoapp.ui.interfaces;

import com.mh.systems.demoapp.web.models.competitionsentrynew.Team;

import java.util.List;

/**
 * Created by admin on 24-08-2017.
 */

public interface OnUpdatePlayers {

    void addPlayersListener(List<Team> teams, int slotPosition, int iTeamPerSlot, int iAddPlayerPosition);
    void removePlayerListener();
}
