package database;

//import validation.CheckInput;
//import ErrorHandling.Validator;
import utilities.DateCalculations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbHandler {

    // queries for owners table
    /* insert statement using prepared statements */
    public void insertOwner(String fname, String lname, String ownerId, String phone, String address)
    {
        MySqlConnect mySqlConnect = new MySqlConnect();
        String insertOwnerSql = "INSERT  INTO owners (ownerId, firstName, lastName, phone, address) VALUES (?,?,?,?,?) ";
        try {
            mySqlConnect.connect().setAutoCommit(false);
            PreparedStatement insertOwnerPrepared = mySqlConnect.connect().prepareStatement(insertOwnerSql);
            insertOwnerPrepared.setString(1, ownerId);
            insertOwnerPrepared.setString(2, fname);
            insertOwnerPrepared.setString(3, lname);
            insertOwnerPrepared.setString(4, phone);
            insertOwnerPrepared.setString(5, address);
            insertOwnerPrepared.executeUpdate();
            mySqlConnect.connect().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getActivationDate(String plate)
    {
        MySqlConnect mySqlConnect = new MySqlConnect();
        String getActivationDateSql = "SELECT activationDate FROM insurance WHERE plate = ?";
        String activationDate = "";
        String insuranceStatus = "";
        try {
            mySqlConnect.connect().setAutoCommit(false);
            PreparedStatement getActivationDatePrepared = mySqlConnect.connect().prepareStatement(getActivationDateSql);
            getActivationDatePrepared.setString(1, plate);
            ResultSet rs = getActivationDatePrepared.executeQuery();
            while(rs.next())
            {
                activationDate = rs.getString("activationDate");
            }
            DateCalculations dateCalculations = new DateCalculations();
            String expirationDate = dateCalculations.calculateExpirationDate(activationDate);
            insuranceStatus = dateCalculations.compareDates(expirationDate);
            mySqlConnect.connect().commit();
            return insuranceStatus;
        } catch (SQLException e) {
            e.printStackTrace();
            return "error - not found";
        }

    }

}
