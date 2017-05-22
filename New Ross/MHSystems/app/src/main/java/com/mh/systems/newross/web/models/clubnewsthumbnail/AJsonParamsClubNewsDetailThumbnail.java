
package com.mh.systems.newross.web.models.clubnewsthumbnail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsClubNewsDetailThumbnail {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("LoginMemberId")
    @Expose
    private String LoginMemberId;
    @SerializedName("ClubNewsId")
    @Expose
    private String ClubNewsId;

    /**
     * @return The version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return The callid
     */
    public String getCallid() {
        return callid;
    }

    /**
     * @param callid The callid
     */
    public void setCallid(String callid) {
        this.callid = callid;
    }

    /**
     * @return The loginMemberId
     */
    public String getLoginMemberId() {
        return LoginMemberId;
    }

    /**
     * @param LoginMemberId The LoginMemberId
     */
    public void setLoginMemberId(String LoginMemberId) {
        this.LoginMemberId = LoginMemberId;
    }

    /**
     * @return The ClubNewsId
     */
    public String getClubNewsId() {
        return ClubNewsId;
    }

    /**
     * @param ClubNewsId The ClubNewsId
     */
    public void setClubNewsId(String ClubNewsId) {
        this.ClubNewsId = ClubNewsId;
    }

}
