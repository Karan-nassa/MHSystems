package com.ucreate.mhsystems.util.API;

import com.google.gson.JsonObject;
import com.ucreate.mhsystems.util.pojo.CompetitionsAPI;
import com.ucreate.mhsystems.util.pojo.CourseDiaryAPI;
import com.ucreate.mhsystems.util.pojo.MembersAPI;
import com.ucreate.mhsystems.util.pojo.MembersDetailAPI;
import com.ucreate.mhsystems.util.pojo.MyAccountAPI;

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
     * <p>
     * TYPE : POST
     * <p>
     * USAGE :-
     * # OLD COURSE
     * # NEW COURSE
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getCourseDiaryEvents(@Body CourseDiaryAPI jsonElements, Callback<JsonObject> response);

    /**
     * Declaration of COMPETITIONS events
     * web service method.
     * <p>
     * TYPE : POST
     * <p>
     * USAGE :-
     * # MY EVENTS
     * # COMPLETED
     * # CURRENT
     * # FUTURE
     */
    @POST("/webapi/api/ClubsApp")
    public void getCompetitionsEvents(@Body CompetitionsAPI jsonElements, Callback<JsonObject> response);

    /**
     * Declaration of My Account
     * web service method.
     * <p>
     * TYPE : POST
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getMyAccount(@Body MyAccountAPI jsonElements, Callback<JsonObject> response);

    /**
     * Declaration of Members web service method.
     * <p>
     * TYPE : POST
     *
     * @param membersAPI
     * @param response
     *
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getMembers(@Body MembersAPI membersAPI, Callback<JsonObject> response);

    /**
     * Declaration of Members Detail screen web service method.
     * <p>
     * TYPE : POST
     *
     * @param membersDetailAPI
     * @param response
     *
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getMembersDetail(@Body MembersDetailAPI membersDetailAPI, Callback<JsonObject> response);
}

