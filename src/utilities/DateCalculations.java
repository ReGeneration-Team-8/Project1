package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCalculations {

    public String calculateExpirationDate(String activationDate) // months are indexed from 0, not 1 - activationDate must be of value πχ 4/6/2017
    {
        String expirationDate = "";
        DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(activationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        expirationDate += now.get(Calendar.DATE) + "/";
        now.setTime(date);
        int month = now.get(Calendar.MONTH);
        if (month > 4) {
            now.add(Calendar.YEAR, 1);
            switch (month) {
                case 5:
                    now.set(Calendar.MONTH, 11);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 6:
                    now.set(Calendar.MONTH, 0);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 7:
                    Calendar febrCal = Calendar.getInstance();
                    Date dateForFebExc = new Date();
                    febrCal.setTime(dateForFebExc);
                    febrCal.set(Calendar.DATE, 27);
                    febrCal.set(Calendar.MONTH, 1);
                    int maxNumOfDays = febrCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (maxNumOfDays < now.get(Calendar.DATE)) // τσεκάρει αν ο φεβρουάριος του έτους έχει λιγότερες μέρες και το κάνει 1 Μαρτίου
                    {
                        expirationDate = "";
                        now.set(Calendar.DATE, 1);
                        expirationDate += now.get(Calendar.DATE) + "/";
                        now.set(Calendar.MONTH, 2);
                        expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    }
                    now.set(Calendar.MONTH, 1);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 8:
                    now.set(Calendar.MONTH, 2);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 9:
                    now.set(Calendar.MONTH, 3);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 10:
                    now.set(Calendar.MONTH, 4);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
                case 11:
                    now.set(Calendar.MONTH, 5);
                    expirationDate += (now.get(Calendar.MONTH) + 1) + "/";
                    break;
            }
        } else {
            now.add(Calendar.MONTH, 7);
            expirationDate += now.get(Calendar.MONTH) + "/";
        }
        expirationDate += now.get(Calendar.YEAR);
       // System.out.println("Activation date is : " + activationDate + " and Expiration date is : " + expirationDate);
        return expirationDate;
    }

    public String compareDates(String expirationDate) {
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


    public String compareDates(int timeFrame, String expirationDate) {
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
}

