
package com.mh.systems.demoapp.models.weather;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Main {

    @SerializedName("temp")
    @Expose
    private Float temp;
    @SerializedName("temp_min")
    @Expose
    private Float temp_min;
    @SerializedName("temp_max")
    @Expose
    private Float temp_max;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("sea_level")
    @Expose
    private Double sea_level;
    @SerializedName("grnd_level")
    @Expose
    private Double grnd_level;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;

    /**
     * 
     * @return
     *     The temp
     */
    public Float getTemp() {
        return temp;
    }

    /**
     * 
     * @param temp
     *     The temp
     */
    public void setTemp(Float temp) {
        this.temp = temp;
    }

    /**
     * 
     * @return
     *     The temp_min
     */
    public Float getTempMin() {
        return temp_min;
    }

    /**
     * 
     * @param temp_min
     *     The temp_min
     */
    public void setTempMin(Float temp_min) {
        this.temp_min = temp_min;
    }

    /**
     * 
     * @return
     *     The temp_max
     */
    public Float getTempMax() {
        return temp_max;
    }

    /**
     * 
     * @param temp_max
     *     The temp_max
     */
    public void setTempMax(Float temp_max) {
        this.temp_max = temp_max;
    }

    /**
     * 
     * @return
     *     The pressure
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     * 
     * @param pressure
     *     The pressure
     */
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    /**
     * 
     * @return
     *     The sea_level
     */
    public Double getSeaLevel() {
        return sea_level;
    }

    /**
     * 
     * @param sea_level
     *     The sea_level
     */
    public void setSeaLevel(Double sea_level) {
        this.sea_level = sea_level;
    }

    /**
     * 
     * @return
     *     The grnd_level
     */
    public Double getGrndLevel() {
        return grnd_level;
    }

    /**
     * 
     * @param grnd_level
     *     The grnd_level
     */
    public void setGrndLevel(Double grnd_level) {
        this.grnd_level = grnd_level;
    }

    /**
     * 
     * @return
     *     The humidity
     */
    public Integer getHumidity() {
        return humidity;
    }

    /**
     * 
     * @param humidity
     *     The humidity
     */
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

}
