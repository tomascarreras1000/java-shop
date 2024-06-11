package business;

import java.util.LinkedList;

public class Shop {
    private String name;
    private String description;
    private int since;
    private float earnings;
    private BusinessModel businessModel;
    private LinkedList<RetailProduct> catalogue;



    public enum BusinessModel {
        MAXIMUM_BENEFITS,
        LOYALTY,
        SPONSORED
    }

    public Shop(String name, String description, int since, float earnings, BusinessModel businessModel) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        this.businessModel = businessModel;
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
