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
                        System.out.println(array.length);
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
}