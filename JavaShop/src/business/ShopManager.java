package business;

import exceptions.BusinessException;
import exceptions.ProductAlreadyExistsException;
import persistance.ShopDAO;

import java.util.LinkedList;

public class ShopManager {
    private LinkedList<Shop> shops;
    private ShopDAO shopDAO;
    public ShopManager(ShopDAO shopDAO) {
        this.shopDAO = shopDAO;
        shops = new LinkedList<>();
    }

    public void createShop(String name, String description, int since) {
        Shop shop = new MaxProfitShop(name, description, since);
        shops.add(shop);
    }

    public void createShop(String name, String description, int since, float loyaltyThreshold) {
        Shop shop = new LoyaltyShop(name, description, since, loyaltyThreshold);
        shops.add(shop);
    }

    public void createShop(String name, String description, int since, String sponsorBrand) {
        Shop shop = new SponsoredShop(name, description, since, sponsorBrand);
        shops.add(shop);
    }

    public String getBusinessModelFromOptions(String option) {
        if (option.equalsIgnoreCase("A")) {
            return "Maximum Profit";
        } else if (option.equalsIgnoreCase("B")) {
            return "Loyalty";
        } else if (option.equalsIgnoreCase("C")) {
            return "Sponsored";
        } else {
            return null;
        }
    }

    /**
     * Finds a shop with provided name. Note that this search is case-sensitive.
     * @param name
     * @return
     */
    public Shop findShopByName(String name) {
        Shop returnShop = null;
        for (Shop shop : shops) {
            if (shop.getName().equals(name)) {
                returnShop = shop;
                break;
            }
        }
        return returnShop;
    }

    public void removeShop(Shop shop) {
        shops.remove(shop);
    }

    public void addProductToShop(String shopName, RetailProduct product) throws BusinessException {
        for (Shop shop : shops) {
            if (shop.getName().equalsIgnoreCase(shopName)) {
                for (RetailProduct retailProduct : shop.getCatalogue())
                    if (retailProduct.getName().equalsIgnoreCase(product.getName()))
                        throw new ProductAlreadyExistsException();
                shop.addProduct(product);
            }
        }
    }

    public void removeRetailProductFromShop(Shop shop, RetailProduct productToRemove) throws Exception {
        if (shop.getCatalogue().contains(productToRemove)) {
            shop.getCatalogue().remove(productToRemove);
        }
        else
            throw new Exception("\nERROR Product does not belong to shop.");
    }

    public void removeBaseProduct(BaseProduct productToRemove) {
        for (Shop shop : shops) {
            for (RetailProduct retailProduct : shop.getCatalogue())
                if (retailProduct.getName().equalsIgnoreCase(productToRemove.getName()))
                    shop.removeProduct(retailProduct);
        }
    }
}
