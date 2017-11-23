
package com.mh.systems.corrstown.web.models.teetimebooking.getdaydata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.R.attr.description;

public class Product {

    @SerializedName("PLU")
    @Expose
    private String PLU;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Price")
    @Expose
    private int Price;
    @SerializedName("BuggyIsValid")
    @Expose
    private Boolean BuggyIsValid;
    @SerializedName("BuggyPLU")
    @Expose
    private String BuggyPLU;
    @SerializedName("BuggyDescription")
    @Expose
    private String BuggyDescription;
    @SerializedName("BuggyIsOptional")
    @Expose
    private Boolean BuggyIsOptional;
    @SerializedName("BuggyPrice")
    @Expose
    private Integer BuggyPrice;
    @SerializedName("CrnSymbol")
    @Expose
    private String CrnSymbol;

    public String getPLU() {
        return PLU;
    }

    public void setPLU(String PLU) {
        this.PLU = PLU;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public Boolean getBuggyIsValid() {
        return BuggyIsValid;
    }

    public void setBuggyIsValid(Boolean BuggyIsValid) {
        this.BuggyIsValid = BuggyIsValid;
    }

    public String getBuggyPLU() {
        return BuggyPLU;
    }

    public void setBuggyPLU(String BuggyPLU) {
        this.BuggyPLU = BuggyPLU;
    }

    public String getBuggyDescription() {
        return BuggyDescription;
    }

    public void setBuggyDescription(String BuggyDescription) {
        this.BuggyDescription = BuggyDescription;
    }

    public Boolean getBuggyIsOptional() {
        return BuggyIsOptional;
    }

    public void setBuggyIsOptional(Boolean BuggyIsOptional) {
        this.BuggyIsOptional = BuggyIsOptional;
    }

    public Integer getBuggyPrice() {
        return BuggyPrice;
    }

    public void setBuggyPrice(Integer BuggyPrice) {
        this.BuggyPrice = BuggyPrice;
    }

    public String getCrnSymbol() {
        return CrnSymbol;
    }

    public void setCrnSymbol(String CrnSymbol) {
        this.CrnSymbol = CrnSymbol;
    }

}
