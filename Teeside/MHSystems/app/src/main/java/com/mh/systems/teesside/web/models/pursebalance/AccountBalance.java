
package com.mh.systems.teesside.web.models.pursebalance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountBalance {

    @SerializedName("CrnID")
    @Expose
    private Integer CrnID;
    @SerializedName("CrnSymbol")
    @Expose
    private String CrnSymbol;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Precision")
    @Expose
    private Integer Precision;
    @SerializedName("RawBalance")
    @Expose
    private Integer RawBalance;
    @SerializedName("Balance")
    @Expose
    private String Balance;

    public Integer getCrnID() {
        return CrnID;
    }

    public void setCrnID(Integer CrnID) {
        this.CrnID = CrnID;
    }

    public String getCrnSymbol() {
        return CrnSymbol;
    }

    public void setCrnSymbol(String CrnSymbol) {
        this.CrnSymbol = CrnSymbol;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Integer getPrecision() {
        return Precision;
    }

    public void setPrecision(Integer Precision) {
        this.Precision = Precision;
    }

    public Integer getRawBalance() {
        return RawBalance;
    }

    public void setRawBalance(Integer RawBalance) {
        this.RawBalance = RawBalance;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        this.Balance = Balance;
    }

}
