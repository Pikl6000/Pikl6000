package code;

/*
    Program made by Pikl
    Java basic login system with connection to mySQL database
    Date : 1.7.2021
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class main extends Time{
    //Scanner
    static Scanner sc = new Scanner(System.in);

    //Colors in console
    public static final String ANSI_RESET  = "\u001B[0m";
    public static final String ANSI_BLACK  = "\u001B[30m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE   = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN   = "\u001B[36m";
    public static final String ANSI_WHITE  = "\u001B[37m";
    public static final String ANSI_BRIGHT_BLACK  = "\u001B[90m";
    public static final String ANSI_BRIGHT_RED    = "\u001B[91m";
    public static final String ANSI_BRIGHT_GREEN  = "\u001B[92m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";
    public static final String ANSI_BRIGHT_BLUE   = "\u001B[94m";
    public static final String ANSI_BRIGHT_PURPLE = "\u001B[95m";
    public static final String ANSI_BRIGHT_CYAN   = "\u001B[96m";
    public static final String ANSI_BRIGHT_WHITE  = "\u001B[97m";

    public static Connection connect(String systemTime,String localTime,BufferedWriter mainWriter) {
        //Creating connection to server
        String con = "jdbc:mysql://localhost:3306/java-login";
        String username = "admin";
        String password = "admin";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(con, username, password);
            System.out.println(ANSI_BRIGHT_GREEN + "Connected to database" + ANSI_RESET);
            logDataSuccess(systemTime,localTime,mainWriter);
        } catch (SQLException throwables) {
            System.out.println(ANSI_BRIGHT_RED + "Opppsss, There was an error connecting to database !" + ANSI_RESET);
            logDataFail(systemTime,localTime,mainWriter);
        }
        return conn;
    }
    //Log - program starts
    public static void logStart(String systemTime,String localTime,BufferedWriter mainWriter){
        try {
            mainWriter.write("=".repeat(80));
            mainWriter.newLine();
            mainWriter.write("Program started at : LT ("+localTime+") ST ("+systemTime+");");
            mainWriter.newLine();
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "Opppsss, There was an error with writing to main log file" + ANSI_RESET);
        }
    }
    //log - program ends
    public static void logEnd(String systemTime,String localTime,BufferedWriter mainWriter){
        try {
            mainWriter.write("Program ended at : LT ("+localTime+") ST ("+systemTime+");");
            mainWriter.newLine();
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "Opppsss, There was an error with writing to main log file" + ANSI_RESET);
        }
    }
    //log - connection to database successful
    public static void logDataSuccess(String systemTime,String localTime,BufferedWriter mainWriter){
        try {
            mainWriter.write("\tConnection to database - Connected (Successful) : LT ("+localTime+") ST ("+systemTime+");");
            mainWriter.newLine();
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "Opppsss, There was an error with writing to main log file" + ANSI_RESET);
        }
    }
    //log - connection to database failed
    public static void logDataFail(String systemTime,String localTime,BufferedWriter mainWriter){
        try {
            mainWriter.write("\tConnection to database - NOT connected (Fail) : LT ("+localTime+") ST ("+systemTime+");");
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "Opppsss, There was an error with writing to main log file" + ANSI_RESET);
        }
    }

    //log - logged in successfully
    public static void loggedSuccessful(String systemTime,String localTime,BufferedWriter loginWriter,BufferedWriter mainWriter,String name){
        try {
            loginWriter.write("User logged in as '"+name+"' (Successful) at : LT ("+localTime+") ST ("+systemTime+");");
            loginWriter.newLine();
            mainWriter.write("\t'"+name+"' logged in (Successful) at : LT ("+localTime+") ST ("+systemTime+");");
            mainWriter.newLine();
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "Opppsss, There was an error with writing to login log file" + ANSI_RESET);
        }
    }
    //log - logged in failed
    public static void loggedFailed(String systemTime,String localTime,BufferedWriter loginWriter,String name){
        try {
            loginWriter.write("User TRIED (Failed) to login to user '"+name+"' at : LT ("+localTime+") ST ("+systemTime+");");
            loginWriter.newLine();
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "Opppsss, There was an error with writing to login log file" + ANSI_RESET);
        }
    }
    //log - logged out
    public static void logout(String systemTime,String localTime,BufferedWriter loginWriter,BufferedWriter mainWriter,String name){
        try {
            loginWriter.write("'\"+name+\"' logged out (Successful) at : LT ("+localTime+") ST ("+systemTime+");");
            loginWriter.newLine();
            mainWriter.write("\t'"+name+"' logged out (Successful) at : LT ("+localTime+") ST ("+systemTime+");");
            mainWriter.newLine();
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "Opppsss, There was an error with writing to login log file" + ANSI_RESET);
        }
    }


    //Methods to get input from user
    public static int getInputNotRegistered(){
        String input = null;
        System.out.println("1 - Login\n2 - Register\n3 - Close program");
        //Getting input from user
        do {
            System.out.print("Enter your choice : ");
            input = sc.nextLine().replaceAll(" +","");
            if (nieJeCislo(input) || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 3) {
                System.out.println(ANSI_BRIGHT_RED+"Please enter number OR enter number from range (1-3) !"+ANSI_RESET);
            }
        }while (nieJeCislo(input) || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 3);
        return Integer.parseInt(input);
    }
    public static int getInputRegistered(){
        String input = null;
        System.out.println("1 - Login\n2 - Register\n3 - Close program\n4 - Update information\n5 - Log out");
        //Getting input from user
        do {
            System.out.print("Enter your choice : ");
            input = sc.nextLine().replaceAll(" +","");
            if (nieJeCislo(input) || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 5) {
                System.out.println(ANSI_BRIGHT_RED+"Please enter number OR enter number from range (1-5) !"+ANSI_RESET);
            }
        }while (nieJeCislo(input) || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 5);
        return Integer.parseInt(input);
    }

    //Method that check if String is Integer
    public static boolean nieJeCislo(String input) {
        if (input == null) return true;
        try {
            int d = Integer.parseInt ( input );
        } catch (NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException, SQLException {
        //Class for getting time
        Time time = new Time();

        //Creating Bufferwriters
        BufferedWriter mainWriter = new BufferedWriter(new FileWriter("src/logs/main-log.txt",true));
        BufferedWriter loginWriter = new BufferedWriter(new FileWriter("src/logs/login-log.txt",true));

        //Check if log files exist - if not , create them
        File mainLog = new File("src/logs/main-log.txt");
        File loginLog = new File("src/logs/login-log.txt");

        if (!mainLog.exists()){
            mainLog.createNewFile();
        }
        if (!loginLog.exists()){
            loginLog.createNewFile();
        }

        //Writing startup log
        logStart(time.getSystemTime(), time.getLocalTime(),mainWriter);

        //Creating connection to database
        Connection conn = connect(time.getSystemTime(), time.getLocalTime(),mainWriter);

        boolean p = true;

        String input = "";
        boolean logged = false;
        while (p){
            System.out.println("-".repeat(45));
            System.out.println(ANSI_BRIGHT_BLUE+"Welcome, please login / register"+ANSI_RESET+"\n");

            if (logged) {
                input = String.valueOf(getInputRegistered());
            }
            else {
                input = String.valueOf(getInputNotRegistered());
            }

            System.out.println();
            //switch with options
            switch (Integer.parseInt(input)){
                case 1 :{//option for login
                    String username = "",pass = "";
                    System.out.println("-".repeat(30));
                    System.out.println(ANSI_BRIGHT_PURPLE+"Login"+ANSI_RESET);

                    ResultSet result = null;
                    //Getting username and password from user
                    do {
                        System.out.print("\tUsername : ");
                        username = sc.nextLine().trim();

                        if (username.isEmpty()) {
                            System.out.println(ANSI_BRIGHT_RED + "\tEnter something" + ANSI_RESET);
                        }

                        //Finding if username exist
                        String statement = "SELECT * FROM users WHERE userName = '"+username+"'";
                        PreparedStatement sql = conn.prepareStatement(statement);
                        result = sql.executeQuery();
                    }while (username.isEmpty());
                    //Entering password
                    do {
                        System.out.print("\tPassword : ");
                        pass = sc.nextLine().trim();
                        if (pass.isEmpty()){
                            System.out.println(ANSI_BRIGHT_RED + "\tEnter something" + ANSI_RESET);
                        }
                        //Finding if password match
                        while (result.next()){
                            if (result.getString("password").equals(pass)){
                                logged = true;
                            }
                        }
                    }while (pass.isEmpty());

                    if (logged){//user logged in , write info that he logged in
                        System.out.println(ANSI_BRIGHT_CYAN+"Logged in !"+ANSI_RESET);
                        loggedSuccessful(time.getSystemTime(),time.getLocalTime(),loginWriter,mainWriter,username);
                    }
                    else {//user haven't logged in, write info that
                        System.out.println(ANSI_BRIGHT_RED+"Wrong password OR username! Try again"+ANSI_RESET);
                        loggedFailed(time.getSystemTime(),time.getLocalTime(),loginWriter,username);
                    }
                    break;
                }
                case 2 :{//option for registering
                    //in progress
                }
                case 3 :{//option for closing the program
                    System.out.println(ANSI_BRIGHT_CYAN+"Thanks for using our program !"+ANSI_RESET);
                    logEnd(time.getSystemTime(), time.getLocalTime(),mainWriter);
                    p = false;
                    break;
                }
                case 4 :{

                }
                case 5 :{
                    logged = false;
                    System.out.println(ANSI_BRIGHT_PURPLE+"Logged out !"+ANSI_RESET);
                }
            }
        }
        //Closing Bufferwriters
        mainWriter.close();
        loginWriter.close();
    }
}
