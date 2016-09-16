
package com.mh.systems.demoapp.models.competitionsEntry;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompEligiblePlayersData {

    @SerializedName("EligibleMembers")
    @Expose
    private List<EligibleMember> EligibleMembers = new ArrayList<EligibleMember>();
    @SerializedName("EligibleFriends")
    @Expose
    private List<Object> EligibleFriends = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The EligibleMembers
     */
    public List<EligibleMember> getEligibleMembers() {
        return EligibleMembers;
    }

    /**
     * 
     * @param EligibleMembers
     *     The EligibleMembers
     */
    public void setEligibleMembers(List<EligibleMember> EligibleMembers) {
        this.EligibleMembers = EligibleMembers;
    }

    /**
     * 
     * @return
     *     The EligibleFriends
     */
    public List<Object> getEligibleFriends() {
        return EligibleFriends;
    }

    /**
     * 
     * @param EligibleFriends
     *     The EligibleFriends
     */
    public void setEligibleFriends(List<Object> EligibleFriends) {
        this.EligibleFriends = EligibleFriends;
    }

}
