
package com.mh.systems.sunningdale.web.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FinanceResultItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private FinanceData Data;

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
     * @return The Data
     */
    public FinanceData getData() {
        return Data;
    }

    /**
     * @param Data The Data
     */
    public void setData(FinanceData Data) {
        this.Data = Data;
    }

}
