package com.mh.systems.littlehampton.web.models.competitionsentry;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 29-09-2016.
 */

public class EligibleMember implements Serializable {

    @SerializedName("MemberID")
    @Expose
    private Integer MemberID;
    @SerializedName("NameRecord")
    @Expose
    private NameRecord NameRecord;
    @SerializedName("HCapExactStr")
    @Expose
    private String HCapExactStr;

    @SerializedName("PlayHCapStr")
    @Expose
    private String PlayHCapStr;

    boolean isMemberSelected;

    public EligibleMember(int MemberID, NameRecord NameRecord ){
        this.MemberID = MemberID;
        this.NameRecord = NameRecord;
    }

    /**
     *
     * @return
     *     The HCapExactStr
     */
    public String getHCapTypeStr() {
        return HCapExactStr;
    }

    /**
     *
     * @param HCapExactStr
     *     The HCapTypeStr
     */
    public void setHCapExactStr(String HCapExactStr) {
        this.HCapExactStr = HCapExactStr;
    }

    /**
     *
     * @return
     *     The MemberID
     */
    public Integer getMemberID() {
        return MemberID;
    }

    /**
     *
     * @param MemberID
     *     The MemberID
     */
    public void setMemberID(Integer MemberID) {
        this.MemberID = MemberID;
    }

    /**
     *
     * @return
     *     The NameRecord
     */
    public NameRecord getNameRecord() {
        return NameRecord;
    }

    /**
     *
     * @param NameRecord
     *     The NameRecord
     */
    public void setNameRecord(NameRecord NameRecord) {
        this.NameRecord = NameRecord;
    }

    /**
     *
     * @return
     *     The isMemberSelected
     */
    public boolean getIsMemberSelected() {
        return isMemberSelected;
    }

    /**
     *
     * @param isMemberSelected
     *     The isMemberSelected
     */
    public void setIsMemberSelected(boolean isMemberSelected) {
        this.isMemberSelected = isMemberSelected;
    }

    /**
     *
     * @return
     *     The PlayHCapStr
     */
    public String getPlayHCapStr() {
        return PlayHCapStr;
    }

    /**
     *
     * @param PlayHCapStr
     *     The PlayHCapStr
     */
    public void setPlayHCapStr(String PlayHCapStr) {
        this.PlayHCapStr = PlayHCapStr;
    }
}
