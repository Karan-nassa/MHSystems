
package com.mh.systems.redlibbets.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MemberOtherInfo {

    @SerializedName("RequireVATInvoice")
    @Expose
    private Boolean RequireVATInvoice;
    @SerializedName("Occupation")
    @Expose
    private String Occupation;
    @SerializedName("CarReg1")
    @Expose
    private String CarReg1;
    @SerializedName("CarReg2")
    @Expose
    private String CarReg2;
    @SerializedName("KioskLogonPin")
    @Expose
    private Integer KioskLogonPin;

    /**
     * @return The RequireVATInvoice
     */
    public Boolean getRequireVATInvoice() {
        return RequireVATInvoice;
    }

    /**
     * @param RequireVATInvoice The RequireVATInvoice
     */
    public void setRequireVATInvoice(Boolean RequireVATInvoice) {
        this.RequireVATInvoice = RequireVATInvoice;
    }

    /**
     * @return The Occupation
     */
    public String getOccupation() {
        return Occupation;
    }

    /**
     * @param Occupation The Occupation
     */
    public void setOccupation(String Occupation) {
        this.Occupation = Occupation;
    }

    /**
     * @return The CarReg1
     */
    public String getCarReg1() {
        return CarReg1;
    }

    /**
     * @param CarReg1 The CarReg1
     */
    public void setCarReg1(String CarReg1) {
        this.CarReg1 = CarReg1;
    }

    /**
     * @return The CarReg2
     */
    public String getCarReg2() {
        return CarReg2;
    }

    /**
     * @param CarReg2 The CarReg2
     */
    public void setCarReg2(String CarReg2) {
        this.CarReg2 = CarReg2;
    }

    /**
     * @return The KioskLogonPin
     */
    public Integer getKioskLogonPin() {
        return KioskLogonPin;
    }

    /**
     * @param KioskLogonPin The KioskLogonPin
     */
    public void setKioskLogonPin(Integer KioskLogonPin) {
        this.KioskLogonPin = KioskLogonPin;
    }

}
