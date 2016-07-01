package com.mh.systems.sunningdale.util.API;

import com.google.gson.JsonObject;
import com.mh.systems.sunningdale.models.AddMemberAPI;
import com.mh.systems.sunningdale.models.CompetitionResultAPI;
import com.mh.systems.sunningdale.models.CompetitionJoinAPI;
import com.mh.systems.sunningdale.models.CompetitionUnjoinAPI;
import com.mh.systems.sunningdale.models.CompetitionsAPI;
import com.mh.systems.sunningdale.models.CourseDiaryAPI;
import com.mh.systems.sunningdale.models.DashboardAPI;
import com.mh.systems.sunningdale.models.Friends.RemoveFriendAPI;
import com.mh.systems.sunningdale.models.FriendsAPI;
import com.mh.systems.sunningdale.models.HandicapAPI;
import com.mh.systems.sunningdale.models.MembersAPI;
import com.mh.systems.sunningdale.models.MembersDetailAPI;
import com.mh.systems.sunningdale.models.MyAccountAPI;
import com.mh.systems.sunningdale.models.ResetPassword.ResetPasswordAPI;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by karan@mh.co.in for
 * all web services on 08-03-2016.
 */
public interface WebServiceMethods {

    /**
     * Declaration of DASHBOARD API.
     * <p/>
     * TYPE : POST
     * <p/>
     * USAGE :-
     */
    @POST("/webapi/api/ClubsApp")
    public void getDashboardData(@Body DashboardAPI jsonElements, Callback<JsonObject> response);

    /**
     * Declaration of COURSE DIARY events
     * web service method.
     * <p/>
     * TYPE : POST
     * <p/>
     * USAGE :-
     * # OLD COURSE
     * # NEW COURSE
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getCourseDiaryEvents(@Body CourseDiaryAPI jsonElements, Callback<JsonObject> response);

    /**
     * Declaration of COMPETITIONS events
     * web service method.
     * <p/>
     * TYPE : POST
     * <p/>
     * USAGE :-
     * # MY EVENTS
     * # COMPLETED
     * # CURRENT
     * # FUTURE
     */
    @POST("/webapi/api/ClubsApp")
    public void getCompetitionsEvents(@Body CompetitionsAPI jsonElements, Callback<JsonObject> response);


    @GET("/webapi/api/ClubsApp/RpcRequest")
    void joinCompetitionEventGet(@Query("aClientId") String aClientId, @Query("aCommand") String aCommand,
                                 @Query("aJsonParams") String aJsonParams,
                                 @Query("aModuleId") String aModuleId,
                                 @Query("aUserClass") String aUserClass,
                                 Callback<JsonObject> cb);

    /**
     * Declaration of JOIN COMPETITION event web service method.
     * <p/>
     * TYPE : POST
     * <p/>
     *
     * @param competitionJoinAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void joinCompetitionEvent(@Body CompetitionJoinAPI competitionJoinAPI, Callback<JsonObject> response);

    /**
     * Declaration of RESULT OF COMPETITION event web service method.
     * <p/>
     * TYPE : POST
     * <p/>
     *
     * @param competitionResultAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp")
    public void resultOfCompetitionEvent(@Body CompetitionResultAPI competitionResultAPI, Callback<JsonObject> response);

    /**
     * Declaration of Members Detail screen web service method.
     * <p/>
     * TYPE : POST
     *
     * @param handicapAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getHandicap(@Body HandicapAPI handicapAPI, Callback<JsonObject> response);

    /**
     * Declaration of My Account
     * web service method.
     * <p/>
     * TYPE : POST
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getMyAccount(@Body MyAccountAPI jsonElements, Callback<JsonObject> response);

    /**
     * Declaration of Members web service method.
     * <p/>
     * TYPE : POST
     *
     * @param membersAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getMembers(@Body MembersAPI membersAPI, Callback<JsonObject> response);

    /**
     * Declaration of Members Detail screen web service method.
     * <p/>
     * TYPE : POST
     *
     * @param membersDetailAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getMembersDetail(@Body MembersDetailAPI membersDetailAPI, Callback<JsonObject> response);

    /**
     * Declaration of ADD MEMBER functionality web service method.
     * <p/>
     * TYPE : POST
     *
     * @param addMemberAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getAddMember(@Body AddMemberAPI addMemberAPI, Callback<JsonObject> response);

    /**
     * Declaration of FRIENDS web service method.
     * <p/>
     * TYPE : POST
     *
     * @param friendsAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void getFriends(@Body FriendsAPI friendsAPI, Callback<JsonObject> response);

    /**
     * Declaration of REMOVE FRIEND web service method.
     * <p/>
     * TYPE : POST
     *
     * @param removeFriendAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp/RpcRequest")
    public void removeFriend(@Body RemoveFriendAPI removeFriendAPI, Callback<JsonObject> response);

    /**
     * Declaration of Reset Password web service method.
     * <p/>
     * TYPE : POST
     *
     * @param resetPasswordAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp")
    public void resetPassword(@Body ResetPasswordAPI resetPasswordAPI, Callback<JsonObject> response);

    /**
     * Declaration of Unjoin Competition web service method.
     * <p/>
     * TYPE : POST
     *
     * @param resetPasswordAPI
     * @param response
     */
    @POST("/webapi/api/ClubsApp")
    public void unjoinCompetition(@Body CompetitionUnjoinAPI competitionUnjoinAPI, Callback<JsonObject> response);
}

