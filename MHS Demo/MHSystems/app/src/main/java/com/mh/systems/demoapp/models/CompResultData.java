
package com.mh.systems.demoapp.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompResultData {

    @SerializedName("EventId")
    @Expose
    private Integer EventId;
    @SerializedName("Results")
    @Expose
    private List<Result> Results = new ArrayList<Result>();

    /**
     * 
     * @return
     *     The EventId
     */
    public Integer getEventId() {
        return EventId;
    }

    /**
     * 
     * @param EventId
     *     The EventId
     */
    public void setEventId(Integer EventId) {
        this.EventId = EventId;
    }

    /**
     * 
     * @return
     *     The Results
     */
    public List<Result> getResults() {
        return Results;
    }

    /**
     * 
     * @param Results
     *     The Results
     */
    public void setResults(List<Result> Results) {
        this.Results = Results;
    }

}
