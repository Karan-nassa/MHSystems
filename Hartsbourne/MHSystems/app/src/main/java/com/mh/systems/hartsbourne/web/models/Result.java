
package com.mh.systems.hartsbourne.web.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Result {

    @SerializedName("ResultID")
    @Expose
    private Integer ResultID;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("ResultEntries")
    @Expose
    private List<ResultEntries> ResultEntries = new ArrayList<ResultEntries>();

    /**
     * 
     * @return
     *     The ResultID
     */
    public Integer getResultID() {
        return ResultID;
    }

    /**
     * 
     * @param ResultID
     *     The ResultID
     */
    public void setResultID(Integer resultID) {
        this.ResultID = ResultID;
    }

    /**
     * 
     * @return
     *     The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * 
     * @param Description
     *     The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * 
     * @return
     *     The ResultEntries
     */
    public List<ResultEntries> getResultEntries() {
        return ResultEntries;
    }

    /**
     * 
     * @param ResultEntries
     *     The ResultEntries
     */
    public void setResultEntries(List<ResultEntries> ResultEntries) {
        this.ResultEntries = ResultEntries;
    }

}
