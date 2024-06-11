package business;

import persistance.ShopDAO;

import java.util.LinkedList;

public class ShopManager {
    private LinkedList<Shop> shops;
    private ShopDAO shopDAO;
    public ShopManager(ShopDAO shopDAO) {
        this.shopDAO = shopDAO;
    }

    public void addShop(Shop shop) {
        shops.add(shop);
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
