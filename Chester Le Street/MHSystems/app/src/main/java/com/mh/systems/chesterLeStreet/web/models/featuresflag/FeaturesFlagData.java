
package com.mh.systems.chesterLeStreet.web.models.featuresflag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturesFlagData {

    @SerializedName("ClientId")
    @Expose
    private Integer clientId;
    @SerializedName("ClubName")
    @Expose
    private String clubName;
    @SerializedName("CourseDiaryFeatures")
    @Expose
    private Boolean courseDiaryFeatures;
    @SerializedName("CompetitionsFeature")
    @Expose
    private Boolean competitionsFeature;
    @SerializedName("HandicapFeature")
    @Expose
    private Boolean handicapFeature;
    @SerializedName("ClubNewsFeature")
    @Expose
    private Boolean clubNewsFeature;
    @SerializedName("MembersFeature")
    @Expose
    private Boolean membersFeature;
    @SerializedName("YourAccountFeature")
    @Expose
    private Boolean yourAccountFeature;
    @SerializedName("PostCode")
    @Expose
    private String postCode;
    @SerializedName("WeatherFeature")
    @Expose
    private Boolean weatherFeature;
    @SerializedName("PushNotificationFeature")
    @Expose
    private Boolean pushNotificationFeature;
    @SerializedName("TopupFeature")
    @Expose
    private Boolean topupFeature;
    @SerializedName("MOTTFeature")
    @Expose
    private Boolean MOTTFeature;

    /**
     * Choose Completed Competitions Type if <MyEventFeature>
     * TRUE
    */
    @SerializedName("MyEventFeature")
    @Expose
    private boolean MyEventFeature;
    @SerializedName("MyEventOnly")
    @Expose
    private boolean MyEventOnly;
    @SerializedName("GenderFilter")
    @Expose
    private int GenderFilter;
    @SerializedName("HCapExactStr")
    @Expose
    private String HCapExactStr;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public Boolean getCourseDiaryFeatures() {
        return courseDiaryFeatures;
    }

    public void setCourseDiaryFeatures(Boolean courseDiaryFeatures) {
        this.courseDiaryFeatures = courseDiaryFeatures;
    }

    public Boolean getCompetitionsFeature() {
        return competitionsFeature;
    }

    public void setCompetitionsFeature(Boolean competitionsFeature) {
        this.competitionsFeature = competitionsFeature;
    }

    public Boolean getHandicapFeature() {
        return handicapFeature;
    }

    public void setHandicapFeature(Boolean handicapFeature) {
        this.handicapFeature = handicapFeature;
    }

    public Boolean getClubNewsFeature() {
        return clubNewsFeature;
    }

    public void setClubNewsFeature(Boolean clubNewsFeature) {
        this.clubNewsFeature = clubNewsFeature;
    }

    public Boolean getMembersFeature() {
        return membersFeature;
    }

    public void setMembersFeature(Boolean membersFeature) {
        this.membersFeature = membersFeature;
    }

    public Boolean getYourAccountFeature() {
        return yourAccountFeature;
    }

    public void setYourAccountFeature(Boolean yourAccountFeature) {
        this.yourAccountFeature = yourAccountFeature;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Boolean getWeatherFeature() {
        return weatherFeature;
    }

    public void setWeatherFeature(Boolean weatherFeature) {
        this.weatherFeature = weatherFeature;
    }

    public Boolean getPushNotificationFeature() {
        return pushNotificationFeature;
    }

    public void setPushNotificationFeature(Boolean pushNotificationFeature) {
        this.pushNotificationFeature = pushNotificationFeature;
    }

    public Boolean getTopupFeature() {
        return topupFeature;
    }

    public void setTopupFeature(Boolean topupFeature) {
        this.topupFeature = topupFeature;
    }

    public Boolean getMOTTFeature() {
        return MOTTFeature;
    }

    public void setMOTTFeature(Boolean MOTTFeature) {
        this.MOTTFeature = MOTTFeature;
    }

    public boolean isMyEventFeature() {
        return MyEventFeature;
    }

    public void setMyEventFeature(boolean myEventFeature) {
        MyEventFeature = myEventFeature;
    }

    public boolean isMyEventOnly() {
        return MyEventOnly;
    }

    public void setMyEventOnly(boolean myEventOnly) {
        MyEventOnly = myEventOnly;
    }

    public int getGenderFilter() {
        return GenderFilter;
    }

    public void setGenderFilter(int genderFilter) {
        GenderFilter = genderFilter;
    }

    /**
     * @return The HCapExactStr
     */
    public String getHCapExactStr() {
        return HCapExactStr;
    }

    /**
     * @param HCapExactStr The HCapExactStr
     */
    public void setHCapExactStr(String HCapExactStr) {
        this.HCapExactStr = HCapExactStr;
    }
}
