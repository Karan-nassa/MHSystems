
package com.ucreate.mhsystems.util.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyAccountAPI {

    @SerializedName("aClientId")
    @Expose
    private Integer aClientId;
    @SerializedName("aCommand")
    @Expose
    private String aCommand;
    @SerializedName("aJsonParams")
    @Expose
    private MyAccountJsonParams myAccountJsonParams;
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
    public MyAccountAPI(Integer aClientId, String aCommand, MyAccountJsonParams myAccountJsonParams, String aModuleId, String aUserClass) {
        this.aClientId = aClientId;
        this.aCommand = aCommand;
        this.myAccountJsonParams = myAccountJsonParams;
        this.aModuleId = aModuleId;
        this.aUserClass = aUserClass;
    }

    /**
     * @return The aClientId
     */
    public Integer getAClientId() {
        return aClientId;
    }

    /**
     * @param aClientId The aClientId
     */
    public void setAClientId(Integer aClientId) {
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
    public MyAccountJsonParams getAJsonParams() {
        return myAccountJsonParams;
    }

    /**
     * @param competitionsJsonParams The competitionsJsonParams
     */
    public void setAJsonParams(MyAccountJsonParams competitionsJsonParams) {
        this.myAccountJsonParams = competitionsJsonParams;
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
