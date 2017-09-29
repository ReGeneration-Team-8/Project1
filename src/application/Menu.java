package application;

import database.DbHandler;
import model.Vehicles;
import utilities.CsvHandler;
import validation.CheckInput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    int choice;
    int choice2;
    boolean isTrue = false;
    private CheckInput check = new CheckInput();
    private DbHandler dbHandler = new DbHandler();
    private CsvHandler csvHandler = new CsvHandler();
    private boolean exitStatus = true;

    public void menuPrint(){            //prints the main menu

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

        while(choice < 1 || choice > 6){
            System.out.println("Try again, we need a number between 1 and 5");
            choice = check.matchInt();
        }
        switch (choice) {
            case 1:
                System.out.println("Enter the plate number");
                do {
                    isTrue = check.matchPlate();
                }while(isTrue == false);
                String status = dbHandler.getActivationDate(check.getStr());
                System.out.println(status);
                csvHandler.validatePlateFromCsv(check.getStr());
                exitMessage();
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
                csvHandler.checkTimeframeExpirationFromCsv(timeFrame);
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
                    int i=1;
                    System.out.println("\nDatabase results: \n");
                    for (String plate:dbHandler.getList()){
                        System.out.println(i + ". " + plate);
                        i++;
                    }
                    i=1;
                    dbHandler.getList().clear();
                    System.out.println("\nCsv results: \n");
                    for (String plate:csvHandler.getList()){
                        System.out.println(i + ". " + plate);
                        i++;
                    }
                    csvHandler.getList().clear();
                }else{
                    System.out.println("Exporting uninsured vehicles in \"UninsuredVehicles.csv\"\n");
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
                            csvHandler.getList().clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Uninsured vehicles exported in \"UninsuredVehicles.csv\"");
                }
                exitMessage();
                break;

            case 3:
                System.out.println("Plate numbers in alphanumerical order\n");
                dbHandler.getDataFromVehicles();
                System.out.println("Plate Number\tSSN \t\t\tActivationDate\n");
                for(Vehicles objectVehicle:dbHandler.getListOfVehicles()){
                    System.out.println(objectVehicle.getPlate()+ "\t\t" + objectVehicle.getOwnerId() + "\t\t" + objectVehicle.getActivationDate());
                }
                dbHandler.getListOfVehicles().clear();
                exitMessage();
                break;
            case 4:
                double fine = -1;
                System.out.println("Give me the default fine number");
                outer:
                while (fine <0) {
                    Scanner scFine = new Scanner(System.in);

                    try{
                        fine = scFine.nextDouble();
                        if (fine < 0) {
                            System.out.println("Enter a positive value");
                            continue outer;
                        }
                        dbHandler.getVehicles(fine);
                    }catch (InputMismatchException e){
                        System.out.println("Please enter a valid fine (numeric)");
                    }
                }
                exitMessage();
                break;
            case 5:
                System.out.println("You 're done\nExit");
                exitStatus = false;
                break;
            case 6:
                CsvHandler csvHandler = new CsvHandler();
                csvHandler.readCsv();
                System.out.println("Pame gia pitsa");
                break;
        }
    }

    public boolean getExitStatus(){
        return exitStatus;
    }

    private void exitMessage() {
        System.out.println("\nPress any key to return to main menu...");
        Scanner returnMenu = new Scanner(System.in);
        returnMenu.next();
    }

}