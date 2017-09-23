package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class CsvHandler {

    public void readCsv () {
        try {
            BufferedReader bReader = new BufferedReader(new FileReader("VehiclesData.csv"));
            String line = "";

            try {
                while ((line = bReader.readLine()) != null) {
                    if (line != null)
                    {
                        String[] array = line.split(";");
                        for(int i=0 ; i<3 ; i++)
                        {
                        }
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