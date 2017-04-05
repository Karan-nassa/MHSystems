
package com.mh.systems.demoapp.web.models.deletetoken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteTokenResult {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private Integer Data;

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
     * @param Result The Result
     */
    public void setResult(Integer Result) {
        this.Result = Result;
    }

    /**
     * @return The Data
     */
    public Integer getData() {
        return Data;
    }

    /**
     * @param Data The Data
     */
    public void setData(Integer Data) {
        this.Data = Data;
    }

}
