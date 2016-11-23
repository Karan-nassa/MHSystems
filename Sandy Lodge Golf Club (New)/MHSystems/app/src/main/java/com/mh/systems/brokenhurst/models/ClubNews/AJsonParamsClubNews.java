
package com.mh.systems.brokenhurst.models.ClubNews;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsClubNews {

    @SerializedName("LoginMemberId")
    @Expose
    private String LoginMemberId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AJsonParamsClubNews() {
    }

    /**
     * 
     * @return
     *     The loginMemberId
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

}
