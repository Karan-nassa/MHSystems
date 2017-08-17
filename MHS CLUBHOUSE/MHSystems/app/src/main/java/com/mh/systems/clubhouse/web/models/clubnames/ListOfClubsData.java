package com.mh.systems.clubhouse.web.models.clubnames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 09-08-2017.
 */

public class ListOfClubsData {

    @SerializedName("Id")
    @Expose
    private Integer Id;
    @SerializedName("ClubName")
    @Expose
    private String ClubName;
    @SerializedName("ClubId")
    @Expose
    private Integer ClubId;
    @SerializedName("IsActive")
    @Expose
    private Boolean IsActive;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getClubName() {
        return ClubName;
    }

    public void setClubName(String ClubName) {
        this.ClubName = ClubName;
    }

    public Integer getClubId() {
        return ClubId;
    }

    public void setClubId(Integer ClubId) {
        this.ClubId = ClubId;
    }

    public Boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }
}
