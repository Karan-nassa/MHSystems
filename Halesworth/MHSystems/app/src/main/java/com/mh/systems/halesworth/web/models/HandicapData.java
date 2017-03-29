
package com.mh.systems.halesworth.web.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class HandicapData {

    @SerializedName("HCapExactStr")
    @Expose
    private String HCapExactStr;
    @SerializedName("HCapPlayStr")
    @Expose
    private String HCapPlayStr;
    @SerializedName("HCapTypeStr")
    @Expose
    private String HCapTypeStr;
    @SerializedName("HCapRecords")
    @Expose
    private List<HCapRecords> HCapRecords = new ArrayList<HCapRecords>();
    @SerializedName("HCapCertificate")
    @Expose
    private HCapCertificate HCapCertificate;

    /**
     * 
     * @return
     *     The HCapExactStr
     */
    public String getHCapExactStr() {
        return HCapExactStr;
    }

    /**
     * 
     * @param HCapExactStr
     *     The HCapExactStr
     */
    public void setHCapExactStr(String HCapExactStr) {
        this.HCapExactStr = HCapExactStr;
    }

    /**
     * 
     * @return
     *     The HCapPlayStr
     */
    public String getHCapPlayStr() {
        return HCapPlayStr;
    }

    /**
     * 
     * @param HCapPlayStr
     *     The HCapPlayStr
     */
    public void setHCapPlayStr(String HCapPlayStr) {
        this.HCapPlayStr = HCapPlayStr;
    }

    /**
     * 
     * @return
     *     The HCapTypeStr
     */
    public String getHCapTypeStr() {
        return HCapTypeStr;
    }

    /**
     * 
     * @param HCapTypeStr
     *     The HCapTypeStr
     */
    public void setHCapTypeStr(String HCapTypeStr) {
        this.HCapTypeStr = HCapTypeStr;
    }

       /**
          *
          * @return
          *     The HCapRecords
          */
    public List<HCapRecords> getHCapRecords() {
        return HCapRecords;
    }

    /**
     *
     * @param HCapRecords
     *     The HCapRecords
     */
    public void setHCapRecords(List<HCapRecords> HCapRecords) {
        this.HCapRecords = HCapRecords;
    }

    /**
     * 
     * @return
     *     The HCapCertificate
     */
    public HCapCertificate getHCapCertificate() {
        return HCapCertificate;
    }

    /**
     * 
     * @param HCapCertificate
     *     The HCapCertificate
     */
    public void setHCapCertificate(HCapCertificate HCapCertificate) {
        this.HCapCertificate = HCapCertificate;
    }

}
