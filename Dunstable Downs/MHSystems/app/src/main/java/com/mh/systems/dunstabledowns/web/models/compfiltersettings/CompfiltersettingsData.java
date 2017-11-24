
package com.mh.systems.dunstabledowns.web.models.compfiltersettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompfiltersettingsData {

    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;
    @SerializedName("ClientId")
    @Expose
    private Integer ClientId;
    @SerializedName("MyEventsOnly")
    @Expose
    private Boolean MyEventsOnly;
    @SerializedName("GenderFilter")
    @Expose
    private Integer GenderFilter;

    public Integer getMemberId() {
        return MemberId;
    }

    public void setMemberId(Integer MemberId) {
        this.MemberId = MemberId;
    }

    public Integer getClientId() {
        return ClientId;
    }

    public void setClientId(Integer ClientId) {
        this.ClientId = ClientId;
    }

    public Boolean getMyEventsOnly() {
        return MyEventsOnly;
    }

    public void setMyEventsOnly(Boolean MyEventsOnly) {
        this.MyEventsOnly = MyEventsOnly;
    }

    public Integer getGenderFilter() {
        return GenderFilter;
    }

    public void setGenderFilter(Integer GenderFilter) {
        this.GenderFilter = GenderFilter;
    }

}
