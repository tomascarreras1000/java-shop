import exceptions.APINotWorkingException;
import exceptions.LocalFilesException;
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
            productDAOAPI.checkAPIStatus();
        } catch (APINotWorkingException e) {
            System.out.println("Error: The API isn’t available.\n");
        }

        System.out.println("Verifying local files...");
        ProductDAOLocal productDAOLocal = new ProductDAOLocal();
        try {
            productDAOLocal.check();
        } catch (LocalFilesException e) {
            System.out.println("Error: The products.json file can’t be accessed.\n");
        }


    }
}