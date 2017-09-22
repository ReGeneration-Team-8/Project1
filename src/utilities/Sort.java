package utilities;

public class Sort {

    public String[] sortPlates(String[] plates)
    {
        int checkNum = 0;

        outer:
        for(int i = 0; i<plates.length; i++)
        {
            for(int j=i+1; j<plates.length; j++)
            {
                checkNum = plates[i].compareTo(plates[j]);
                if(checkNum > 0)
                {
                    String tempPlate = plates[i];
                    plates[i] = plates[j];
                    plates[j] = tempPlate;
                }
                else
                    continue outer;
            }
        }
        for(int i=0; i<plates.length; i++)
        {
            System.out.println(plates[i]);
        }
        return plates;
    }// ens sort plates

}

