package validation;

import java.util.Scanner;
import java.lang.String;

public class CheckInput {

    private Scanner sc = new Scanner(System.in);
    private String str = "";

    public boolean matchPlate() {
        str = sc.nextLine().toUpperCase();
        if (str.matches("^[A-Z]{3}\\-[1-9][0-9]{3}$")) {
            System.out.println("You have entered a valid plate number\n");
            return true;
        }else{
            return false;
        }
    }

    public int matchInt(){
        int returnValue = 0;
        str = sc.nextLine();
        if (str.matches("^[1-6]$")) {
            returnValue = Integer.parseInt(str);
        }
        return returnValue;
    }

    public int matchInt2(){
        int returnValue = 0;
        str = sc.nextLine();
        if (str.matches("^[1-2]$")) {
            returnValue = Integer.parseInt(str);
        }
        return returnValue;
    }

    public String getStr(){
        return str;
    }

    public int matchTimeFrame(int timeFrame){
        if (timeFrame < 0) {
            System.out.println("Give number of days (positive)");
            timeFrame = -1;
        }
        return timeFrame;
    }


}
