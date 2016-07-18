
package com.mh.systems.demoapp.models;


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
//    @SerializedName("PrivateAddress")
//    @Expose
//    private PrivateAddress PrivateAddress;

    /**
     * 
     * @return
     *     The TelNoHome
     */
    public String getTelNoHome() {
        return TelNoHome;
    }

    /**
     * 
     * @param TelNoHome
     *     The TelNoHome
     */
    public void setTelNoHome(String TelNoHome) {
        this.TelNoHome = TelNoHome;
    }

    /**
     * 
     * @return
     *     The TelNoWork
     */
    public String getTelNoWork() {
        return TelNoWork;
    }

    /**
     * 
     * @param TelNoWork
     *     The TelNoWork
     */
    public void setTelNoWork(String TelNoWork) {
        this.TelNoWork = TelNoWork;
    }

    /**
     * 
     * @return
     *     The TelNoMob
     */
    public String getTelNoMob() {
        return TelNoMob;
    }

    /**
     * 
     * @param TelNoMob
     *     The TelNoMob
     */
    public void setTelNoMob(String TelNoMob) {
        this.TelNoMob = TelNoMob;
    }

    /**
     * 
     * @return
     *     The EMail
     */
    public String getEMail() {
        return EMail;
    }

    /**
     * 
     * @param EMail
     *     The EMail
     */
    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    /**
     * 
     * @return
     *     The Address
     */
    public Address getAddress() {
        return Address;
    }

    /**
     * 
     * @param Address
     *     The Address
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
