
package com.mh.systems.newross.web.models.clubnewsthumbnail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClubNewsThumbnailData implements Serializable {

    @SerializedName("ClubNewsID")
    @Expose
    private int ClubNewsID;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("Message")
    @Expose
    private String Message = "";
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
    private boolean IsActive;
    @SerializedName("IsRead")
    @Expose
    private boolean IsRead;
    @SerializedName("IsDeleted")
    @Expose
    private boolean IsDeleted;

    /**
     * @return The ClubNewsID
     */
    public int getClubNewsID() {
        return ClubNewsID;
    }

    /**
     * @param ClubNewsID The ClubNewsID
     */
    public void setClubNewsID(int ClubNewsID) {
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
    public void setTime(String time) {
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
    public boolean isIsActive() {
        return IsActive;
    }

    /**
     * @param IsActive The IsActive
     */
    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }

    /**
     * @return The IsRead
     */
    public boolean getIsRead() {
        return IsRead;
    }

    /**
     * @param IsRead The IsRead
     */
    public void setIsRead(boolean IsRead) {
        this.IsRead = IsRead;
    }

    /**
     * @return The IsDeleted
     */
    public boolean getIsDeleted() {
        return IsDeleted;
    }

    /**
     * @param IsDeleted The IsDeleted
     */
    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

}
