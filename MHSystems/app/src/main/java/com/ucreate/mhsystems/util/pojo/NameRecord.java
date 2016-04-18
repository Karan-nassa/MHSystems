
package com.ucreate.mhsystems.util.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NameRecord {

    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("Forename")
    @Expose
    private String Forename;
    @SerializedName("KnownAs")
    @Expose
    private String KnownAs;
    @SerializedName("Surname")
    @Expose
    private String Surname;
    @SerializedName("Suffix")
    @Expose
    private String Suffix;
    @SerializedName("DisplayName")
    @Expose
    private String DisplayName;
    @SerializedName("SortName")
    @Expose
    private String SortName;
    @SerializedName("DearName")
    @Expose
    private String DearName;
    @SerializedName("LabelName")
    @Expose
    private String LabelName;
    @SerializedName("FormalName")
    @Expose
    private String FormalName;

    /**
     * 
     * @return
     *     The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * 
     * @param Title
     *     The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * 
     * @return
     *     The Forename
     */
    public String getForename() {
        return Forename;
    }

    /**
     * 
     * @param Forename
     *     The Forename
     */
    public void setForename(String Forename) {
        this.Forename = Forename;
    }

    /**
     * 
     * @return
     *     The KnownAs
     */
    public String getKnownAs() {
        return KnownAs;
    }

    /**
     * 
     * @param KnownAs
     *     The KnownAs
     */
    public void setKnownAs(String KnownAs) {
        this.KnownAs = KnownAs;
    }

    /**
     * 
     * @return
     *     The Surname
     */
    public String getSurname() {
        return Surname;
    }

    /**
     * 
     * @param Surname
     *     The Surname
     */
    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    /**
     * 
     * @return
     *     The Suffix
     */
    public String getSuffix() {
        return Suffix;
    }

    /**
     * 
     * @param Suffix
     *     The Suffix
     */
    public void setSuffix(String Suffix) {
        this.Suffix = Suffix;
    }

    /**
     * 
     * @return
     *     The DisplayName
     */
    public String getDisplayName() {
        return DisplayName;
    }

    /**
     * 
     * @param DisplayName
     *     The DisplayName
     */
    public void setDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
    }

    /**
     * 
     * @return
     *     The SortName
     */
    public String getSortName() {
        return SortName;
    }

    /**
     * 
     * @param SortName
     *     The SortName
     */
    public void setSortName(String SortName) {
        this.SortName = SortName;
    }

    /**
     * 
     * @return
     *     The DearName
     */
    public String getDearName() {
        return DearName;
    }

    /**
     * 
     * @param DearName
     *     The DearName
     */
    public void setDearName(String DearName) {
        this.DearName = DearName;
    }

    /**
     * 
     * @return
     *     The LabelName
     */
    public String getLabelName() {
        return LabelName;
    }

    /**
     * 
     * @param LabelName
     *     The LabelName
     */
    public void setLabelName(String LabelName) {
        this.LabelName = LabelName;
    }

    /**
     * 
     * @return
     *     The FormalName
     */
    public String getFormalName() {
        return FormalName;
    }

    /**
     * 
     * @param FormalName
     *     The FormalName
     */
    public void setFormalName(String FormalName) {
        this.FormalName = FormalName;
    }

}
