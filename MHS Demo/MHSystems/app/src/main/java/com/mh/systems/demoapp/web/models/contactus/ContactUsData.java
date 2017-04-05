
package com.mh.systems.demoapp.web.models.contactus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactUsData {

    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Telephone")
    @Expose
    private String Telephone;
    @SerializedName("FaxNo")
    @Expose
    private String FaxNo;
    @SerializedName("Email")
    @Expose
    private String Email;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getFaxNo() {
        return FaxNo;
    }

    public void setFaxNo(String FaxNo) {
        this.FaxNo = FaxNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

}
