package com.mh.systems.york.web.models.competitionsentry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("RecordID")
    @Expose
    private Integer RecordID;
    @SerializedName("PlayerName")
    @Expose
    private String PlayerName;

    /**
     *
     * @return
     * The RecordID
     */
    public Integer getRecordID() {
        return RecordID;
    }

    /**
     *
     * @param RecordID
     * The RecordID
     */
    public void setRecordID(Integer recordID) {
        this.RecordID = RecordID;
    }

    /**
     *
     * @return
     * The PlayerName
     */
    public String getPlayerName() {
        return PlayerName;
    }

    /**
     *
     * @param PlayerName
     * The PlayerName
     */
    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

}