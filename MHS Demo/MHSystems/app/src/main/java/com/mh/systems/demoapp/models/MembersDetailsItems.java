
package com.mh.systems.demoapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MembersDetailsItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private MembersDetailsData Data;

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
     * @return The HandicapData
     */
    public MembersDetailsData getData() {
        return Data;
    }

    /**
     * @param Data The HandicapData
     */
    public void setData(MembersDetailsData Data) {
        this.Data = Data;
    }

}
