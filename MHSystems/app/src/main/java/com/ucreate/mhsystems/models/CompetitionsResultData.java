
package com.ucreate.mhsystems.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompetitionsResultData {

    @SerializedName("ResultID")
    @Expose
    private Integer ResultID;
    @SerializedName("IsPrimaryResult")
    @Expose
    private Boolean IsPrimaryResult;
    @SerializedName("ResultEntries")
    @Expose
    private ArrayList<ResultEntry> ResultEntries = new ArrayList<ResultEntry>();

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
     *     The IsPrimaryResult
     */
    public Boolean getIsPrimaryResult() {
        return IsPrimaryResult;
    }

    /**
     * 
     * @param isPrimaryResult
     *     The IsPrimaryResult
     */
    public void setIsPrimaryResult(Boolean IsPrimaryResult) {
        this.IsPrimaryResult = IsPrimaryResult;
    }

    /**
     * 
     * @return
     *     The ResultEntries
     */
    public ArrayList<ResultEntry> getResultEntries() {
        return ResultEntries;
    }

    /**
     * 
     * @param ResultEntries
     *     The ResultEntries
     */
    public void setResultEntries(ArrayList<ResultEntry> ResultEntries) {
        this.ResultEntries = ResultEntries;
    }

}
