import exceptions.APINotWorkingException;
import exceptions.LocalFilesException;
import exceptions.PersistanceException;
import persistance.ProductDAO;
import persistance.ProductDAOAPI;
import persistance.ProductDAOLocal;
import presentation.Controller;
import presentation.UI;

public class Main {
    public static void main(String[] args) {

        UI ui = new UI();
        Controller controller = new Controller(ui);

        ui.showArt();
        ui.intro();

        System.out.println("Checking API status...\n");
        ProductDAOAPI productDAOAPI = new ProductDAOAPI();
        try {
            productDAOAPI.checkStatus();
        } catch (APINotWorkingException e) {
            System.out.println(e.getMessage());
        }


        // Check if local data can be accessed
        System.out.println("Verifying local files...");
        ProductDAOLocal productDAOLocal = new ProductDAOLocal();
        try {
            productDAOLocal.checkStatus();
        } catch (PersistanceException e) {
            System.out.println(e.getMessage());
        }
    }
}