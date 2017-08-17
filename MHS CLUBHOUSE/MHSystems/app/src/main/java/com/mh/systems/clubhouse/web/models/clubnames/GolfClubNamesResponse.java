
package com.mh.systems.clubhouse.web.models.clubnames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GolfClubNamesResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private List<ListOfClubsData> Data = null;

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

    public List<ListOfClubsData> getData() {
        return Data;
    }

    public void setData(List<ListOfClubsData> data) {
        this.Data = Data;
    }

}
