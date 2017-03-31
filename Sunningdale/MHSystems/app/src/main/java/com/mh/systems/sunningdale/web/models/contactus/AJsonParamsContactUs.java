
package com.mh.systems.sunningdale.web.models.contactus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsContactUs {

    @SerializedName("version")
    @Expose
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }



}
