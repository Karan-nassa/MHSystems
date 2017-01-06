package com.mh.systems.demoapp.util.API;

import com.google.gson.JsonObject;
import com.mh.systems.demoapp.models.AddMemberAPI;
import com.mh.systems.demoapp.models.ClubNews.ClubNewsAPI;
import com.mh.systems.demoapp.models.ClubNews.ClubNewsDetailAPI;
import com.mh.systems.demoapp.models.ClubNewsThumbnail.ClubNewsThumbnailAPI;
import com.mh.systems.demoapp.models.ClubNewsThumbnail.ClubNewsThumbnailDetailAPI;
import com.mh.systems.demoapp.models.CompetitionResultAPI;
import com.mh.systems.demoapp.models.CompetitionJoinAPI;
import com.mh.systems.demoapp.models.CompetitionUnjoinAPI;
import com.mh.systems.demoapp.models.CompetitionsAPI;
import com.mh.systems.demoapp.models.ContactUs.ContactUsAPI;
import com.mh.systems.demoapp.models.CourseDiaryAPI;
import com.mh.systems.demoapp.models.DashboardAPI;
import com.mh.systems.demoapp.models.DeleteToken.DeleteTokenAPI;
import com.mh.systems.demoapp.models.EditDetailMode.EditDetailModeAPI;
import com.mh.systems.demoapp.models.ForgotPassword.ForgotPasswordAPI;
import com.mh.systems.demoapp.models.Friends.RemoveFriendAPI;
import com.mh.systems.demoapp.models.FriendsAPI;
import com.mh.systems.demoapp.models.HCapHistory.HCapHistoryAPI;
import com.mh.systems.demoapp.models.HandicapAPI;
import com.mh.systems.demoapp.models.MembersAPI;
import com.mh.systems.demoapp.models.MembersDetailAPI;
import com.mh.systems.demoapp.models.FinanceAPI;
import com.mh.systems.demoapp.models.ResetPassword.ResetPasswordAPI;
import com.mh.systems.demoapp.models.TogglePrivacy.TogglePrivacyAPI;
import com.mh.systems.demoapp.models.TopUp.TopUpPricesListAPI;
import com.mh.systems.demoapp.models.UnreadNewsCount.GetUnreadNewsCountAPI;
import com.mh.systems.demoapp.models.UpdatePassword.UpdatePassswordAPI;
import com.mh.systems.demoapp.models.competitionsEntry.CompEligiblePlayersAPI;
import com.mh.systems.demoapp.models.competitionsEntry.GetClubEventAPI;
import com.mh.systems.demoapp.models.competitionsEntry.UpdateCompEntryAPI;
import com.mh.systems.demoapp.models.registerToken.RegisterTokenAPI;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
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
//    @POST("/api/ClubsApp")
//    public void getClubNews(@Body ClubNewsAPI clubNewsAPI, Callback<JsonObject> response);

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

    /**
     * Declaration of Forgot Password web service method.
     * <p/>
     * TYPE : POST
     *
     * @param forgotPasswordAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void forgotPassword(@Body ForgotPasswordAPI forgotPasswordAPI, Callback<JsonObject> response);

    /**
     * Declaration of UPDATE temporary Password web service method.
     * <p/>
     * TYPE : POST
     *
     * @param updatePassswordAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void updatePassword(@Body UpdatePassswordAPI updatePassswordAPI, Callback<JsonObject> response);

    /**
     * Declaration of Weather web service method.
     * <p/>
     * TYPE : GET
     *
     * @param type     : Weather or Forecast
     * @param mode     : JSON or XML
     * @param appid    : API key
     * @param lat      : lat
     * @param lon      : lon
     * @param response : Response of API
     */
    @GET("/data/2.5/{type}")
    public void getWeatherState(@Path("type") String type,
                                @Query("mode") String mode,
                                @Query("appid") String appid,
                                @Query("lat") String lat,
                                @Query("lon") String lon,
                                Callback<JsonObject> response);

    /**
     * Declaration of WEATHER API status on dashboard.
     * <p/>
     * TYPE : POST
     *
     * @param type         : WEATHER OR FORCEAST
     * @param aClientId    : Club ID like 44071043 for Demo App.
     * @param aCurrentDate : Current date.
     * @param response     : Weather api response in JSON format.
     */
    @POST("/ClubAppUse/{type}")
    public void weatherAPI(@Path("type") String type, @Query("aClientId") String aClientId, @Query("aCurrentDate") String aCurrentDate, Callback<JsonObject> response);

    /**
     * Declaration of FORCAST API status on dashboard.
     * <p/>
     * TYPE : POST
     *
     * @param type      : WEATHER OR FORCEAST
     * @param aClientId : Club ID like 44071043 for Demo App.
     * @param aHour     : Time hour in running device.
     * @param response  : Weather api response in JSON format.
     */
    @POST("/ClubAppUse/forecast")
    public void forcastAPI(@Query("aClientId") String aClientId, @Query("aHour") String aHour, Callback<JsonObject> response);

    /**
     * Declaration of REGISTRATION TOKEN API in background service.
     * <p/>
     * TYPE : POST
     *
     * @param registerTokenAPI : Time hour in running device.
     * @param response         : Weather api response in JSON format.
     */
    @POST("/api/ClubsApp")
    public void registerToken(@Body RegisterTokenAPI registerTokenAPI, Callback<JsonObject> response);

    /**
     * Declaration of DELETE TOKEN API which is using for push
     * notifications.
     * <p/>
     * TYPE : POST
     *
     * @param deleteTokenAPI : Delete Token which sending at time of Registeration.
     * @param response       : Weather api response in JSON format.
     */
    @POST("/api/ClubsApp")
    public void deleteToken(@Body DeleteTokenAPI deleteTokenAPI, Callback<JsonObject> response);

    /**
     * Declaration of GET UNREAD CLUB NEWS API which will be
     * display on dashboard at top of Club News icon.
     * <p/>
     * TYPE : POST
     *
     * @param getUnreadNewsCountAPI : Object of unread club news.
     * @param response              : Weather api response in JSON format.
     */
    @POST("/api/ClubsApp")
    public void getUnreadClubNewsCount(@Body GetUnreadNewsCountAPI getUnreadNewsCountAPI, Callback<JsonObject> response);

    /**
     * Declaration of Club News web service method with Thumbnail
     * of image.
     * <p/>
     * TYPE : POST
     *
     * @param clubNewsThumbnailAPI :  Club News with Thumbnail.
     * @param response
     */
    @POST("/api/ClubsApp")
    public void getClubNewsThumbnail(@Body ClubNewsThumbnailAPI clubNewsThumbnailAPI, Callback<JsonObject> response);

    /**
     * Declaration of Club News detail content of Thumbnail web
     * service method.
     * <p/>
     * TYPE : POST
     *
     * @param clubNewsThumbnailDetailAPI :  Club News detail of Thumbnail.
     * @param response
     */
    @POST("/api/ClubsApp")
    public void getClubNewsThumbnailDetail(@Body ClubNewsThumbnailDetailAPI clubNewsThumbnailDetailAPI, Callback<JsonObject> response);

    /**
     * Declaration of CONTACT US web service declaration.
     * <p/>
     * TYPE : POST
     *
     * @param contactUsAPI
     * @param response
     */
    @POST("/api/ClubsApp")
    public void contactUs(@Body ContactUsAPI contactUsAPI, Callback<JsonObject> response);

    /**
     * Declaration of TOP UP prices list web service declaration.
     * <p/>
     * TYPE : POST
     *
     * @param aClientId     : Client ID.
     * @param aMemberId     : Member ID.
     * @param response      : Top Up Price list.
     */
    @GET("/api/ApifsiGateway/TopUps")
    public void getTopUpPricesList(@Query("aClientId") String aClientId, @Query("aMemberId") String aMemberId, Callback<JsonObject> response);
}

