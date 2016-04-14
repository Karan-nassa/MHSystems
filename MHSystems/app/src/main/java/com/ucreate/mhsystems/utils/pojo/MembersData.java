
package com.ucreate.mhsystems.utils.pojo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MembersData {

    @SerializedName("MembersList")
    @Expose
    private List<MembersList> MembersList = new ArrayList<MembersList>();
    @SerializedName("Member")
    @Expose
    private Member Member;

    /**
     * 
     * @return
     *     The MembersList
     */
    public List<MembersList> getMembersList() {
        return MembersList;
    }

    /**
     * 
     * @param MembersList
     *     The MembersList
     */
    public void setMembersList(List<MembersList> MembersList) {
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
