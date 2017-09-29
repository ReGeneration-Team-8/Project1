package model;

public class Owners {       //POJO class with setters and getters

    private int ownerId;
    private String firstName;
    private String lastName;

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(String firstName){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(String lastName){
        return lastName;
    }
}