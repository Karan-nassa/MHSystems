
package com.mh.systems.littlehampton.web.models.teetimebooking.getdaydata;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slot {

    @SerializedName("SlotStart")
    @Expose
    private String SlotStart;
    @SerializedName("SlotEnd")
    @Expose
    private String SlotEnd;
    @SerializedName("SlotStartDateTime")
    @Expose
    private String SlotStartDateTime;
    @SerializedName("PackageName")
    @Expose
    private String PackageName;
    @SerializedName("Products")
    @Expose
    private List<Product> Products = null;
    @SerializedName("MaxBuggies")
    @Expose
    private int MaxBuggies;

    public String getSlotStart() {
        return SlotStart;
    }

    public void setSlotStart(String SlotStart) {
        this.SlotStart = SlotStart;
    }

    public String getSlotEnd() {
        return SlotEnd;
    }

    public void setSlotEnd(String SlotEnd) {
        this.SlotEnd = SlotEnd;
    }

    public String getSlotStartDateTime() {
        return SlotStartDateTime;
    }

    public void setSlotStartDateTime(String slotStartDateTime) {
        SlotStartDateTime = slotStartDateTime;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String PackageName) {
        this.PackageName = PackageName;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> Products) {
        this.Products = Products;
    }

    public int getMaxBuggies() {
        return MaxBuggies;
    }

    public void setMaxBuggies(int maxBuggies) {
        MaxBuggies = maxBuggies;
    }
}
