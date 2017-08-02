##Version 1.4.0 ()

Changed the following issues:
    
   ####22nd March, 2017
   - UPDATE: Display 'NetTotal' instead of 'GetScoreSummary' in Completed Competitions details.
   - Fixed: Resolved crashed. java.lang.Class<guildford_.fragments.MyAccountTabFragment> has no zero argument constructor.

   ####31st March, 2017
   - UPDATE: Re-ordering the methods (override, private, public) and the packaging structure.
   - FIXED: hCap value not showing on dashboard.
   
   ####15th May, 2017
   - FIXED: android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@5878646 is not valid; is your activity running?
            
   ####18th July, 2017
   - FIXED: #23 java.lang.IndexOutOfBoundsException: Invalid index 0, size is 0 in Club News at onActivityResult

   ####2nd August, 2017
   - FIXED: Friend/Unfriend is not getting updated in one go as need to switch the screen to get the result.
   - FIXED: Member detail screen should display 'FormalName' not 'FullName'
   - FIXED: In Members, when click for detailed screen, the fields are reflecting for a moment but no data is showing up.
   - FIXED: Your Account 'Edit Details' and 'Toggle Privacy' settings is not getting saved.

##Version1.3.0 (2016-02-16)

- Fixed: Handicap graph date format 'MM/dd/yyyy' changed to 'dd/MM/yyyy'.
- Fixed: Club News: Implements Serializable to pass club news data on detail screen instead of passing value one by one.

    #####25th Jan, 2017
    - UPDATE: 'Friends' info on FRIENDS tab display information on new screen instead of Alert Dialog.
    - UPDATE: REMOVE 'Contact Us' from Settings. Create new tab of 'Contact Us' along with MEMBERS and FRIENDS tabs. Also update 'CONTACT US' information.

   #####2nd Feb, 2017
    - UPDATE: Replace 'SimpleDrawerView' with 'CircleImageView' for better performance.
    - FIXED: Implement Internet check for Forecast Weather data.

   ####16th Feb, 2017
    - Enhanced: Remove 'libValidation' library used for input passwords validations and implements custom code.
    - UPDATE: Display 'No Image' till club news doesn't load image.
    - NEW: Integrate push notification functionality [HIDE].
    - NEW: Integrate Register Token functionality with GCM.
    - UPDATE: Integrate Club News with Thumbnail features.
    - NEW: Integrate Logout functionality (API).
    - NEW: Display Unread Club News badger cound on dashboard [HIDE].
    
   ####3rd March, 2017
    - UPDATE: Get extra deduction amount from key 'TopupTxFeeStr' in ToUpActivity along with pricing list. Also display deduction amount before make payment if exists.
    - NEW: Display VERSION NAME in the Settings screen at bottom.
    - UPDATE: Change the FSI Top up price list and Make Payment url from testing to production.
    
    ####16th March, 2017
     - Fixed: Resolved crash of wrong menu item displayed on YOUR ACCOUNT option.
     - Fixed: Resolved crashed. java.lang.Class<guildford_.fragments.MyAccountTabFragment> has no zero argument constructor

    ####27th July, 2017
    - UPDATE: Update the Finance tab UI with 4 columns (date/time, item, amount and balance).
    - FIXED : Navigate back from finance detail to finance tab, top-right filter icon was not working. Now fixed.
    - UPDATE: Add In App Update Notification version alert dialog one time when any update found.
    - UPDATE: Integrate the feature flag to Membership Type in Members.
    - FIXED:  Completed Competitions should display from 1st date to end of the month always.

##Version 1.2.0 (2016-12-26)

- New: Contact us feature added.

##Version 1.1.0 (2016-12-22)

- New: Weather feature added.

##Version 1.0.0 (2016-12-09)

Initial release.