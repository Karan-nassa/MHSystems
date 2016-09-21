package com.mh.systems.demoapp.util.API;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.models.AddMemberAPI;
import com.mh.systems.demoapp.models.ClubNews.ClubNewsAPI;
import com.mh.systems.demoapp.models.ClubNews.ClubNewsDetailAPI;
import com.mh.systems.demoapp.models.CompetitionResultAPI;
import com.mh.systems.demoapp.models.CompetitionJoinAPI;
import com.mh.systems.demoapp.models.CompetitionUnjoinAPI;
import com.mh.systems.demoapp.models.CompetitionsAPI;
import com.mh.systems.demoapp.models.CourseDiaryAPI;
import com.mh.systems.demoapp.models.DashboardAPI;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeAPI;
import com.mh.systems.demoapp.models.Friends.RemoveFriendAPI;
import com.mh.systems.demoapp.models.FriendsAPI;
import com.mh.systems.demoapp.models.HCapHistory.HCapHistoryAPI;
import com.mh.systems.demoapp.models.HandicapAPI;
import com.mh.systems.demoapp.models.MembersAPI;
import com.mh.systems.demoapp.models.MembersDetailAPI;
import com.mh.systems.demoapp.models.FinanceAPI;
import com.mh.systems.demoapp.models.ResetPassword.ResetPasswordAPI;
import com.mh.systems.demoapp.models.TogglePrivacy.TogglePrivacyAPI;
import com.mh.systems.demoapp.models.competitionsEntry.CompEligiblePlayersAPI;
import com.mh.systems.demoapp.models.competitionsEntry.GetClubEventAPI;
import com.mh.systems.demoapp.models.competitionsEntry.UpdateCompEntryAPI;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by karan@ucreate.co.in for
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
    @POST("/api/ClubsApp")
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
    @POST("/api/ClubsApp/RpcRequest")
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
    @POST("/api/ClubsApp")
    public void getCompetitionsEvents(@Body CompetitionsAPI jsonElements, Callback<JsonObject> response);


    @GET("/api/ClubsApp/RpcRequest")
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
    @POST("/api/ClubsApp/RpcRequest")
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
    @POST("/api/ClubsApp")
    public void resultOfCompetitionEvent(@Body CompetitionResultAPI competitionResultAPI, Callback<JsonObject> response);

    /**
     * Declaration of Members Detail screen web service method.
     * <p/>
     * TYPE : POST
     *
     * @param handicapAPI
     * @param response
     */
    @POST("/api/ClubsApp/RpcRequest")
    public void getHandicap(@Body HandicapAPI handicapAPI, Callback<JsonObject> response);

    /**
     * Declaration of My Account
     * web service method.
     * <p/>
     * TYPE : POST
     *//*
    @POST("/ClubsApp/RpcRequest")
    public void getMyAccount(@Body FinanceAPI jsonElements, Callback<JsonObject> response);*/

    /**
     * Declaration of Finance API method declaration.
     * <p/>
     * TYPE : POST
     */
    @POST("/api/ClubsApp/RpcRequest")
    public void getFinanceDetail(@Body FinanceAPI jsonElements, Callback<JsonObject> response);

    /**
     * Declaration of Members web service method.
     * <p/>
     * TYPE : POST
     *
     * @param membersAPI
     * @param response
     */
    @POST("/api/ClubsApp/RpcRequest")
    public void getMembers(@Body MembersAPI membersAPI, Callback<JsonObject> response);

    /**
     * Declaration of Members Detail screen web service method.
     * <p/>
     * TYPE : POST
     *
     * @param membersDetailAPI
     * @param response
     */
    @POST("/api/ClubsApp/RpcRequest")
    public void getMembersDetail(@Body MembersDetailAPI membersDetailAPI, Callback<JsonObject> response);

    /**
     * Declaration of ADD MEMBER functionality web service method.
     * <p/>
     * TYPE : POST
     *
     * @param addMemberAPI
     * @param response
     */
    @POST("/api/ClubsApp/RpcRequest")
    public void getAddMember(@Body AddMemberAPI addMemberAPI, Callback<JsonObject> response);

    /**
     * Declaration of FRIENDS web service method.
     * <p/>
     * TYPE : POST
     *
     * @param friendsAPI
     * @param response
     */
    @POST("/api/ClubsApp/RpcRequest")
    public void getFriends(@Body FriendsAPI friendsAPI, Callback<JsonObject> response);

    /**
     * Declaration of REMOVE FRIEND web service method.
     * <p/>
     * TYPE : POST
     *
     * @param removeFriendAPI
     * @param response
     */
    @POST("/api/ClubsApp/RpcRequest")
    public void removeFriend(@Body RemoveFriendAPI removeFriendAPI, Callback<JsonObject> response);

    /**
     * Declaration of Reset Password web service method.
     * <p/>
     * TYPE : POST
     *
     * @param resetPasswordAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void resetPassword(@Body ResetPasswordAPI resetPasswordAPI, Callback<JsonObject> response);

    /**
     * Declaration of Unjoin Competition web service method.
     * <p/>
     * TYPE : POST
     *
     * @param competitionUnjoinAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void unjoinCompetition(@Body CompetitionUnjoinAPI competitionUnjoinAPI, Callback<JsonObject> response);

    /**
     * Declaration of Club News web service method.
     * <p/>
     * TYPE : POST
     *
     * @param clubNewsAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void getClubNews(@Body ClubNewsAPI clubNewsAPI, Callback<JsonObject> response);

    /**
     * Declaration of update Club News READ/DELETE web service method.
     * <p/>
     * TYPE : POST
     *
     * @param clubNewsDetailAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void updateClubNews(@Body ClubNewsDetailAPI clubNewsDetailAPI, Callback<JsonObject> response);

    /**
     * Declaration of HANDICAP HISTORY web service method.
     * <p/>
     * TYPE : POST
     *
     * @param hCapHistoryAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void getHCapHistory(@Body HCapHistoryAPI hCapHistoryAPI, Callback<JsonObject> response);

    /**
     * Declaration of Update Members detail service method.
     * <p/>
     * TYPE : POST
     *
     * @param editDetailModeAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void updateMemberDetails(@Body EditDetailModeAPI editDetailModeAPI, Callback<JsonObject> response);

    /**
     * Declaration of Update Members detail service method.
     * <p/>
     * TYPE : POST
     *
     * @param togglePrivacyAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void updatePrivacySettings(@Body TogglePrivacyAPI togglePrivacyAPI, Callback<JsonObject> response);

    /**
     * Declaration of GETCLUBEVENT web service to get detail
     * of COMPETITION event by passing 'eventId'.
     * <p/>
     * TYPE : POST
     *
     * @param getClubEventAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void getClubEvent(@Body GetClubEventAPI getClubEventAPI, Callback<JsonObject> response);

    /**
     * Declaration of GETCOMPELIGIBLEPLAYERS web service to get eligible players list
     * of COMPETITION event by passing 'eventId'.
     * <p/>
     * TYPE : POST
     *
     * @param compEligiblePlayersAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void getEligiblePlayersList(@Body CompEligiblePlayersAPI compEligiblePlayersAPI, Callback<JsonObject> response);

    /**
     * Declaration of UPDATECLUBEVENTENTRIES web service to update paid Competition Entry
     * of COMPETITION.
     * <p/>
     * TYPE : POST
     *
     * @param updateCompEntryAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void updateCompEntry(@Body UpdateCompEntryAPI updateCompEntryAPI, Callback<JsonObject> response);
}

