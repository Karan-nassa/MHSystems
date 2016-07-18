
package com.mh.systems.hartsbourne.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HCapRecords {

    @SerializedName("ClubID")
    @Expose
    private Integer ClubID;
    @SerializedName("MemberID")
    @Expose
    private Integer MemberID;
    @SerializedName("ShowScoreStats")
    @Expose
    private Boolean ShowScoreStats;
    @SerializedName("IsHomeComp")
    @Expose
    private Boolean IsHomeComp;
    @SerializedName("IsAwayComp")
    @Expose
    private Boolean IsAwayComp;
    @SerializedName("DateRecordedStr")
    @Expose
    private String DateRecordedStr;
    @SerializedName("DatePlayedStr")
    @Expose
    private String DatePlayedStr;
    @SerializedName("RoundNoStr")
    @Expose
    private String RoundNoStr;
    @SerializedName("CompetitionOrReason")
    @Expose
    private String CompetitionOrReason;
    @SerializedName("VenueOrAuthoriser")
    @Expose
    private String VenueOrAuthoriser;
    @SerializedName("RoundSSSStr")
    @Expose
    private String RoundSSSStr;
    @SerializedName("RoundCSSStr")
    @Expose
    private String RoundCSSStr;
    @SerializedName("ConguCode")
    @Expose
    private String ConguCode;
    @SerializedName("GrossStr")
    @Expose
    private String GrossStr;
    @SerializedName("RoundPlayHCapStr")
    @Expose
    private String RoundPlayHCapStr;
    @SerializedName("NettScoreStr")
    @Expose
    private String NettScoreStr;
    @SerializedName("NdbAdjStr")
    @Expose
    private String NdbAdjStr;
    @SerializedName("AdjGrossScoreStr")
    @Expose
    private String AdjGrossScoreStr;
    @SerializedName("PointsStr")
    @Expose
    private String PointsStr;
    @SerializedName("RoundParStr")
    @Expose
    private String RoundParStr;
    @SerializedName("GrossDiffStr")
    @Expose
    private String GrossDiffStr;
    @SerializedName("NettDiffStr")
    @Expose
    private String NettDiffStr;
    @SerializedName("HCapAdjStr")
    @Expose
    private String HCapAdjStr;
    @SerializedName("NewExactHCapOnlyStr")
    @Expose
    private String NewExactHCapOnlyStr;
    @SerializedName("NewPlayHCapOnlyStr")
    @Expose
    private String NewPlayHCapOnlyStr;
    @SerializedName("HCapStatusStr")
    @Expose
    private String HCapStatusStr;
    @SerializedName("CompFormatStr")
    @Expose
    private String CompFormatStr;
    @SerializedName("InternalEntryType")
    @Expose
    private Integer InternalEntryType;
    @SerializedName("HCapRecRowHandle")
    @Expose
    private Integer HCapRecRowHandle;
    @SerializedName("ColumnCells")
    @Expose
    private List<Object> ColumnCells = new ArrayList<Object>();
    @SerializedName("Style")
    @Expose
    private Integer Style;
    @SerializedName("StyleFlags")
    @Expose
    private Integer StyleFlags;
    @SerializedName("IsSubHeader")
    @Expose
    private Boolean IsSubHeader;
    @SerializedName("SubHeaderText")
    @Expose
    private Object SubHeaderText;
    @SerializedName("SubHeaderAlign")
    @Expose
    private Integer SubHeaderAlign;
    @SerializedName("SubHeaderStyle")
    @Expose
    private Integer SubHeaderStyle;
    @SerializedName("IsOddRow")
    @Expose
    private Boolean IsOddRow;
    @SerializedName("IsEvenRow")
    @Expose
    private Boolean IsEvenRow;


    /**
     * @return The ClubID
     */
    public Integer getClubID() {
        return ClubID;
    }

    /**
     * @param ClubID The ClubID
     */
    public void setClubID(Integer ClubID) {
        this.ClubID = ClubID;
    }

    /**
     * @return The MemberID
     */
    public Integer getMemberID() {
        return MemberID;
    }

    /**
     * @param MemberID The MemberID
     */
    public void setMemberID(Integer MemberID) {
        this.MemberID = MemberID;
    }

    /**
     * @return The ShowScoreStats
     */
    public Boolean getShowScoreStats() {
        return ShowScoreStats;
    }

    /**
     * @param ShowScoreStats The ShowScoreStats
     */
    public void setShowScoreStats(Boolean ShowScoreStats) {
        this.ShowScoreStats = ShowScoreStats;
    }

    /**
     * @return The IsHomeComp
     */
    public Boolean getIsHomeComp() {
        return IsHomeComp;
    }

    /**
     * @param IsHomeComp The IsHomeComp
     */
    public void setIsHomeComp(Boolean IsHomeComp) {
        this.IsHomeComp = IsHomeComp;
    }

    /**
     * @return The IsAwayComp
     */
    public Boolean getIsAwayComp() {
        return IsAwayComp;
    }

    /**
     * @param IsAwayComp The IsAwayComp
     */
    public void setIsAwayComp(Boolean IsAwayComp) {
        this.IsAwayComp = IsAwayComp;
    }

    /**
     * @return The DateRecordedStr
     */
    public String getDateRecordedStr() {
        return DateRecordedStr;
    }

    /**
     * @param DateRecordedStr The DateRecordedStr
     */
    public void setDateRecordedStr(String DateRecordedStr) {
        this.DateRecordedStr = DateRecordedStr;
    }

    /**
     * @return The DatePlayedStr
     */
    public String getDatePlayedStr() {
        return DatePlayedStr;
    }

    /**
     * @param DatePlayedStr The DatePlayedStr
     */
    public void setDatePlayedStr(String DatePlayedStr) {
        this.DatePlayedStr = DatePlayedStr;
    }

    /**
     * @return The RoundNoStr
     */
    public String getRoundNoStr() {
        return RoundNoStr;
    }

    /**
     * @param RoundNoStr The RoundNoStr
     */
    public void setRoundNoStr(String RoundNoStr) {
        this.RoundNoStr = RoundNoStr;
    }

    /**
     * @return The CompetitionOrReason
     */
    public String getCompetitionOrReason() {
        return CompetitionOrReason;
    }

    /**
     * @param CompetitionOrReason The CompetitionOrReason
     */
    public void setCompetitionOrReason(String CompetitionOrReason) {
        this.CompetitionOrReason = CompetitionOrReason;
    }

    /**
     * @return The VenueOrAuthoriser
     */
    public String getVenueOrAuthoriser() {
        return VenueOrAuthoriser;
    }

    /**
     * @param VenueOrAuthoriser The VenueOrAuthoriser
     */
    public void setVenueOrAuthoriser(String VenueOrAuthoriser) {
        this.VenueOrAuthoriser = VenueOrAuthoriser;
    }

    /**
     * @return The RoundSSSStr
     */
    public String getRoundSSSStr() {
        return RoundSSSStr;
    }

    /**
     * @param RoundSSSStr The RoundSSSStr
     */
    public void setRoundSSSStr(String RoundSSSStr) {
        this.RoundSSSStr = RoundSSSStr;
    }

    /**
     * @return The RoundCSSStr
     */
    public String getRoundCSSStr() {
        return RoundCSSStr;
    }

    /**
     * @param RoundCSSStr The RoundCSSStr
     */
    public void setRoundCSSStr(String RoundCSSStr) {
        this.RoundCSSStr = RoundCSSStr;
    }

    /**
     * @return The ConguCode
     */
    public String getConguCode() {
        return ConguCode;
    }

    /**
     * @param ConguCode The ConguCode
     */
    public void setConguCode(String ConguCode) {
        this.ConguCode = ConguCode;
    }

    /**
     * @return The GrossStr
     */
    public String getGrossStr() {
        return GrossStr;
    }

    /**
     * @param GrossStr The GrossStr
     */
    public void setGrossStr(String GrossStr) {
        this.GrossStr = GrossStr;
    }

    /**
     * @return The RoundPlayHCapStr
     */
    public String getRoundPlayHCapStr() {
        return RoundPlayHCapStr;
    }

    /**
     * @param RoundPlayHCapStr The RoundPlayHCapStr
     */
    public void setRoundPlayHCapStr(String RoundPlayHCapStr) {
        this.RoundPlayHCapStr = RoundPlayHCapStr;
    }

    /**
     * @return The NettScoreStr
     */
    public String getNettScoreStr() {
        return NettScoreStr;
    }

    /**
     * @param NettScoreStr The NettScoreStr
     */
    public void setNettScoreStr(String NettScoreStr) {
        this.NettScoreStr = NettScoreStr;
    }

    /**
     * @return The NdbAdjStr
     */
    public String getNdbAdjStr() {
        return NdbAdjStr;
    }

    /**
     * @param NdbAdjStr The NdbAdjStr
     */
    public void setNdbAdjStr(String NdbAdjStr) {
        this.NdbAdjStr = NdbAdjStr;
    }

    /**
     * @return The AdjGrossScoreStr
     */
    public String getAdjGrossScoreStr() {
        return AdjGrossScoreStr;
    }

    /**
     * @param AdjGrossScoreStr The AdjGrossScoreStr
     */
    public void setAdjGrossScoreStr(String AdjGrossScoreStr) {
        this.AdjGrossScoreStr = AdjGrossScoreStr;
    }

    /**
     * @return The PointsStr
     */
    public String getPointsStr() {
        return PointsStr;
    }

    /**
     * @param PointsStr The PointsStr
     */
    public void setPointsStr(String PointsStr) {
        this.PointsStr = PointsStr;
    }

    /**
     * @return The RoundParStr
     */
    public String getRoundParStr() {
        return RoundParStr;
    }

    /**
     * @param RoundParStr The RoundParStr
     */
    public void setRoundParStr(String RoundParStr) {
        this.RoundParStr = RoundParStr;
    }

    /**
     * @return The GrossDiffStr
     */
    public String getGrossDiffStr() {
        return GrossDiffStr;
    }

    /**
     * @param GrossDiffStr The GrossDiffStr
     */
    public void setGrossDiffStr(String GrossDiffStr) {
        this.GrossDiffStr = GrossDiffStr;
    }

    /**
     * @return The NettDiffStr
     */
    public String getNettDiffStr() {
        return NettDiffStr;
    }

    /**
     * @param NettDiffStr The NettDiffStr
     */
    public void setNettDiffStr(String NettDiffStr) {
        this.NettDiffStr = NettDiffStr;
    }

    /**
     * @return The HCapAdjStr
     */
    public String getHCapAdjStr() {
        return HCapAdjStr;
    }

    /**
     * @param HCapAdjStr The HCapAdjStr
     */
    public void setHCapAdjStr(String HCapAdjStr) {
        this.HCapAdjStr = HCapAdjStr;
    }

    /**
     * @return The NewExactHCapOnlyStr
     */
    public String getNewExactHCapOnlyStr() {
        return NewExactHCapOnlyStr;
    }

    /**
     * @param NewExactHCapOnlyStr The NewExactHCapOnlyStr
     */
    public void setNewExactHCapOnlyStr(String NewExactHCapOnlyStr) {
        this.NewExactHCapOnlyStr = NewExactHCapOnlyStr;
    }

    /**
     * @return The NewPlayHCapOnlyStr
     */
    public String getNewPlayHCapOnlyStr() {
        return NewPlayHCapOnlyStr;
    }

    /**
     * @param NewPlayHCapOnlyStr The NewPlayHCapOnlyStr
     */
    public void setNewPlayHCapOnlyStr(String NewPlayHCapOnlyStr) {
        this.NewPlayHCapOnlyStr = NewPlayHCapOnlyStr;
    }

    /**
     * @return The HCapStatusStr
     */
    public String getHCapStatusStr() {
        return HCapStatusStr;
    }

    /**
     * @param HCapStatusStr The HCapStatusStr
     */
    public void setHCapStatusStr(String HCapStatusStr) {
        this.HCapStatusStr = HCapStatusStr;
    }

    /**
     * @return The CompFormatStr
     */
    public String getCompFormatStr() {
        return CompFormatStr;
    }

    /**
     * @param CompFormatStr The CompFormatStr
     */
    public void setCompFormatStr(String CompFormatStr) {
        this.CompFormatStr = CompFormatStr;
    }

    /**
     * @return The InternalEntryType
     */
    public Integer getInternalEntryType() {
        return InternalEntryType;
    }

    /**
     * @param InternalEntryType The InternalEntryType
     */
    public void setInternalEntryType(Integer InternalEntryType) {
        this.InternalEntryType = InternalEntryType;
    }

    /**
     * @return The HCapRecRowHandle
     */
    public Integer getHCapRecRowHandle() {
        return HCapRecRowHandle;
    }

    /**
     * @param HCapRecRowHandle The HCapRecRowHandle
     */
    public void setHCapRecRowHandle(Integer HCapRecRowHandle) {
        this.HCapRecRowHandle = HCapRecRowHandle;
    }

    /**
     * @return The ColumnCells
     */
    public List<Object> getColumnCells() {
        return ColumnCells;
    }

    /**
     * @param ColumnCells The ColumnCells
     */
    public void setColumnCells(List<Object> ColumnCells) {
        this.ColumnCells = ColumnCells;
    }

    /**
     * @return The Style
     */
    public Integer getStyle() {
        return Style;
    }

    /**
     * @param Style The Style
     */
    public void setStyle(Integer Style) {
        this.Style = Style;
    }

    /**
     * @return The StyleFlags
     */
    public Integer getStyleFlags() {
        return StyleFlags;
    }

    /**
     * @param StyleFlags The StyleFlags
     */
    public void setStyleFlags(Integer StyleFlags) {
        this.StyleFlags = StyleFlags;
    }

    /**
     * @return The IsSubHeader
     */
    public Boolean getIsSubHeader() {
        return IsSubHeader;
    }

    /**
     * @param IsSubHeader The IsSubHeader
     */
    public void setIsSubHeader(Boolean IsSubHeader) {
        this.IsSubHeader = IsSubHeader;
    }

    /**
     * @return The SubHeaderText
     */
    public Object getSubHeaderText() {
        return SubHeaderText;
    }

    /**
     * @param SubHeaderText The SubHeaderText
     */
    public void setSubHeaderText(Object SubHeaderText) {
        this.SubHeaderText = SubHeaderText;
    }

    /**
     * @return The SubHeaderAlign
     */
    public Integer getSubHeaderAlign() {
        return SubHeaderAlign;
    }

    /**
     * @param SubHeaderAlign The SubHeaderAlign
     */
    public void setSubHeaderAlign(Integer SubHeaderAlign) {
        this.SubHeaderAlign = SubHeaderAlign;
    }

    /**
     * @return The SubHeaderStyle
     */
    public Integer getSubHeaderStyle() {
        return SubHeaderStyle;
    }

    /**
     * @param SubHeaderStyle The SubHeaderStyle
     */
    public void setSubHeaderStyle(Integer SubHeaderStyle) {
        this.SubHeaderStyle = SubHeaderStyle;
    }

    /**
     * @return The IsOddRow
     */
    public Boolean getIsOddRow() {
        return IsOddRow;
    }

    /**
     * @param IsOddRow The IsOddRow
     */
    public void setIsOddRow(Boolean IsOddRow) {
        this.IsOddRow = IsOddRow;
    }

    /**
     * @return The IsEvenRow
     */
    public Boolean getIsEvenRow() {
        return IsEvenRow;
    }

    /**
     * @param IsEvenRow The IsEvenRow
     */
    public void setIsEvenRow(Boolean IsEvenRow) {
        this.IsEvenRow = IsEvenRow;
    }

}
