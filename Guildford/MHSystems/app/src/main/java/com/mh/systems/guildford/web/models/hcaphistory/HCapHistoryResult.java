
package com.mh.systems.guildford.web.models.hcaphistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HCapHistoryResult {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("CompEligiblePlayersData")
    @Expose
    private List<HCapHistoryData> Data = new ArrayList<HCapHistoryData>();

    /**
     * @return The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * @param Message The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * @return The Result
     */
    public Integer getResult() {
        return Result;
    }

    /**
     * @param Result The Result
     */
    public void setResult(Integer Result) {
        this.Result = Result;
    }

    /**
     * @return The CompEligiblePlayersData
     */
    public List<HCapHistoryData> getData() {
        return Data;
    }

    /**
     * @param Data The CompEligiblePlayersData
     */
    public void setData(List<HCapHistoryData> Data) {
        this.Data = Data;
    }

}
