####Version 2.0.0 (19th Sep, 2017)

 - FIXED: #23 java.lang.IndexOutOfBoundsException: Invalid index 0, size is 0 in Club News at onActivityResult
 
 ####13th Sep, 2017
  - NEW: Now different types of <B>UPCOMING COMPEITITONS</B> depends upon the ```teamsize``` i.e. 1,2,3 and 4.
  - NEW: Update web service for <B>Unjoin</B> members from the <B>Entered COMPEITITONS</B>.
 
####Version 1.9.0 (7th June, 2017)
   - UPDATE: Display 'Account' and 'Balance' title along with month title on Finance.
   - UPDATE: The balance needs to be complete - not + 101.... Allow for up to £9,999.99.

####Version 1.8.0 (26th May, 2017)
   - FIXED: Fixed the In app update notification logic issue.
   - FIXED: Finance tab 'DiscountAmountBalance' showing wrong value.
   
####Version 1.7.0 (25th May, 2017)

   ####21st March, 2017
   - UPDATE: Display 'NettTotal' instead of 'GetScoreSummary' in Completed Competitions details.
   
   ####31st March, 2017
   - UPDATE: Re-ordering the methods (override, private, public) and the packaging structure.
   - FIXED: hCap value not showing on dashboard.
   
   ####11th April 2017
   - FIXED: Set Top up amount followed by 2 fraction values.
   
   ####15th May, 2017
   - FIXED: android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@5878646 is not valid; is your activity running?
    
   ####24th May, 2017
   - UPDATE: Update the Finance tab UI with 4 columns (date/time, item, amount and balance).
   - FIXED : Navigate back from finance detail to finance tab, top-right filter icon was not working. Now fixed.
   - UPDATE: Add In App Update Notification version alert dialog one time when any update found.
   - UPDATE: Integrate the feature flag to Membership Type in Members.
           
##Version  1.6.0 (20th March, 2017)

  - Update changes in Version 1.6.0 are as below:

    #####17th Feb, 2017
     - UPDATE: Change 'FSI' payment gateway from STAGING to PRODUCTION server.

    #####20th Feb, 2017
    - NEW: Deploy FSI Top ups gateway integration.
     
    ####3rd March, 2017
    - UPDATE: Get extra deduction amount from key 'TopupTxFeeStr' in ToUpActivity along with pricing list. Also display deduction amount before make payment if exists.
    - NEW: Display VERSION NAME in the Settings screen at bottom.

    ####16th March, 2017
     - Fixed: Resolved crash of wrong menu item displayed on YOUR ACCOUNT option.

##Version  1.5.0 (16th Feb, 2017)

- Fixed: Handicap graph date format 'MM/dd/yyyy' changed to 'dd/MM/yyyy'.
- Fixed: Club News: Implements Serializable to pass club news data on detail screen instead of passing value one by one.

    #####25th Jan, 2017
    - UPDATE: 'Friends' info on FRIENDS tab display information on new screen instead of Alert Dialog.
    - UPDATE: REMOVE 'Contact Us' from Settings. Create new tab of 'Contact Us' along with MEMBERS and FRIENDS tabs. Also update 'CONTACT US' information.

    #####3rd Feb, 2017
    - UPDATE: Replace 'SimpleDrawerView' with 'CircleImageView' for better performance.
    - FIXED: Implement Internet check for Forecast Weather data.

    ####7th Feb, 2017
    - NEW: Integrate push notification functionality.
	- NEW: Integrate Register Token functionality with GCM.
	- UPDATE: Integrate Club News with Thumbnail features.
	- NEW: Integrate Logout functionality (API).

	####10th Feb, 2017
      - Enhanced: Remove 'libValidation' library used for input passwords validations and implements custom code.

    ####13th Feb, 2017
      - NEW: Implements 'Webcam' webview integration.

    ####14th Feb, 2017
      - UPDATE: Replace 'Webcam' webview with image. Only webcam image will be displayed.
	  
	####15th Feb, 2017
	  - UPDATE: Implements features flag functionality on dashboard.
	  - UPDATE: Integrate webcam functionality dynamically.

	 ####16th Feb, 2017
       - UPDATE: Replace image loader library 'Glide' with 'Picasso' version 2.5.2.
       - UPDATE: Display 'No Image' till club news doesn't load image.

##Version 1.4.0 (2016-12-01)

- New: Contact us feature added

##Version 1.3.0 (2016-11-04)

- New: Weather feature added

##Version 1.2.0 (2016-10-25)

- Small bug fixes
- Performance enhancements

##Version 1.1.0 (2016-10-11)

- General bug fixes

##Version 1.0.0 (2016-10-07)

Initial release