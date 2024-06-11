package presentation;

import business.*;
import exceptions.BusinessException;
import exceptions.InvalidRetailPriceException;
import exceptions.PersistanceException;
import exceptions.ProductNotFoundException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    private final UI ui;

    private final ProductManager productManager;
    private final CartManager cartManager;
    private final ShopManager shopManager;

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
                runSearchMenu();
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
            } catch (Exception | PersistanceException e) {
                ui.showMessage(e.getMessage());
            }
        } while (option != 3);
    }

    public void executeProductsMenu(int option) throws Exception, PersistanceException {
        switch (option) {
            case 1:
                productsOptionOne();
                break;
            case 2:
                productsOptionTwo();
                break;
            case 3:
                break;
            default:
                ui.showMessage("\nInvalid option. Please enter a valid option!");
                break;
        }
    }

    private void productsOptionOne() throws PersistanceException, Exception {
        String productName = ui.askForString("\nPlease enter the product's name: ");
        String productBrand = ui.askForString("Please enter the product's brand: ");
        float productMaxPrice = ui.askForFloat("Please enter the product's maximum retail price: ");

        ui.showMessage("\nThe system supports the following product categories: ");
        ui.showMessage("""
                 A) General
                 B) Reduced Taxes
                 C) Superreduced Taxes\
                """);
        String productCategory = ui.askForString("\nPlease pick the product’s category: ");
        productManager.createBaseProduct(productName, productBrand, productCategory, (float) productMaxPrice);
        ui.showMessage("\nThe product \"" + productName + "\" by \"" + productBrand + "\" was added to the system.");
    }

    private void productsOptionTwo() throws PersistanceException, Exception {
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
            } catch (Exception | BusinessException | PersistanceException e) {
                ui.showMessage(e.getMessage());
            }
        } while (option != 4);
    }

    public void executeShopsMenu(int option) throws BusinessException, PersistanceException, Exception {
        switch (option) {
            case 1:
                // New shop menu
                shopsOptionOne();
                break;
            case 2:
                // Expand a shop's catalogue
                shopsOptionTwo();
                break;
            case 3:
                // Reduce a shop's catalogue
                shopsOptionThree();
                break;
            case 4:
                break;
            default:
                ui.showMessage("\nInvalid option. Please enter a valid option!");
                break;
        }
    }

    /**
     * New shop menu
     * @throws Exception is an invalid business model is entered
     */
    private void shopsOptionOne() throws PersistanceException, Exception {
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
            float loyaltyThreshold = ui.askForFloat("\nPlease enter the shop's loyalty threshold: ");
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

    /**
     * Catalogue expansion menu
     * @throws Exception
     */
    private void shopsOptionTwo() throws PersistanceException, BusinessException, FileNotFoundException {
        String shopName = ui.askForString("\nPlease enter the shop's name: ");
        String productName = ui.askForString("Please enter the product's name: ");
        float currentPrice = ui.askForFloat("Please enter the product’s price at this shop: ");

        BaseProduct product = productManager.findProductByName(productName);
        if (product == null) {
            throw new ProductNotFoundException(product.getName());
        }
        String productBrand = product.getBrand();

        shopManager.addProductToShop(shopName, productManager.createRetailProductFromBaseProduct(product, currentPrice));

        ui.showMessage("\"" + productName + "\" by \""+ productBrand + "\" is now being sold at \"" + shopName + "\".");
    }

    private void shopsOptionThree() throws PersistanceException, Exception {
        String shopName = ui.askForString("Please enter the shop’s name: ");
        Shop shop = shopManager.findShopByName(shopName);
        LinkedList<RetailProduct> catalog = shop.getCatalogue();

        if (catalog.isEmpty()) {
            ui.showMessage("This shop currently has no products in its catalog.");
            return;
        }

        ui.showMessage("This shop sells the following products: \n");
        for (int i = 0; i < catalog.size(); i++) {
            Product product = catalog.get(i);
            ui.showMessage((i + 1) + ". " + product.getDescription());
        }

        int productPosition = ui.askForInteger("\nWhich one would you like to remove? ");

        if (productPosition < 1 || productPosition > catalog.size()) {
            ui.showMessage("\nERROR: Choose an existing option.");
        } else {
            RetailProduct selectedProduct = catalog.get(productPosition - 1);
            String productName = selectedProduct.getName();
            String productBrand = selectedProduct.getBrand();

            shopManager.removeRetailProductFromShop(shop, selectedProduct);
            ui.showMessage("\"" + productName + "\" by \"" + productBrand + "\" is no longer being sold at \"" + shopName + "\".");

        }
    }
    private void runSearchMenu() {
//        String query = ui.askForString("Enter your query: ");
//
//        List<Product> products = productController.searchProducts(query);
//
//        if (products.isEmpty()) {
//            ui.showMessage("No products found matching the query.");
//            return;
//        }
//
//        ui.showMessage("The following products were found:");
//        int index = 1;
//        for (Product product : products) {
//            ui.showMessage(index + ") \"" + product.getProductName() + "\" by \"" + product.getProductBrand() + "\"");
//
//            ArrayList<Shop> sellingShops = shopController.findShopsSellingProduct(product);
//            if (sellingShops.isEmpty()) {
//                ui.showMessage("This product is not currently being sold in any shops.");
//            } else {
//                for (Shop sellingShop : sellingShops) {
//                    ui.showMessage("Sold at:");
//                    double price = sellingShop.getProductPrice(product);
//                    ui.showMessage("- " + sellingShop.getShopName() + ": " + price);
//                }
//            }
//            index++;
//        }
//        ui.showMessage(index + ") Back");
//
//        int choice = ui.askForInteger("Which product would you like to review? ");
//        if (choice == index) {
//            return;
//        }
//        if (choice < 1 || choice > products.size()) {
//            ui.showMessage("Invalid choice. Please enter a valid option.");
//            return;
//        }
//
//        Product selectedProduct = products.get(choice - 1);
//
//        ui.showMessage("1) Read Reviews");
//        ui.showMessage("2) Review Product");
//        int reviewOption = ui.askForInteger("Choose an option: ");
//
//        switch (reviewOption) {
//            case 1:
//                List<Review> reviews = productController.getProductReviews(selectedProduct.getProductName(), selectedProduct.getProductBrand());
//                displayReviews(reviews, selectedProduct);
//                break;
//            case 2:
//                addReview(selectedProduct);
//                break;
//            default:
//                ui.showMessage("Invalid option. Returning to main menu.");
//                break;
//        }
    }
    private void addBaseProduct(BaseProduct baseProduct) {

    }

    private void removeBaseProduct(BaseProduct productToRemove) throws PersistanceException {
        productManager.removeBaseProduct(productToRemove);
        cartManager.removeProduct(productToRemove);
        shopManager.removeBaseProduct(productToRemove);
    }
}
