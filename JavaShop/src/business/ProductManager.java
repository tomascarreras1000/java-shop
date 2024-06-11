package business;

import exceptions.InvalidRetailPriceException;
import exceptions.PersistanceException;
import persistance.ProductDAO;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class ProductManager {
    private ProductDAO productDAO;

    public ProductManager(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void createBaseProduct(String name, String brand, String category, float maxRetailPrice) throws PersistanceException, Exception {
        category = AssignCategory(category);
        if (category == null)
            throw new Exception("Invalid category");

        BaseProduct newBaseProduct = new BaseProduct(name, brand, category, maxRetailPrice);
        productDAO.createProduct(newBaseProduct);
    }

    public RetailProduct createRetailProductFromBaseProduct(BaseProduct baseProduct, float retailPrice) throws PersistanceException, InvalidRetailPriceException {
        if (retailPrice > baseProduct.getMaxRetailPrice())
            throw new InvalidRetailPriceException("Invalid retail price");

        return new RetailProduct(baseProduct, retailPrice);
    }

    private String AssignCategory(String category) {
        if (category.equalsIgnoreCase("A")) {
            return "General";
        } else if (category.equalsIgnoreCase("B")) {
            return "Reduced";
        } else if (category.equalsIgnoreCase("C")) {
            return "Super Reduced";
        } else {
            return null;
        }
    }

    public void removeBaseProduct(BaseProduct baseProduct) throws PersistanceException {
        productDAO.removeProduct(baseProduct);
    }

    public LinkedList<BaseProduct> getBaseProducts() throws PersistanceException {
        return productDAO.getProducts();
    }

    /**
     * Finds a product with provided name. Note that this search is case-sensitive.
     *
     * @param name
     * @return
     */
    public BaseProduct findProductByName(String name) throws PersistanceException, FileNotFoundException {
        BaseProduct returnProduct = null;

        for (BaseProduct baseProduct : productDAO.getProducts()) {
            if (baseProduct.getName().equals(name)) {
                returnProduct = baseProduct;
                break;
            }
        }
        return returnProduct;
    }
}
