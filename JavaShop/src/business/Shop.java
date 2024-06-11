package business;

import java.util.LinkedList;

public abstract class Shop {
    protected String name;
    protected String description;
    protected int since;
    protected float earnings;
    protected LinkedList<RetailProduct> catalogue;

    public void addProduct(RetailProduct product) {
        catalogue.add(product);
    }
    public void removeProduct(Product product) {
        catalogue.remove(product);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public int getSince() {
        return since;
    }

    public float getEarnings() {
        return earnings;
    }

    public LinkedList<RetailProduct> getCatalogue() {
        return catalogue;
    }
}
