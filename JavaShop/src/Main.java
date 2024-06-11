import business.BaseProduct;
import business.CartManager;
import business.ProductManager;
import business.ShopManager;
import exceptions.APINotWorkingException;
import exceptions.LocalFilesException;
import exceptions.PersistanceException;
import persistance.*;
import presentation.Controller;
import presentation.UI;

public class Main {
    public static void main(String[] args) {

        UI ui = new UI();
        ProductManager productManager = new ProductManager();
        CartManager cartManager = new CartManager();
        ShopManager shopManager = new ShopManager();
        Controller controller = new Controller(ui, productManager, cartManager, shopManager);

        //TODO: check the interface casts
        ShopDAO shopDAO = new ShopDAOAPI();
        ProductDAO productDAO = new ProductDAOAPI();

        ui.showArt();
        ui.intro();

        // Check if API is working
        System.out.println("Checking API status...\n");
        try {
            productDAO.checkStatus();
            shopDAO.checkStatus();
            //TODO: Implement the rest of the code, add DAOS to managers
        } catch (PersistanceException eAPI) {
            System.out.println(eAPI.getMessage());

            // Check if local data can be accessed
            System.out.println("Verifying local files...");
            productDAO = new ProductDAOLocal();
            shopDAO = new ShopDAOLocal();
            try {
                productDAO.checkStatus();
                shopDAO.checkStatus();
                //TODO: Implement the rest of the code, add DAOS to managers
            } catch (PersistanceException eLocal) {
                System.out.println(eLocal.getMessage());
                System.out.println("Shutting down...");
            }
        }
    }
}