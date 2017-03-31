
package com.mh.systems.halesworth.web.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HCapCertificate {

    @SerializedName("ClubName")
    @Expose
    private String ClubName;
    @SerializedName("IssueDate")
    @Expose
    private String IssueDate;
    @SerializedName("PlayerName")
    @Expose
    private String PlayerName;
    @SerializedName("CDHID")
    @Expose
    private String CDHID;
    @SerializedName("HisOrHer")
    @Expose
    private String HisOrHer;
    @SerializedName("ExactHCap")
    @Expose
    private String ExactHCap;
    @SerializedName("PlayHCap")
    @Expose
    private String PlayHCap;
    @SerializedName("SignedBy")
    @Expose
    private String SignedBy;
    @SerializedName("MHSBanner")
    @Expose
    private String MHSBanner;
    @SerializedName("SignatureImageUri")
    @Expose
    private String SignatureImageUri;

    /**
     * 
     * @return
     *     The ClubName
     */
    public String getClubName() {
        return ClubName;
    }

    /**
     * 
     * @param ClubName
     *     The ClubName
     */
    public void setClubName(String ClubName) {
        this.ClubName = ClubName;
    }

    /**
     * 
     * @return
     *     The IssueDate
     */
    public String getIssueDate() {
        return IssueDate;
    }

    /**
     * 
     * @param IssueDate
     *     The IssueDate
     */
    public void setIssueDate(String IssueDate) {
        this.IssueDate = IssueDate;
    }

    /**
     * 
     * @return
     *     The PlayerName
     */
    public String getPlayerName() {
        return PlayerName;
    }

    /**
     * 
     * @param PlayerName
     *     The PlayerName
     */
    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    /**
     * 
     * @return
     *     The CDHID
     */
    public String getCDHID() {
        return CDHID;
    }

    /**
     * 
     * @param CDHID
     *     The CDHID
     */
    public void setCDHID(String CDHID) {
        this.CDHID = CDHID;
    }

    /**
     * 
     * @return
     *     The HisOrHer
     */
    public String getHisOrHer() {
        return HisOrHer;
    }

    /**
     * 
     * @param HisOrHer
     *     The HisOrHer
     */
    public void setHisOrHer(String HisOrHer) {
        this.HisOrHer = HisOrHer;
    }

    /**
     * 
     * @return
     *     The ExactHCap
     */
    public String getExactHCap() {
        return ExactHCap;
    }

    /**
     * 
     * @param ExactHCap
     *     The ExactHCap
     */
    public void setExactHCap(String ExactHCap) {
        this.ExactHCap = ExactHCap;
    }

    /**
     * 
     * @return
     *     The PlayHCap
     */
    public String getPlayHCap() {
        return PlayHCap;
    }

    /**
     * 
     * @param PlayHCap
     *     The PlayHCap
     */
    public void setPlayHCap(String PlayHCap) {
        this.PlayHCap = PlayHCap;
    }

    /**
     * 
     * @return
     *     The SignedBy
     */
    public String getSignedBy() {
        return SignedBy;
    }

    /**
     * 
     * @param SignedBy
     *     The SignedBy
     */
    public void setSignedBy(String SignedBy) {
        this.SignedBy = SignedBy;
    }

    /**
     * 
     * @return
     *     The MHSBanner
     */
    public String getMHSBanner() {
        return MHSBanner;
    }

    /**
     * 
     * @param MHSBanner
     *     The MHSBanner
     */
    public void setMHSBanner(String MHSBanner) {
        this.MHSBanner = MHSBanner;
    }

    /**
     * 
     * @return
     *     The SignatureImageUri
     */
    public String getSignatureImageUri() {
        return SignatureImageUri;
    }

    /**
     * 
     * @param SignatureImageUri
     *     The SignatureImageUri
     */
    public void setSignatureImageUri(String SignatureImageUri) {
        this.SignatureImageUri = SignatureImageUri;
    }

}
