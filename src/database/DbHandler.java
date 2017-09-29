package database;

import model.Vehicles;
import utilities.DateCalculations;
import utilities.Sort;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DbHandler {            //manipulates database

    private List<String> expiredPlates = new ArrayList<String>();
    private List<String> plateList = new ArrayList<String>();
    private List<Vehicles> listOfVehicles = new ArrayList<Vehicles>();

    public void insertVehicle(String plate, String activationDate, String ownerId)
    {
        MySqlConnect mySqlConnect = new MySqlConnect();
        String insertVehicleSql = "INSERT  INTO Vehicles (plate, activationDate, ownerId) VALUES (?,?,?) ";

        try {
            mySqlConnect.connect().setAutoCommit(false);
            PreparedStatement insertVehiclePrepared = mySqlConnect.connect().prepareStatement(insertVehicleSql);
            insertVehiclePrepared.setString(1, plate);
            insertVehiclePrepared.setString(2, activationDate);
            insertVehiclePrepared.setString(3, ownerId);
            insertVehiclePrepared.executeUpdate();
            mySqlConnect.connect().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mySqlConnect.disconnect();
    }

    public void insertOwner(String ownerId, String firstName, String lastName)
    {
        MySqlConnect mySqlConnect = new MySqlConnect();
        String insertOwnerSql = "INSERT  INTO Owners (ownerId, firstName, lastName) VALUES (?,?,?) ";

        try {
            mySqlConnect.connect().setAutoCommit(false);
            PreparedStatement insertOwnerPrepared = mySqlConnect.connect().prepareStatement(insertOwnerSql);
            insertOwnerPrepared.setString(1, ownerId);
            insertOwnerPrepared.setString(2, firstName);
            insertOwnerPrepared.setString(3, lastName);
            insertOwnerPrepared.executeUpdate();
            mySqlConnect.connect().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mySqlConnect.disconnect();
    }

    public String getActivationDate(String plate)   //handles the case where the plate was not found in db
    {
        MySqlConnect mySqlConnect = new MySqlConnect();
        String getActivationDateSql = "SELECT activationDate FROM vehicles WHERE plate = ?";
        String activationDate = "";
        String insuranceStatus = "";

        try {
            mySqlConnect.connect().setAutoCommit(false);
            PreparedStatement getActivationDatePrepared = mySqlConnect.connect().prepareStatement(getActivationDateSql);
            getActivationDatePrepared.setString(1, plate);
            ResultSet rs = getActivationDatePrepared.executeQuery();
            if (!rs.isBeforeFirst() ) {//checks if result set is empty - //added yesterday *********************************
                System.out.println("Plate not found in database");
            }
            else
            {
                while(rs.next())
                {
                    activationDate = rs.getString("activationDate");
                }
                DateCalculations dateCalculations = new DateCalculations();
                String expirationDate = dateCalculations.calculateExpirationDate(activationDate);
                insuranceStatus = dateCalculations.compareDates(expirationDate);
                mySqlConnect.connect().commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error - not found";
        }
        mySqlConnect.disconnect();
        return insuranceStatus;
    }

    public String getActivationDates(int timeFrame)    //returns all plates from the database and validates them by timeframe
    {

        MySqlConnect mySqlConnect = new MySqlConnect();
        String getActivationDateSql = "SELECT plate, activationDate FROM vehicles";
        String activationDate = "";
        String insuranceStatus = "";
        String expiredPlate = "";

        try {
            mySqlConnect.connect().setAutoCommit(false);
            Statement stmt = mySqlConnect.connect().createStatement();
            ResultSet rs = stmt.executeQuery(getActivationDateSql);
            DateCalculations dateCalculations = new DateCalculations();
            while(rs.next())
            {
                activationDate = rs.getString("activationDate");
                expiredPlate = rs.getString("plate");
                String expirationDate = dateCalculations.calculateExpirationDate(activationDate);
                insuranceStatus = dateCalculations.compareDates(timeFrame, expirationDate);
                if (insuranceStatus.equals("expired")){
                    expiredPlates.add(expiredPlate);
                }
            }
            mySqlConnect.connect().commit();
            mySqlConnect.disconnect();
            return insuranceStatus;
        } catch (SQLException e) {
            e.printStackTrace();
            return "error - not found";
        }
    }

    public void getDataFromVehicles()   // Get all data from Vehicles table and insert to an arraylist.
                                        // Each row is an object of type Vehicle. We sort the objects by plate
    {
        MySqlConnect mySqlConnect = new MySqlConnect();
        String getPlateSql = "SELECT * FROM vehicles ORDER BY ownerId";
        String plate = "";
        String ownerId = "";
        String date = "";

        try {
            mySqlConnect.connect().setAutoCommit(false);
            Statement stmt = mySqlConnect.connect().createStatement();
            ResultSet rs = stmt.executeQuery(getPlateSql);
            while(rs.next())
            {
                plate = rs.getString("plate");
                ownerId = rs.getString("ownerId");
                date = rs.getString("activationDate");
                Vehicles tempVehicle = new Vehicles();
                tempVehicle.setPlate(plate);
                tempVehicle.setOwnerId(ownerId);
                tempVehicle.setActivationDate(date);
                listOfVehicles.add(tempVehicle);
            }
            Sort sorting = new Sort();
            sorting.bubbleSortObjects(listOfVehicles);
            mySqlConnect.connect().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mySqlConnect.disconnect();
    }

    public void getVehicles(double fine)    //Get all data from Vehicles table and calculates fine for each owner's expired vehicles.
                                            //We just print the results
    {
        MySqlConnect mySqlConnect = new MySqlConnect();
        String getVehiclesSql = "SELECT * FROM vehicles ORDER BY ownerId";
        String plate = "";
        String ownerId = "";
        String activationDate = "";
        String insuranceStatus = "";
        String expiredPlate = "";
        String tempOwnerId = "";
        int counter = 1;

        try {
            mySqlConnect.connect().setAutoCommit(false);
            Statement stmt = mySqlConnect.connect().createStatement();
            ResultSet rs = stmt.executeQuery(getVehiclesSql);
            DateCalculations dateCalculations = new DateCalculations();
            while(rs.next())
            {
                activationDate = rs.getString("activationDate");
                expiredPlate = rs.getString("plate");
                ownerId = rs.getString("ownerId");
                String expirationDate = dateCalculations.calculateExpirationDate(activationDate);
                insuranceStatus = dateCalculations.compareDates(expirationDate);
                if (insuranceStatus.equals("Your insurance has been expired today at 12:00") ||
                        insuranceStatus.equals("Your insurance has been expired")){
                    if(tempOwnerId.equals(""))
                    {tempOwnerId = ownerId;}
                    else if(tempOwnerId.equals(ownerId)) {
                        counter++;
                    }
                    else
                    {
                        System.out.println("The owner with SSN: "+ tempOwnerId + " has to pay " + String.format( "%.2f", counter*fine ) +
                                " bitcoins for his uninsured vehicles");
                        tempOwnerId = ownerId;
                        counter = 1;
                    }
                }
            }
            mySqlConnect.connect().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mySqlConnect.disconnect();
    }


    public List<String> getList(){
        return expiredPlates;
    }
    public List<Vehicles> getListOfVehicles(){return listOfVehicles;}


}