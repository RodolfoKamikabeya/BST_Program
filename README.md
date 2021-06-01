# BST_Program

## This program is part of COT 5405 Design & Analysis Of Algorithms course. 

### Algorithms Design Project to inputs an unknown number of records from a sequential file using BST.

**Step 1.** Write a program to insert the records into Binary Search Tree by account number if the birth date is a valid calendar date, leap year, etc.   

* If the record is a duplicate do not store it twice.  Flag duplicates as errors records. Then print the records
* Implement a menu to allow the user to select the desired function.  
* The program will create a BST using the data stored on the MASTER input file when it is launched. 


**Step 2.**  The program must  display the following MENU:
<pre>
        
                               BST UPDATE
                                 SYSTEM
                 A)	    To Add an new record
                 D)	    To delete an existing record
                 M)         To MODIFY a record
                 P)         To print a report (in order)
                 S)         Save Changes {write current BST to MASTER FILE}
                 Q)         Quit [Exit System] 
 </pre>


**Step 3.** Implementation guide_lines:
<pre>
  Option A :   prompt for the account number of the record to be added to the BST.  If the record 
               already exist  DISPLAY “ DUPLICATES ARE NOT ALLOWED”
               Re-display menu
               If the record is not found prompt for the remaining fields (one at a time) and insert 
               the new record into the BST
  Optiion M/D: Prompt the user for the account number of the record to be modified/deleted 
               if the record is not present   DISPLAY “ RECORD 999 is NOT FOUND”
               re-display the menu 
               if the record is present prompt the user for the remaining fields (one at a time) modify
               the record and DISPLAY “ RECORD 999  MODIFIED”
               if the delete option is selected delete the record if it exist and display
               “are your sure” if the user enters yes then delete the record and  display
               “Record 99 deleted”
               Otherwise display  “Record 999  does not exist”
  Option S:    Write the records stored in the BST back to the sequential MASTER FILE
               DISPLAY “ CHANGES SAVED 999 records written to MASTER FILE” then terminate program
  Option Q :   Terminate the system without saving changes
  {Note options are not case sensitive}
 </pre>
### Sample Data
<pre>
201 Smith T  Joe 12 10 1990 45000.00 a 850-789-1234
349 Williams L  keith 01 19 1990 23500.00 B 895-422-9404
999 Mark  Q  Smith 02 13 1995 24442.90 B 850-905-909
</pre>
