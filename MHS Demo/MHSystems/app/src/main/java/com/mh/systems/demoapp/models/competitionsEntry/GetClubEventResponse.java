
package com.mh.systems.demoapp.models.competitionsEntry;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetClubEventResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("UnreadNewsCountData")
    @Expose
    private GetClubEventData Data;

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
     *     The UnreadNewsCountData
     */
    public GetClubEventData getGetClubEventData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The UnreadNewsCountData
     */
    public void setGetClubEventData(GetClubEventData Data) {
        this.Data = Data;
    }

}
