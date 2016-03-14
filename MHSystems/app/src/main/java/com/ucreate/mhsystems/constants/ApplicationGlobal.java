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
    public static final String KEY_ROLLBAR_CLIENT_TESTING ="aae35eeaa0e74a619c7542f98d60f8ee"; //Personal credentials

    /**
     * Declare a bool used to display and pass message to Rollbar or not? TRUE means YES
     * Otherwise set FALSE.
     */
    public static boolean isRollMessageDisplay = true;


}
