package com.mh.systems.demoapp.models.competitionsEntry;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Team {

    @SerializedName("EntryID")
    @Expose
    private Integer EntryID;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("EntryFee")
    @Expose
    private Double EntryFee;
    @SerializedName("ZoneID")
    @Expose
    private Integer ZoneID;
    @SerializedName("SlotNo")
    @Expose
    private Integer SlotNo;
    @SerializedName("TeamNo")
    @Expose
    private Integer TeamNo;
    @SerializedName("Players")
    @Expose
    private List<Players> Players = new ArrayList<Players>();
    @SerializedName("SlotTime")
    @Expose
    private String SlotTime;

    /**
     *
     * @return
     * The EntryID
     */
    public Integer getEntryID() {
        return EntryID;
    }

    /**
     *
     * @param EntryID
     * The EntryID
     */
    public void setEntryID(Integer EntryID) {
        this.EntryID = EntryID;
    }

    /**
     *
     * @return
     * The Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     *
     * @param Status
     * The Status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     *
     * @return
     * The EntryFee
     */
    public Double getEntryFee() {
        return EntryFee;
    }

    /**
     *
     * @param EntryFee
     * The EntryFee
     */
    public void setEntryFee(Double EntryFee) {
        this.EntryFee = EntryFee;
    }

    /**
     *
     * @return
     * The ZoneID
     */
    public Integer getZoneID() {
        return ZoneID;
    }

    /**
     *
     * @param ZoneID
     * The ZoneID
     */
    public void setZoneID(Integer ZoneID) {
        this.ZoneID = ZoneID;
    }

    /**
     *
     * @return
     * The SlotNo
     */
    public Integer getSlotNo() {
        return SlotNo;
    }

    /**
     *
     * @param SlotNo
     * The SlotNo
     */
    public void setSlotNo(Integer SlotNo) {
        this.SlotNo = SlotNo;
    }

    /**
     *
     * @return
     * The TeamNo
     */
    public Integer getTeamNo() {
        return TeamNo;
    }

    /**
     *
     * @param TeamNo
     * The TeamNo
     */
    public void setTeamNo(Integer TeamNo) {
        this.TeamNo = TeamNo;
    }

    /**
     *
     * @return
     * The Players
     */
    public List<Players> getPlayers() {
        return Players;
    }

    /**
     *
     * @param Players
     * The Players
     */
    public void setPlayers(List<Players> Players) {
        this.Players = Players;
    }

    /**
     *
     * @return
     * The SlotTime
     */
    public String getSlotTime() {
        return SlotTime;
    }

    /**
     *
     * @param SlotTime
     * The SlotTime
     */
    public void setSlotTime(String slotTime) {
        this.SlotTime = slotTime;
    }
}