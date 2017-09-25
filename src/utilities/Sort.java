package utilities;

import java.util.ArrayList;

public class Sort {

    public static void bubbleSort(ArrayList<String> list)
    {
        String temp;
        for (int i = 0; i < list.size(); i++)
        {
            for (int j = 0; j < list.size() - i -1; j++) {
                if (compare(list.get(j), list.get(j+1)) == 1)
                {
                    temp = list.get(j);
                    list.set(j,list.get(j+1) );
                    list.set(j+1, temp);
                }
            }
        }
    }

    public static int compare(String s1, String s2){
        int result = 0;
        for (int i = 0; i < s1.length(); i++){
            int a = s1.charAt(i);
            int b = s2.charAt(i);
            if(a < b){
                result = -1;
                break;
            }
            else if(a > b){
                result = 1;
                break;
            }
        }
        return result;
    }
}
