
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCompEntryResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private NewCompEntryData Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = Message;
    }

    public Integer getResult() {
        return Result;
    }

    public void setResult(Integer Result) {
        this.Result = Result;
    }

    public NewCompEntryData getData() {
        return Data;
    }

    public void setData(NewCompEntryData Data) {
        this.Data = Data;
    }

}