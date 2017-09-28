package utilities;

import database.DbHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CsvHandler {

    private DbHandler dbHandler;
    private NameGeneration nameGeneration;
    private DateCalculations dateCalculations = new DateCalculations();
    private List<String> expiredPlates = new ArrayList<String>();

    public void readCsv () {
        dbHandler = new DbHandler();
        nameGeneration = new NameGeneration();
        List<String> distinctOwnerId = new ArrayList<String>();
        try {
            BufferedReader bReader = new BufferedReader(new FileReader("VehiclesData.csv"));
            String line = "";

            try {
                while ((line = bReader.readLine()) != null) {
                    if (line != null)
                    {
                        String[] array = line.split(";+");
                        //System.out.println(array.length);
                        if(distinctOwnerId.isEmpty()) {
                            String rndName = nameGeneration.randomIdentifier();
                            String rndLastName = nameGeneration.randomIdentifier();
                            distinctOwnerId.add(array[1]);
                            dbHandler.insertOwner(array[1], rndName, rndLastName);
                        }
                        else if(!distinctOwnerId.contains(array[1])){
                            String rndName = nameGeneration.randomIdentifier();
                            String rndLastName = nameGeneration.randomIdentifier();
                            distinctOwnerId.add(array[1]);
                            dbHandler.insertOwner(array[1], rndName, rndLastName);
                        }
                        dbHandler.insertVehicle(array[0], array[2], array[1]);
                    }
                }
                if (bReader == null)
                {
                    bReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException nf) {
            System.out.println("File Not Found");

        }
    } //End of Method

    //checks the csv to validate the plate
    public void validatePlateFromCsv(String plateToValidate) {
        String validationResult = "";
        try {
            BufferedReader bReader = new BufferedReader(new FileReader("VehiclesData.csv"));
            String line = "";
            try {
                System.out.println("Validating plate from csv");
                while ((line = bReader.readLine()) != null) {
                    if (line != null)
                    {
                        String[] array = line.split(";+");
                        if(plateToValidate.equals(array[0].toUpperCase()))//checks if exists in csv,validates and exits the loop
                        {
                            System.out.println("Plate was found...wait for validation");
                            String expirationDateTocheck = dateCalculations.calculateExpirationDate(array[2]);
                            validationResult = dateCalculations.compareDates(expirationDateTocheck);
                            System.out.println(validationResult);
                            break;
                        }
                    }//end if
                }
                if(validationResult.equals(""))
                {
                    validationResult = "Plate not found!";
                    System.out.println(validationResult);
                }
                if (bReader == null)
                {
                    bReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException nf) {
            System.out.println("File Not Found");
        }
    } //End of readCsvAsDatabase Method

    public void checkTimeframeExpirationFromCsv(int timeFrame)
    {
        try {
            BufferedReader bReader = new BufferedReader(new FileReader("VehiclesData.csv"));
            String line = "";
            String activationDate = "";
            String expiredPlate = "";
            String insuranceStatus = "";
            try {
                System.out.println("Checking timeframe expiration from csv");
                while ((line = bReader.readLine()) != null) {
                    if (line != null)
                    {
                        String[] array = line.split(";+");
                        activationDate = array[2];
                        expiredPlate = array[0];
                        String expirationDate = dateCalculations.calculateExpirationDate(activationDate);
                        insuranceStatus = dateCalculations.compareDates(timeFrame, expirationDate);
                        if (insuranceStatus.equals("expired")){
                            expiredPlates.add(expiredPlate);
                        }
                    }//end if
                }
                if (bReader == null)
                {
                    bReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException nf) {
            System.out.println("File Not Found");
        }
    }

    public List<String> getList(){
        return expiredPlates;
    }

}//end of class