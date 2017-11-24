package com.mh.systems.guildford.web.models;

/**
 * Created by admin on 14-11-2017.
 */

public class CompFilterOptions {
    String strTitle;
    boolean isSelected;

    public CompFilterOptions(String strTitle, boolean isSelected) {
        this.strTitle = strTitle;
        this.isSelected = isSelected;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
