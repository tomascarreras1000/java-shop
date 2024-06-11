package business;

import persistance.ProductDAO;

import java.util.LinkedList;

public class ProductManager {
    private LinkedList<BaseProduct> baseProducts;
    private ProductDAO productDAO;

    public ProductManager(ProductDAO productDAO) {
        baseProducts = new LinkedList<BaseProduct>();
        this.productDAO = productDAO;
    }
    public void addBaseProduct(BaseProduct baseProduct) {
        baseProducts.add(baseProduct);
    }
    public void removeBaseProduct(BaseProduct baseProduct) {
        baseProducts.remove(baseProduct);
    }
    public LinkedList<BaseProduct> getBaseProducts() {
        return baseProducts;
    }
}
