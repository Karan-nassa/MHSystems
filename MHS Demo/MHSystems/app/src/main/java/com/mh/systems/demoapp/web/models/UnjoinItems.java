
package com.mh.systems.demoapp.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UnjoinItems {

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
     * @return The message
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
     * @param result The Result
     */
    public void setResult(Integer result) {
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
    public void setData(String data) {
        this.Data = Data;
    }

}
