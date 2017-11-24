
package com.mh.systems.dunstabledowns.web.models.competitionsentrynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Player implements Serializable {

    @SerializedName("IsGuest")
    @Expose
    private Boolean IsGuest;
    @SerializedName("MemberId")
    @Expose
    private String MemberId;
    @SerializedName("GuestName")
    @Expose
    private Object GuestName;
    @SerializedName("GuestHCap")
    @Expose
    private Object GuestHCap;

    public Boolean getIsGuest() {
        return IsGuest;
    }

    public void setIsGuest(Boolean IsGuest) {
        this.IsGuest = IsGuest;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

    public Object getGuestName() {
        return GuestName;
    }

    public void setGuestName(Object GuestName) {
        this.GuestName = GuestName;
    }

    public Object getGuestHCap() {
        return GuestHCap;
    }

    public void setGuestHCap(Object GuestHCap) {
        this.GuestHCap = GuestHCap;
    }

}
