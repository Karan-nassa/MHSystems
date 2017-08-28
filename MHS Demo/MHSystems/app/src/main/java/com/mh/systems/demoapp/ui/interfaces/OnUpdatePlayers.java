package com.mh.systems.demoapp.ui.interfaces;

import com.mh.systems.demoapp.web.models.competitionsentrynew.Team;

import java.util.ArrayList;

/**
 * Created by admin on 24-08-2017.
 */

public interface OnUpdatePlayers {

    void addPlayersListener(ArrayList<Team> teams, int slotPosition, int iTeamPerSlot, int iAddPlayerPosition, boolean isAlertUpdate);
    void removePlayerListener(ArrayList<Team> teams, int iSlotPosition, int iAddPlayerPosition);
}
