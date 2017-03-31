package com.mh.systems.guildford.web.models.competitionsentry;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entry {

    @SerializedName("EntryID")
    @Expose
    private Integer EntryID;
    @SerializedName("reservedSlotNo")
    @Expose
    private Integer reservedSlotNo;
    @SerializedName("reservedSlotTime")
    @Expose
    private String reservedSlotTime;
    @SerializedName("Players")
    @Expose
    private List<Player> Players = new ArrayList<Player>();

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
     * The reservedSlotNo
     */
    public Integer getReservedSlotNo() {
        return reservedSlotNo;
    }

    /**
     *
     * @param reservedSlotNo
     * The reservedSlotNo
     */
    public void setReservedSlotNo(Integer reservedSlotNo) {
        this.reservedSlotNo = reservedSlotNo;
    }

    /**
     *
     * @return
     * The reservedSlotTime
     */
    public String getReservedSlotTime() {
        return reservedSlotTime;
    }

    /**
     *
     * @param reservedSlotTime
     * The reservedSlotTime
     */
    public void setReservedSlotTime(String reservedSlotTime) {
        this.reservedSlotTime = reservedSlotTime;
    }

    /**
     *
     * @return
     * The Players
     */
    public List<Player> getPlayers() {
        return Players;
    }

    /**
     *
     * @param Players
     * The Players
     */
    public void setPlayers(List<Player> Players) {
        this.Players = Players;
    }

}