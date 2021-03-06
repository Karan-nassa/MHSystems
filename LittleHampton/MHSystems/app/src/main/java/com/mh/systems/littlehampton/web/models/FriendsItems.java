
package com.mh.systems.littlehampton.web.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendsItems {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("CompResultData")
    @Expose
    private List<FriendsData> Data = new ArrayList<FriendsData>();

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
     *     The CompResultData
     */
    public List<FriendsData> getData() {
        return Data;
    }

    /**
     * 
     * @param Data
     *     The CompResultData
     */
    public void setData(List<FriendsData> Data) {
        this.Data = Data;
    }

}
