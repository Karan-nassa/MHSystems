
package com.mh.systems.dunstabledowns.web.models.clubnews;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ClubNewsDetailAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private AJsonParamsClubNewsDetail aJsonParams;
    @SerializedName("aModuleId")
    @Expose
    private String aModuleId;

    /**
     * No args constructor for use in serialization
     */
    public ClubNewsDetailAPI() {
    }

    /**
     * @param aJsonParams
     * @param aModuleId
     * @param aClientId
     * @param aCommand
     */
    public ClubNewsDetailAPI(String aClientId, String aCommand, AJsonParamsClubNewsDetail aJsonParams, String aModuleId) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.aJsonParams = aJsonParams;
        this.aModuleId = aModuleId;
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
     * @return The aJsonParams
     */
    public AJsonParamsClubNewsDetail getAJsonParams() {
        return aJsonParams;
    }

    /**
     * @param aJsonParams The aJsonParams
     */
    public void setAJsonParams(AJsonParamsClubNewsDetail aJsonParams) {
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

}
