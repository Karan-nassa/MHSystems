package com.mh.systems.york.utils.constants;

/**
 * Created by karan@ucreate.co.in to use
 * all type of CONSTANTS used through out
 * the application on 19-02-2016.
 */
public class ApplicationGlobal {

    /**
     * Declares the ROLL BAR key for PRODUCTION ENVIRONMENT.
     */
    public static final String KEY_ROLLBAR_CLIENT_PRODUCTION = "67213e233d1e48259b0b4fa0e735d80f";

    /**
     * Declares the ROLL BAR key for TEST ENVIRONMENT.
     */
    // public static final String KEY_ROLLBAR_CLIENT_TESTING = "f4494cf9f54846c6ac690aad1e624598";
    public static final String KEY_ROLLBAR_CLIENT_TESTING = "aae35eeaa0e74a619c7542f98d60f8ee"; //Personal credentials

    /**
     * Declare a bool used to display and pass message to Rollbar or not? TRUE means YES
     * Otherwise set FALSE.
     */
    public static boolean isRollMessageDisplay = true;

    /**
     * Declare all HOME dashboard MENU
     * options constants.
     */
    public static final int POSITION_YOUR_HANDICAP = 1;
    public static final int POSITION_COURSE_DIARY = 2;
    public static final int POSITION_COMPETITIONS = 3;
    public static final int POSITION_MEMBERS = 4;
    public static final int POSITION_ABOUT_CLUB = 5;
    public static final int POSITION_MY_ACCOUNT = 6;

    /**
     * Use this for COMPETITIONS ENTRY.
     */
    public static final int POSITION_MEMBERS_BOOKING = 7;

    public static final int POSITION_OLD_COURSE = 1;
    public static final int POSITION_NEW_COURSE = 2;

    /**
     * Used during tap on icons or update dates for COMPETITIONS and COURSE DIARY
     * previous, next month arrow navigation.
     */
    public static final int ACTION_NOTHING = 0;
    public static final int ACTION_PREVIOUS_MONTH = 1;
    public static final int ACTION_NEXT_MONTH = 2;
    public static final int ACTION_TODAY = 3;
    public static final int ACTION_CALENDAR = 4;

    /**
     * Declaration of constant used through out the app to pass
     * intent from one Activity to another.
     */
    public static final String TAG_POPUP_THEME = "colorTheme";
    public static final String TAG_CALL_FROM = "callFrom";


    /**
     * Used during tap on SPINNER/DROPDOWN selectable items to sent on
     * {@link com.mh.systems.york.fragments.MembersTabFragment} for update MEMBERS list accordingly.
     */
    public static final int ACTION_MEMBERS_ALL = 2;
    public static final int ACTION_MEMBERS_LADIES = 1;
    public static final int ACTION_MEMBERS_GENTLEMENS = 0;

    /**
     * Used during tap on SPINNER/DROPDOWN selectable items to sent on
     * {@link com.mh.systems.york.fragments.MembersTabFragment} for update FRIENDS list accordingly.
     */
    public static final int ACTION_FRIENDS_YOUR_FRIENDS = 0;
    public static final int ACTION_FRIENDS_ADDED_ME = 1;

    /**
     * Declares the KEY used to pass Member ID from
     * MEMBERS list to MEMBER DETAIL SCREEN.
     */
    public static final String KEY_MEMBER_ID = "MEMBER_ID";

    /**
     * Declares the instance of LOAD MORE VALUES specified for
     * scroll down to load next specific content.
     */
    public static final int LOAD_MORE_VALUES = 12;

    /**
     * Declare some of the CONSTANT fields which
     * will be used during call web service.
     */
    public static String TAG_GCLUB_WEBSERVICES = "WEBSERVICES";
    public static String TAG_GCLUB_MEMBERS = "Members";
    public static int TAG_GCLUB_VERSION = 1;
    public static String TAG_GCLUB_CALL_ID = "1456315336575";
    public static String TAG_NEW_GCLUB_CALL_ID = "1467204496474";
    public static String TAG_CLIENT_ID = "44132036";

    /**
     * SHARED PREFERENCE KEY COLLECTION.
     */
    public static final String SHARED_PREF = "MHS_york";
    public static final String KEY_USER_LOGINID = "UserLoginID";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CLUB_ID = "ClubID";
    public static final String KEY_MEMBERID = "MemberID";
    public static final String KEY_HCAP_TYPE_STR = "HCapTypeStr";
    public static final String KEY_HCAP_EXACT_STR = "HCapExactStr";
    public static final String KEY_HCAP_PLAY_STR = "HCapPlayStr";
    public static final String KEY_COURSES = "Courses";
    public static final String KEY_FIRST_TIME_LOGIN = "FirstTimeLogin";

    public static final String KEY_COURSE_DIARY_FEATURE = "CourseDiaryFeatures";
    public static final String KEY_COMPETITIONS_FEATURE = "CompetitionsFeature";
    public static final String KEY_HANDICAP_FEATURE = "HandicapFeature";
    public static final String KEY_MEMBERS_FEATURE = "MembersFeature";
    public static final String KEY_CLUB_NEWS_FEATURE = "ClubNewsFeature";
    public static final String KEY_YOUR_ACCOUNT_FEATURE = "YourAccountFeature";
}










