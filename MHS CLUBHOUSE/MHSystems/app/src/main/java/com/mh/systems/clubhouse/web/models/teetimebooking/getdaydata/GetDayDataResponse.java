
package com.mh.systems.clubhouse.web.models.teetimebooking.getdaydata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDayDataResponse {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private Integer result;
    @SerializedName("Data")
    @Expose
    private GetDayData getDayData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public GetDayData getGetDayData() {
        return getDayData;
    }

    public void setGetDayData(GetDayData getDayData) {
        this.getDayData = getDayData;
    }

}
