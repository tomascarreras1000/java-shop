package business;

import exceptions.BusinessException;
import exceptions.ProductNotFoundException;

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

    public float getPriceFromProduct(Product product) throws BusinessException {
        for (RetailProduct retailProduct : catalogue) {
            if (retailProduct.getName().equals(product.getName())) {
                return retailProduct.getRetailPrice();
            }
        }
        throw new ProductNotFoundException(product.getName());
    }
}
