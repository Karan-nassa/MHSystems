
package com.mh.systems.teesside.web.models.pursebalance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PurseBalanceData {

    @SerializedName("Status")
    @Expose
    private Status Status;
    @SerializedName("MemberID")
    @Expose
    private Integer MemberID;
    @SerializedName("AccountBalances")
    @Expose
    private List<AccountBalance> AccountBalances;

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status Status) {
        this.Status = Status;
    }

    public Integer getMemberID() {
        return MemberID;
    }

    public void setMemberID(Integer MemberID) {
        this.MemberID = MemberID;
    }

    public List<AccountBalance> getAccountBalances() {
        return AccountBalances;
    }

    public void setAccountBalances(List<AccountBalance> AccountBalances) {
        this.AccountBalances = AccountBalances;
    }

}
