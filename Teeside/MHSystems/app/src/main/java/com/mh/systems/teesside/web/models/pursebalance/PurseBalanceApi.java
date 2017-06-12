
package com.mh.systems.teesside.web.models.pursebalance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurseBalanceApi {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsPurseApi aJsonParamsPurseApi;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;
    @SerializedName("aUserClass")
    @Expose
    private String aUserClass;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PurseBalanceApi() {
    }

    /**
     * 
     * @param aJsonParamsPurseApi
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public PurseBalanceApi(String aClientId, String aCommand, AJsonParamsPurseApi aJsonParamsPurseApi, String aModuleId, String aUserClass) {
        super();
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParamsPurseApi = aJsonParamsPurseApi;
        this.aModuleId = aModuleId;
        this.aUserClass = aUserClass;
    }

    public String getAClientId() {
        return aClientId;
    }

    public void setAClientId(String aClientId) {
        this.aClientId = aClientId;
    }

    public String getACommand() {
        return aCommand;
    }

    public void setACommand(String aCommand) {
        this.aCommand = aCommand;
    }

    public AJsonParamsPurseApi getAJsonParams() {
        return aJsonParamsPurseApi;
    }

    public void setAJsonParams(AJsonParamsPurseApi aJsonParamsPurseApi) {
        this.aJsonParamsPurseApi = aJsonParamsPurseApi;
    }

    public String getAModuleId() {
        return aModuleId;
    }

    public void setAModuleId(String aModuleId) {
        this.aModuleId = aModuleId;
    }

    public String getAUserClass() {
        return aUserClass;
    }

    public void setAUserClass(String aUserClass) {
        this.aUserClass = aUserClass;
    }

}
