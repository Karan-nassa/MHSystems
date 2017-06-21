
package com.mh.systems.corrstown.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HandicapResultItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("LoginData")
    @Expose
    private HandicapData Data;

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
    public void setResult(Integer Result) {
        this.Result = Result;
    }

    /**
     * 
     * @return
     *     The LoginData
     */
    public HandicapData getData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The LoginData
     */
    public void setData(HandicapData Data) {
        this.Data = Data;
    }

}
