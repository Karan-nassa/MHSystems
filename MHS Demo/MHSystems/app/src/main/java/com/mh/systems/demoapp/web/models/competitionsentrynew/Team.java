
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("ZoneId")
    @Expose
    private Integer ZoneId;
    @SerializedName("SlotIdx")
    @Expose
    private Integer SlotIdx;
    @SerializedName("TeamIdx")
    @Expose
    private Integer TeamIdx;
    @SerializedName("TeamName")
    @Expose
    private String TeamName;
    @SerializedName("Players")
    @Expose
    private List<Player> Players = null;
    @SerializedName("EntryFee")
    @Expose
    private Integer EntryFee;

    public Integer getZoneId() {
        return ZoneId;
    }

    public void setZoneId(Integer ZoneId) {
        this.ZoneId = ZoneId;
    }

    public Integer getSlotIdx() {
        return SlotIdx;
    }

    public void setSlotIdx(Integer SlotIdx) {
        this.SlotIdx = SlotIdx;
    }

    public Integer getTeamIdx() {
        return TeamIdx;
    }

    public void setTeamIdx(Integer TeamIdx) {
        this.TeamIdx = TeamIdx;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String TeamName) {
        this.TeamName = TeamName;
    }

    public List<Player> getPlayers() {
        return Players;
    }

    public void setPlayers(List<Player> players) {
        this.Players = Players;
    }

    public Integer getEntryFee() {
        return EntryFee;
    }

    public void setEntryFee(Integer EntryFee) {
        this.EntryFee = EntryFee;
    }

}
