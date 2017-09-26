package application;

import database.DbHandler;
import utilities.CsvHandler;
import utilities.DateCalculations;
import validation.CheckInput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    int choice;
    int choice2;
    boolean isTrue = false;
    private CheckInput check = new CheckInput();
    private DbHandler dbHandler = new DbHandler();
    private CsvHandler csvHandler = new CsvHandler();//we use it on 1 and 4,(the change i made) - //added yesterday *********************************
    private boolean exitStatus = true;

    public void menuPrint(){

        System.out.println("==============================");
        System.out.println("|             MENU           |");
        System.out.println("==============================");
        System.out.println("| Options:                   |");
        System.out.println("| 1. Vehicle Insurance Status|");
        System.out.println("| 2. Insurance Expiration    |");
        System.out.println("| 3. Sorting                 |");
        System.out.println("| 4. Fine Calculation        |");
        System.out.println("| 5. Exit                    |");
        System.out.println("==============================");
    }

    public void menuChoice(){
        Scanner inputNumber = new Scanner(System.in);
        try{
            choice = inputNumber.nextInt();
        }
        catch (InputMismatchException nfe) {
            System.out.println("You have entered a non numeric field value");
        }

        while(choice < 1 || choice > 5){
            System.out.println("Try again, we need a number between 1 and 5");
            choice = check.matchInt();
        }
        switch (choice) {
            case 1:
                do {
                    System.out.println("Enter the plate number");
                    isTrue = check.matchPlate();
                }while(isTrue == false);
                String status = dbHandler.getActivationDate(check.getStr());
                System.out.println(status);
                csvHandler.validatePlateFromCsv(check.getStr());//added yesterday *********************************
                break;
            case 2:
                int timeFrame = 0;
                do{
                    System.out.println("Give a valid timeframe");
                    Scanner inputTimeFrame = new Scanner(System.in);
                    int timeToCheck = 0;
                    try{
                        timeToCheck = inputTimeFrame.nextInt();
                        timeFrame = check.matchTimeFrame(timeToCheck);
                    }
                    catch (InputMismatchException nfe) {
                        System.out.println("You have entered a non numeric field value");
                        timeFrame = -1;
                    }
                }while(timeFrame == -1);
                dbHandler.getActivationDates(timeFrame);
                csvHandler.checkTimeframeExpirationFromCsv(timeFrame);// added yesterday ************************************
                System.out.println("\nThere are " + dbHandler.getList().size() + " uninsured vehicles\n");
                System.out.println("==============================");
                System.out.println("|        Export Format       |");
                System.out.println("==============================");
                System.out.println("| Options:                   |");
                System.out.println("| 1. Console                 |");
                System.out.println("| 2. Csv                     |");
                System.out.println("==============================");

                Scanner inputNumber2 = new Scanner(System.in);
                try{
                    choice2 = inputNumber2.nextInt();
                }
                catch (InputMismatchException nfe) {
                    System.out.println("You have entered a non numeric field value");
                }

                while(choice2 < 1 || choice2 > 2){
                    System.out.println("Try again, we need a number between 1 and 2");
                    choice2 = check.matchInt2();
                }
                if(choice2 == 1){
                    for (String plate:dbHandler.getList()){
                        System.out.println(plate);
                    }
                    dbHandler.getList().clear();
                    // added yesterday *****************************************************************
                    System.out.println("\nNow printing results based on the same timeframe afte checking csv data :\n");
                    for (String plate:csvHandler.getList()){
                        System.out.println(plate);
                    }
                    csvHandler.getList().clear();
                    // end of yesterday's additions *******************************************************
                }else{
                    BufferedWriter br = null;
                    try {
                        br = new BufferedWriter(new FileWriter("UninsuredVehicles.csv"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    StringBuilder sb = new StringBuilder();
                    for (String plate:dbHandler.getList()) {
                        sb.append(plate);
                        sb.append("\n");
                    }

                    try {
                        br.write(sb.toString());
                        br.close();
                        dbHandler.getList().clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    finally {
                        try {
                            br.close(); // close will automatically flush the data
                            dbHandler.getList().clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;

            case 3:
                System.out.println("Plate numbers in alphanumerical order\n");
                dbHandler.getPlate();
                for (String plate:dbHandler.getListOfPlates()){
                    System.out.println(plate);
                }
                dbHandler.getListOfPlates().clear();
                break;
            case 4:
                System.out.println("Give me the default fine number");
                Scanner scFine = new Scanner(System.in);
                double fine = scFine.nextDouble();
                dbHandler.getVehicles(fine);
                /*csvHandler.readCsv();
                System.out.println("Pame gia pitsa");*/
                break;
            case 5:
                System.out.println("You 're done\nExit");
                exitStatus = false;
                break;

            case 6:
                //  CsvHandler csvHandler = new CsvHandler();
                //  csvHandler.readCsv();
                //  System.out.println("Pame gia pitsa");
                break;
        }
    }

    public boolean getExitStatus(){
        return exitStatus;
    }
}