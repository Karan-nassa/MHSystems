package com.mh.systems.york.web.models.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mh.systems.york.web.models.weather.WeatherData;

/**
 * Created by Karan Nassa on 09-11-2016.
 * <p>
 * Organization : ucreate.it
 * Email        : karan@ucreate.it
 */
public class ForecastApiResponse {
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Result")
    @Expose
    private Integer Result;
    @SerializedName("Data")
    @Expose
    private ForecastMain Data;

    /**
     *
     * @return
     *     The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     *
     * @param Message
     *     The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     *
     * @return
     *     The Result
     */
    public Integer getResult() {
        return Result;
    }

    /**
     *
     * @param Result
     *     The Result
     */
    public void setResult(Integer Result) {
        this.Result = Result;
    }

    /**
     *
     * @return
     *     The Data
     */
    public ForecastMain getData() {
        return Data;
    }

    /**
     *
     * @param Data
     *     The Data
     */
    public void setData(ForecastMain Data) {
        this.Data = Data;
    }

}
