
package com.mh.systems.guildford.models.competitionsEntry;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompEligiblePlayersResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private CompEligiblePlayersData Data;

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
    public void setMessage(String Message) {
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
    public CompEligiblePlayersData getData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The CompEligiblePlayersData
     */
    public void setData(CompEligiblePlayersData data) {
        this.Data = Data;
    }

}
