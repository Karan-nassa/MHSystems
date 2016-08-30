
package com.mh.systems.sandyLodge.models.ClubNews;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsClubNewsDetail {

    @SerializedName("LoginMemberId")
    @Expose
    private String LoginMemberId;
    @SerializedName("ClubNewsID")
    @Expose
    private Integer ClubNewsID;
    @SerializedName("IsRead")
    @Expose
    private Boolean IsRead;
    @SerializedName("IsDelete")
    @Expose
    private Boolean IsDelete;

    /**
     * 
     * @return
     *     The LoginMemberId
     */
    public String getLoginMemberId() {
        return LoginMemberId;
    }

    /**
     * 
     * @param LoginMemberId
     *     The LoginMemberId
     */
    public void setLoginMemberId(String LoginMemberId) {
        this.LoginMemberId = LoginMemberId;
    }

    /**
     * 
     * @return
     *     The ClubNewsID
     */
    public Integer getClubNewsID() {
        return ClubNewsID;
    }

    /**
     * 
     * @param ClubNewsID
     *     The ClubNewsID
     */
    public void setClubNewsID(Integer ClubNewsID) {
        this.ClubNewsID = ClubNewsID;
    }

    /**
     * 
     * @return
     *     The IsRead
     */
    public Boolean getIsRead() {
        return IsRead;
    }

    /**
     * 
     * @param IsRead
     *     The IsRead
     */
    public void setIsRead(Boolean IsRead) {
        this.IsRead = IsRead;
    }

    /**
     * 
     * @return
     *     The IsDelete
     */
    public Boolean getIsDelete() {
        return IsDelete;
    }

    /**
     * 
     * @param IsDelete
     *     The IsDelete
     */
    public void setIsDelete(Boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

}
