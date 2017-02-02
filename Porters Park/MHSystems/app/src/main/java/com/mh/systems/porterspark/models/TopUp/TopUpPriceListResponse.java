
package com.mh.systems.porterspark.models.TopUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopUpPriceListResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private TopUpPriceListData Data;

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

    public TopUpPriceListData getData() {
        return Data;
    }

    public void setData(TopUpPriceListData Data) {
        this.Data = Data;
    }

}
