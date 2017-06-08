
package com.mh.systems.littlehampton.web.models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MembersData {

    @SerializedName("MembersList")
    @Expose
    private ArrayList<MembersList> MembersList = new ArrayList<MembersList>();
    @SerializedName("Member")
    @Expose
    private Member Member;

    public MembersData(ArrayList<com.mh.systems.littlehampton.web.models.MembersList> membersList, com.mh.systems.littlehampton.web.models.Member member) {
        this.MembersList = membersList;
        this.Member = member;
    }

    /**
     * 
     * @return
     *     The MembersList
     */
    public ArrayList<MembersList> getMembersList() {
        return MembersList;
    }

    /**
     * 
     * @param MembersList
     *     The MembersList
     */
    public void setMembersList(ArrayList<MembersList> MembersList) {
        this.MembersList = MembersList;
    }

    /**
     * 
     * @return
     *     The Member
     */
    public Member getMember() {
        return Member;
    }

    /**
     * 
     * @param Member
     *     The Member
     */
    public void setMember(Member Member) {
        this.Member = Member;
    }

}
