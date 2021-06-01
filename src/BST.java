import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Scanner;

public class BST {

    static String MasterFileInput = "emp3.txt";

/* Class containing left
 and right child of current node
* and key value*/
class Node
{
    int acct_number;
    Node left, right;
    String lastName, firstName;
    int month;
    int day;
    int year;  // 4 digits
    float annual_salary;
    String dept_code;  //    Note: may be entered in either upper or lower case (two characters)
    String phone_number; //example 850-345-9999

    //constructor
    public Node(int id,String last_Name, String first_Name, int Month, int Day, int Year,
                float annual_Salary,String dept_Code, String phone_Number)
    {
        acct_number = id;
        left = right = null;
        lastName = last_Name;
        firstName = first_Name;
        month = Month;
        day= Day;
        year= Year;
        dept_code=dept_Code;
        annual_salary= annual_Salary;
        phone_number=phone_Number;
    }
}

    // Root of BST
    Node root;

    // Constructor
    BST()
    {
        root = null;
    }

    // This method mainly calls insertRec()
    void insert(int acct_number, String lastName, String firstName, int month, int day, int year,
                float annual_salary, String dept_code, String phone_number, int countline)
    {
        root = insertRec(root, acct_number,lastName,firstName,month,day,year,annual_salary,dept_code,phone_number,countline);
    }

    /* A recursive function to
       insert a new key in BST */
    Node insertRec(Node root, int acct_number,String lastName, String firstName, int month, int day, int year,
                   float annual_salary,String dept_code, String phone_number,int countline)
    {

        /* If the tree is empty,
           return a new node */
        if (root == null)
        {
            root = new Node(acct_number,lastName,firstName,month,day,year,annual_salary,dept_code,phone_number);
            return root;
        }

        /* Otherwise, recur down the tree */
        if (acct_number < root.acct_number)
            root.left = insertRec(root.left, acct_number,lastName,firstName,month,day,year,annual_salary,dept_code,phone_number,countline);
        else if (acct_number > root.acct_number)
            root.right = insertRec(root.right, acct_number,lastName,firstName,month,day,year,annual_salary,dept_code,phone_number,countline);
        else if(acct_number==root.acct_number){
            //Call the function to write the total error in the error report
            System.out.println("DUPLICATES ARE NOT ALLOWED\n\n");}

        /* return the (unchanged) node pointer */
        return root;
    }

    // This method mainly calls InorderRec()
    void inorder()
    {
        inorderRec(root);
    }

    // This method mainly calls PreorderRec()
    void print_to_MasterFIle()
    {
        printinorder_to_MasterFIle(root);
    }

    // A utility function to
    // do inorder traversal of BST
    void inorderRec(Node root)
    {
        if (root != null) {
            inorderRec(root.left);
            WriteToFile(root.acct_number,root.lastName,root.firstName,root.month,root.day,root.year,
                    root.annual_salary,  root.dept_code,root.phone_number,"inorder");
            inorderRec(root.right);

        }
    }

    void printinorder_to_MasterFIle(Node root)
    {
        if (root != null) {

            printinorder_to_MasterFIle(root.left);
            WriteToMaster(root.acct_number,root.lastName,root.firstName,root.month,root.day,root.year,
                    root.annual_salary,  root.dept_code,root.phone_number);
            printinorder_to_MasterFIle(root.right);

        }
    }


    // This method mainly calls deleteRec()
    void deleteAcct(int acct_number) { root = deleteRec(root, acct_number); }

    /* A recursive function to
     delete an existing account number in BST
    */
    Node deleteRec(Node root, int acct_number)
    {
        /* Base Case:Check if the tree is empty */
        if (root == null){
            return root;}

        //Otherwise, recur down the tree
        //search in the left size and delete the account number
        if (acct_number < root.acct_number)
            root.left = deleteRec(root.left, acct_number);
        //search in the right size and delete the account numb
        else if (acct_number > root.acct_number)
            root.right = deleteRec(root.right, acct_number);

            // if key is same as root's key, then This is the node to be deleted
        else {
            // node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // node with two children: Get the inorder
            // successor (smallest in the right subtree)
            root.acct_number = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.acct_number);
        }

