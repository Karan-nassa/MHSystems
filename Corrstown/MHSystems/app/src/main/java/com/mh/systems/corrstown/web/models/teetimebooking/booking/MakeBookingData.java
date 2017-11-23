
package com.mh.systems.corrstown.web.models.teetimebooking.booking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeBookingData {

    @SerializedName("Version")
    @Expose
    private Integer Version;
    @SerializedName("Success")
    @Expose
    private Boolean Success;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;
    @SerializedName("FundsAvail")
    @Expose
    private Integer FundsAvail;
    @SerializedName("IsFlagged")
    @Expose
    private Boolean IsFlagged;
    @SerializedName("MonthStartDate")
    @Expose
    private String MonthStartDate;
    @SerializedName("FirstBookableDate")
    @Expose
    private String FirstBookableDate;
    @SerializedName("LastBookableDate")
    @Expose
    private String LastBookableDate;
    @SerializedName("SysParams")
    @Expose
    private SysParams SysParams;
    @SerializedName("Bookings")
    @Expose
    private List<Booking> Bookings = null;
    @SerializedName("Days")
    @Expose
    private List<Object> Days = null;
    @SerializedName("CurrentDay")
    @Expose
    private Object CurrentDay;
    @SerializedName("HaveBookings")
    @Expose
    private Boolean HaveBookings;

    public Integer getVersion() {
        return Version;
    }

    public void setVersion(Integer Version) {
        this.Version = Version;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Integer getMemberId() {
        return MemberId;
    }

    public void setMemberId(Integer MemberId) {
        this.MemberId = MemberId;
    }

    public Integer getFundsAvail() {
        return FundsAvail;
    }

    public void setFundsAvail(Integer FundsAvail) {
        this.FundsAvail = FundsAvail;
    }

    public Boolean getIsFlagged() {
        return IsFlagged;
    }

    public void setIsFlagged(Boolean IsFlagged) {
        this.IsFlagged = IsFlagged;
    }

    public String getMonthStartDate() {
        return MonthStartDate;
    }

    public void setMonthStartDate(String MonthStartDate) {
        this.MonthStartDate = MonthStartDate;
    }

    public String getFirstBookableDate() {
        return FirstBookableDate;
    }

    public void setFirstBookableDate(String FirstBookableDate) {
        this.FirstBookableDate = FirstBookableDate;
    }

    public String getLastBookableDate() {
        return LastBookableDate;
    }

    public void setLastBookableDate(String LastBookableDate) {
        this.LastBookableDate = LastBookableDate;
    }

    public SysParams getSysParams() {
        return SysParams;
    }

    public void setSysParams(SysParams SysParams) {
        this.SysParams = SysParams;
    }

    public List<Booking> getBookings() {
        return Bookings;
    }

    public void setBookings(List<Booking> Bookings) {
        this.Bookings = Bookings;
    }

    public List<Object> getDays() {
        return Days;
    }

    public void setDays(List<Object> Days) {
        this.Days = Days;
    }

    public Object getCurrentDay() {
        return CurrentDay;
    }

    public void setCurrentDay(Object CurrentDay) {
        this.CurrentDay = CurrentDay;
    }

    public Boolean getHaveBookings() {
        return HaveBookings;
    }

    public void setHaveBookings(Boolean HaveBookings) {
        this.HaveBookings = HaveBookings;
    }

}
