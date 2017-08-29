
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable {

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
     * EntryStatus equal to
     * 0, If not booked
     * 1, if booked by someone else
     * 2, If booked by itself
     */
    private int EntryStatus;

    /**
     * If user made any changes/update.
     * Example: Member remove itself or anyone else which
     * he booked earlier.
     */
    private boolean isAnyUpdated = false;

    private boolean isAlreadyBooked = false;

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

    public int getEntryStatus() {
        return EntryStatus;
    }

    public void setEntryStatus(int entryStatus) {
        EntryStatus = entryStatus;
    }

    public boolean isAnyUpdated() {
        return isAnyUpdated;
    }

    public void setAnyUpdated(boolean anyUpdated) {
        isAnyUpdated = anyUpdated;
    }

    public boolean isAlreadyBooked() {
        return isAlreadyBooked;
    }

    public void setAlreadyBooked(boolean alreadyBooked) {
        isAlreadyBooked = alreadyBooked;
    }
}
