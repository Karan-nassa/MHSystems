package com.mh.systems.hartsbourne.web.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan@ucreate.co.in to load
 * Course diary events items on 04-03-2016.
 */
public class CourseDiaryItemsCopy {

    String Message;
    String Result;
    List<CourseDiaryDataCopy> Data = new ArrayList<>();


    /**
     * @return The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * @param Message The Message
     */
    public void setMessage(String message) {
        Message = message;
    }

    /**
     * @return The Result
     */
    public String getResult() {
        return Result;
    }

    /**
     * @param Result The Result
     */
    public void setResult(String result) {
        Result = result;
    }

    /**
     * @return The MyAccountData
     */
    public List<CourseDiaryDataCopy> getData() {
        return Data;
    }

    /**
     * @param Data The MyAccountData
     */
    public void setData(List<CourseDiaryDataCopy> data) {
        Data = data;
    }
}
