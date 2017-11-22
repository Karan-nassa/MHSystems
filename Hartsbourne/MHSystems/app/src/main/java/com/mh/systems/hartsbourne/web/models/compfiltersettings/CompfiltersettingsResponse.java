
package com.mh.systems.hartsbourne.web.models.compfiltersettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompfiltersettingsResponse {

    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private CompfiltersettingsData Data;

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

    public CompfiltersettingsData getData() {
        return Data;
    }

    public void setData(CompfiltersettingsData data) {
        this.Data = Data;
    }

}
