
package com.mh.systems.dunstabledowns.web.models.topup;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopUpPriceListData {

    @SerializedName("CrnSym")
    @Expose
    String CrnSym;
    @SerializedName("MinTopup")
    @Expose
    private Integer MinTopup;
    @SerializedName("MaxTopup")
    @Expose
    private Integer MaxTopup;
    @SerializedName("MinTopupStr")
    @Expose
    private String MinTopupStr;
    @SerializedName("MaxTopupStr")
    @Expose
    private String MaxTopupStr;
    @SerializedName("TopupList")
    @Expose
    private List<TopupList> TopupList = null;

    @SerializedName("TopupTxFeeStr")
    private String TopupTxFeeStr;

    public Integer getMinTopup() {
        return MinTopup;
    }

    public void setMinTopup(Integer MinTopup) {
        this.MinTopup = MinTopup;
    }

    public Integer getMaxTopup() {
        return MaxTopup;
    }

    public void setMaxTopup(Integer maxTopup) {
        this.MaxTopup = MaxTopup;
    }

    public String getMinTopupStr() {
        return MinTopupStr;
    }

    public void setMinTopupStr(String MinTopupStr) {
        this.MinTopupStr = MinTopupStr;
    }

    public String getMaxTopupStr() {
        return MaxTopupStr;
    }

    public void setMaxTopupStr(String MaxTopupStr) {
        this.MaxTopupStr = MaxTopupStr;
    }

    public List<TopupList> getTopupList() {
        return TopupList;
    }

    public void setTopupList(List<TopupList> TopupList) {
        this.TopupList = TopupList;
    }

    public String getCrnSym() {
        return CrnSym;
    }

    public void setCrnSym(String CrnSym) {
        CrnSym = CrnSym;
    }

    public String getTopupTxFeeStr() {
        return TopupTxFeeStr;
    }

    public void setTopupTxFeeStr(String topupTxFeeStr) {
        TopupTxFeeStr = topupTxFeeStr;
    }

}
