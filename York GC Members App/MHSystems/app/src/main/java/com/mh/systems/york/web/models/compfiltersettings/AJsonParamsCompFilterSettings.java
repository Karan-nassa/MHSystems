
package com.mh.systems.york.web.models.compfiltersettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsCompFilterSettings {

    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("MyEventsOnly")
    @Expose
    private Boolean MyEventsOnly;
    @SerializedName("MemberId")
    @Expose
    private String MemberId;
    @SerializedName("GenderFilter")
    @Expose
    private int GenderFilter;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public Boolean getMyEventsOnly() {
        return MyEventsOnly;
    }

    public void setMyEventsOnly(Boolean MyEventsOnly) {
        this.MyEventsOnly = MyEventsOnly;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

    public int getGenderFilter() {
        return GenderFilter;
    }

    public void setGenderFilter(int GenderFilter) {
        this.GenderFilter = GenderFilter;
    }

}
