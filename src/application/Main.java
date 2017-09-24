package application;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        while(menu.getExitStatus() == true) {
            menu.menuPrint();
            menu.menuChoice();
        }
    }
}
