package com.mh.systems.corrstown.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by karan@ucreate.co.in to get JSOn response of
 * COURSE DIARY data on 02-03-2016.
 */
public class CourseDiaryDataCopy {

    String category;
    String courseEventDate;
    String courseEventTime;
    String courseKey;
    String courseName;
    String dayName;
    String desc;
    String endTime;
    boolean isShareSocially;
    boolean joinStatus;
    String monthName;
    String prizePerGuest;
    String startTime;
    String title;
    String logo;
    int slotType;
    int iMonthNum;
    String Duration;
    @SerializedName("startTime24Format")
    @Expose
    private String startTime24Format;
    @SerializedName("strCourseEventDate")
    @Expose
    private String strCourseEventDate;


    public CourseDiaryDataCopy(String strCourseEventDate) {
        this.strCourseEventDate = strCourseEventDate;
    }

    /**
     * @return The startTime24Format
     */
    public String getStartTime24Format() {
        return startTime24Format;
    }

    /**
     * @param startTime24Format The startTime24Format
     */
    public void setStartTime24Format(String startTime24Format) {
        this.startTime24Format = startTime24Format;
    }

    /**
     * @return The strCourseEventDate
     */
    public String getStrCourseEventDate() {
        return strCourseEventDate;
    }

    /**
     * @param strCourseEventDate The strCourseEventDate
     */
    public void setStrCourseEventDate(String strCourseEventDate) {
        this.strCourseEventDate = strCourseEventDate;
    }


    /**
     * @return The Duration
     */
    public String getDuration() {
        return Duration;
    }

    /**
     * @param Duration The Duration
     */
    public void setDuration(String duration) {
        Duration = duration;
    }

    /**
     * @return The iMonthNum
     */
    public int getiMonthNum() {
        return iMonthNum;
    }

    /**
     * @return The iMonthNum
     */
    public void setiMonthNum(int iMonthNum) {
        this.iMonthNum = iMonthNum;
    }

    /**
     * @return The slotType
     */
    public int getSlotType() {
        return slotType;
    }

    /**
     * @param slotType The slotType
     */
    public void setSlotType(int slotType) {
        this.slotType = slotType;
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
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return The courseEventDate
     */
    public String getCourseEventDate() {
        return courseEventDate;
    }

    /**
     * @param courseEventDate The courseEventDate
     */
    public void setCourseEventDate(String courseEventDate) {
        this.courseEventDate = courseEventDate;
    }

    /**
     * @return The courseEventTime
     */
    public String getCourseEventTime() {
        return courseEventTime;
    }

    /**
     * @param courseEventTime The courseEventTime
     */
    public void setCourseEventTime(String courseEventTime) {
        this.courseEventTime = courseEventTime;
    }

    /**
     * @return The courseKey
     */
    public String getCourseKey() {
        return courseKey;
    }

    /**
     * @param courseKey The courseKey
     */
    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }

    /**
     * @return The courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @param courseName The courseName
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
     * @return The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime The endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return The isShareSocially
     */
    public boolean isShareSocially() {
        return isShareSocially;
    }

    /**
     * @param isShareSocially The isShareSocially
     */
    public void setIsShareSocially(boolean isShareSocially) {
        this.isShareSocially = isShareSocially;
    }

    /**
     * @return The joinStatus
     */
    public boolean isJoinStatus() {
        return joinStatus;
    }

    /**
     * @param joinStatus The joinStatus
     */
    public void setJoinStatus(boolean joinStatus) {
        this.joinStatus = joinStatus;
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
     * @return The prizePerGuest
     */
    public String getPrizePerGuest() {
        return prizePerGuest;
    }

    /**
     * @param prizePerGuest The prizePerGuest
     */
    public void setPrizePerGuest(String prizePerGuest) {
        this.prizePerGuest = prizePerGuest;
    }

    /**
     * @return The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
}
