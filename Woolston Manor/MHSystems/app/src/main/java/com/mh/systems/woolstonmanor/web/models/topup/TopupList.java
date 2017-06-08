
package com.mh.systems.woolstonmanor.web.models.topup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopupList {

    @SerializedName("Text")
    @Expose
    private String Text;
    @SerializedName("Value")
    @Expose
    private String Value;

    public TopupList(String text, String value) {
        Text = text;
        Value = value;
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

}
