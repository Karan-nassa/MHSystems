package com.mh.systems.york.models;

/**
 * Created by karan@ucreate.co.in on 23-06-2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultEntries {

    @SerializedName("PlaceStr")
    @Expose
    private String PlaceStr;
    @SerializedName("EntryName")
    @Expose
    private String EntryName;
    @SerializedName("ScoreSummary")
    @Expose
    private String ScoreSummary;
    @SerializedName("ExactHCap")
    @Expose
    private String ExactHCap;
    @SerializedName("NettTotal")
    @Expose
    private String NettTotal;

    /**
     * @return The PlaceStr
     */
    public String getPlaceStr() {
        return PlaceStr;
    }

    /**
     * @param PlaceStr The PlaceStr
     */
    public void setPlaceStr(String PlaceStr) {
        this.PlaceStr = PlaceStr;
    }

    /**
     * @return The EntryName
     */
    public String getEntryName() {
        return EntryName;
    }

    /**
     * @param EntryName The EntryName
     */
    public void setEntryName(String EntryName) {
        this.EntryName = EntryName;
    }

    /**
     * @return The ScoreSummary
     */
    public String getScoreSummary() {
        return ScoreSummary;
    }

    /**
     * @param ScoreSummary The ScoreSummary
     */
    public void setScoreSummary(String ScoreSummary) {
        this.ScoreSummary = ScoreSummary;
    }

    /**
     * @return The ExactHCap
     */
    public String getExactHCap() {
        return ExactHCap;
    }

    /**
     * @param ExactHCap The ExactHCap
     */
    public void setExactHCap(String ExactHCap) {
        this.ExactHCap = ExactHCap;
    }

    public String getNettTotal() {
        return NettTotal;
    }

    public void setNettTotal(String nettTotal) {
        NettTotal = nettTotal;
    }
}
