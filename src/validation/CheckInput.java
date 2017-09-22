package validation;

import java.util.Scanner;
import java.lang.String;

public class CheckInput {

    private Scanner sc = new Scanner(System.in);
    private String str = "";

    public boolean matchPlate() {
        str = sc.nextLine().toUpperCase();
        if (str.matches("^[A-Z]{3}\\-[1-9][0-9]{3}$")) {
            System.out.println("you have entered a valid plate number");
            return true;
        }else{
            return false;
        }
    }

    public int matchInt(){
        int returnValue = 0;
        str = sc.nextLine();
        if (str.matches("^[1-5]$")) {
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
}
