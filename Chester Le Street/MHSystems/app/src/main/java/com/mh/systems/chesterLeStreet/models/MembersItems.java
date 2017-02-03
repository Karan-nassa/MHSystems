
package com.mh.systems.chesterLeStreet.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MembersItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("MembersDetailsData")
    @Expose
    private MembersData Data;

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
     *     The MembersDetailsData
     */
    public MembersData getData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The MembersDetailsData
     */
    public void setData(MembersData Data) {
        this.Data = Data;
    }

}
