
package com.ucreate.mhsystems.util.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MemBalance {

    @SerializedName("NameStr")
    @Expose
    private String NameStr;
    @SerializedName("CrnSymbolStr")
    @Expose
    private String CrnSymbolStr;
    @SerializedName("ValueStr")
    @Expose
    private String ValueStr;

    /**
     * 
     * @return
     *     The NameStr
     */
    public String getNameStr() {
        return NameStr;
    }

    /**
     * 
     * @param NameStr
     *     The NameStr
     */
    public void setNameStr(String NameStr) {
        this.NameStr = NameStr;
    }

    /**
     * 
     * @return
     *     The CrnSymbolStr
     */
    public String getCrnSymbolStr() {
        return CrnSymbolStr;
    }

    /**
     * 
     * @param CrnSymbolStr
     *     The CrnSymbolStr
     */
    public void setCrnSymbolStr(String CrnSymbolStr) {
        this.CrnSymbolStr = CrnSymbolStr;
    }

    /**
     * 
     * @return
     *     The ValueStr
     */
    public String getValueStr() {
        return ValueStr;
    }

    /**
     * 
     * @param ValueStr
     *     The ValueStr
     */
    public void setValueStr(String ValueStr) {
        this.ValueStr = ValueStr;
    }

}
