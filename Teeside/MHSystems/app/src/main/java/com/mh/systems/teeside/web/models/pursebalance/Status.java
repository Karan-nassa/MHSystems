
package com.mh.systems.teeside.web.models.pursebalance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("Result")
    @Expose
    private Integer result;
    @SerializedName("ErrorMessage")
    @Expose
    private Object errorMessage;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

}
