
package com.mh.systems.dunstabledowns.web.models.competitionsentry;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ClubEventStartSheet {

    @SerializedName("Capacity")
    @Expose
    private Integer Capacity;
    @SerializedName("Zones")
    @Expose
    private List<Zone> Zones = new ArrayList<Zone>();

    /**
     * @return The Capacity
     */
    public Integer getCapacity() {
        return Capacity;
    }

    /**
     * @param Capacity The Capacity
     */
    public void setCapacity(Integer Capacity) {
        this.Capacity = Capacity;
    }

    /**
     * @return The Zones
     */
    public List<Zone> getZones() {
        return Zones;
    }

    /**
     * @param Zones The Zones
     */
    public void setZones(List<Zone> zones) {
        this.Zones = Zones;
    }

}
