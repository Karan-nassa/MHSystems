package com.mh.systems.brokenhurst.models.competitionsEntry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 29-09-2016.
 */

public class NameRecord implements Serializable {
    @SerializedName("DisplayName")
    @Expose
    private String DisplayName;

    public NameRecord(String playerName) {
        this.DisplayName = playerName;
    }

    /**
     *
     * @return
     *     The DisplayName
     */
    public String getDisplayName() {
        return DisplayName;
    }

    /**
     *
     * @param DisplayName
     *     The DisplayName
     */
    public void setDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
    }
}
