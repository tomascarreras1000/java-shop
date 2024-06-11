package business;

import java.util.LinkedList;

public abstract class Shop {
    private String name;
    private String description;
    private int since;
    private float earnings;
    private LinkedList<RetailProduct> catalogue;

    public void addProduct(RetailProduct product) {
        catalogue.add(product);
    }
    public void removeProduct(Product product) {
        catalogue.remove(product);
    }

    public String getName() {
        return name;
    }
}
