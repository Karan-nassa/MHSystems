
package com.mh.systems.york.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddMemberAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsAddMember aJsonParams;
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
    public AddMemberAPI() {
    }

    /**
     * 
     * @param aJsonParams
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public AddMemberAPI(String aClientId, String aCommand, AJsonParamsAddMember aJsonParams, String aModuleId, String aUserClass) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParams = aJsonParams;
        this.aModuleId = aModuleId;
        this.aUserClass = aUserClass;
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
     *     The aJsonParams
     */
    public AJsonParamsAddMember getAJsonParams() {
        return aJsonParams;
    }

    /**
     * 
     * @param aJsonParams
     *     The aJsonParams
     */
    public void setAJsonParams(AJsonParamsAddMember aJsonParams) {
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

}
