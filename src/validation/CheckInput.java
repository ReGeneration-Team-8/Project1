package validation;

import java.util.Scanner;
import java.lang.String;

public class CheckInput {

    private Scanner sc = new Scanner(System.in);
    private String str = "";

    public boolean matchPlate() {       //checks if the inserted plate has the right syntax
        str = sc.nextLine().toUpperCase();
        if (str.matches("^[A-Z]{3}\\-[1-9][0-9]{3}$")) {
            System.out.println("You have entered a valid plate number\n");
            return true;
        }else{
            System.out.println("Enter a valid plate number (e.g.: ABC-1234)");
            return false;
        }
    }

    public int matchInt(){               //checks if the user typed an integer
        int returnValue = 0;
        str = sc.nextLine();
        if (str.matches("^[1-6]$")) {
            returnValue = Integer.parseInt(str);
        }
        return returnValue;
    }

    public int matchInt2(){             //checks if the user typed an integer between 1 and 2
        int returnValue = 0;
        str = sc.nextLine();
        if (str.matches("^[1-2]$")) {
            returnValue = Integer.parseInt(str);
        }
        return returnValue;
    }

    public String getStr(){             //getter of String str
        return str;
    }

    public int matchTimeFrame(int timeFrame){        //checks if the user typed a positive timeframe
        if (timeFrame < 0) {
            System.out.println("You have entered a negative number of days");
            timeFrame = -1;
        }
        return timeFrame;
    }


}
