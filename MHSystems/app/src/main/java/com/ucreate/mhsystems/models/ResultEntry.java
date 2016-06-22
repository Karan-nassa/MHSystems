
package com.ucreate.mhsystems.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResultEntry {

    @SerializedName("PlaceStr")
    @Expose
    private String PlaceStr;
    @SerializedName("ShortName")
    @Expose
    private String ShortName;
    @SerializedName("ScoreSummary")
    @Expose
    private String ScoreSummary;
    /**
     * 
     * @return
     *     The PlaceStr
     */
    public String getPlaceStr() {
        return PlaceStr;
    }

    /**
     * 
     * @param PlaceStr
     *     The PlaceStr
     */
    public void setPlaceStr(String placeStr) {
        this.PlaceStr = PlaceStr;
    }

    /**
     * 
     * @return
     *     The ShortName
     */
    public String getShortName() {
        return ShortName;
    }

    /**
     * 
     * @param ShortName
     *     The ShortName
     */
    public void setShortName(String shortName) {
        this.ShortName = ShortName;
    }

    /**
     * 
     * @return
     *     The ScoreSummary
     */
    public String getScoreSummary() {
        return ScoreSummary;
    }

    /**
     * 
     * @param ScoreSummary
     *     The ScoreSummary
     */
    public void setScoreSummary(String scoreSummary) {
        this.ScoreSummary = ScoreSummary;
    }

}
