
package com.mh.systems.dunstabledowns.web.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PrivateAddress {

    @SerializedName("Line1")
    @Expose
    private String Line1;
    @SerializedName("Line2")
    @Expose
    private String Line2;
    @SerializedName("Line3")
    @Expose
    private String Line3;
    @SerializedName("Town")
    @Expose
    private String Town;
    @SerializedName("County")
    @Expose
    private String County;
    @SerializedName("PostCode")
    @Expose
    private String PostCode;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("LineCount")
    @Expose
    private Integer LineCount;
    @SerializedName("AsBlock")
    @Expose
    private List<Object> AsBlock = new ArrayList<Object>();
    @SerializedName("AsText")
    @Expose
    private String AsText;
    @SerializedName("AsLine")
    @Expose
    private String AsLine;

    /**
     * @return The Line1
     */
    public String getLine1() {
        return Line1;
    }

    /**
     * @param Line1 The Line1
     */
    public void setLine1(String Line1) {
        this.Line1 = Line1;
    }

    /**
     * @return The Line2
     */
    public String getLine2() {
        return Line2;
    }

    /**
     * @param Line2 The Line2
     */
    public void setLine2(String Line2) {
        this.Line2 = Line2;
    }

    /**
     * @return The Line3
     */
    public String getLine3() {
        return Line3;
    }

    /**
     * @param Line3 The Line3
     */
    public void setLine3(String Line3) {
        this.Line3 = Line3;
    }

    /**
     * @return The Town
     */
    public String getTown() {
        return Town;
    }

    /**
     * @param Town The Town
     */
    public void setTown(String Town) {
        this.Town = Town;
    }

    /**
     * @return The County
     */
    public String getCounty() {
        return County;
    }

    /**
     * @param County The County
     */
    public void setCounty(String County) {
        this.County = County;
    }

    /**
     * @return The PostCode
     */
    public String getPostCode() {
        return PostCode;
    }

    /**
     * @param PostCode The PostCode
     */
    public void setPostCode(String PostCode) {
        this.PostCode = PostCode;
    }

    /**
     * @return The Country
     */
    public String getCountry() {
        return Country;
    }

    /**
     * @param Country The Country
     */
    public void setCountry(String Country) {
        this.Country = Country;
    }

    /**
     * @return The LineCount
     */
    public Integer getLineCount() {
        return LineCount;
    }

    /**
     * @param LineCount The LineCount
     */
    public void setLineCount(Integer LineCount) {
        this.LineCount = LineCount;
    }

    /**
     * @return The AsBlock
     */
    public List<Object> getAsBlock() {
        return AsBlock;
    }

    /**
     * @param AsBlock The AsBlock
     */
    public void setAsBlock(List<Object> AsBlock) {
        this.AsBlock = AsBlock;
    }

    /**
     * @return The AsText
     */
    public String getAsText() {
        return AsText;
    }

    /**
     * @param AsText The AsText
     */
    public void setAsText(String AsText) {
        this.AsText = AsText;
    }

    /**
     * @return The AsLine
     */
    public String getAsLine() {
        return AsLine;
    }

    /**
     * @param AsLine The AsLine
     */
    public void setAsLine(String AsLine) {
        this.AsLine = AsLine;
    }

}
