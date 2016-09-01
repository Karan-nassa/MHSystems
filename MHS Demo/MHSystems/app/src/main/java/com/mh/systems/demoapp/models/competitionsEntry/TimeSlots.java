package com.mh.systems.demoapp.models.competitionsEntry;

import java.util.ArrayList;

/**
 * Create Model class for create {@link ArrayList} of
 * object type.
 */
public class TimeSlots {

    String strTimeOfEvent;
    boolean isSelected;

    public TimeSlots(String strTimeOfEvent, boolean isSelected) {
        this.strTimeOfEvent = strTimeOfEvent;
        this.isSelected = isSelected;
    }

    public String getStrTimeOfEvent() {
        return strTimeOfEvent;
    }

    public void setStrTimeOfEvent(String strTimeOfEvent) {
        this.strTimeOfEvent = strTimeOfEvent;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
