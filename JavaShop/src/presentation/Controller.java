package presentation;

import business.*;

import java.util.List;

public class Controller {
    private UI ui;

    private ProductManager productManager;
    private CartManager cartManager;
    private ShopManager shopManager;

    public Controller(UI ui, ProductManager productManager, CartManager cartManager, ShopManager shopManager) {
        this.ui = ui;
        this.productManager = productManager;
        this.cartManager = cartManager;
        this.shopManager = shopManager;
    }

    public void mainMenu() {
        int option;
        do {
            ui.showMainMenu();
            option = ui.askForInteger("\nChoose an option: ");
            try {
                executeMenu(option);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
        } while (option != 6);
    }

    private void executeMenu(int option) throws Exception {
        switch (option) {
            case 1:
                runProductsMenu();
                break;
            case 2:
                runShopsMenu();
                break;
            case 3:
                //optionThree();
                break;
            case 4:
                //optionFour();
                break;
            case 5:
                //optionFive();
                break;
            case 6:
                ui.showMessage("\nWe hope to see you again!");
                break;
            default:
                ui.showMessage("Invalid option. Please enter a valid option!");
                break;
        }
    }

    public void runProductsMenu() throws Exception {
        int option;
        do {
            ui.showProductsMenu();
            option = ui.askForInteger("\nChoose an option: ");
            try {
                executeProductsMenu(option);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
        } while (option != 3);
    }

    public void executeProductsMenu(int option) throws Exception {
        switch (option) {
            case 1:
                ProductsOptionOne();
                break;
            case 2:
                ProductsOptionTwo();
                break;
            case 3:
                break;
            default:
                ui.showMessage("\nInvalid option. Please enter a valid option!");
                break;
        }
    }

    private void ProductsOptionOne() throws Exception {
        String productName = ui.askForString("\nPlease enter the product's name: ");
        String productBrand = ui.askForString("Please enter the product's brand: ");
        double productMaxPrice = ui.askForDouble("Please enter the product's maximum retail price: ");

        ui.showMessage("\nThe system supports the following product categories: ");
        ui.showMessage(" A) General\n" +
                " B) Reduced Taxes\n" +
                " C) Superreduced Taxes");
        String productCategory = ui.askForString("\nPlease pick the product’s category: ");
        productManager.createBaseProduct(productName, productBrand, productCategory, (float) productMaxPrice);
        ui.showMessage("\nThe product \"" + productName + "\" by \"" + productBrand + "\" was added to the system.");
    }

    private void ProductsOptionTwo() throws Exception {
        List<BaseProduct> productList = productManager.getBaseProducts();
        if (productList.isEmpty()) {
            ui.showMessage("There are currently no products available.");
            return;
        }

        boolean continueRemoving = true;
        while (continueRemoving) {
            ui.showMessage("These are the currently available products: \n");
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                ui.showMessage((i + 1) + ". " + product.getDescription());
            }
            int productPosition = ui.askForInteger("\nWhich one would you like to remove? ");

            if (productPosition < 1 || productPosition > productList.size()) {
                ui.showMessage("\nERROR: Choose an existing option.");
            } else {
                Product selectedProduct = productList.get(productPosition - 1);

                if (ui.confirm("\nAre you sure you want to remove " + selectedProduct.getDescription() + "? ")) {
                    // Remove the product from the system
                    productManager.removeBaseProduct(productList.get(productPosition - 1));

                    shopManager.removeBaseProduct(productList.get(productPosition - 1));

                    ui.showMessage("\n" + selectedProduct.getDescription() + " has been withdrawn from sale.");
                    continueRemoving = false;
                } else {
                    continueRemoving = false;
                }
            }
        }
    }


    public void runShopsMenu() {
        int option;
        do {
            ui.showShopsMenu();
            option = ui.askForInteger("\nChoose an option: ");
            try {
                executeShopsMenu(option);
            } catch (Exception e) {
                ui.showMessage(e.getMessage());
            }
        } while (option != 4);
    }

    public void executeShopsMenu(int option) throws Exception{
        switch (option) {
            case 1:
                ShopsOptionOne();
                break;
            case 2:
                ShopsOptionTwo();
                break;
            case 3:
                ShopsOptionThree();
                break;
            case 4:
                break;
            default:
                ui.showMessage("\nInvalid option. Please enter a valid option!");
                break;
        }
    }

    private void ShopsOptionOne() throws Exception {
        String shopName = ui.askForString("\nPlease enter the shop's name: ");
        String shopDescription = ui.askForString("Please enter the shop's description: ");
        int shopFoundationYear = ui.askForInteger("Please enter the shop's founding year: ");

        ui.showMessage("\nThe system supports the following business models: \n");
        ui.showMessage(" \tA) Maximum Benefits\n" +
                " \tB) Loyalty\n" +
                " \tC) Sponsored");
        String shopBusinessModel = ui.askForString("\nPlease pick the shop’s business model: ");
        shopBusinessModel = shopManager.getBusinessModelFromOptions(shopBusinessModel);
        if (shopBusinessModel == null) {
            throw new Exception("\nERROR: Choose a valid business model!");
        }
        else if (shopBusinessModel.equals("Loyalty")) {
            float loyaltyThreshold = (float) ui.askForDouble("\nPlease enter the shop's loyalty threshold: ");
            shopManager.createShop(shopName, shopDescription, shopFoundationYear, loyaltyThreshold);
        }
        else if (shopBusinessModel.equals("Sponsored")) {
            String sponsorBrand = ui.askForString("\nPlease enter the shop's sponsor brand: ");
            shopManager.createShop(shopName, shopDescription, shopFoundationYear, sponsorBrand);
        }
        else {
            shopManager.createShop(shopName, shopDescription, shopFoundationYear);
        }
        ui.showMessage("\n\"" + shopName + "\" is now a part of the elCofre family.");
    }

    private void OptionTwo() throws Exception {
        String shopName = ui.askForString("\nPlease enter the shop's name: ");
        String productName = ui.askForString("Please enter the product's name: ");
        double currentPrice = ui.askForDouble("Please enter the product’s price at this shop: ");

        Product product = productManager.findProductByName(productName);
        if (product == null) {
            throw new ProductNotFoundException(productName);
        }
        String productBrand = product.getProductBrand();

        shopManager.addProductToShop(shopName, product, currentPrice);

        ui.showMessage("\"" + productName + "\" by \""+ productBrand + "\" is now being sold at \"" + shopName + "\".");
    }

    private void addBaseProduct(BaseProduct baseProduct) {

    }

    private void removeBaseProduct(BaseProduct productToRemove) {
        productManager.removeBaseProduct(productToRemove);
        cartManager.removeProduct(productToRemove);
        shopManager.removeBaseProduct(productToRemove);
    }
}
