
package com.mh.systems.corrstown.web.models.teetimebooking.getmonthdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.R.attr.data;
import static android.R.id.message;

public class GetMonthDataResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private GetMonthData GetMonthData;

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

    public GetMonthData getData() {
        return GetMonthData;
    }

    public void setData(GetMonthData GetMonthData) {
        this.GetMonthData = GetMonthData;
    }

}
