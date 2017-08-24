
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllPlayer implements Serializable {

    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;
    @SerializedName("NameAndHandicap")
    @Expose
    private String NameAndHandicap;
    @SerializedName("IsFriend")
    @Expose
    private Boolean IsFriend;
    @SerializedName("EntryFee")
    @Expose
    private Integer EntryFee;
    @SerializedName("EntryStatus")
    @Expose
    private Integer EntryStatus;

    boolean isMemberSelected;

    public Integer getMemberId() {
        return MemberId;
    }

    public void setMemberId(Integer MemberId) {
        this.MemberId = MemberId;
    }

    public String getNameAndHandicap() {
        return NameAndHandicap;
    }

    public void setNameAndHandicap(String NameAndHandicap) {
        this.NameAndHandicap = NameAndHandicap;
    }

    public Boolean getIsFriend() {
        return IsFriend;
    }

    public void setIsFriend(Boolean IsFriend) {
        this.IsFriend = IsFriend;
    }

    public Integer getEntryFee() {
        return EntryFee;
    }

    public void setEntryFee(Integer EntryFee) {
        this.EntryFee = EntryFee;
    }

    public Integer getEntryStatus() {
        return EntryStatus;
    }

    public void setEntryStatus(Integer EntryStatus) {
        this.EntryStatus = EntryStatus;
    }

    /**
     * @return The isMemberSelected
     */
    public boolean getIsMemberSelected() {
        return isMemberSelected;
    }

    /**
     * @param isMemberSelected The isMemberSelected
     */
    public void setIsMemberSelected(boolean isMemberSelected) {
        this.isMemberSelected = isMemberSelected;
    }
}
