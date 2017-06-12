
package com.mh.systems.teesside.web.models.unreadnewscount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUnreadNewsResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private UnreadNewsCountData Data;

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
     * @return The UnreadNewsCountData
     */
    public UnreadNewsCountData getData() {
        return Data;
    }

    /**
     * @param UnreadNewsCountData The UnreadNewsCountData
     */
    public void setData(UnreadNewsCountData Data) {
        this.Data = Data;
    }

}
