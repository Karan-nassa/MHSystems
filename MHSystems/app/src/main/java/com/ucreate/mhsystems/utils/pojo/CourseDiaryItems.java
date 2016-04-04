package com.ucreate.mhsystems.utils.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan@ucreate.co.in to load
 * Course diary events items on 04-03-2016.
 */
public class CourseDiaryItems {

    String Message;
    String Result;
    List<CourseDiaryData> Data = new ArrayList<>();


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
    public List<CourseDiaryData> getData() {
        return Data;
    }

    /**
     * @param Data The MyAccountData
     */
    public void setData(List<CourseDiaryData> data) {
        Data = data;
    }
}
