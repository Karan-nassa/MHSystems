
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mh.systems.demoapp.web.models.competitionsentrynew.confirmbooking.Booking;

public class NewCompEntryData {

    @SerializedName("ClientId")
    @Expose
    private Integer ClientId;
    @SerializedName("EventID")
    @Expose
    private String EventID;
    @SerializedName("PayeeId")
    @Expose
    private Integer PayeeId;
    @SerializedName("PayeeName")
    @Expose
    private String PayeeName;
    @SerializedName("MemberId")
    @Expose
    private Integer MemberId;
    @SerializedName("EventStartDate")
    @Expose
    private EventStartDate EventStartDate;
    @SerializedName("EntryCloseDate")
    @Expose
    private EntryCloseDate EntryCloseDate;
    @SerializedName("EventName")
    @Expose
    private String EventName;
    @SerializedName("EventDescription")
    @Expose
    private String EventDescription;
    @SerializedName("TeamSize")
    @Expose
    private Integer TeamSize;
    @SerializedName("IsNewBooking")
    @Expose
    private Boolean IsNewBooking;
    @SerializedName("CanModifyBooking")
    @Expose
    private Boolean CanModifyBooking;
    @SerializedName("IsPayee")
    @Expose
    private Boolean IsPayee;
    @SerializedName("CanRemoveOwnTeamOnly")
    @Expose
    private Boolean CanRemoveOwnTeamOnly;
    @SerializedName("MaxTeamCount")
    @Expose
    private Integer MaxTeamCount;
    @SerializedName("IsSimpleMode")
    @Expose
    private Boolean IsSimpleMode;
    @SerializedName("IsZonedMode")
    @Expose
    private Boolean IsZonedMode;
    @SerializedName("IsSlotMode")
    @Expose
    private Boolean IsSlotMode;
    @SerializedName("AllowGuests")
    @Expose
    private Boolean AllowGuests;
    @SerializedName("FundsAvailable")
    @Expose
    private Integer FundsAvailable;
    @SerializedName("TotalBookingFee")
    @Expose
    private Integer TotalBookingFee;
    @SerializedName("CrnSymbol")
    @Expose
    private String CrnSymbol;
    @SerializedName("Zones")
    @Expose
    private List<Zone> Zones = null;
    @SerializedName("Booking")
    @Expose
    private List<Booking> Booking = null;
    @SerializedName("AllPlayers")
    @Expose
    private List<AllPlayer> AllPlayers = null;
    @SerializedName("HCapSelector")
    @Expose
    private Object HCapSelector;

    public Integer getClientId() {
        return ClientId;
    }

    public void setClientId(Integer ClientId) {
        this.ClientId = ClientId;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String EventID) {
        this.EventID = EventID;
    }

    public Integer getPayeeId() {
        return PayeeId;
    }

    public void setPayeeId(Integer PayeeId) {
        this.PayeeId = PayeeId;
    }

    public String getPayeeName() {
        return PayeeName;
    }

    public void setPayeeName(String PayeeName) {
        this.PayeeName = PayeeName;
    }

    public Integer getMemberId() {
        return MemberId;
    }

    public void setMemberId(Integer MemberId) {
        this.MemberId = MemberId;
    }

    public EventStartDate getEventStartDate() {
        return EventStartDate;
    }

    public void setEventStartDate(EventStartDate EventStartDate) {
        this.EventStartDate = EventStartDate;
    }

    public EntryCloseDate getEntryCloseDate() {
        return EntryCloseDate;
    }

    public void setEntryCloseDate(EntryCloseDate EntryCloseDate) {
        this.EntryCloseDate = EntryCloseDate;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String EventDescription) {
        this.EventDescription = EventDescription;
    }

    public Integer getTeamSize() {
        return TeamSize;
    }

    public void setTeamSize(Integer TeamSize) {
        this.TeamSize = TeamSize;
    }

    public Boolean getIsNewBooking() {
        return IsNewBooking;
    }

    public void setIsNewBooking(Boolean IsNewBooking) {
        this.IsNewBooking = IsNewBooking;
    }

    public Boolean getCanModifyBooking() {
        return CanModifyBooking;
    }

    public void setCanModifyBooking(Boolean CanModifyBooking) {
        this.CanModifyBooking = CanModifyBooking;
    }

    public Boolean getIsPayee() {
        return IsPayee;
    }

    public void setIsPayee(Boolean IsPayee) {
        this.IsPayee = IsPayee;
    }

    public Boolean getCanRemoveOwnTeamOnly() {
        return CanRemoveOwnTeamOnly;
    }

    public void setCanRemoveOwnTeamOnly(Boolean CanRemoveOwnTeamOnly) {
        this.CanRemoveOwnTeamOnly = CanRemoveOwnTeamOnly;
    }

    public Integer getMaxTeamCount() {
        return MaxTeamCount;
    }

    public void setMaxTeamCount(Integer MaxTeamCount) {
        this.MaxTeamCount = MaxTeamCount;
    }

    public Boolean getIsSimpleMode() {
        return IsSimpleMode;
    }

    public void setIsSimpleMode(Boolean IsSimpleMode) {
        this.IsSimpleMode = IsSimpleMode;
    }

    public Boolean getIsZonedMode() {
        return IsZonedMode;
    }

    public void setIsZonedMode(Boolean IsZonedMode) {
        this.IsZonedMode = IsZonedMode;
    }

    public Boolean getIsSlotMode() {
        return IsSlotMode;
    }

    public void setIsSlotMode(Boolean IsSlotMode) {
        this.IsSlotMode = IsSlotMode;
    }

    public Boolean getAllowGuests() {
        return AllowGuests;
    }

    public void setAllowGuests(Boolean AllowGuests) {
        this.AllowGuests = AllowGuests;
    }

    public Integer getFundsAvailable() {
        return FundsAvailable;
    }

    public void setFundsAvailable(Integer FundsAvailable) {
        this.FundsAvailable = FundsAvailable;
    }

    public Integer getTotalBookingFee() {
        return TotalBookingFee;
    }

    public void setTotalBookingFee(Integer TotalBookingFee) {
        this.TotalBookingFee = TotalBookingFee;
    }

    public String getCrnSymbol() {
        return CrnSymbol;
    }

    public void setCrnSymbol(String CrnSymbol) {
        this.CrnSymbol = CrnSymbol;
    }

    public List<Zone> getZones() {
        return Zones;
    }

    public void setZones(List<Zone> Zones) {
        this.Zones = Zones;
    }

    public List<Booking> getBooking() {
        return Booking;
    }

    public void setBooking(List<Booking> Booking) {
        this.Booking = Booking;
    }

    public List<AllPlayer> getAllPlayers() {
        return AllPlayers;
    }

    public void setAllPlayers(List<AllPlayer> AllPlayers) {
        this.AllPlayers = AllPlayers;
    }

    public Object getHCapSelector() {
        return HCapSelector;
    }

    public void setHCapSelector(Object HCapSelector) {
        this.HCapSelector = HCapSelector;
    }

}
