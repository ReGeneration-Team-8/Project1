package application;

import utilities.CsvHandler;
import validation.CheckInput;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    int choice;
    int choice2;
    boolean isTrue = false;
    private CheckInput check = new CheckInput();

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
                break;
            case 2:
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
                break;
            case 3:
                System.out.println("3");
                break;
            case 4:
                System.out.println("4");
                break;
            case 5:
                CsvHandler csvHandler = new CsvHandler();
                csvHandler.readCsv();
                System.out.println("Pame gia pitsa");
        }
    }

}
