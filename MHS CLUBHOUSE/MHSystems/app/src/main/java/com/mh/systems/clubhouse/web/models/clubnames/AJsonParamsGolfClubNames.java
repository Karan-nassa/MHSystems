
package com.mh.systems.clubhouse.web.models.clubnames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsGolfClubNames {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

}
