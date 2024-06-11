package business;

import persistance.ShopDAO;

import java.util.LinkedList;

public class ShopManager {
    private LinkedList<Shop> shops = new LinkedList<Shop>();
    private ShopDAO shopDAO;
    public ShopManager(ShopDAO shopDAO) {
        shops = new LinkedList<Shop>();
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
