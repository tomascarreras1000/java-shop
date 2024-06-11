package business;

import java.util.LinkedList;

public class ShopManager {
    private LinkedList<Shop> shops = new LinkedList<Shop>();

    public ShopManager() {
        shops = new LinkedList<Shop>();
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
