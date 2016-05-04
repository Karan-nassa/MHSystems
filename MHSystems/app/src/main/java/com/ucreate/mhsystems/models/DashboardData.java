
package com.ucreate.mhsystems.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DashboardData {

    @SerializedName("ClubID")
    @Expose
    private Integer ClubID;
    @SerializedName("MemberID")
    @Expose
    private Integer MemberID;
    @SerializedName("UserLoginID")
    @Expose
    private String UserLoginID;
    @SerializedName("UserPassword")
    @Expose
    private String UserPassword;
    @SerializedName("AutoPassword")
    @Expose
    private String AutoPassword;
    @SerializedName("IsReadOnly")
    @Expose
    private Boolean IsReadOnly;
    @SerializedName("IsRegistered")
    @Expose
    private Boolean IsRegistered;
    @SerializedName("MemberIDAsString")
    @Expose
    private String MemberIDAsString;
    @SerializedName("NameRecord")
    @Expose
    private com.ucreate.mhsystems.models.NameRecord NameRecord;
    @SerializedName("MembershipStatus")
    @Expose
    private String MembershipStatus;
    @SerializedName("Gender")
    @Expose
    private Integer Gender;
    @SerializedName("AgeOrAdult")
    @Expose
    private Integer AgeOrAdult;
    @SerializedName("ContactDetails")
    @Expose
    private com.ucreate.mhsystems.models.ContactDetails ContactDetails;
    @SerializedName("ContactMethods")
    @Expose
    private com.ucreate.mhsystems.models.ContactMethods ContactMethods;
    @SerializedName("MemberOtherInfo")
    @Expose
    private com.ucreate.mhsystems.models.MemberOtherInfo MemberOtherInfo;
    @SerializedName("WebConfig")
    @Expose
    private com.ucreate.mhsystems.models.WebConfig WebConfig;
    @SerializedName("SocialNetworks")
    @Expose
    private List<Object> SocialNetworks = new ArrayList<Object>();
    @SerializedName("IsSuperUser")
    @Expose
    private Boolean IsSuperUser;
    @SerializedName("IsSysAdmin")
    @Expose
    private Boolean IsSysAdmin;
    @SerializedName("IsMensGolfAdmin")
    @Expose
    private Boolean IsMensGolfAdmin;
    @SerializedName("IsLadiesGolfAdmin")
    @Expose
    private Boolean IsLadiesGolfAdmin;
    @SerializedName("MessageToMember")
    @Expose
    private Object MessageToMember;
    @SerializedName("HCapTypeStr")
    @Expose
    private String HCapTypeStr;
    @SerializedName("HCapType")
    @Expose
    private Integer HCapType;
    @SerializedName("HCapCat")
    @Expose
    private Integer HCapCat;
    @SerializedName("HCapCatStr")
    @Expose
    private String HCapCatStr;
    @SerializedName("HCapExactStr")
    @Expose
    private String HCapExactStr;
    @SerializedName("HCapPlay")
    @Expose
    private Integer HCapPlay;
    @SerializedName("HCapPlayStr")
    @Expose
    private String HCapPlayStr;
    @SerializedName("HCapStatus")
    @Expose
    private String HCapStatus;
    @SerializedName("HCapClub")
    @Expose
    private String HCapClub;
    @SerializedName("UnionID")
    @Expose
    private String UnionID;
    @SerializedName("IsGolfer")
    @Expose
    private Boolean IsGolfer;
    @SerializedName("IsLoggedInUser")
    @Expose
    private Boolean IsLoggedInUser;
    @SerializedName("IsUserFollowing")
    @Expose
    private Boolean IsUserFollowing;
    @SerializedName("IsUserNominated")
    @Expose
    private Boolean IsUserNominated;
    @SerializedName("CanUserSeeStats")
    @Expose
    private Boolean CanUserSeeStats;
    @SerializedName("LinksFromLoggedOnUser")
    @Expose
    private Integer LinksFromLoggedOnUser;
    @SerializedName("LinksToLoggedOnUser")
    @Expose
    private Integer LinksToLoggedOnUser;
    @SerializedName("HCapListstr")
    @Expose
    private Object HCapListstr;
    @SerializedName("LastJoiningDate")
    @Expose
    private String LastJoiningDate;
    @SerializedName("hCapList")
    @Expose
    private List<HCapList> hCapList = new ArrayList<HCapList>();
    @SerializedName("IsVisitor")
    @Expose
    private Boolean IsVisitor;
    @SerializedName("MemberIDStr")
    @Expose
    private String MemberIDStr;
    @SerializedName("VisibleLinks")
    @Expose
    private Integer VisibleLinks;
    @SerializedName("PrivacyContext")
    @Expose
    private Integer PrivacyContext;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("FormalName")
    @Expose
    private String FormalName;
    @SerializedName("LabelName")
    @Expose
    private String LabelName;
    @SerializedName("DearName")
    @Expose
    private String DearName;
    @SerializedName("KnownAs")
    @Expose
    private String KnownAs;
    @SerializedName("VisibleTelNoHome")
    @Expose
    private String VisibleTelNoHome;
    @SerializedName("VisibleTelNoWork")
    @Expose
    private String VisibleTelNoWork;
    @SerializedName("VisibleTelNoMob")
    @Expose
    private String VisibleTelNoMob;
    @SerializedName("VisibleEmail")
    @Expose
    private String VisibleEmail;
    @SerializedName("VisibleAddressAsLine")
    @Expose
    private String VisibleAddressAsLine;

    /**
     * 
     * @return
     *     The ClubID
     */
    public Integer getClubID() {
        return ClubID;
    }

    /**
     * 
     * @param ClubID
     *     The ClubID
     */
    public void setClubID(Integer ClubID) {
        this.ClubID = ClubID;
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
     *     The UserLoginID
     */
    public String getUserLoginID() {
        return UserLoginID;
    }

    /**
     * 
     * @param UserLoginID
     *     The UserLoginID
     */
    public void setUserLoginID(String UserLoginID) {
        this.UserLoginID = UserLoginID;
    }

    /**
     * 
     * @return
     *     The UserPassword
     */
    public String getUserPassword() {
        return UserPassword;
    }

    /**
     * 
     * @param UserPassword
     *     The UserPassword
     */
    public void setUserPassword(String UserPassword) {
        this.UserPassword = UserPassword;
    }

    /**
     * 
     * @return
     *     The AutoPassword
     */
    public String getAutoPassword() {
        return AutoPassword;
    }

    /**
     * 
     * @param AutoPassword
     *     The AutoPassword
     */
    public void setAutoPassword(String AutoPassword) {
        this.AutoPassword = AutoPassword;
    }

    /**
     * 
     * @return
     *     The IsReadOnly
     */
    public Boolean getIsReadOnly() {
        return IsReadOnly;
    }

    /**
     * 
     * @param IsReadOnly
     *     The IsReadOnly
     */
    public void setIsReadOnly(Boolean IsReadOnly) {
        this.IsReadOnly = IsReadOnly;
    }

    /**
     * 
     * @return
     *     The IsRegistered
     */
    public Boolean getIsRegistered() {
        return IsRegistered;
    }

    /**
     * 
     * @param IsRegistered
     *     The IsRegistered
     */
    public void setIsRegistered(Boolean IsRegistered) {
        this.IsRegistered = IsRegistered;
    }

    /**
     * 
     * @return
     *     The MemberIDAsString
     */
    public String getMemberIDAsString() {
        return MemberIDAsString;
    }

    /**
     * 
     * @param MemberIDAsString
     *     The MemberIDAsString
     */
    public void setMemberIDAsString(String MemberIDAsString) {
        this.MemberIDAsString = MemberIDAsString;
    }

    /**
     * 
     * @return
     *     The NameRecord
     */
    public com.ucreate.mhsystems.models.NameRecord getNameRecord() {
        return NameRecord;
    }

    /**
     * 
     * @param NameRecord
     *     The NameRecord
     */
    public void setNameRecord(com.ucreate.mhsystems.models.NameRecord NameRecord) {
        this.NameRecord = NameRecord;
    }

    /**
     * 
     * @return
     *     The MembershipStatus
     */
    public String getMembershipStatus() {
        return MembershipStatus;
    }

    /**
     * 
     * @param MembershipStatus
     *     The MembershipStatus
     */
    public void setMembershipStatus(String MembershipStatus) {
        this.MembershipStatus = MembershipStatus;
    }

    /**
     * 
     * @return
     *     The Gender
     */
    public Integer getGender() {
        return Gender;
    }

    /**
     * 
     * @param Gender
     *     The Gender
     */
    public void setGender(Integer Gender) {
        this.Gender = Gender;
    }

    /**
     * 
     * @return
     *     The AgeOrAdult
     */
    public Integer getAgeOrAdult() {
        return AgeOrAdult;
    }

    /**
     * 
     * @param AgeOrAdult
     *     The AgeOrAdult
     */
    public void setAgeOrAdult(Integer AgeOrAdult) {
        this.AgeOrAdult = AgeOrAdult;
    }

    /**
     * 
     * @return
     *     The ContactDetails
     */
    public com.ucreate.mhsystems.models.ContactDetails getContactDetails() {
        return ContactDetails;
    }

    /**
     * 
     * @param ContactDetails
     *     The ContactDetails
     */
    public void setContactDetails(com.ucreate.mhsystems.models.ContactDetails ContactDetails) {
        this.ContactDetails = ContactDetails;
    }

    /**
     * 
     * @return
     *     The ContactMethods
     */
    public com.ucreate.mhsystems.models.ContactMethods getContactMethods() {
        return ContactMethods;
    }

    /**
     * 
     * @param ContactMethods
     *     The ContactMethods
     */
    public void setContactMethods(com.ucreate.mhsystems.models.ContactMethods ContactMethods) {
        this.ContactMethods = ContactMethods;
    }

    /**
     * 
     * @return
     *     The MemberOtherInfo
     */
    public com.ucreate.mhsystems.models.MemberOtherInfo getMemberOtherInfo() {
        return MemberOtherInfo;
    }

    /**
     * 
     * @param MemberOtherInfo
     *     The MemberOtherInfo
     */
    public void setMemberOtherInfo(com.ucreate.mhsystems.models.MemberOtherInfo MemberOtherInfo) {
        this.MemberOtherInfo = MemberOtherInfo;
    }

    /**
     * 
     * @return
     *     The WebConfig
     */
    public com.ucreate.mhsystems.models.WebConfig getWebConfig() {
        return WebConfig;
    }

    /**
     * 
     * @param WebConfig
     *     The WebConfig
     */
    public void setWebConfig(com.ucreate.mhsystems.models.WebConfig WebConfig) {
        this.WebConfig = WebConfig;
    }

    /**
     * 
     * @return
     *     The SocialNetworks
     */
    public List<Object> getSocialNetworks() {
        return SocialNetworks;
    }

    /**
     * 
     * @param SocialNetworks
     *     The SocialNetworks
     */
    public void setSocialNetworks(List<Object> SocialNetworks) {
        this.SocialNetworks = SocialNetworks;
    }

    /**
     * 
     * @return
     *     The IsSuperUser
     */
    public Boolean getIsSuperUser() {
        return IsSuperUser;
    }

    /**
     * 
     * @param IsSuperUser
     *     The IsSuperUser
     */
    public void setIsSuperUser(Boolean IsSuperUser) {
        this.IsSuperUser = IsSuperUser;
    }

    /**
     * 
     * @return
     *     The IsSysAdmin
     */
    public Boolean getIsSysAdmin() {
        return IsSysAdmin;
    }

    /**
     * 
     * @param IsSysAdmin
     *     The IsSysAdmin
     */
    public void setIsSysAdmin(Boolean IsSysAdmin) {
        this.IsSysAdmin = IsSysAdmin;
    }

    /**
     * 
     * @return
     *     The IsMensGolfAdmin
     */
    public Boolean getIsMensGolfAdmin() {
        return IsMensGolfAdmin;
    }

    /**
     * 
     * @param IsMensGolfAdmin
     *     The IsMensGolfAdmin
     */
    public void setIsMensGolfAdmin(Boolean IsMensGolfAdmin) {
        this.IsMensGolfAdmin = IsMensGolfAdmin;
    }

    /**
     * 
     * @return
     *     The IsLadiesGolfAdmin
     */
    public Boolean getIsLadiesGolfAdmin() {
        return IsLadiesGolfAdmin;
    }

    /**
     * 
     * @param IsLadiesGolfAdmin
     *     The IsLadiesGolfAdmin
     */
    public void setIsLadiesGolfAdmin(Boolean IsLadiesGolfAdmin) {
        this.IsLadiesGolfAdmin = IsLadiesGolfAdmin;
    }

    /**
     * 
     * @return
     *     The MessageToMember
     */
    public Object getMessageToMember() {
        return MessageToMember;
    }

    /**
     * 
     * @param MessageToMember
     *     The MessageToMember
     */
    public void setMessageToMember(Object MessageToMember) {
        this.MessageToMember = MessageToMember;
    }

    /**
     * 
     * @return
     *     The HCapTypeStr
     */
    public String getHCapTypeStr() {
        return HCapTypeStr;
    }

    /**
     * 
     * @param HCapTypeStr
     *     The HCapTypeStr
     */
    public void setHCapTypeStr(String HCapTypeStr) {
        this.HCapTypeStr = HCapTypeStr;
    }

    /**
     * 
     * @return
     *     The HCapType
     */
    public Integer getHCapType() {
        return HCapType;
    }

    /**
     * 
     * @param HCapType
     *     The HCapType
     */
    public void setHCapType(Integer HCapType) {
        this.HCapType = HCapType;
    }

    /**
     * 
     * @return
     *     The HCapCat
     */
    public Integer getHCapCat() {
        return HCapCat;
    }

    /**
     * 
     * @param HCapCat
     *     The HCapCat
     */
    public void setHCapCat(Integer HCapCat) {
        this.HCapCat = HCapCat;
    }

    /**
     * 
     * @return
     *     The HCapCatStr
     */
    public String getHCapCatStr() {
        return HCapCatStr;
    }

    /**
     * 
     * @param HCapCatStr
     *     The HCapCatStr
     */
    public void setHCapCatStr(String HCapCatStr) {
        this.HCapCatStr = HCapCatStr;
    }

    /**
     * 
     * @return
     *     The HCapExactStr
     */
    public String getHCapExactStr() {
        return HCapExactStr;
    }

    /**
     * 
     * @param HCapExactStr
     *     The HCapExactStr
     */
    public void setHCapExactStr(String HCapExactStr) {
        this.HCapExactStr = HCapExactStr;
    }

    /**
     * 
     * @return
     *     The HCapPlay
     */
    public Integer getHCapPlay() {
        return HCapPlay;
    }

    /**
     * 
     * @param HCapPlay
     *     The HCapPlay
     */
    public void setHCapPlay(Integer HCapPlay) {
        this.HCapPlay = HCapPlay;
    }

    /**
     * 
     * @return
     *     The HCapPlayStr
     */
    public String getHCapPlayStr() {
        return HCapPlayStr;
    }

    /**
     * 
     * @param HCapPlayStr
     *     The HCapPlayStr
     */
    public void setHCapPlayStr(String HCapPlayStr) {
        this.HCapPlayStr = HCapPlayStr;
    }

    /**
     * 
     * @return
     *     The HCapStatus
     */
    public String getHCapStatus() {
        return HCapStatus;
    }

    /**
     * 
     * @param HCapStatus
     *     The HCapStatus
     */
    public void setHCapStatus(String HCapStatus) {
        this.HCapStatus = HCapStatus;
    }

    /**
     * 
     * @return
     *     The HCapClub
     */
    public String getHCapClub() {
        return HCapClub;
    }

    /**
     * 
     * @param HCapClub
     *     The HCapClub
     */
    public void setHCapClub(String HCapClub) {
        this.HCapClub = HCapClub;
    }

    /**
     * 
     * @return
     *     The UnionID
     */
    public String getUnionID() {
        return UnionID;
    }

    /**
     * 
     * @param UnionID
     *     The UnionID
     */
    public void setUnionID(String UnionID) {
        this.UnionID = UnionID;
    }

    /**
     * 
     * @return
     *     The IsGolfer
     */
    public Boolean getIsGolfer() {
        return IsGolfer;
    }

    /**
     * 
     * @param IsGolfer
     *     The IsGolfer
     */
    public void setIsGolfer(Boolean IsGolfer) {
        this.IsGolfer = IsGolfer;
    }

    /**
     * 
     * @return
     *     The IsLoggedInUser
     */
    public Boolean getIsLoggedInUser() {
        return IsLoggedInUser;
    }

    /**
     * 
     * @param IsLoggedInUser
     *     The IsLoggedInUser
     */
    public void setIsLoggedInUser(Boolean IsLoggedInUser) {
        this.IsLoggedInUser = IsLoggedInUser;
    }

    /**
     * 
     * @return
     *     The IsUserFollowing
     */
    public Boolean getIsUserFollowing() {
        return IsUserFollowing;
    }

    /**
     * 
     * @param IsUserFollowing
     *     The IsUserFollowing
     */
    public void setIsUserFollowing(Boolean IsUserFollowing) {
        this.IsUserFollowing = IsUserFollowing;
    }

    /**
     * 
     * @return
     *     The IsUserNominated
     */
    public Boolean getIsUserNominated() {
        return IsUserNominated;
    }

    /**
     * 
     * @param IsUserNominated
     *     The IsUserNominated
     */
    public void setIsUserNominated(Boolean IsUserNominated) {
        this.IsUserNominated = IsUserNominated;
    }

    /**
     * 
     * @return
     *     The CanUserSeeStats
     */
    public Boolean getCanUserSeeStats() {
        return CanUserSeeStats;
    }

    /**
     * 
     * @param CanUserSeeStats
     *     The CanUserSeeStats
     */
    public void setCanUserSeeStats(Boolean CanUserSeeStats) {
        this.CanUserSeeStats = CanUserSeeStats;
    }

    /**
     * 
     * @return
     *     The LinksFromLoggedOnUser
     */
    public Integer getLinksFromLoggedOnUser() {
        return LinksFromLoggedOnUser;
    }

    /**
     * 
     * @param LinksFromLoggedOnUser
     *     The LinksFromLoggedOnUser
     */
    public void setLinksFromLoggedOnUser(Integer LinksFromLoggedOnUser) {
        this.LinksFromLoggedOnUser = LinksFromLoggedOnUser;
    }

    /**
     * 
     * @return
     *     The LinksToLoggedOnUser
     */
    public Integer getLinksToLoggedOnUser() {
        return LinksToLoggedOnUser;
    }

    /**
     * 
     * @param LinksToLoggedOnUser
     *     The LinksToLoggedOnUser
     */
    public void setLinksToLoggedOnUser(Integer LinksToLoggedOnUser) {
        this.LinksToLoggedOnUser = LinksToLoggedOnUser;
    }

    /**
     * 
     * @return
     *     The HCapListstr
     */
    public Object getHCapListstr() {
        return HCapListstr;
    }

    /**
     * 
     * @param HCapListstr
     *     The HCapListstr
     */
    public void setHCapListstr(Object HCapListstr) {
        this.HCapListstr = HCapListstr;
    }

    /**
     * 
     * @return
     *     The LastJoiningDate
     */
    public String getLastJoiningDate() {
        return LastJoiningDate;
    }

    /**
     * 
     * @param LastJoiningDate
     *     The LastJoiningDate
     */
    public void setLastJoiningDate(String LastJoiningDate) {
        this.LastJoiningDate = LastJoiningDate;
    }

    /**
     * 
     * @return
     *     The hCapList
     */
    public List<HCapList> getHCapList() {
        return hCapList;
    }

    /**
     * 
     * @param hCapList
     *     The hCapList
     */
    public void setHCapList(List<HCapList> hCapList) {
        this.hCapList = hCapList;
    }

    /**
     * 
     * @return
     *     The IsVisitor
     */
    public Boolean getIsVisitor() {
        return IsVisitor;
    }

    /**
     * 
     * @param IsVisitor
     *     The IsVisitor
     */
    public void setIsVisitor(Boolean IsVisitor) {
        this.IsVisitor = IsVisitor;
    }

    /**
     * 
     * @return
     *     The MemberIDStr
     */
    public String getMemberIDStr() {
        return MemberIDStr;
    }

    /**
     * 
     * @param MemberIDStr
     *     The MemberIDStr
     */
    public void setMemberIDStr(String MemberIDStr) {
        this.MemberIDStr = MemberIDStr;
    }

    /**
     * 
     * @return
     *     The VisibleLinks
     */
    public Integer getVisibleLinks() {
        return VisibleLinks;
    }

    /**
     * 
     * @param VisibleLinks
     *     The VisibleLinks
     */
    public void setVisibleLinks(Integer VisibleLinks) {
        this.VisibleLinks = VisibleLinks;
    }

    /**
     * 
     * @return
     *     The PrivacyContext
     */
    public Integer getPrivacyContext() {
        return PrivacyContext;
    }

    /**
     * 
     * @param PrivacyContext
     *     The PrivacyContext
     */
    public void setPrivacyContext(Integer PrivacyContext) {
        this.PrivacyContext = PrivacyContext;
    }

    /**
     * 
     * @return
     *     The Name
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @param Name
     *     The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * 
     * @return
     *     The FormalName
     */
    public String getFormalName() {
        return FormalName;
    }

    /**
     * 
     * @param FormalName
     *     The FormalName
     */
    public void setFormalName(String FormalName) {
        this.FormalName = FormalName;
    }

    /**
     * 
     * @return
     *     The LabelName
     */
    public String getLabelName() {
        return LabelName;
    }

    /**
     * 
     * @param LabelName
     *     The LabelName
     */
    public void setLabelName(String LabelName) {
        this.LabelName = LabelName;
    }

    /**
     * 
     * @return
     *     The DearName
     */
    public String getDearName() {
        return DearName;
    }

    /**
     * 
     * @param DearName
     *     The DearName
     */
    public void setDearName(String DearName) {
        this.DearName = DearName;
    }

    /**
     * 
     * @return
     *     The KnownAs
     */
    public String getKnownAs() {
        return KnownAs;
    }

    /**
     * 
     * @param KnownAs
     *     The KnownAs
     */
    public void setKnownAs(String KnownAs) {
        this.KnownAs = KnownAs;
    }

    /**
     * 
     * @return
     *     The VisibleTelNoHome
     */
    public String getVisibleTelNoHome() {
        return VisibleTelNoHome;
    }

    /**
     * 
     * @param VisibleTelNoHome
     *     The VisibleTelNoHome
     */
    public void setVisibleTelNoHome(String VisibleTelNoHome) {
        this.VisibleTelNoHome = VisibleTelNoHome;
    }

    /**
     * 
     * @return
     *     The VisibleTelNoWork
     */
    public String getVisibleTelNoWork() {
        return VisibleTelNoWork;
    }

    /**
     * 
     * @param VisibleTelNoWork
     *     The VisibleTelNoWork
     */
    public void setVisibleTelNoWork(String VisibleTelNoWork) {
        this.VisibleTelNoWork = VisibleTelNoWork;
    }

    /**
     * 
     * @return
     *     The VisibleTelNoMob
     */
    public String getVisibleTelNoMob() {
        return VisibleTelNoMob;
    }

    /**
     * 
     * @param VisibleTelNoMob
     *     The VisibleTelNoMob
     */
    public void setVisibleTelNoMob(String VisibleTelNoMob) {
        this.VisibleTelNoMob = VisibleTelNoMob;
    }

    /**
     * 
     * @return
     *     The VisibleEmail
     */
    public String getVisibleEmail() {
        return VisibleEmail;
    }

    /**
     * 
     * @param VisibleEmail
     *     The VisibleEmail
     */
    public void setVisibleEmail(String VisibleEmail) {
        this.VisibleEmail = VisibleEmail;
    }

    /**
     * 
     * @return
     *     The VisibleAddressAsLine
     */
    public String getVisibleAddressAsLine() {
        return VisibleAddressAsLine;
    }

    /**
     * 
     * @param VisibleAddressAsLine
     *     The VisibleAddressAsLine
     */
    public void setVisibleAddressAsLine(String VisibleAddressAsLine) {
        this.VisibleAddressAsLine = VisibleAddressAsLine;
    }

}
