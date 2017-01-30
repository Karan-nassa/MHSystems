
package com.mh.systems.demoapp.models.competitionsEntry;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompEligiblePlayersData {

    @SerializedName("EligibleMembers")
    @Expose
    private ArrayList<EligibleMember> EligibleMembers = new ArrayList<EligibleMember>();
    @SerializedName("EligibleFriends")
    @Expose
    private ArrayList<EligibleMember> EligibleFriends = new ArrayList<EligibleMember>();

    /**
     * @return The EligibleMembers
     */
    public ArrayList<EligibleMember> getEligibleMembers() {
        return EligibleMembers;
    }

    /**
     * @param EligibleMembers The EligibleMembers
     */
    public void setEligibleMembers(ArrayList<EligibleMember> EligibleMembers) {
        this.EligibleMembers = EligibleMembers;
    }

    /**
     * @return The EligibleFriends
     */
    public ArrayList<EligibleMember> getEligibleFriends() {
        return EligibleFriends;
    }

    /**
     * @param EligibleFriends The EligibleFriends
     */
    public void setEligibleFriends(ArrayList<EligibleMember> EligibleMember) {
        this.EligibleFriends = EligibleMember;
    }

}
