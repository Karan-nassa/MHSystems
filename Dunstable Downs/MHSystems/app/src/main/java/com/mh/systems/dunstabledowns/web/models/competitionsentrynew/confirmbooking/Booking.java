
package com.mh.systems.dunstabledowns.web.models.competitionsentrynew.confirmbooking;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mh.systems.dunstabledowns.web.models.competitionsentrynew.Player;

public class Booking implements Serializable {

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
    private Double EntryFee;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Booking() {
    }

    /**
     * 
     * @param teamName
     * @param teamIdx
     * @param players
     * @param entryFee
     * @param slotIdx
     * @param zoneId
     */
    public Booking(Integer ZoneId, Integer SlotIdx,
                   Integer TeamIdx, String TeamName,
                   List<Player> Players, Double EntryFee) {
        super();
        this.ZoneId = ZoneId;
        this.SlotIdx = SlotIdx;
        this.TeamIdx = TeamIdx;
        this.TeamName = TeamName;
        this.Players = Players;
        this.EntryFee = EntryFee;
    }

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

    public void setPlayers(List<Player> Players) {
        this.Players = Players;
    }

    public Double getEntryFee() {
        return EntryFee;
    }

    public void setEntryFee(Double EntryFee) {
        this.EntryFee = EntryFee;
    }

}
