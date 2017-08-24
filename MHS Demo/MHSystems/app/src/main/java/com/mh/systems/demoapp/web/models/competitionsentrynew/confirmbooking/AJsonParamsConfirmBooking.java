
package com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsConfirmBooking {

    @SerializedName("ClientId")
    @Expose
    private Integer ClientId;
    @SerializedName("EventId")
    @Expose
    private Integer EventId;
    @SerializedName("PayeeId")
    @Expose
    private Integer PayeeId;
    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;
    @SerializedName("RemoveEntry")
    @Expose
    private Boolean RemoveEntry;
    @SerializedName("Booking")
    @Expose
    private List<Booking> Booking = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AJsonParamsConfirmBooking() {
    }

    /**
     * 
     * @param booking
     * @param eventId
     * @param payeeId
     * @param memberId
     * @param removeEntry
     * @param ClientId
     */
    public AJsonParamsConfirmBooking(Integer ClientId, Integer EventId, Integer PayeeId, Integer MemberId, Boolean RemoveEntry, List<Booking> Booking) {
        super();
        this.ClientId = ClientId;
        this.EventId = EventId;
        this.PayeeId = PayeeId;
        this.MemberId = MemberId;
        this.RemoveEntry = RemoveEntry;
        this.Booking = Booking;
    }

    public Integer getClientId() {
        return ClientId;
    }

    public void setClientId(Integer ClientId) {
        this.ClientId = ClientId;
    }

    public Integer getEventId() {
        return EventId;
    }

    public void setEventId(Integer EventId) {
        this.EventId = EventId;
    }

    public Integer getPayeeId() {
        return PayeeId;
    }

    public void setPayeeId(Integer PayeeId) {
        this.PayeeId = PayeeId;
    }

    public Integer getMemberId() {
        return MemberId;
    }

    public void setMemberId(Integer MemberId) {
        this.MemberId = MemberId;
    }

    public Boolean getRemoveEntry() {
        return RemoveEntry;
    }

    public void setRemoveEntry(Boolean RemoveEntry) {
        this.RemoveEntry = RemoveEntry;
    }

    public List<Booking> getBooking() {
        return Booking;
    }

    public void setBooking(List<Booking> Booking) {
        this.Booking = Booking;
    }

}
