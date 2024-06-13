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

    /**
     * @param products
     * @throws BusinessException
     */
    public float purchaseProducts(LinkedList<RetailProduct> products) throws BusinessException {
        float newEarnings = 0;
        for (RetailProduct product : products) {
            newEarnings = newEarnings + calculateOriginalPrice(product);
        }
        this.earnings = this.earnings + newEarnings;
        return newEarnings;
    }
    public float calculateOriginalPrice(RetailProduct product) {
        switch (product.getCategory()) {
            case "General":
                return product.getRetailPrice() / (1 + (21 / 100));
            case "Reduced":
                if (product.getAverageStars() > 3.5)
                    return product.getRetailPrice() / (1 + (5 / 100));
                return product.getRetailPrice() / (1 + (10 / 100));
            case "SuperReduced":
                if (product.getRetailPrice() > 100)
                    return product.getRetailPrice();
                return product.getRetailPrice() / (1 + (4 / 100));
            default:
                return product.getRetailPrice();
        }
    }
    public float getProductPrice(RetailProduct product) {
        return product.getRetailPrice();
    }
}
