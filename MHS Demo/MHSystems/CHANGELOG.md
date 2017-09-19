####Version 1.4 (19th, Sep, 2017)

 Release the following changes:
 
 ####20th March, 2017
 - UPDATE: Display 'NettTotal' instead of 'GetScoreSummary' in Completed Competitions details.
 
 ####11th April 2017
 - Fixed: Set Top up amount followed by 2 fraction values.
 
 ####15th May, 2017
 - FIXED: android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@5878646 is not valid; is your activity running?
 
 ####14th June, 2017
   - UPDATE: Update the Finance tab UI with 4 columns (date/time, item, amount and balance).
   - FIXED : Navigate back from finance detail to finance tab, top-right filter icon was not working. Now fixed.
   - UPDATE: Add In App Update Notification version alert dialog one time when any update found.
   - UPDATE: Integrate the feature flag to Membership Type in Members.

 ####18th May, 2017
   - FIXED: #23 java.lang.IndexOutOfBoundsException: Invalid index 0, size is 0 in Club News at onActivityResult

 ####1st Aug, 2017
   - FIXED: Friend/Unfriend is not getting updated in one go as need to switch the screen to get the result.
   - FIXED: Member detail screen should display 'FormalName' not 'FullName'
   - FIXED: In Members, when click for detailed screen, the fields are reflecting for a moment but no data is showing up.
   - FIXED: Your Account 'Edit Details' and 'Toggle Privacy' settings is not getting saved.

 ####14th Sep, 2017
   - NEW: Now different types of <B>UPCOMING COMPEITITONS</B> depends upon the ```teamsize``` i.e. 1,2,3 and 4.
   - NEW: Update web service for <B>Unjoin</B> members from the <B>Entered COMPEITITONS</B>.
   - NEW: Expand Dashboard size and decrease logo size if more than 6 items available otherwise remain same.

####Version 1.0 (2017-03-17)

   Initially release.

   ####2nd March, 2017
   - UPDATE: Get extra deduction amount from key 'TopupTxFeeStr' in ToUpActivity along with pricing list. Also display deduction amount before make payment if exists.
   - NEW: Display VERSION NAME in the Settings screen at bottom.

   ####14th March, 2017
   - Fixed: Resolved crash of wrong menu item displayed on YOUR ACCOUNT option.
   - Fixed: Resolved crashed. java.lang.Class<...\fragments.MyAccountTabFragment> has no zero argument constructor

   ####16th Feb, 2017
   - UPDATE: Replace image loader library 'Glide' with 'Picasso' version 2.5.2.
   - UPDATE: Display 'No Image' till club news doesn't load image.

   ####10th Feb, 2017
   - Enhanced: Remove 'libValidation' library used for input passwords validations and implements custom code.

   #####2nd Feb, 2017
   - UPDATE: Replace 'SimpleDrawerView' with 'CircleImageView' for better performance.

   ####25th Jan, 2017
   - UPDATE: 'Friends' info on FRIENDS tab display information on new screen instead of Alert Dialog.
   - UPDATE: REMOVE 'Contact Us' from Settings. Create new tab of 'Contact Us' along with MEMBERS and FRIENDS tabs. Also update 'CONTACT US' information.

####Initial Created.

   - New: Weather feature added.
   - New: Contact us feature added.
   - Fixed: Handicap graph date format 'MM/dd/yyyy' changed to 'dd/MM/yyyy'.
   - Fixed: Club News: Implements Serializable to pass club news data on detail screen instead of passing value one by one.
   - New:  Top up Functionality.
