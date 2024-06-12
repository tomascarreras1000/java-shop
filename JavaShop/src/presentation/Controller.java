package presentation;

import business.*;
import exceptions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
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
            } catch (Exception | PersistanceException | BusinessException e) {
                ui.showMessage(e.getMessage());
            }
        } while (option != 6);
    }

    private void executeMenu(int option) throws PersistanceException, BusinessException, Exception {
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
                runShopListMenu();
                break;
            case 5:
                runCartMenu();
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
        if (productManager.findProductByName(productName) != null)
            throw new Exception("\"" + productName + "\" name is taken.");

        String productBrand = ui.askForStringFormatted("Please enter the product's brand: ");
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
            ui.showMessage("0. Back\n");
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                ui.showMessage((i + 1) + ". " + product.getDescription());
            }
            int productPosition = ui.askForInteger("\nWhich one would you like to remove? ");

            if (productPosition < 0 || productPosition > productList.size()) {
                ui.showMessage("\nERROR: Choose an existing option.");
            } else if (productPosition == 0) {
                break;
            } else {
                Product selectedProduct = productList.get(productPosition - 1);

                if (ui.confirm("\nAre you sure you want to remove " + selectedProduct.getDescription() + "? ")) {
                    // Remove the product from the system
                    productManager.removeBaseProduct(productList.get(productPosition - 1));

                    shopManager.removeBaseProduct(productList.get(productPosition - 1));

                    ui.showMessage("\n" + selectedProduct.getDescription() + " has been withdrawn from sale.");
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
     *
     * @throws Exception if an invalid business model is entered
     */
    private void shopsOptionOne() throws PersistanceException, Exception {
        // Name field
        String shopName = ui.askForString("\nPlease enter the shop's name: ");
        if (shopManager.findShopByName(shopName) != null)
            throw new Exception("\"" + shopName + "\" name is taken.");

        // Description field
        String shopDescription = ui.askForString("Please enter the shop's description: ");

        // Foundation date field
        int shopFoundationYear = ui.askForInteger("Please enter the shop's founding year: ");
        if (shopFoundationYear < 0)
            throw new Exception("\"" + shopFoundationYear + "\" is not a valid year.");

        // Business model field/s
        ui.showMessage("\nThe system supports the following business models: \n");
        ui.showMessage("""
                 \tA) Maximum Benefits
                 \tB) Loyalty
                 \tC) Sponsored\
                """);
        String shopBusinessModel = ui.askForString("\nPlease pick the shop’s business model: ");
        shopBusinessModel = shopManager.getBusinessModelFromOptions(shopBusinessModel);
        if (shopBusinessModel == null) {
            throw new Exception("\nERROR: Choose a valid business model!");
        } else if (shopBusinessModel.equals("Loyalty")) {
            float loyaltyThreshold = ui.askForFloat("\nPlease enter the shop's loyalty threshold: ");
            shopManager.createShop(shopName, shopDescription, shopFoundationYear, loyaltyThreshold);
        } else if (shopBusinessModel.equals("Sponsored")) {
            String sponsorBrand = ui.askForString("\nPlease enter the shop's sponsor brand: ");
            shopManager.createShop(shopName, shopDescription, shopFoundationYear, sponsorBrand);
        } else {
            shopManager.createShop(shopName, shopDescription, shopFoundationYear);
        }

        // Confirmation output
        ui.showMessage("\n\"" + shopName + "\" is now a part of the elCofre family.");
    }

    /**
     * Catalogue expansion menu
     *
     * @throws Exception
     */
    private void shopsOptionTwo() throws PersistanceException, BusinessException {
        // Shop name field
        String shopName = ui.askForString("\nPlease enter the shop's name: ");
        if (shopManager.findShopByName(shopName) == null)
            throw new ShopNotFoundException(shopName);

        // Product name field
        String productName = ui.askForString("Please enter the product's name: ");
        BaseProduct product = productManager.findProductByName(productName);
        if (product == null)
            throw new ProductNotFoundException(productName);

        // Price field
        float currentPrice = ui.askForFloat("Please enter the product’s price at this shop: ");
        if (currentPrice > product.getMaxRetailPrice())
            throw new InvalidRetailPriceException("Invalid retail price");

        String productBrand = product.getBrand();
        shopManager.addProductToShop(shopName, productManager.createRetailProductFromBaseProduct(product, currentPrice));

        ui.showMessage("\"" + productName + "\" by \"" + productBrand + "\" is now being sold at \"" + shopName + "\".");
    }

    private void shopsOptionThree() throws PersistanceException, BusinessException, Exception {
        // Name field
        String shopName = ui.askForString("Please enter the shop’s name: ");
        Shop shop = shopManager.findShopByName(shopName);
        if (shop == null)
            throw new ShopNotFoundException(shopName);

        LinkedList<RetailProduct> catalog = shop.getCatalogue();

        if (catalog.isEmpty()) {
            ui.showMessage("This shop currently has no products in its catalog.");
            return;
        }

        ui.showMessage("This shop sells the following products: \n0. Back\n");
        for (int i = 0; i < catalog.size(); i++) {
            Product product = catalog.get(i);
            ui.showMessage((i + 1) + ". " + product.getDescription());
        }

        int productPosition = ui.askForInteger("\nWhich one would you like to remove? ");

        if (productPosition < 0 || productPosition > catalog.size()) {
            ui.showMessage("\nERROR: Choose an existing option.");
        } else if (productPosition == 0) {
            return;
        } else {
            RetailProduct selectedProduct = catalog.get(productPosition - 1);
            String productName = selectedProduct.getName();
            String productBrand = selectedProduct.getBrand();

            shopManager.removeRetailProductFromShop(shop, selectedProduct);
            ui.showMessage("\"" + productName + "\" by \"" + productBrand + "\" is no longer being sold at \"" + shopName + "\".");

        }
    }

    private void runSearchMenu() throws PersistanceException, BusinessException {
        // Query field
        String query = ui.askForString("\nEnter your query: ");
        LinkedList<BaseProduct> products = productManager.getAllProductsContainingText(query);

        if (products.isEmpty()) {
            ui.showMessage("No products found matching the query.");
            return;
        }

        ui.showMessage("\nThe following products were found:");
        int index = 1;
        for (Product product : products) {
            ui.showMessage("\t" + index + ") \"" + product.getName() + "\" by \"" + product.getBrand() + "\"");

            LinkedList<Shop> sellingShops = shopManager.getAllShopsWithProduct(product);
            if (sellingShops.isEmpty()) {
                ui.showMessage("\tThis product is not currently being sold in any shop.");
            } else {
                for (Shop sellingShop : sellingShops) {
                    ui.showMessage("\t\tSold at:");
                    float price = sellingShop.getPriceFromProduct(product);
                    ui.showMessage("\t\t\t- " + sellingShop.getName() + ": " + price);
                }
            }
            index++;
        }
        ui.showMessage("\t" + index + ") Back");

        int choice = ui.askForInteger("Select a product: ");
        if (choice == index) {
            return;
        }
        if (choice < 1 || choice > products.size()) {
            ui.showMessage("Invalid choice. Please enter a valid option.");
            return;
        }

        BaseProduct selectedProduct = products.get(choice - 1);

        ui.showMessage("""
                What would you like to do?
                \t1) Read Reviews
                \t2) Review Product
                                
                \t3) Back
                """);
        int reviewOption = ui.askForInteger("Choose an option: ");

        switch (reviewOption) {
            case 1:
                List<ProductReview> reviews = selectedProduct.getReviews();
                if (reviews.isEmpty()) {
                    ui.showMessage("There are no reviews for this product yet.");
                    return;
                }

                ui.showMessage("\nThese are the reviews for \"" + selectedProduct.getName() + "\" by \"" + selectedProduct.getBrand() + "\":");
                float totalStars = 0;
                for (ProductReview review : reviews) {
                    ui.showMessage("\t" + review.getReviewAsText());
                    totalStars += review.getStars();
                }

                float averageRating = totalStars / reviews.size();
                ui.showMessage("\nAverage rating: " + String.format("%.2f", averageRating) + "*"); // TODO: double check this
                break;
            case 2:
                int stars = ui.askForInteger("\nRate the product from 1 to 5: ");
                if (stars < 1 || stars > 5) {
                    ui.showMessage("Invalid choice. Please enter a valid option.");
                    return;
                }
                String text = ui.askForString("Please enter the review: ");
                productManager.writeReview(selectedProduct, text, stars);
                ui.showMessage("The " + stars + "* review has been added to the product.");
                break;
            case 3:
                return;
            default:
                ui.showMessage("Invalid option. Returning to main menu.");
                break;
        }
    }

    private void runShopListMenu() throws PersistanceException, BusinessException {
        List<Shop> shops = shopManager.getShops();

        ui.showMessage("The elCofre family is formed by the following shops: \n");
        for (int i = 0; i < shops.size(); i++) {
            ui.showMessage((i + 1) + ") " + shops.get(i).getName());
        }
        ui.showMessage("\n" + (shops.size() + 1) + ") Back");

        int choice = ui.askForInteger("Which catalogue do you want to see? ");
        if (choice < 1 || choice > shops.size() + 1) {
            ui.showMessage("Invalid choice. Please enter a valid option.");
            return;
        } else if (choice == shops.size() + 1) {
            return;
        }

        Shop selectedShop = shops.get(choice - 1);

        ui.showMessage("\n" + selectedShop.getName() + " - Since " + selectedShop.getSince());
        ui.showMessage(selectedShop.getDescription() + "\n");

        LinkedList<RetailProduct> catalogue = selectedShop.getCatalogue();

        for (int i = 0; i < catalogue.size(); i++) {
            RetailProduct product = catalogue.get(i);

            ui.showMessage("\t" + (i + 1) + ") \"" + product.getName() + "\" by \"" + product.getBrand() + "\"");
            ui.showMessage("Price: " + product.getRetailPrice() + "\n");
        }

        ui.showMessage("\t" + (catalogue.size() + 1) + ") Back");

        int productChoice = ui.askForInteger("Which one are you interested in? ");
        if (productChoice < 1 || productChoice > catalogue.size() + 1) {
            ui.showMessage("Invalid choice. Returning to main menu.");
            return;
        } else if (productChoice == catalogue.size() + 1) {
            return;
        }

        RetailProduct selectedProduct = catalogue.get(productChoice - 1);
        BaseProduct baseProduct = productManager.findProductByName(selectedProduct.getName());

        ui.showMessage("1) Read Reviews");
        ui.showMessage("2) Review Product");
        ui.showMessage("3) Add to Cart");
        int productOption = ui.askForInteger("Choose an option: ");
        switch (productOption) {
            case 1:
                List<ProductReview> reviews = (baseProduct).getReviews();
                if (reviews.isEmpty()) {
                    ui.showMessage("There are no reviews for this product yet.");
                    return;
                }

                ui.showMessage("\nThese are the reviews for \"" + selectedProduct.getName() + "\" by \"" + selectedProduct.getBrand() + "\":");
                float totalStars = 0;
                for (ProductReview review : reviews) {
                    ui.showMessage("\t" + review.getReviewAsText());
                    totalStars += review.getStars();
                }

                float averageRating = totalStars / reviews.size();
                ui.showMessage("\nAverage rating: " + String.format("%.2f", averageRating) + "*"); // TODO: double check this
                break;
            case 2:
                int stars = ui.askForInteger("\nRate the product from 1 to 5: ");
                if (stars < 1 || stars > 5) {
                    ui.showMessage("Invalid choice. Please enter a valid option.");
                    return;
                }
                String text = ui.askForString("Please enter the review: ");
                productManager.writeReview(baseProduct, text, stars);
                ui.showMessage("The " + stars + "* review has been added to the product.");
                break;
            case 3:
                cartManager.addProduct(selectedProduct, selectedShop);
                ui.showMessage("1x \"" + selectedProduct.getName() + "\" by \"" + selectedProduct.getBrand() + "\" has been added to your cart.");
                break;
            default:
                ui.showMessage("Invalid option. Returning to main menu.");
                break;
        }
    }

    private void runCartMenu() {
        HashMap<String, LinkedList<RetailProduct>> cart = cartManager.getCart();
        if (cart.isEmpty()) {
            ui.showMessage("Your cart is empty.");
        } else {
            ui.showMessage("Your cart contains the following items:");
            float total = 0;
            for (LinkedList<RetailProduct> list : cart.values()) {
                for (RetailProduct retailProduct : list) {
                    ui.showMessage("\t- \"" + retailProduct.getName() + "\" by \"" + retailProduct.getBrand() + "\"\n");
                    ui.showMessage("\t\tPrice: " + retailProduct.getRetailPrice() + "\n");
                    total += retailProduct.getRetailPrice();
                }
            }
            ui.showMessage("Total: " + total);
        }

        ui.showMessage("1) Checkout");
        ui.showMessage("2) Clear cart");
        ui.showMessage("3) Back");

        int cartOption = ui.askForInteger("Choose an option: ");
        switch (cartOption) {
            case 1:
                String confirmCheckout = ui.askForString("Are you sure you want to checkout? ");
                if (confirmCheckout.equalsIgnoreCase("YES")) {
                    // TODO: Compra
                } else {
                    ui.showMessage("Checkout cancelled.");
                }
                break;
            case 2:
                String confirmClear = ui.askForString("Are you sure you want to clear your cart? ");
                if (confirmClear.equalsIgnoreCase("YES")) {
                    cartManager.clearCart();
                    ui.showMessage("Your cart has been cleared.");
                } else {
                    ui.showMessage("Clear cart cancelled.");
                }
                break;
            case 3:
                ui.showMessage("Returning to the main menu.");
                break;
            default:
                ui.showMessage("Invalid option. Returning to main menu.");
                break;
        }

    }
}
