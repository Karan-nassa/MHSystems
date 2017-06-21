
package com.mh.systems.corrstown.web.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AJsonParamsCourse {

    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("datefrom")
    @Expose
    private String datefrom;
    @SerializedName("dateto")
    @Expose
    private String dateto;
    @SerializedName("callid")
    @Expose
    private String callid;
    @SerializedName("pageNo")
    @Expose
    private String pageNo;
    @SerializedName("pageSize")
    @Expose
    private String pageSize;
    @SerializedName("courseKey")
    @Expose
    private String courseKey;

    /**
     *
     * @return
     *     The courseKey
     */
    public String getCourseKey() {
        return courseKey;
    }

    /**
     *
     * @param courseKey
     *     The courseKey
     */
    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }

    /**
     * 
     * @return
     *     The version
     */
    public int getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * 
     * @return
     *     The datefrom
     */
    public String getDatefrom() {
        return datefrom;
    }

    /**
     * 
     * @param datefrom
     *     The datefrom
     */
    public void setDatefrom(String datefrom) {
        this.datefrom = datefrom;
    }

    /**
     * 
     * @return
     *     The dateto
     */
    public String getDateto() {
        return dateto;
    }

    /**
     * 
     * @param dateto
     *     The dateto
     */
    public void setDateto(String dateto) {
        this.dateto = dateto;
    }

    /**
     * 
     * @return
     *     The callid
     */
    public String getCallid() {
        return callid;
    }

    /**
     * 
     * @param callid
     *     The callid
     */
    public void setCallid(String callid) {
        this.callid = callid;
    }

    /**
     * 
     * @return
     *     The pageNo
     */
    public String getPageNo() {
        return pageNo;
    }

    /**
     * 
     * @param pageNo
     *     The pageNo
     */
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 
     * @return
     *     The pageSize
     */
    public String getPageSize() {
        return pageSize;
    }

    /**
     * 
     * @param pageSize
     *     The pageSize
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

}
