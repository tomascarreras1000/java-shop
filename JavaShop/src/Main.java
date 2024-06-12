import business.*;
import exceptions.PersistanceException;
import persistance.*;
import presentation.Controller;
import presentation.UI;

public class Main {
    public static void main(String[] args) {

        UI ui = new UI();

        ShopDAO shopDAO = new ShopDAOAPI();
        ProductDAO productDAO = new ProductDAOAPI();

        ui.showArt();
        ui.intro();

        // Check if API is working
        System.out.println("Checking API status...");
        try {
            productDAO.checkStatus();
            shopDAO.checkStatus();

            CartManager cartManager = new CartManager();
            ProductManager productManager = new ProductManager(productDAO);
            ShopManager shopManager = new ShopManager(shopDAO);
            Controller controller = new Controller(ui, productManager, cartManager, shopManager);
            controller.mainMenu();
        } catch (PersistanceException eAPI) {
            System.out.println(eAPI.getMessage());

            // Check if local data can be accessed
            System.out.println("Verifying local files...");
            productDAO = new ProductDAOLocal();
            shopDAO = new ShopDAOLocal();
            try {
                productDAO.checkStatus();
                shopDAO.checkStatus();

                CartManager cartManager = new CartManager();
                ShopManager shopManager = new ShopManager(shopDAO);
                ProductManager productManager = new ProductManager(productDAO);
                Controller controller = new Controller(ui, productManager, cartManager, shopManager);
                controller.mainMenu();

            } catch (PersistanceException eLocal) {
                System.out.println(eLocal.getMessage());
                System.out.println("\nShutting down...");
            }
        }
    }
}