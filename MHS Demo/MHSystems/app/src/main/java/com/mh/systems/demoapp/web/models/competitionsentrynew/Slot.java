
package com.mh.systems.demoapp.web.models.competitionsentrynew;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slot {

    @SerializedName("Status")
    @Expose
    private Integer Status;
    @SerializedName("TeeOffTime")
    @Expose
    private String TeeOffTime;
    @SerializedName("Teams")
    @Expose
    private List<Team> Teams = null;

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        this.Status = Status;
    }

    public String getTeeOffTime() {
        return TeeOffTime;
    }

    public void setTeeOffTime(String TeeOffTime) {
        this.TeeOffTime = TeeOffTime;
    }

    public List<Team> getTeams() {
        return Teams;
    }

    public void setTeams(List<Team> Teams) {
        this.Teams = Teams;
    }

}
