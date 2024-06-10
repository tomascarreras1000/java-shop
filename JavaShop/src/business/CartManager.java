package business;

import java.util.HashMap;
import java.util.LinkedList;

public class CartManager {
    private HashMap<String, LinkedList<RetailProduct>> cart;
    public CartManager() {
        cart = new HashMap<String, LinkedList<RetailProduct>>();
    }
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
}
