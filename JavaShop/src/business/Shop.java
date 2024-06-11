package business;

import java.util.LinkedList;

public class Shop {
    private String name;
    private String description;
    private int since;
    private float earnings;
    private LinkedList<RetailProduct> catalogue;

    public Shop(String name, String description, int since, float earnings) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        catalogue = new LinkedList<RetailProduct>();
    }
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
