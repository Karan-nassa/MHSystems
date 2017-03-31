
package com.mh.systems.york.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinanceAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private FinanceAJsonParams aJsonParams;
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
    public FinanceAPI(String aClientId, String aCommand, FinanceAJsonParams aJsonParams, String aModuleId, String aUserClass) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParams = aJsonParams;
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
    public FinanceAJsonParams getAJsonParams() {
        return aJsonParams;
    }

    /**
     * @param aJsonParams The aJsonParams
     */
    public void setAJsonParams(FinanceAJsonParams aJsonParams) {
        this.aJsonParams = aJsonParams;
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
