
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("IsGuest")
    @Expose
    private Boolean IsGuest;
    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;
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

    public Integer getMemberId() {
        return MemberId;
    }

    public void setMemberId(Integer MemberId) {
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
