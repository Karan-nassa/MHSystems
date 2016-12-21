
package com.mh.systems.sandylodge.models.CourseNames;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CourseNamesAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aUserClass")
    @Expose
    private String aUserClass;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsCourseNames aJsonParams;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CourseNamesAPI() {
    }

    /**
     * 
     * @param aJsonParams
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public CourseNamesAPI(String aClientId, String aCommand, String aModuleId, String aUserClass, AJsonParamsCourseNames aJsonParams) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aModuleId = aModuleId;
        this.aUserClass = aUserClass;
        this.aJsonParams = aJsonParams;
    }

    /**
     * 
     * @return
     *     The aClientId
     */
    public String getAClientId() {
        return aClientId;
    }

    /**
     * 
     * @param aClientId
     *     The aClientId
     */
    public void setAClientId(String aClientId) {
        this.aClientId = aClientId;
    }

    /**
     * 
     * @return
     *     The aCommand
     */
    public String getACommand() {
        return aCommand;
    }

    /**
     * 
     * @param aCommand
     *     The aCommand
     */
    public void setACommand(String aCommand) {
        this.aCommand = aCommand;
    }

    /**
     * 
     * @return
     *     The aModuleId
     */
    public String getAModuleId() {
        return aModuleId;
    }

    /**
     * 
     * @param aModuleId
     *     The aModuleId
     */
    public void setAModuleId(String aModuleId) {
        this.aModuleId = aModuleId;
    }

    /**
     * 
     * @return
     *     The aUserClass
     */
    public String getAUserClass() {
        return aUserClass;
    }

    /**
     * 
     * @param aUserClass
     *     The aUserClass
     */
    public void setAUserClass(String aUserClass) {
        this.aUserClass = aUserClass;
    }

    /**
     * 
     * @return
     *     The aJsonParams
     */
    public AJsonParamsCourseNames getAJsonParams() {
        return aJsonParams;
    }

    /**
     * 
     * @param aJsonParams
     *     The aJsonParams
     */
    public void setAJsonParams(AJsonParamsCourseNames aJsonParams) {
        this.aJsonParams = aJsonParams;
    }

}
