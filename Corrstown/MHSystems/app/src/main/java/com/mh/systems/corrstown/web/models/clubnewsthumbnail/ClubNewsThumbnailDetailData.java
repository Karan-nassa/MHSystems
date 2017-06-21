
package com.mh.systems.corrstown.web.models.clubnewsthumbnail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClubNewsThumbnailDetailData {

    @SerializedName("ClubNewsID")
    @Expose
    private Integer ClubNewsID;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("Time")
    @Expose
    private String Time;
    @SerializedName("DateTimeText")
    @Expose
    private String DateTimeText;
    @SerializedName("IsActive")
    @Expose
    private Boolean IsActive;
    @SerializedName("IsRead")
    @Expose
    private Boolean IsRead;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean IsDeleted;

    /**
     * @return The ClubNewsID
     */
    public Integer getClubNewsID() {
        return ClubNewsID;
    }

    /**
     * @param ClubNewsID The ClubNewsID
     */
    public void setClubNewsID(Integer ClubNewsID) {
        this.ClubNewsID = ClubNewsID;
    }

    /**
     * @return The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * @param Message The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * @return The Date
     */
    public String getDate() {
        return Date;
    }

    /**
     * @param Date The Date
     */
    public void setDate(String Date) {
        this.Date = Date;
    }

    /**
     * @return The Time
     */
    public String getTime() {
        return Time;
    }

    /**
     * @param Time The Time
     */
    public void setTime(String Time) {
        this.Time = Time;
    }

    /**
     * @return The DateTimeText
     */
    public String getDateTimeText() {
        return DateTimeText;
    }

    /**
     * @param DateTimeText The DateTimeText
     */
    public void setDateTimeText(String DateTimeText) {
        this.DateTimeText = DateTimeText;
    }

    /**
     * @return The IsActive
     */
    public Boolean getIsActive() {
        return IsActive;
    }

    /**
     * @param IsActive The IsActive
     */
    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }

    /**
     * @return The IsRead
     */
    public Boolean getIsRead() {
        return IsRead;
    }

    /**
     * @param IsRead The IsRead
     */
    public void setIsRead(Boolean IsRead) {
        this.IsRead = IsRead;
    }

    /**
     * @return The IsDeleted
     */
    public Boolean getIsDeleted() {
        return IsDeleted;
    }

    /**
     * @param IsDeleted The IsDeleted
     */
    public void setIsDeleted(Boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

}
