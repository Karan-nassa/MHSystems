
package com.mh.systems.halesworth.web.models.toggleprivacy;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AJsonParamsToggle {

    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("MemberId")
    @Expose
    private String MemberId;
    @SerializedName("TelNoHomePrivacy")
    @Expose
    private String TelNoHomePrivacy;
    @SerializedName("TelNoWorkPrivacy")
    @Expose
    private String TelNoWorkPrivacy;
    @SerializedName("TelNoMobPrivacy")
    @Expose
    private String TelNoMobPrivacy;
    @SerializedName("EMailPrivacy")
    @Expose
    private String EMailPrivacy;
    @SerializedName("AddressPrivacy")
    @Expose
    private String AddressPrivacy;

    /**
     * 
     * @return
     *     The version
     */
    public int getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * 
     * @return
     *     The callid
     */
    public String getCallid() {
        return callid;
    }

    /**
     * 
     * @param callid
     *     The callid
     */
    public void setCallid(String callid) {
        this.callid = callid;
    }

    /**
     * 
     * @return
     *     The MemberId
     */
    public String getMemberId() {
        return MemberId;
    }

    /**
     * 
     * @param MemberId
     *     The MemberId
     */
    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

    /**
     * 
     * @return
     *     The TelNoHomePrivacy
     */
    public String getTelNoHomePrivacy() {
        return TelNoHomePrivacy;
    }

    /**
     * 
     * @param TelNoHomePrivacy
     *     The TelNoHomePrivacy
     */
    public void setTelNoHomePrivacy(String TelNoHomePrivacy) {
        this.TelNoHomePrivacy = TelNoHomePrivacy;
    }

    /**
     * 
     * @return
     *     The TelNoWorkPrivacy
     */
    public String getTelNoWorkPrivacy() {
        return TelNoWorkPrivacy;
    }

    /**
     * 
     * @param TelNoWorkPrivacy
     *     The TelNoWorkPrivacy
     */
    public void setTelNoWorkPrivacy(String TelNoWorkPrivacy) {
        this.TelNoWorkPrivacy = TelNoWorkPrivacy;
    }

    /**
     * 
     * @return
     *     The TelNoMobPrivacy
     */
    public String getTelNoMobPrivacy() {
        return TelNoMobPrivacy;
    }

    /**
     * 
     * @param TelNoMobPrivacy
     *     The TelNoMobPrivacy
     */
    public void setTelNoMobPrivacy(String TelNoMobPrivacy) {
        this.TelNoMobPrivacy = TelNoMobPrivacy;
    }

    /**
     * 
     * @return
     *     The EMailPrivacy
     */
    public String getEMailPrivacy() {
        return EMailPrivacy;
    }

    /**
     * 
     * @param EMailPrivacy
     *     The EMailPrivacy
     */
    public void setEMailPrivacy(String EMailPrivacy) {
        this.EMailPrivacy = EMailPrivacy;
    }

    /**
     * 
     * @return
     *     The AddressPrivacy
     */
    public String getAddressPrivacy() {
        return AddressPrivacy;
    }

    /**
     * 
     * @param AddressPrivacy
     *     The AddressPrivacy
     */
    public void setAddressPrivacy(String AddressPrivacy) {
        this.AddressPrivacy = AddressPrivacy;
    }

}
