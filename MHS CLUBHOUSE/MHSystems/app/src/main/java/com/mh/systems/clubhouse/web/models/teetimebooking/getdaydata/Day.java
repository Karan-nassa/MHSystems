
package com.mh.systems.clubhouse.web.models.teetimebooking.getdaydata;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("OverQuota")
    @Expose
    private Boolean OverQuota;
    @SerializedName("HasSlots")
    @Expose
    private Boolean HasSlots;
    @SerializedName("Slots")
    @Expose
    private List<Slot> Slots = null;
    @SerializedName("Bookables")
    @Expose
    private List<Bookable> Bookables = null;

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public Boolean getOverQuota() {
        return OverQuota;
    }

    public void setOverQuota(Boolean OverQuota) {
        this.OverQuota = OverQuota;
    }

    public Boolean getHasSlots() {
        return HasSlots;
    }

    public void setHasSlots(Boolean HasSlots) {
        this.HasSlots = HasSlots;
    }

    public List<Slot> getSlots() {
        return Slots;
    }

    public void setSlots(List<Slot> Slots) {
        this.Slots = Slots;
    }

    public List<Bookable> getBookables() {
        return Bookables;
    }

    public void setBookables(List<Bookable> Bookables) {
        this.Bookables = Bookables;
    }

}
