
package com.mh.systems.sunningdale.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MyAccountItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("MembersDetailsData")
    @Expose
    private List<MyAccountData> Data;

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
     *     The MyAccountData
     */
    public List<MyAccountData> getMyAccountData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The MyAccountData
     */
    public void setMyAccountData(List<MyAccountData> Data) {
        this.Data = Data;
    }

}
