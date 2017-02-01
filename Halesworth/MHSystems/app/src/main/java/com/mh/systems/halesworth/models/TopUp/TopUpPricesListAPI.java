package com.mh.systems.halesworth.models.TopUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Karan Nassa on 29-12-2016.
 * <p>
 * Organization : ucreate.it
 * Email        : karan@ucreate.it
 */
public class TopUpPricesListAPI {

    @SerializedName("aClientId")
    @Expose
    private String aClientId;
    @SerializedName("aMemberId")
    @Expose
    private String aMemberId;

    /**
     * No args constructor for use in serialization
     */
    public TopUpPricesListAPI() {
    }

    /**
     * @param aClientId
     * @param aMemberId
     */
    public TopUpPricesListAPI(String aClientId, String aMemberId) {
        super();
        this.aClientId = aClientId;
        this.aMemberId = aMemberId;
    }

    public String getAClientId() {
        return aClientId;
    }

    public void setAClientId(String aClientId) {
        this.aClientId = aClientId;
    }

    public String getAMemberId() {
        return aMemberId;
    }

    public void setAMemberId(String aMemberId) {
        this.aMemberId = aMemberId;
    }
}
