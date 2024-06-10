package presentation;

public class Controller {
    private UI ui;
    public Controller(UI ui) {
        this.ui = ui;
    }

    public void mainMenu() {
        int option;
        do {
            ui.showMenu();
            option = ui.askForInteger("\nChoose an option: ");
            //executeMainMenu(option);
        } while (option != 6);
    }
}
