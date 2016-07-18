
package com.mh.systems.porterspark.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BillParts {

    @SerializedName("ForMemberTitle")
    @Expose
    private String ForMemberTitle;
    @SerializedName("ForMemberGroup")
    @Expose
    private String ForMemberGroup;
    @SerializedName("ForMemberTax")
    @Expose
    private Integer ForMemberTax;
    @SerializedName("ForMemberValueX")
    @Expose
    private Integer ForMemberValueX;
    @SerializedName("ForMemberValueI")
    @Expose
    private Integer ForMemberValueI;
    @SerializedName("Lines")
    @Expose
    private List<Line> Lines = new ArrayList<Line>();

    /**
     * 
     * @return
     *     The ForMemberTitle
     */
    public String getForMemberTitle() {
        return ForMemberTitle;
    }

    /**
     * 
     * @param ForMemberTitle
     *     The ForMemberTitle
     */
    public void setForMemberTitle(String ForMemberTitle) {
        this.ForMemberTitle = ForMemberTitle;
    }

    /**
     * 
     * @return
     *     The ForMemberGroup
     */
    public String getForMemberGroup() {
        return ForMemberGroup;
    }

    /**
     * 
     * @param ForMemberGroup
     *     The ForMemberGroup
     */
    public void setForMemberGroup(String ForMemberGroup) {
        this.ForMemberGroup = ForMemberGroup;
    }

    /**
     * 
     * @return
     *     The ForMemberTax
     */
    public Integer getForMemberTax() {
        return ForMemberTax;
    }

    /**
     * 
     * @param ForMemberTax
     *     The ForMemberTax
     */
    public void setForMemberTax(Integer ForMemberTax) {
        this.ForMemberTax = ForMemberTax;
    }

    /**
     * 
     * @return
     *     The ForMemberValueX
     */
    public Integer getForMemberValueX() {
        return ForMemberValueX;
    }

    /**
     * 
     * @param ForMemberValueX
     *     The ForMemberValueX
     */
    public void setForMemberValueX(Integer ForMemberValueX) {
        this.ForMemberValueX = ForMemberValueX;
    }

    /**
     * 
     * @return
     *     The ForMemberValueI
     */
    public Integer getForMemberValueI() {
        return ForMemberValueI;
    }

    /**
     * 
     * @param ForMemberValueI
     *     The ForMemberValueI
     */
    public void setForMemberValueI(Integer ForMemberValueI) {
        this.ForMemberValueI = ForMemberValueI;
    }

    /**
     * 
     * @return
     *     The Lines
     */
    public List<Line> getLines() {
        return Lines;
    }

    /**
     * 
     * @param Lines
     *     The Lines
     */
    public void setLines(List<Line> Lines) {
        this.Lines = Lines;
    }

}
