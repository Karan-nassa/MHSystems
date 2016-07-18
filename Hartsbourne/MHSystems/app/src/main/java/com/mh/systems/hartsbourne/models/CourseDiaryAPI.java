
package com.mh.systems.hartsbourne.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseDiaryAPI {

    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsCourse aJsonParams;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aUserClass")
    @Expose
    private String aUserClass;

    public CourseDiaryAPI(AJsonParamsCourse aJsonParams, String aModuleId, String aClientId, String aCommand, String aUserClass) {
        this.aJsonParams = aJsonParams;
        this.aModuleId = aModuleId;
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aUserClass = aUserClass;
    }


    /**
     * 
     * @return
     *     The aJsonParams
     */
    public AJsonParamsCourse getAJsonParams() {
        return aJsonParams;
    }

    /**
     * 
     * @param aJsonParams
     *     The aJsonParams
     */
    public void setAJsonParams(AJsonParamsCourse aJsonParams) {
        this.aJsonParams = aJsonParams;
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

}
