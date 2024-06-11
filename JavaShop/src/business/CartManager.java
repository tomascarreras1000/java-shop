package business;

import java.util.HashMap;
import java.util.LinkedList;

public class CartManager {
    private HashMap<String, LinkedList<RetailProduct>> cart;

    public CartManager() {}
    public void addProduct(RetailProduct product, Shop shop) {
        LinkedList<RetailProduct>productList = cart.get(shop.getName());
        productList.add(product);
    }
    public void removeProduct(RetailProduct product, Shop shop) {
        LinkedList<RetailProduct>productList = cart.get(shop.getName());
        productList.remove(product);
        if (productList.isEmpty()) {
            cart.remove(shop.getName());
        }
    }
    public void removeProduct(Product productToRemove) {
        for (LinkedList<RetailProduct> productList : cart.values()) {
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getName().equals(productToRemove.getName())) {
                    productList.remove(i);
                }
            }
        }

    }
}
