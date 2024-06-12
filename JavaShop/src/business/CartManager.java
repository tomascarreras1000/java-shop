package business;

import java.util.HashMap;
import java.util.LinkedList;

public class CartManager {
    private HashMap<String, LinkedList<RetailProduct>> cart;

    public CartManager() {
        cart = new HashMap<String, LinkedList<RetailProduct>>();
    }
    public void addProduct(RetailProduct product, Shop shop) {
        if (cart.containsKey(shop.getName())) {
            cart.get(shop.getName()).add(product);
        }
        else {
            LinkedList<RetailProduct> newList = new LinkedList<RetailProduct>();
            newList.add(product);
            cart.put(shop.getName(), newList);
        }
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
                    break;
                }
            }
        }
    }
    public HashMap<String, LinkedList<RetailProduct>> getCart() {
        return cart;
    }

    public float getCartTotalPrice() {
        float total = 0;
        for (LinkedList<RetailProduct> list : cart.values()) {
            for (RetailProduct retailProduct : list) {
                total += retailProduct.getRetailPrice();
            }
        }
        return total;
    }

    public void clearCart(){
        cart.clear();
    }
}
