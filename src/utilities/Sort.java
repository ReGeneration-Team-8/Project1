package utilities;

import model.Vehicles;
import java.util.List;

public class Sort {

    public static void bubbleSortObjects(List<Vehicles> list) {     // sort arrayList of Objects of type Vehicles
        Vehicles temp;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 1; j < list.size() - i; j++) {
                if (list.get(j - 1).compareTo(list.get(j)) > 0) {
                    temp = list.get(j - 1);
                    list.set(j - 1, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }
}





