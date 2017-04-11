
package com.mh.systems.demoapp.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ContactDetails {

    @SerializedName("TelNoHome")
    @Expose
    private String TelNoHome;
    @SerializedName("TelNoWork")
    @Expose
    private String TelNoWork;
    @SerializedName("TelNoMob")
    @Expose
    private String TelNoMob;
    @SerializedName("EMail")
    @Expose
    private String EMail;
    @SerializedName("Address")
    @Expose
    private Address Address;

    /* ++++ EDIT MY DETAILS PRIVACY ++++ */
    @SerializedName("EMailPrivacy")
    @Expose
    private String EMailPrivacy;
    @SerializedName("Address1Privacy")
    @Expose
    private String Address1Privacy;
    @SerializedName("TelNoMobPrivacy")
    @Expose
    private String TelNoMobPrivacy;
    @SerializedName("TelNoWorkPrivacy")
    @Expose
    private String TelNoWorkPrivacy;
    @SerializedName("TelNoHomePrivacy")
    @Expose
    private String TelNoHomePrivacy;

    /**
     * @return The TelNoWorkPrivacy
     */
    public String getTelNoWorkPrivacy() {
        return TelNoWorkPrivacy;
    }

    /**
     * @param TelNoWorkPrivacy The TelNoWorkPrivacy
     */
    public void setTelNoWorkPrivacy(String TelNoWorkPrivacy) {
        TelNoWorkPrivacy = TelNoWorkPrivacy;
    }

    /**
     * @return The TelNoHomePrivacy
     */
    public String getTelNoHomePrivacy() {
        return TelNoHomePrivacy;
    }

    /**
     * @param TelNoHomePrivacy The TelNoHomePrivacy
     */
    public void setTelNoHomePrivacy(String TelNoHomePrivacy) {
        TelNoHomePrivacy = TelNoHomePrivacy;
    }

    /**
     * @return The TelNoMobPrivacy
     */
    public String getTelNoMobPrivacy() {
        return TelNoMobPrivacy;
    }

    /**
     * @param TelNoMobPrivacy The TelNoMobPrivacy
     */
    public void setTelNoMobPrivacy(String TelNoMobPrivacy) {
        TelNoMobPrivacy = TelNoMobPrivacy;
    }

    /**
     * @return The EMailPrivacy
     */
    public String getEMailPrivacy() {
        return EMailPrivacy;
    }

    /**
     * @param EMailPrivacy The EMailPrivacy
     */
    public void setEMailPrivacy(String EMailPrivacy) {
        this.EMailPrivacy = EMailPrivacy;
    }

    /**
     * @return The Address1Privacy
     */
    public String getAddress1Privacy() {
        return Address1Privacy;
    }

    /**
     * @param Address1Privacy The Address1Privacy
     */
    public void setAddress1Privacy(String Address1Privacy) {
        Address1Privacy = Address1Privacy;
    }

  /* ++++ EDIT MY DETAILS PRIVACY ++++ */

    /**
     * @return The TelNoHome
     */
    public String getTelNoHome() {
        return TelNoHome;
    }

    /**
     * @param TelNoHome The TelNoHome
     */
    public void setTelNoHome(String TelNoHome) {
        this.TelNoHome = TelNoHome;
    }

    /**
     * @return The TelNoWork
     */
    public String getTelNoWork() {
        return TelNoWork;
    }

    /**
     * @param TelNoWork The TelNoWork
     */
    public void setTelNoWork(String TelNoWork) {
        this.TelNoWork = TelNoWork;
    }

    /**
     * @return The TelNoMob
     */
    public String getTelNoMob() {
        return TelNoMob;
    }

    /**
     * @param TelNoMob The TelNoMob
     */
    public void setTelNoMob(String TelNoMob) {
        this.TelNoMob = TelNoMob;
    }

    /**
     * @return The EMail
     */
    public String getEMail() {
        return EMail;
    }

    /**
     * @param EMail The EMail
     */
    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    /**
     * @return The Address
     */
    public Address getAddress() {
        return Address;
    }

    /**
     * @param Address The Address
     */
    public void setAddress(Address Address) {
        this.Address = Address;
    }

    /**
     *
     * @return
     *     The PrivateAddress
     *//*
    public PrivateAddress getPrivateAddress() {
        return PrivateAddress;
    }

    *//**
     *
     * @param PrivateAddress
     *     The PrivateAddress
     *//*
    public void setPrivateAddress(PrivateAddress PrivateAddress) {
        this.PrivateAddress = PrivateAddress;
    }*/

}
