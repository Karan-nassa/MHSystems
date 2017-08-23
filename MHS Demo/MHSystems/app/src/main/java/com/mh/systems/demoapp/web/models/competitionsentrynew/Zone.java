
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Zone {

    @SerializedName("ZoneIdx")
    @Expose
    private Integer ZoneIdx;
    @SerializedName("ZoneName")
    @Expose
    private String ZoneName;
    @SerializedName("Slots")
    @Expose
    private List<Slot> Slots = null;
    @SerializedName("FreePlaces")
    @Expose
    private String FreePlaces;
    @SerializedName("TeamsPerSlot")
    @Expose
    private int TeamsPerSlot;
    private boolean isExpand = false;

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public Integer getZoneIdx() {
        return ZoneIdx;
    }

    public void setZoneIdx(Integer ZoneIdx) {
        this.ZoneIdx = ZoneIdx;
    }

    public String getZoneName() {
        return ZoneName;
    }

    public void setZoneName(String ZoneName) {
        this.ZoneName = ZoneName;
    }

    public List<Slot> getSlots() {
        return Slots;
    }

    public void setSlots(List<Slot> Slots) {
        this.Slots = Slots;
    }

    public String getFreePlaces() {
        return FreePlaces;
    }

    public void setFreePlaces(String FreePlaces) {
        this.FreePlaces = FreePlaces;
    }

    public int getTeamsPerSlot() {
        return TeamsPerSlot;
    }

    public void setTeamsPerSlot(int teamsPerSlot) {
        TeamsPerSlot = teamsPerSlot;
    }
}
