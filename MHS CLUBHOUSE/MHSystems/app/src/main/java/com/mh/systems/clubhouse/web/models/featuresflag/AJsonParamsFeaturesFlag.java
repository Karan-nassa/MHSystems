
package com.mh.systems.clubhouse.web.models.featuresflag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsFeaturesFlag {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("memberid")
    @Expose
    private String memberid;
    @SerializedName("LoginMemberId")
    @Expose
    private String LoginMemberId;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getLoginMemberId() {
        return LoginMemberId;
    }

    public void setLoginMemberId(String LoginMemberId) {
        this.LoginMemberId = LoginMemberId;
    }
}
