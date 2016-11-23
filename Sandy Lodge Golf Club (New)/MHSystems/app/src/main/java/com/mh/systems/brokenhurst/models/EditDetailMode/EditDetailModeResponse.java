
package com.mh.systems.brokenhurst.models.EditDetailMode;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EditDetailModeResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("CompEligiblePlayersData")
    @Expose
    private String Data;

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
    public String getData() {
        return Data;
    }

    /**
     * @param Data The CompEligiblePlayersData
     */
    public void setData(String Data) {
        this.Data = Data;
    }

}
