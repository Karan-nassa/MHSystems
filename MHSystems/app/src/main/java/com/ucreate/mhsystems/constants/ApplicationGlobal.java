package com.ucreate.mhsystems.constants;

/**
 * Created by karan@ucreate.co.in to use
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



}
