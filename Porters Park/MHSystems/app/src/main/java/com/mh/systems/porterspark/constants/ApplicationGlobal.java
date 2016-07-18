package com.mh.systems.porterspark.constants;

/**
 * Created by karan@mh.co.in to use
 * all type of CONSTANTS used through out
 * the application on 19-02-2016.
 */
public class ApplicationGlobal {

    /**
     * Declares the ROLL BAR key for PRODUCTION ENVIRONMENT.
     */
    public static final String KEY_ROLLBAR_CLIENT_PRODUCTION = "4a927eeda0724fc69e83acbb6b3a7443";

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
     * Declares the KEY used to pass some of the
     * data on DETAIL screen of MY ACCOUNT INVOICE.
     */
    public static final String KEY_INVOICE_TITLE = "INVOICE_TAX_TITLE";
    public static final String KEY_INVOICE_NUMBER = "INVOICE_NUMBER";
    public static final String KEY_INVOICE_VALUE = "INVOICE_VALUE";
    public static final String KEY_INVOICE_TAX = "INVOICE_TAX";
    public static final String KEY_INVOICE_TOTAL_PAYABLE = "INVOICE_TAX_PAYABLE";
    public static final String KEY_INVOICE_DATE = "INVOICE_DATE";
    public static final String KEY_INVOICE_DESCRIPTION = "INVOICE_DESCRIPTION";
    public static final String KEY_INVOICE_BILL_FROM = "INVOICE_BILL_FROM";
    public static final String KEY_INVOICE_BILL_TO = "INVOICE_BILL_TO";
    public static final String KEY_INVOICE_STATUS_STR = "INVOICE_STATUS_STR";


    /**
     * Used during tap on SPINNER/DROPDOWN selectable items to sent on
     * {@link com.mh.systems.porterspark.fragments.MembersTabFragment} for update MEMBERS list accordingly.
     */
    public static final int ACTION_MEMBERS_ALL = 2;
    public static final int ACTION_MEMBERS_LADIES = 1;
    public static final int ACTION_MEMBERS_GENTLEMENS = 0;

    /**
     * Used during tap on SPINNER/DROPDOWN selectable items to sent on
     * {@link com.mh.systems.porterspark.fragments.MembersTabFragment} for update FRIENDS list accordingly.
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
    public static String TAG_CLIENT_ID = "44071043";

    /**
     * SHARED PREFERENCE KEY COLLECTION.
     */
    public static final String SHARED_PREF = "MHSystemsPrefs_porterspark";
    public static final String KEY_USER_LOGINID = "UserLoginID";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CLUB_ID = "ClubID";
    public static final String KEY_MEMBERID = "MemberID";
    public static final String KEY_HCAP_TYPE_STR = "HCapTypeStr";
    public static final String KEY_HCAP_EXACT_STR = "HCapExactStr";
    public static final String KEY_HCAP_PLAY_STR = "HCapPlayStr";

}










