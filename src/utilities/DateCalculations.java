package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DateCalculations {

    public String calculateExpirationDate(String activationDate)        //calculates the expiration date
    {
        String expirationDate = "";
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");   //creates a format for date
        Date date = null;

        try {
            date = dateFormat.parse(activationDate);          //transform date to the desirable format
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        expirationDate += now.get(Calendar.DATE) + "/";
        int month = now.get(Calendar.MONTH);

        if (month > 4) {                                    //for months after May the year of expiration date has to be changed
            switch (month) {
                case 5:                                     //June -> December
                    now.set(Calendar.MONTH, 11);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 6:                                     //July -> January
                    now.add(Calendar.YEAR, 1);
                    now.set(Calendar.MONTH, 0);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 7:                                     //August -> February , checks if February of the new year has one more day
                    now.add(Calendar.YEAR, 1);
                    Calendar febrCal = Calendar.getInstance();
                    Date dateForFebExc = new Date();
                    febrCal.setTime(dateForFebExc);
                    febrCal.set(Calendar.DAY_OF_MONTH, 27);
                    febrCal.set(Calendar.MONTH, 1);
                    int maxNumOfDays = febrCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (maxNumOfDays < now.get(Calendar.DAY_OF_MONTH)) //
                    {
                        expirationDate = "";
                        now.set(Calendar.DAY_OF_MONTH, 1);
                        expirationDate += now.get(Calendar.DAY_OF_MONTH) + "/";
                        now.set(Calendar.MONTH, 2);
                        expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    }
                    else
                    {
                        now.set(Calendar.MONTH, 1);
                        expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    }
                    break;
                case 8:
                    now.add(Calendar.YEAR, 1);
                    now.set(Calendar.MONTH, 2);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 9:
                    now.add(Calendar.YEAR, 1);
                    now.set(Calendar.MONTH, 3);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 10:
                    now.add(Calendar.YEAR, 1);
                    now.set(Calendar.MONTH, 4);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 11:
                    now.add(Calendar.YEAR, 1);
                    now.set(Calendar.MONTH, 5);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
            }
        } else {                                            //from January to May
            now.add(Calendar.MONTH, 7);
            expirationDate += now.get(Calendar.MONTH) + "/";
        }
        expirationDate += now.get(Calendar.YEAR);
        //System.out.println("Activation date is : " + activationDate + " and Expiration date is : " + expirationDate);
        return expirationDate;
    }

    public String compareDates(String expirationDate) {     //compares expirationDate with current date
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        Date currentDate = new Date();
        Date dateToCheck = null;

        try {
            dateFormat.format(currentDate);
            dateToCheck = dateFormat.parse(expirationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar now = Calendar.getInstance();
        now.setTime(currentDate);
        Calendar expr = Calendar.getInstance();
        expr.setTime(dateToCheck);
        String insuranceStatus = "";

        if (!expr.before(now)) {
            insuranceStatus = "Your insurance is active";
        } else if (expr.equals(now)) {
            String timeStamp = new SimpleDateFormat("H").format(Calendar.getInstance().getTime());
            System.out.println(timeStamp);
            int hours = Integer.parseInt(timeStamp);
            if (hours > 12) {
                insuranceStatus = "Your insurance has been expired today at 12:00";
            }
        } else {
            insuranceStatus = "Your insurance has been expired";
        }
        return insuranceStatus;
    }


    public String compareDates(int timeFrame, String expirationDate) {  //overloading compareDates because an extra argument is needed
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        Date currentDate = new Date();
        Date dateToCheck = null;

        try {
            dateFormat.format(currentDate);
            dateToCheck = dateFormat.parse(expirationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar now = Calendar.getInstance();
        now.setTime(currentDate);
        now.add(Calendar.DATE, timeFrame);
        Calendar expr = Calendar.getInstance();
        expr.setTime(dateToCheck);
        String insuranceStatus = "";

        if (!expr.before(now)) {
            insuranceStatus = "active";
        } else if (expr.equals(now)) {
            String timeStamp = new SimpleDateFormat("H").format(Calendar.getInstance().getTime());
            System.out.println(timeStamp);
            int hours = Integer.parseInt(timeStamp);
            if (hours > 12) {
                insuranceStatus = "expired";
            }
        } else {
            insuranceStatus = "expired";
        }
        return insuranceStatus;
    }

    public boolean chechIfDateExists(String date){      //checks if date is a valid date (days)
        boolean returnValue = true;
        String[] dateFragments = date.split("/");
        //System.out.println(dateFragments.length);
        if(dateFragments[1].equals("4")||dateFragments[1].equals("04") || dateFragments[1].equals("6")
                ||dateFragments[1].equals("06")||dateFragments[1].equals("9")
                || dateFragments[1].equals("09")||dateFragments[1].equals("11")){
            if(Integer.parseInt(dateFragments[0]) > 30){
                returnValue = false;
            }
        }
        else if(dateFragments[1].equals("2")){
            int disekto = Integer.parseInt(dateFragments[2]) % 4;
            if(disekto == 0 && Integer.parseInt(dateFragments[0]) > 29){
                returnValue = false;
            }else if (Integer.parseInt(dateFragments[0]) > 28){
                returnValue = false;
            }
        }
        return returnValue;
    }
}

