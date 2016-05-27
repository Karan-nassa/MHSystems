
package com.ucreate.mhsystems.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompetitionJoinAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParamsJoinCompetition")
    @Expose
    private AJsonParamsJoinCompetition aJsonParamsJoinCompetition;
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
    public CompetitionJoinAPI() {
    }

    /**
     * 
     * @param aJsonParamsJoinCompetition
     * @param aModuleId
     * @param aUserClass
     * @param aClientId
     * @param aCommand
     */
    public CompetitionJoinAPI(String aClientId, String aCommand, AJsonParamsJoinCompetition aJsonParamsJoinCompetition, String aModuleId, String aUserClass) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParamsJoinCompetition = aJsonParamsJoinCompetition;
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
     *     The aJsonParamsJoinCompetition
     */
    public AJsonParamsJoinCompetition getAJsonParams() {
        return aJsonParamsJoinCompetition;
    }

    /**
     * 
     * @param aJsonParamsJoinCompetition
     *     The aJsonParamsJoinCompetition
     */
    public void setAJsonParams(AJsonParamsJoinCompetition aJsonParamsJoinCompetition) {
        this.aJsonParamsJoinCompetition = aJsonParamsJoinCompetition;
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
