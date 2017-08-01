##Version 1.5.0 ()
 - FIXED: #23 java.lang.IndexOutOfBoundsException: Invalid index 0, size is 0 in Club News at onActivityResult

 ####1st Aug, 2017
  - FIXED: Friend/Unfriend is not getting updated in one go as need to switch the screen to get the result.
  - FIXED: Member detail screen should display 'FormalName' not 'FullName'
  - FIXED: In Members, when click for detailed screen, the fields are reflecting for a moment but no data is showing up.
  - FIXED: Your Account 'Edit Details' and 'Toggle Privacy' settings is not getting saved.


##Version 1.4.0 (05.July, 2017)
 - UPDATE: Extend the background colour on Halesworths App on dashboard.
 
##Version 1.3.0 (12th June, 2017)

 - UPDATE: Display 'Account' and 'Balance' title along with month title on Finance.
 - UPDATE: Update the Finance tab UI with 4 columns (date/time, item, amount and balance).
 - FIXED : Navigate back from finance detail to finance tab, top-right filter icon was not working. Now fixed.
 - UPDATE: Add In App Update Notification version alert dialog one time when any update found.
 - UPDATE: Integrate the feature flag to Membership Type in Members.
 - UPDATE: The balance needs to be complete - not + 101.... Allow for up to £9,999.99.

##Version 1.2.0 (26th May, 2017)

- New: Contact us feature added.
- Fixed: Handicap graph date format 'MM/dd/yyyy' changed to 'dd/MM/yyyy'.
- Fixed: Club News: Implements Serializable to pass club news data on detail screen instead of passing value one by one.

    #####25th Jan, 2017
    - UPDATE: 'Friends' info on FRIENDS tab display information on new screen instead of Alert Dialog.
    - UPDATE: REMOVE 'Contact Us' from Settings. Create new tab of 'Contact Us' along with MEMBERS and FRIENDS tabs. Also update 'CONTACT US' information.

    #####3rd Feb, 2017
    - UPDATE: Replace 'SimpleDrawerView' with 'CircleImageView' for better performance.
    - FIXED: Implement Internet check for Forecast Weather data.

    ####21st Feb, 2017
    - Enhanced: Remove 'libValidation' library used for input passwords validations and implements custom code.
    - UPDATE: Display 'No Image' till club news doesn't load image.
    - UPDATE: Club News with thumbnail feature added.
    - Enhanced: Move 'Features Flag' and 'Registration Token' from Login to Dashboard.
    - NEW: Integrate Club News Push Notification [Hide].
    - NEW: Add 'Delete Token' functionality.
    - NEW: Add 'Unread News Badger' count functionality [Hide].

    ####3rd March, 2017
     - UPDATE: Get extra deduction amount from key 'TopupTxFeeStr' in ToUpActivity along with pricing list. Also display deduction amount before make payment if exists.
     - NEW: Display VERSION NAME in the Settings screen at bottom.
	 - UPDATE: Change the FSI Top up price list and Make Payment url from testing to production.

    ####16th March, 2017
     - Fixed: Resolved crash of wrong menu item displayed on YOUR ACCOUNT option.
     
    ####22nd March, 2017
    - UPDATE: Display 'NetTotal' instead of 'GetScoreSummary' in Completed Competitions details.
    - Fixed: Resolved crashed. java.lang.Class<com.mh.systems.guildford.fragments.MyAccountTabFragment> has no zero argument constructor.
    
    ####31st March, 2017
    - UPDATE: Re-ordering the methods (override, private, public) and the packaging structure.
    - NEW: Integrate Cards Top Ups feature.
    - FIXED: hCap value not showing on dashboard.
    
    ####11th April 2017
    - Fixed: Set Top up amount followed by 2 fraction values.
    
    ####15th May, 2017
    - FIXED: android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@5878646 is not valid; is your activity running?
       
    ####25th May, 2017
    - UPDATE: Update the Finance tab UI with 4 columns (date/time, item, amount and balance).
    - FIXED : Navigate back from finance detail to finance tab, top-right filter icon was not working. Now fixed.
    - UPDATE: Add In App Update Notification version alert dialog one time when any update found.
    - UPDATE: Integrate the feature flag to Membership Type in Members.
         
##Version 1.1.0 (2nd Dec, 2016)

- New: Weather feature added.

##Version 1.0.0 (18th Nov, 2016)

Initial release.