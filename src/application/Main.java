package application;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();                 //creates an object of type menu
        while(menu.getExitStatus() == true) {   //create a loop of menus
            menu.menuPrint();
            menu.menuChoice();
        }
    }
}
