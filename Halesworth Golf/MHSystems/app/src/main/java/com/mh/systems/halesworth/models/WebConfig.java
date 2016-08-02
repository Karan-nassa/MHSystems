
package com.mh.systems.halesworth.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WebConfig {

    @SerializedName("WebConfigData")
    @Expose
    private String WebConfigData;

    /**
     * 
     * @return
     *     The WebConfigData
     */
    public String getWebConfigData() {
        return WebConfigData;
    }

    /**
     * 
     * @param WebConfigData
     *     The WebConfigData
     */
    public void setWebConfigData(String WebConfigData) {
        this.WebConfigData = WebConfigData;
    }

}
