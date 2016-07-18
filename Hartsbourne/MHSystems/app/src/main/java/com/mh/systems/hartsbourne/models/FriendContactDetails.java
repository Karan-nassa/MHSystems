
package com.mh.systems.hartsbourne.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FriendContactDetails {

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
    private com.mh.systems.hartsbourne.models.Address Address;

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
    public com.mh.systems.hartsbourne.models.Address getAddress() {
        return Address;
    }

    /**
     * 
     * @param Address
     *     The Address
     */
    public void setAddress(com.mh.systems.hartsbourne.models.Address Address) {
        this.Address = Address;
    }

}
