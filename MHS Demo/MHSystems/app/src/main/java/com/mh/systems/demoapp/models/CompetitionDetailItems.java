
package com.mh.systems.demoapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompetitionDetailItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("CompEligiblePlayersData")
    @Expose
    private CompResultData Data;

    /**
     * 
     * @return
     *     The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * 
     * @param Message
     *     The Message
     */
    public void setMessage(String message) {
        this.Message = Message;
    }

    /**
     * 
     * @return
     *     The Result
     */
    public Integer getResult() {
        return Result;
    }

    /**
     * 
     * @param Result
     *     The Result
     */
    public void setResult(Integer result) {
        this.Result = Result;
    }

    /**
     * 
     * @return
     *     The CompEligiblePlayersData
     */
    public CompResultData getCompResultData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The CompEligiblePlayersData
     */
    public void setCompResultData(CompResultData Data) {
        this.Data = Data;
    }

}
