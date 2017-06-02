
package com.mh.systems.redlibbets.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompetitionsData {

    @SerializedName("IsEntryOpen")
    @Expose
    private Boolean IsEntryOpen;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("pricePerGuest")
    @Expose
    private String pricePerGuest;
    @SerializedName("joinStatus")
    @Expose
    private Boolean joinStatus;
    @SerializedName("eventDate")
    @Expose
    private String eventDate;
    @SerializedName("eventTime")
    @Expose
    private String eventTime;
    @SerializedName("eventDateStr")
    @Expose
    private String eventDateStr;
    @SerializedName("monthName")
    @Expose
    private String monthName;
    @SerializedName("dayName")
    @Expose
    private String dayName;
    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("eventStatusStr")
    @Expose
    private String eventStatusStr;
    @SerializedName("IsMemberJoined")
    @Expose
    private boolean IsMemberJoined;

    /****************************************************************
     * START:
     * New four fields added to make Competitions fees dynamically.
     ***************************************************************/

    @SerializedName("PlayerPosition")
    @Expose
    private String PlayerPosition;
    @SerializedName("PlayerName")
    @Expose
    private String PlayerName;
    @SerializedName("PlayerHandicapped")
    @Expose
    private String PlayerHandicapped;
    @SerializedName("PlayerTotal")
    @Expose
    private String PlayerTotal;

    /**
     *
     * @return
     * The PlayerPosition
     */
    public String getPlayerPosition() {
        return PlayerPosition;
    }

    /**
     *
     * @param PlayerPosition
     * The PlayerPosition
     */
    public void setPlayerPosition(String PlayerPosition) {
        this.PlayerPosition = PlayerPosition;
    }

    /**
     *
     * @return
     * The PlayerName
     */
    public String getPlayerName() {
        return PlayerName;
    }

    /**
     *
     * @param PlayerName
     * The PlayerName
     */
    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    /**
     *
     * @return
     * The PlayerHandicapped
     */
    public String getPlayerHandicapped() {
        return PlayerHandicapped;
    }

    /**
     *
     * @param PlayerHandicapped
     * The PlayerHandicapped
     */
    public void setPlayerHandicapped(String PlayerHandicapped) {
        this.PlayerHandicapped = PlayerHandicapped;
    }

    /**
     *
     * @return
     * The playerTotal
     */
    public String getPlayerTotal() {
        return PlayerTotal;
    }

    /**
     *
     * @param PlayerTotal
     * The PlayerTotal
     */
    public void setPlayerTotal(String PlayerTotal) {
        this.PlayerTotal = PlayerTotal;
    }

    /****************************************************************
     * END:
     * New four fields added to make Competitions fees dynamically.
     ***************************************************************/

    /**
     * @return The eventStatusStr
     */
    public String getEventStatusStr() {
        return eventStatusStr;
    }

    /**
     * @param eventStatusStr The eventStatusStr
     */
    public void setEventStatusStr(String eventStatusStr) {
        this.eventStatusStr = eventStatusStr;
    }

    /**
     * @return The IsEntryOpen
     */
    public Boolean getEntryOpen() {
        return IsEntryOpen;
    }

    /**
     * @param entryOpen The IsEntryOpen
     */
    public void setEntryOpen(Boolean entryOpen) {
        IsEntryOpen = entryOpen;
    }

    /**
     * @return The eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * @param eventId The eventId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * @return The IsEntryOpen
     */
    public Boolean getIsEntryOpen() {
        return IsEntryOpen;
    }

    /**
     * @param IsEntryOpen The IsEntryOpen
     */
    public void setIsEntryOpen(Boolean IsEntryOpen) {
        this.IsEntryOpen = IsEntryOpen;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc The desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return The pricePerGuest
     */
    public String getPricePerGuest() {
        return pricePerGuest;
    }

    /**
     * @param pricePerGuest The pricePerGuest
     */
    public void setPricePerGuest(String pricePerGuest) {
        this.pricePerGuest = pricePerGuest;
    }

    /**
     * @return The joinStatus
     */
    public Boolean getJoinStatus() {
        return joinStatus;
    }

    /**
     * @param joinStatus The joinStatus
     */
    public void setJoinStatus(Boolean joinStatus) {
        this.joinStatus = joinStatus;
    }

    /**
     * @return The eventDate
     */
    public String getEventDate() {
        return eventDate;
    }

    /**
     * @param eventDate The eventDate
     */
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * @return The eventTime
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     * @param eventTime The eventTime
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * @return The eventDateStr
     */
    public String getEventDateStr() {
        return eventDateStr;
    }

    /**
     * @param eventDateStr The eventDateStr
     */
    public void setEventDateStr(String eventDateStr) {
        this.eventDateStr = eventDateStr;
    }

    /**
     * @return The monthName
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * @param monthName The monthName
     */
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    /**
     * @return The dayName
     */
    public String getDayName() {
        return dayName;
    }

    /**
     * @param dayName The dayName
     */
    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    /**
     * @return The IsMemberJoined
     */
    public boolean getIsMemberJoined() {
        return IsMemberJoined;
    }

    /**
     * @param isMemberJoined The isMemberJoined
     */
    public void setIsMemberJoined(boolean isMemberJoined) {
        IsMemberJoined = isMemberJoined;
    }


}
