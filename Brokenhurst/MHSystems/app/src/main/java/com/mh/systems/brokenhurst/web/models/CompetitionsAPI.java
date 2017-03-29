
package com.mh.systems.brokenhurst.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionsAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private CompetitionsJsonParams competitionsJsonParams;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aUserClass")
    @Expose
    private String aUserClass;


    /**
     * Constructor to initialize data members to
     * POST.
     */
    public CompetitionsAPI(String aClientId, String aCommand, CompetitionsJsonParams competitionsJsonParams, String aModuleId, String aUserClass) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.competitionsJsonParams = competitionsJsonParams;
        this.aModuleId = aModuleId;
        this.aUserClass = aUserClass;
    }

    /**
     * @return The aClientId
     */
    public String getAClientId() {
        return aClientId;
    }

    /**
     * @param aClientId The aClientId
     */
    public void setAClientId(String aClientId) {
        this.aClientId = aClientId;
    }

    /**
     * @return The aCommand
     */
    public String getACommand() {
        return aCommand;
    }

    /**
     * @param aCommand The aCommand
     */
    public void setACommand(String aCommand) {
        this.aCommand = aCommand;
    }

    /**
     * @return The competitionsJsonParams
     */
    public CompetitionsJsonParams getAJsonParams() {
        return competitionsJsonParams;
    }

    /**
     * @param competitionsJsonParams The competitionsJsonParams
     */
    public void setAJsonParams(CompetitionsJsonParams competitionsJsonParams) {
        this.competitionsJsonParams = competitionsJsonParams;
    }

    /**
     * @return The aModuleId
     */
    public String getAModuleId() {
        return aModuleId;
    }

    /**
     * @param aModuleId The aModuleId
     */
    public void setAModuleId(String aModuleId) {
        this.aModuleId = aModuleId;
    }

    /**
     * @return The aUserClass
     */
    public String getAUserClass() {
        return aUserClass;
    }

    /**
     * @param aUserClass The aUserClass
     */
    public void setAUserClass(String aUserClass) {
        this.aUserClass = aUserClass;
    }

}