        return root;
    }
    // inorder successor can be obtained by finding the minimum value in right child of the node
    int minValue(Node root)
    {
        int minv = root.acct_number;
        while (root.left != null)
        {
            minv = root.left.acct_number;
            root = root.left;
        }
        return minv;
    }
    //Check if the lastName or firstName contains digit
    private static boolean checkName(String name) {
        String temp ="";
        // Loop looking for character "c" in the word
        for (Character c :name.toCharArray()) {
            //if character is a letter return the letter, if not temp contains digit
            if (Character.isLetter(c))
                temp +=c;
            else if(Character.isDigit(c))
                temp+=c;
        }
        //if temp contains digit return false
        if(temp.matches(".*\\d.*"))
            return false;
            //if temp is just letter return true
        else
            return true;
    }
    //Check how many days has each month
    private static int checkDaysMonth(int month, boolean leap) {
        switch (month){
            //Jan, Mar, May, Jul, Aug, Oct, Dec - 31 days
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            //Fev. Common year - 28 days , Leap year 29 days
            case 2:
                if(leap == true)
                    return 29;
                return 28;
            //Apr, Jun, Set, Nov - 30 days
            case 4: case 6: case 9: case 11:
                return 30;
        }
        return 0;
    }
    // check for leap year
    private static boolean checkLeapYear(int year) {
        // year to be checked
        boolean leap = false;
        // if the year is divided by 4
        if (year % 4 == 0) {
            // if the year is century
            if (year % 100 == 0) {
                // year that is divisible by 100 is a leap year only if it is also divisible by 400
                //if is %400 is leap year
                if (year % 400 == 0)
                    leap = true;
                else
                    leap = false;
            }
            // if the year is not century
            else
                leap = true;
        }
        else
            leap = false;

        return leap;
    }
    //Format the date to a short date notation
    public static String getMonth(int month, int day, int year) {
        switch (month){
            case 1:
                return "Jan."+day+","+year;
            case 2:
                return "Feb."+day+","+year;
            case 3:
                return "Mar."+day+","+year;
            case 4:
                return "Apr."+day+","+year;
            case 5:
                return "May."+day+","+year;
            case 6:
                return "Jun."+day+","+year;
            case 7:
                return "Jul."+day+","+year;
            case 8:
                return "Aug."+day+","+year;
            case 9:
                return "Set."+day+","+year;
            case 10:
                return "Oct."+day+","+year;
            case 11:
                return "Nov."+day+","+year;
            case 12:
                return "Dec."+day+","+year;
        }
        return "";
    }
    //check if is a valid age
    public static int checkAge(int year){
        // if the employer is still working, the end date is today's date
        LocalDate currentdate = LocalDate.now();
        //get today`s year, month and day
        int currentYear = currentdate.getYear();
        //return 0 if is an invalid age(e.g. future date of birth)
        if(year>currentYear) return 0;

        return currentYear -year;
    }
    //Check valid phoneNumber
    private static boolean checkPhoneNumber(String phone_Number) {

        String temp ="";
        // Loop looking for character "c" in phone number
        for(Character c: phone_Number.toCharArray()){
            //if character is a number add to temp variable
            if(Character.isDigit(c)){
                temp +=c;
            }
        }
        //if temp variable has 9 digits, returns true
        if(temp.length()==10) {
            return true;}
        return false;
    }
    //Check valid phoneNumber
    private static boolean checkDigit(String input) {

       int count =0;
       int count2=0;
        // Loop looking for character "c" in phone number
        for(Character c: input.toCharArray()){
            //if character is a number add to temp variable
            if(Character.isDigit(c)){
                count++;
            }
            count2++;
        }
        //if temp variable has 9 digits, returns true

        if(count == count2) {
            return true;}
        return false;
    }

    // service rounded to the nearest year
    private static int nearestYear(int year, int month,int day) {

        // if the employer is still working, the end date is today's date
        LocalDate currentdate = LocalDate.now();
        //get today`s year, month and day
        int currentMonth = currentdate.getMonthValue();
        int currentDay = currentdate.getDayOfMonth();


        //if the employer retired before Jun, the nearest year is decreased by one
        if(Math.abs(currentMonth -month)>6) year =year+1;

        else if(Math.abs(currentMonth-month)==6)
            if(currentDay-day<0)
                year= year+1;

        return year;

    }

    //Function writes the titles and subtitles in the output file
    private static void WriteFileTopics(String type) throws FileNotFoundException {
        //format the date and time in the report
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //check the date and time for the report
        LocalDateTime now = LocalDateTime.now();
        //format the currency

        try {
            File file = null;
            String reportType="";
            if(type =="inorder") {
                // Get the file
                file = new File("report_out_inorder.rpt");
                reportType ="In Order";
            }

            if (file.exists()) {
                RandomAccessFile raf = new RandomAccessFile(file, "rw"); //Open the file for reading and writing
                raf.setLength(0); //set the length of the character sequence equal to 0
            }
            FileWriter fw = new FileWriter(file, true);
            PrintWriter printer = new PrintWriter(fw);

            //Create new format for the report
            Formatter formatter = new Formatter();
            //Convert all the data to string, including the current date and time
            printer.append(String.valueOf(formatter.format("%50s %20s %15s", "Employee Report", " ", dtf.format(now))));
            printer.append('\n');
            formatter = new Formatter();
            printer.append(String.valueOf(formatter.format("%50s%s%s","***",reportType,"***")));
            printer.append('\n');

            formatter = new Formatter();
            printer.append(String.valueOf(formatter.format("%10s %15s %15s %15s %15s %15s %15s %15s","Acct#", "Last", "First","Date of","Annual","Department","Age","Phone")));
            printer.append('\n');

            formatter = new Formatter();
            printer.append(String.valueOf(formatter.format("%10s %15s %15s %15s %15s %15s %15s %15s","     ", "Name", "Initial","Birth","Salary","Code","   ","Number")));
            printer.append('\n');
            printer.append('\n');
            printer.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    //Create the output file
    static void WriteToFile(int acct_number,String lastName, String firstName, int month, int day, int year,
                            float annual_salary,String dept_code, String phone_number,String type) {
        //format the currency
        DecimalFormat moneyFormat = new DecimalFormat("$0,000.00");
        try {

            //  Print the records ordered by last name into the output file
            File file = null;
            if(type =="inorder") {
                // Get the file
                file = new File("report_out_inorder.rpt");

            }

            // Create new format for the report
            //Create new format for the report
            Formatter formatter = new Formatter();
            FileWriter fw = new FileWriter(file, true);
            PrintWriter printer = new PrintWriter(fw);
            //Print all the objects from the node into the output file
            printer.append(String.valueOf(formatter.format("%10s %15s %15s %15s %15s %15s %15s %20s", acct_number, lastName, firstName, getMonth(month,day,year),
                    moneyFormat.format(annual_salary), dept_code,checkAge(nearestYear(year,month,day)), phone_number)));
            printer.append('\n');

            printer.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    //Print into the MASTER FILE
    static void WriteToMaster(int acct_number,String lastName, String firstName, int month, int day, int year,
                              float annual_salary,String dept_code, String phone_number) {

        try {

            //Create error file
            File MasterFile= new File(MasterFileInput);

            //Create new format for the error report
            Formatter formatter = new Formatter();
            FileWriter fw = new FileWriter(MasterFile, true);
            PrintWriter printer = new PrintWriter(fw);


            //Print all the objects from the node into the output file
            printer.append(String.valueOf(formatter.format("%s %s %s %s %s %s %s %s %s ", acct_number, lastName, firstName, month,day,year,
                    annual_salary, dept_code, phone_number)));
            printer.append('\n');

            printer.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    //Print into the error output
    private static void errorOutput(String errorRecord, int countline, int countTotalError, int acct_number) {
        try {
            // Get the file
            File file = new File("error.rpt");
            FileWriter fw = new FileWriter(file, true);
            PrintWriter printer = new PrintWriter(fw);
            //Create new format for the error output
            Formatter formatter = new Formatter();

            if (countTotalError == 0) {
                //Print the record line and th error type
                printer.append(String.valueOf(formatter.format("%5s%d %35s %35s","record " ,countline, errorRecord,acct_number)));
                printer.append('\n');
            }
            else{
                //Print the record line and th error type
                printer.append('\n');
                printer.append(String.valueOf(formatter.format("%5s%d","Total number of error record " ,countTotalError-1)));
            }

            printer.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    //Function countTotal errors
    private static int countLineError() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("error.rpt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines-1;

    }

    //Function countTotal records and print in the output
    private static void countTotalreport(int countline,int linesError,String type) throws IOException {
        try{
            //  Print the records ordered by last name into the output file
            File file = null;
            if(type =="inorder") {
                // Get the file
                file = new File("report_out_inorder.rpt");

            }

            FileWriter fw = new FileWriter(file, true);
            PrintWriter printer = new PrintWriter(fw);
            //Create new format for the report
            Formatter formatter = new Formatter();
            printer.append('\n');
            printer.append(String.valueOf(formatter.format("%10s %d", "Total record read ", countline)));

            formatter = new Formatter();
            printer.append('\n');
            printer.append(String.valueOf(formatter.format("%10s %d", "Total records processed ", countline-linesError)));

            formatter = new Formatter();
            printer.append('\n');
            printer.append(String.valueOf(formatter.format("%10s %d", "Total error records ", linesError)));

            printer.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    // Function to traverse the tree in preorder
    // and check if the given node exists in it
    static boolean CheckAcctExists( Node root, int acct_number)
    {
        if (root == null)
            return false;

        if (root.acct_number == acct_number)
            return true;

        // verify if the node is in the left subtree (recur on left subtree)
        boolean checkleft= CheckAcctExists(root.left,acct_number);

        // node found, no need to look further
        if(checkleft) return true;

        // node is not found in left,
        // so recur on right subtree /
        boolean checkright = CheckAcctExists(root.right, acct_number);

        return checkright;
    }

    //enum class for the menu options
    enum option {
        A,
        D,
        M,
        P,
        S,
        Q
    }
    private static void menu(BST tree, int countline,int lines) throws IOException {
        Scanner scan = new Scanner(System.in);
        // if the employer is still working, the end date is today's date
        LocalDate currentdate = LocalDate.now();
        //get today`s year, month and day
        int currentyear = currentdate.getYear();
        int validInput =0;
        do {
            System.out.println("BST UPDATE SYSTEM");
            System.out.println("A) Add an new record");
            System.out.println("D) Delete an existing record");
            System.out.println("M) Modify a record");
            System.out.println("P) Print a report (in order)");
            System.out.println("S) Save changes");
            System.out.println("Q) Quit system");

            System.out.println("Enter option: ");
            String check = scan.next();
            option choice = option.valueOf(check);//accept user input
            switch (choice) {
                case A:
                    System.out.println("Add an new record: ");
                    System.out.println("Enter the account number: ");
                    int acct_number =scan.nextInt();
                    //check if the account number exist in our binary tree
                    if(CheckAcctExists(tree.root, acct_number)) {
                        System.out.println("No Duplicate Allowed");
                        System.out.println("\n\n");

                    } else {
                        System.out.println("Enter the lastName: ");
                        String lastName = scan.next();
                        //check if the lastname contains digit
                        while(checkName(lastName)!=true){
                            System.out.println("Enter a valid lastName: ");
                            lastName = scan.next();
                        }
                        System.out.println("Enter the firstName: ");
                        String FirstName = scan.next();
                        //check if the Firstname contains digit
                        while(checkName(FirstName)!=true){
                            System.out.println("Enter a valid firstName: ");
                            FirstName = scan.next();
                        }
                        //default parameters for verification the input data
                        int month = 13;
                        int day = 32;
                        int year = currentyear;
                        float annual_salary = 0;
                        String phone_number="";

                       //force loop that contains an invalid birthday
                        while(month>12 || day >checkDaysMonth(month,checkLeapYear(year))|| year> currentyear) {

                            System.out.println("Enter a valid employer birthday mm/dd/yyyy: ");
                            String birthday = scan.next();
                            //alocate the birthday to an array
                            String[] valuesBirthday = birthday.split("/");
                            //check if is a digit
                            if(checkDigit(valuesBirthday[0])!=false && checkDigit(valuesBirthday[1])!=false && checkDigit(valuesBirthday[2])!=false){
                            //check if the data inserted pass the valid test and check if are digits and convert to integer
                            month = Integer.parseInt(valuesBirthday[0]);
                            day = Integer.parseInt(valuesBirthday[1]);
                            year = Integer.parseInt(valuesBirthday[2]);
                            }
                        }

                        //force loop to verify a valid annual salary
                        while(annual_salary ==0){
                            System.out.println("Enter the annual_salary: ");
                        annual_salary = scan.nextFloat();

                        }

                        //force loop to verify the department code
                        System.out.println("Enter the dept_code: ");
                        String dept_codes = scan.next();
                        //check a valid dept code (no digit)
                        while(checkName(dept_codes)!=true) {
                            System.out.println("Enter a valid dept_code: ");
                            dept_codes = scan.next();
                        }
                        //force loop to verify a valid phone number
                        while(checkPhoneNumber(phone_number)!=true){
                        System.out.println("Enter the phone_number: ");
                        phone_number = scan.next();
                        }

                        countline++;
                        //Record the data into the tree
                        if (checkName(lastName) != false && checkName(FirstName) != false && month != 0 && day != 0 && checkAge(year) !=0 && annual_salary != 0
                                && month <= 12 && day <= checkDaysMonth(month, checkLeapYear(year)) && dept_codes != "" && checkPhoneNumber(phone_number)!=false){
                        tree.insert(acct_number, lastName, FirstName, month, day, year, annual_salary, dept_codes, phone_number, countline);}
                        //if contains errors
                        else {System.out.println("Error input data");}
                        System.out.println("\n\n");
                        validInput = 1;
                    }
                    break;
                case D:
                    System.out.println("Enter the account number: ");
                    int acct_number_delete =scan.nextInt();
                    //check if the account number exist in our binary tree
                    if(CheckAcctExists(tree.root, acct_number_delete)){
                        System.out.println("Are you sure? Y/N");
                        String DeleteCheck = scan.next();

                        if (DeleteCheck.equals("Y")){
                            System.out.println(DeleteCheck);
                            tree.deleteAcct(acct_number_delete);
                            System.out.println("Record " + acct_number_delete + " deleted\n\n");
                        }
                    }
                    else
                        System.out.println("Record "+ acct_number_delete+ " is NOT FOUND\n\n");
                    validInput=2;
                    break;
                case M:

                    System.out.println("Enter the account number: ");
                    int acct_number_mod =scan.nextInt();
                    //check if the account number exist in our binary tree
                    if(CheckAcctExists(tree.root, acct_number_mod)){
                        tree.deleteAcct(acct_number_mod);

                        System.out.println("Enter the lastName: ");
                        String newlastName =scan.next();
                        //check if the lastname contains digit
                        while(checkName(newlastName)!=true){
                            System.out.println("Enter a valid lastName: ");
                            newlastName = scan.next();
                        }

                        System.out.println("Enter the firstName: ");
                        String newFirstName =scan.next();
                        //check if the Firstname contains digit
                        while(checkName(newFirstName)!=true){
                            System.out.println("Enter a valid lastName: ");
                            newFirstName = scan.next();
                        }

                        //default parameters for verification the input data
                        int newmonth = 13;
                        int newday = 32;
                        int newyear = currentyear;
                        float newannual_salary = 0;

                        String newphone_number="";

                        //force loop that contains an invalid birthday
                        while(newmonth>12 || newday >checkDaysMonth(newmonth,checkLeapYear(newyear)) || newyear> currentyear) {

                            System.out.println("Enter a valid employer birthday mm/dd/yyyy: ");
                            String newbirthday =scan.next();
                            //alocate the birthday to an array
                            String[] newvaluesBirthday = newbirthday.split("/");

                            //check if is a digit
                            if(checkDigit(newvaluesBirthday[0])!=false && checkDigit(newvaluesBirthday[1])!=false && checkDigit(newvaluesBirthday[2])!=false) {
                                //check if the data inserted pass the valid test and check if are digits and convert to integer
                                newmonth = Integer.parseInt(newvaluesBirthday[0]);
                                newday = Integer.parseInt(newvaluesBirthday[1]);
                                newyear = Integer.parseInt(newvaluesBirthday[2]);
                            }
                        }

                        //force loop to verify a valid annual salary
                        while(newannual_salary  ==0){
                            System.out.println("Enter the annual_salary: ");
                            newannual_salary = scan.nextFloat();
                        }

                        //force loop to verify the department code
                        System.out.println("Enter the dept_code: ");
                        String newdept_codes = scan.next();
                        //check a valid dept code (no digit)
                        while(checkName(newdept_codes)!=true) {
                            System.out.println("Enter a valid dept_code: ");
                            newdept_codes = scan.next();
                        }

                        //force loop to verify a valid phone number
                        while(checkPhoneNumber(newphone_number)!=true){
                            System.out.println("Enter the phone_number: ");
                            newphone_number = scan.next();
                        }

                        if (checkName(newlastName) != false && checkName(newFirstName) != false && newmonth != 0 && newday != 0 && checkAge(newyear) !=0 && newannual_salary != 0
                                && newmonth <= 12 && newday <= checkDaysMonth(newmonth, checkLeapYear(newyear)) && newdept_codes != "" && checkPhoneNumber(newphone_number)!=false){
                        tree.insert(acct_number_mod,newlastName,newFirstName,newmonth,newday,newyear,newannual_salary,newdept_codes,newphone_number,countline);
                        System.out.println("Record "+ acct_number_mod+ " updated\n\n");}
                        else {System.out.println("Error input data\n\n");}
                    }
                    else
                        System.out.println("Record "+ acct_number_mod+ " is NOT FOUND\n\n");

                    validInput=3;
                    break;
                case P:

                    WriteFileTopics("inorder");
                    tree.inorder();
                    countTotalreport(countline,lines-1,"inorder");
                    System.out.println("Report printed\n\n");
                    validInput=4;
                    break;
                case S:

                    //Create error file
                    File MasterFile= new File(MasterFileInput);
                    //if the file exist, erase its content
                    if (MasterFile.exists()) {
                        RandomAccessFile raf = new RandomAccessFile(MasterFile, "rw"); //Open the file for reading and writing
                        raf.setLength(0); //set the length of the character sequence equal to 0
                    }
                    tree.print_to_MasterFIle();
                    int total = countline-lines-1;
                    System.out.println("Changes saved "+total+" records written to MASTER FILE\n");
                    validInput=5;
                    break;
                case Q:
                    System.out.println("Terminate program");
                    validInput=6;
                    System.exit(6);

                default:
                    System.out.println("Incorrect input: Please type a valid input ");
            }
        } while (validInput != 6); //force the user to choose a valid input [1-3]
    }

    public static void main(String[] args) throws IOException {
        BST tree = new BST();

        //count the current line (record the line to the error file)
        int countline=0;
        //count total error (obs. starts with 1 to manipulate the conditional in method errorOutput)

        //Create error file
        File errorFile= new File("error.rpt");
        //if the file exist, erase its content
        if (errorFile.exists()) {
            RandomAccessFile raf = new RandomAccessFile(errorFile, "rw"); //Open the file for reading and writing
            raf.setLength(0); //set the length of the character sequence equal to 0
        }
        FileWriter fw = new FileWriter(errorFile, true);
        PrintWriter printer = new PrintWriter(fw);
        //Create new format for the error report
        Formatter formatter = new Formatter();
        //Print the reference topics for the error report
        printer.append(String.valueOf(formatter.format("%5s %35s %35s", "Error file", "Error Messages", "Account Number")));
        printer.append('\n');
        printer.append('\n');
        printer.close();

        // Open and read the Input file
        File file = new File(MasterFileInput);
        // Scanner all the file
        Scanner scannerFile = new Scanner(file);

        //While until have lines in the file
        while (scannerFile.hasNext()) {

            //Store all the data line by line into variable to populate the node
            //Each variable will be an object in the node
            int acct_number = Integer.parseInt(scannerFile.next());
            String lastName = scannerFile.next();
            String FirstName = scannerFile.next();
            int month = Integer.parseInt(scannerFile.next());
            int day = Integer.parseInt(scannerFile.next());
            int year = Integer.parseInt(scannerFile.next());
            float annual_salary = Float.parseFloat(scannerFile.next());
            String dept_codes = scannerFile.next().toUpperCase();
            String phone_number = scannerFile.next();

            //Count the current line for the error report
            countline++;

            //Check the errors name, invalid date, annual salary = 0, unknown department code
            //if does not contains errors
            if (checkName(lastName) != false && checkName(FirstName) != false && month != 0 && day != 0 && checkAge(year) !=0 && annual_salary != 0
                    && month <= 12 && day <= checkDaysMonth(month, checkLeapYear(year)) && dept_codes != "" && checkPhoneNumber(phone_number)!=false){
                //Record the data into the tree
                tree.insert(acct_number,lastName, FirstName, month, day, year, annual_salary,dept_codes, phone_number,countline);

            }
            //if contains errors
            else {
                //Error in the name
                if (checkName(lastName) == false || checkName(FirstName) == false)
                    errorOutput("<name error  " + lastName + " " + FirstName, countline, 0,acct_number);

                    //Error in the start date (0/0/0)
                else if (month == 0 || day == 0 || year == 0)
                    errorOutput("<start date error  " + month + "/" + day + "/" + year, countline, 0, acct_number);

                else if (checkAge(year) ==0)
                    errorOutput("<error birth date  " + year+"  age: "+checkAge(year), countline, 0, acct_number);

                    //Error in the month >12
                else if (month >= 13)
                    errorOutput("<month error month>12" + month, countline, 0, acct_number);

                    //Error in the amount of days according to each month (e.g. 32 days in Jan)
                else if (day > checkDaysMonth(month, checkLeapYear(year)))
                    errorOutput("<day error  " + day, countline, 0, acct_number);

                    //Error annual salary = 0
                else if (annual_salary == 0)
                    errorOutput("<annual salary error  " + annual_salary, countline, 0, acct_number);

                    //Error department code doesn't exist
                else if (dept_codes == "")
                    errorOutput("<dept code error  " + dept_codes, countline, 0, acct_number);

                    //Error wrong phone number doesn't exist
                else if (checkPhoneNumber(phone_number) == false)
                    errorOutput("<dept code error  " + phone_number, countline, 0, acct_number);

            }
        }

        //Call the function to write the total error in the error report
        int lines =countLineError();
        errorOutput("",0,lines, 0);
        //Call menu function
        menu(tree,countline,lines);
    }
}
