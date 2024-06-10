package persistance;

import business.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductDAO {
    public void checkStatus();
    public ArrayList<Product> getProducts();
    public void writeProduct(Product product);
    public void updateProducts(List<Product> productList);
    public void removeProduct(Product product);
    public Product getProductByNameAndBrand(String productName, String productBrand);
    public void updateProduct(Product updatedProduct);
    public boolean compareProducts(Product product1, Product product2);
}
