
package com.mh.systems.guildford.web.models.teetimebooking.getmonthdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SysParams {

    @SerializedName("WeekOffset")
    @Expose
    private Integer WeekOffset;
    @SerializedName("NearHorizon")
    @Expose
    private Integer NearHorizon;
    @SerializedName("FarHorizon")
    @Expose
    private Integer FarHorizon;
    @SerializedName("FarHorizonDate")
    @Expose
    private String FarHorizonDate;
    @SerializedName("MaxEventsPerDay")
    @Expose
    private Integer MaxEventsPerDay;
    @SerializedName("MaxEventsPerMonth")
    @Expose
    private Integer MaxEventsPerMonth;
    @SerializedName("MaxEventsPerYear")
    @Expose
    private Integer MaxEventsPerYear;
    @SerializedName("MaxEventsPerPeriod")
    @Expose
    private Integer MaxEventsPerPeriod;
    @SerializedName("RollingPeriod")
    @Expose
    private Integer RollingPeriod;
    @SerializedName("CancellationHorizon")
    @Expose
    private Integer CancellationHorizon;

    public Integer getWeekOffset() {
        return WeekOffset;
    }

    public void setWeekOffset(Integer WeekOffset) {
        this.WeekOffset = WeekOffset;
    }

    public Integer getNearHorizon() {
        return NearHorizon;
    }

    public void setNearHorizon(Integer NearHorizon) {
        this.NearHorizon = NearHorizon;
    }

    public Integer getFarHorizon() {
        return FarHorizon;
    }

    public void setFarHorizon(Integer FarHorizon) {
        this.FarHorizon = FarHorizon;
    }

    public String getFarHorizonDate() {
        return FarHorizonDate;
    }

    public void setFarHorizonDate(String FarHorizonDate) {
        this.FarHorizonDate = FarHorizonDate;
    }

    public Integer getMaxEventsPerDay() {
        return MaxEventsPerDay;
    }

    public void setMaxEventsPerDay(Integer MaxEventsPerDay) {
        this.MaxEventsPerDay = MaxEventsPerDay;
    }

    public Integer getMaxEventsPerMonth() {
        return MaxEventsPerMonth;
    }

    public void setMaxEventsPerMonth(Integer MaxEventsPerMonth) {
        this.MaxEventsPerMonth = MaxEventsPerMonth;
    }

    public Integer getMaxEventsPerYear() {
        return MaxEventsPerYear;
    }

    public void setMaxEventsPerYear(Integer MaxEventsPerYear) {
        this.MaxEventsPerYear = MaxEventsPerYear;
    }

    public Integer getMaxEventsPerPeriod() {
        return MaxEventsPerPeriod;
    }

    public void setMaxEventsPerPeriod(Integer MaxEventsPerPeriod) {
        this.MaxEventsPerPeriod = MaxEventsPerPeriod;
    }

    public Integer getRollingPeriod() {
        return RollingPeriod;
    }

    public void setRollingPeriod(Integer RollingPeriod) {
        this.RollingPeriod = RollingPeriod;
    }

    public Integer getCancellationHorizon() {
        return CancellationHorizon;
    }

    public void setCancellationHorizon(Integer CancellationHorizon) {
        this.CancellationHorizon = CancellationHorizon;
    }

}
