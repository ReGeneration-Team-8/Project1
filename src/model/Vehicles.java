package model;

public class Vehicles implements Comparable{

    private String plate;
    private String activationDate;
    private String ownerId;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    private int compare(String s1, String s2){          //compare 2 strings character by character
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

    @Override
    public int compareTo(Object o) {        //we implement compareTo from interface Comparable
        int result = 0;
        try {
            Vehicles v = (Vehicles) o;
            result = compare(this.plate, v.getPlate());
        }catch (ClassCastException e)
        {e.printStackTrace();}
        return result;
    }
}
