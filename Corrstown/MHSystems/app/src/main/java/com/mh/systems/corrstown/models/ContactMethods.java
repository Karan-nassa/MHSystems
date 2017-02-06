
package com.mh.systems.corrstown.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ContactMethods {

    @SerializedName("CorrespondenceMethod")
    @Expose
    private Integer CorrespondenceMethod;
    @SerializedName("BillingContactMethod")
    @Expose
    private Integer BillingContactMethod;
    @SerializedName("MessageContactMethod")
    @Expose
    private Integer MessageContactMethod;
    @SerializedName("MessageNotificationMethod")
    @Expose
    private Integer MessageNotificationMethod;

    /**
     * 
     * @return
     *     The CorrespondenceMethod
     */
    public Integer getCorrespondenceMethod() {
        return CorrespondenceMethod;
    }

    /**
     * 
     * @param CorrespondenceMethod
     *     The CorrespondenceMethod
     */
    public void setCorrespondenceMethod(Integer CorrespondenceMethod) {
        this.CorrespondenceMethod = CorrespondenceMethod;
    }

    /**
     * 
     * @return
     *     The BillingContactMethod
     */
    public Integer getBillingContactMethod() {
        return BillingContactMethod;
    }

    /**
     * 
     * @param BillingContactMethod
     *     The BillingContactMethod
     */
    public void setBillingContactMethod(Integer BillingContactMethod) {
        this.BillingContactMethod = BillingContactMethod;
    }

    /**
     * 
     * @return
     *     The MessageContactMethod
     */
    public Integer getMessageContactMethod() {
        return MessageContactMethod;
    }

    /**
     * 
     * @param MessageContactMethod
     *     The MessageContactMethod
     */
    public void setMessageContactMethod(Integer MessageContactMethod) {
        this.MessageContactMethod = MessageContactMethod;
    }

    /**
     * 
     * @return
     *     The MessageNotificationMethod
     */
    public Integer getMessageNotificationMethod() {
        return MessageNotificationMethod;
    }

    /**
     * 
     * @param MessageNotificationMethod
     *     The MessageNotificationMethod
     */
    public void setMessageNotificationMethod(Integer MessageNotificationMethod) {
        this.MessageNotificationMethod = MessageNotificationMethod;
    }

}
