package business;

import persistance.ProductDAO;

import java.util.LinkedList;

public class ProductManager {
    private LinkedList<BaseProduct> baseProducts;
    private ProductDAO productDAO;

    public ProductManager(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void createBaseProduct(String name, String brand, String category, float maxRetailPrice) throws Exception {
        category = AssignCategory(category);
        if (category == null)
            throw new Exception("Invalid category");

        BaseProduct newBaseProduct = new BaseProduct(name, brand, category, maxRetailPrice);
        baseProducts.add(newBaseProduct);
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

    public void removeBaseProduct(BaseProduct baseProduct) {
        baseProducts.remove(baseProduct);
    }

    public LinkedList<BaseProduct> getBaseProducts() {
        return baseProducts;
    }
}
