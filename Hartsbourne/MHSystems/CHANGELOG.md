##Version 1.6.0 ()

- UPDATE the following changes in the code are:

  ####20th March, 2017
  - Fixed: Move splash background image to correspoding directories. Roll bar item no 31. java.lang.RuntimeException: Canvas: trying to draw too large(132710400bytes) bitmap.
  
  ####22th March, 2017
  - Fixed: Resolved crashed. java.lang.Class<com.mh.systems.guildford.fragments.MyAccountTabFragment> has no zero argument constructor
  - UPDATE: Display 'NetTotal' instead of 'GetScoreSummary' in Completed Competitions details.

  ####31st March, 2017
  - UPDATE: Re-ordering the methods (override, private, public) and the packaging structure.
  - FIXED: hCap value not showing on dashboard.
 
  ####11th April 2017
  - FIXED: Set Top up amount followed by 2 fraction values.
  
  ####17th May, 2017
  - UPDATE: Update the App icon and dashboard logo (ic_home_golfclub).
 
  ###18th July, 2017
  - FIXED: #23 java.lang.IndexOutOfBoundsException: Invalid index 0, size is 0 in Club News at onActivityResult
 
 ####24th May, 2017
   - UPDATE: Update the Finance tab UI with 4 columns (date/time, item, amount and balance).
   - FIXED : Navigate back from finance detail to finance tab, top-right filter icon was not working. Now fixed.
   - UPDATE: Add In App Update Notification version alert dialog one time when any update found.
   - UPDATE: Integrate the feature flag to Membership Type in Members.
   - FIXED:  Completed Competitions should display from 1st date to end of the month always.

 ####2nd August, 2017
  - FIXED: Friend/Unfriend is not getting updated in one go as need to switch the screen to get the result.
  - FIXED: Member detail screen should display 'FormalName' not 'FullName'
  - FIXED: In Members, when click for detailed screen, the fields are reflecting for a moment but no data is showing up.
  - FIXED: Your Account 'Edit Details' and 'Toggle Privacy' settings is not getting saved.

 ####17th Aug, 2017
  - UPDATE: Display 'GetScoreSummary' instead of 'NettTotal' in Completed Competitions details.
  - FIXED:  Display 'No Club News' when user delete last news from list.
  
 ####29th, Sep, 2017
 - NEW: Mobile App Item 124: Issue with Club News - Orientation.

##Version  1.5.0 (2017-03-08)

- Fixed all changes in this version 1.5

    #####16th Feb, 2017
     - UPDATE: Replace image loader library 'Glide' with 'Picasso' version 2.5.2.
     - UPDATE: Display 'No Image' till club news doesn't load image.

    #####20th Feb, 2017
    - NEW: Deploy FSI Top ups gateway integration.
    
    ####3rd March, 2017
     - UPDATE: Get extra deduction amount from key 'TopupTxFeeStr' in ToUpActivity along with pricing list. Also display deduction amount before make payment if exists.
     - NEW: Display VERSION NAME in the Settings screen at bottom.
     
    ####16th March, 2017
     - Fixed: Resolved crash of wrong menu item displayed on YOUR ACCOUNT option.

##Version  1.4.0 (2017-01-14)

- Fixed: Handicap graph date format 'MM/dd/yyyy' changed to 'dd/MM/yyyy'.
- Fixed: Club News: Implements Serializable to pass club news data on detail screen instead of passing value one by one.

    #####25th Jan, 2017
    - UPDATE: 'Friends' info on FRIENDS tab display information on new screen instead of Alert Dialog.
    - UPDATE: REMOVE 'Contact Us' from Settings. Create new tab of 'Contact Us' along with MEMBERS and FRIENDS tabs. Also update 'CONTACT US' information.

    #####3rd Feb, 2017
    - UPDATE: Replace 'SimpleDrawerView' with 'CircleImageView' for better performance.
    - FIXED: Implement Internet check for Forecast Weather data.

    #####13th Feb, 2017
    - NEW: Integrate BRS diary.

    ####14th Feb, 2017
    - NEW: Integrate 'Glide' library to load images from URL for club news.
    - UPDATE: Club News with thumbnail feature added.
    - Enhanced: Remove 'libValidation' library used for input passwords validations and implements custom code.
    - Enhanced: Move 'Features Flag' and 'Registration Token' from Login to Dashboard.
    - NEW: Add 'Delete Token' functionality.
    - NEW: Add 'Unread News Badger' count functionality [Hide].

##Version 1.3.0 (2016-12-26)

- New: Contact us feature added

##Version 1.2.0 (2016-12-26)

- New: Weather feature added

##Version 1.1.0 (2016-12-02)

- Small bug fixes
- Performance enhancements
- SUSPENDED by Google

##Version 1.0.0 (2016-10-13)

Initial release.