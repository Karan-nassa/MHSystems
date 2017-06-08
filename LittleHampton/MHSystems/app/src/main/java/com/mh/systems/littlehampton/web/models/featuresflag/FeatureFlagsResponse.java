
package com.mh.systems.littlehampton.web.models.featuresflag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeatureFlagsResponse {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private Integer result;
    @SerializedName("Data")
    @Expose
    private FeaturesFlagData data;

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

    public FeaturesFlagData getData() {
        return data;
    }

    public void setData(FeaturesFlagData data) {
        this.data = data;
    }

}
