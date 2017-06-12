
package com.mh.systems.teesside.web.models.pursebalance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurseBalanceResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private PurseBalanceData Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Integer getResult() {
        return Result;
    }

    public void setResult(Integer Result) {
        this.Result = Result;
    }

    public PurseBalanceData getData() {
        return Data;
    }

    public void setData(PurseBalanceData Data) {
        this.Data = Data;
    }

}
