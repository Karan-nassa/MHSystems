
package com.mh.systems.woolstonmanor.web.models.teetimebooking.getdaydata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bookable {

    @SerializedName("Slot")
    @Expose
    private Slot Slot;
    @SerializedName("Product")
    @Expose
    private Product Product;
    @SerializedName("IncludesBuggy")
    @Expose
    private Boolean IncludesBuggy;
    @SerializedName("MemberCanAfford")
    @Expose
    private Boolean MemberCanAfford;
    @SerializedName("TotalPrice")
    @Expose
    private Integer TotalPrice;

    public Slot getSlot() {
        return Slot;
    }

    public void setSlot(Slot Slot) {
        this.Slot = Slot;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product Product) {
        this.Product = Product;
    }

    public Boolean getIncludesBuggy() {
        return IncludesBuggy;
    }

    public void setIncludesBuggy(Boolean IncludesBuggy) {
        this.IncludesBuggy = IncludesBuggy;
    }

    public Boolean getMemberCanAfford() {
        return MemberCanAfford;
    }

    public void setMemberCanAfford(Boolean MemberCanAfford) {
        this.MemberCanAfford = MemberCanAfford;
    }

    public Integer getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Integer TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

}
