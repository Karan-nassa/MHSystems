
package com.mh.systems.dunstabledowns.web.models.competitionsentrynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventStartDate implements Serializable {

    @SerializedName("Y")
    @Expose
    private Integer Y;
    @SerializedName("M")
    @Expose
    private Integer M;
    @SerializedName("D")
    @Expose
    private Integer D;
    @SerializedName("H")
    @Expose
    private Integer H;
    @SerializedName("N")
    @Expose
    private Integer N;
    @SerializedName("S")
    @Expose
    private Integer S;
    @SerializedName("FullDateStr")
    @Expose
    private String FullDateStr;
    @SerializedName("ShortDateStr")
    @Expose
    private String ShortDateStr;
    @SerializedName("FullTimeStr")
    @Expose
    private String FullTimeStr;
    @SerializedName("ShortTimeStr")
    @Expose
    private String ShortTimeStr;
    @SerializedName("FullDateTimeStr")
    @Expose
    private String FullDateTimeStr;
    @SerializedName("ShortDateTimeStr")
    @Expose
    private String ShortDateTimeStr;

    public Integer getY() {
        return Y;
    }

    public void setY(Integer y) {
        this.Y = Y;
    }

    public Integer getM() {
        return M;
    }

    public void setM(Integer m) {
        this.M = M;
    }

    public Integer getD() {
        return D;
    }

    public void setD(Integer d) {
        this.D = D;
    }

    public Integer getH() {
        return H;
    }

    public void setH(Integer h) {
        this.H = H;
    }

    public Integer getN() {
        return N;
    }

    public void setN(Integer N) {
        this.N = N;
    }

    public Integer getS() {
        return S;
    }

    public void setS(Integer S) {
        this.S = S;
    }

    public String getFullDateStr() {
        return FullDateStr;
    }

    public void setFullDateStr(String FullDateStr) {
        this.FullDateStr = FullDateStr;
    }

    public String getShortDateStr() {
        return ShortDateStr;
    }

    public void setShortDateStr(String ShortDateStr) {
        this.ShortDateStr = ShortDateStr;
    }

    public String getFullTimeStr() {
        return FullTimeStr;
    }

    public void setFullTimeStr(String FullTimeStr) {
        this.FullTimeStr = FullTimeStr;
    }

    public String getShortTimeStr() {
        return ShortTimeStr;
    }

    public void setShortTimeStr(String ShortTimeStr) {
        this.ShortTimeStr = ShortTimeStr;
    }

    public String getFullDateTimeStr() {
        return FullDateTimeStr;
    }

    public void setFullDateTimeStr(String FullDateTimeStr) {
        this.FullDateTimeStr = FullDateTimeStr;
    }

    public String getShortDateTimeStr() {
        return ShortDateTimeStr;
    }

    public void setShortDateTimeStr(String ShortDateTimeStr) {
        this.ShortDateTimeStr = ShortDateTimeStr;
    }

}
