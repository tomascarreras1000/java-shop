package business;

import persistance.ShopDAO;

import java.util.LinkedList;

public class ShopManager {
    private LinkedList<Shop> shops;
    private ShopDAO shopDAO;
    public ShopManager(ShopDAO shopDAO) {
        this.shopDAO = shopDAO;
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

    public void removeShop(Shop shop) {
        shops.remove(shop);
    }

    public void removeBaseProduct(BaseProduct productToRemove) {
        for (Shop shop : shops) {
            shop.removeProduct(productToRemove);
        }
    }
}
