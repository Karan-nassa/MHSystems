package com.ucreate.mhsystems.utils.API;

import com.google.gson.JsonObject;
import com.ucreate.mhsystems.utils.pojo.CourseDiaryAPI;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by karan@ucreate.co.in for
 * all web services on 08-03-2016.
 */
public interface WebServiceMethods {

    /**
     * Declaration of COURSE DIARY events
     * web service method.
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getCourseDiaryEvents(@Body CourseDiaryAPI jsonElements,  Callback<JsonObject> response);
}

