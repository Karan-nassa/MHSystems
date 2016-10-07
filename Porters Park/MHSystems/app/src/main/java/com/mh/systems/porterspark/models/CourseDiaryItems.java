package com.mh.systems.porterspark.models;

import java.util.ArrayList;

/**
 * Created by karan@ucreate.co.in to load
 * Course diary events items on 04-03-2016.
 */
public class CourseDiaryItems {

    String Message;
    String Result;
    ArrayList<CourseDiaryData> Data = new ArrayList<>();


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
    public ArrayList<CourseDiaryData> getData() {
        return Data;
    }

    /**
     * @param Data The MyAccountData
     */
    public void setData(ArrayList<CourseDiaryData> data) {
        Data = data;
    }
}
