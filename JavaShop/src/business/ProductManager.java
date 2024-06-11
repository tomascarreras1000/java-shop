package business;

import java.util.LinkedList;

public class ProductManager {
    private LinkedList<BaseProduct> baseProducts;

    public ProductManager() {
        baseProducts = new LinkedList<BaseProduct>();
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
