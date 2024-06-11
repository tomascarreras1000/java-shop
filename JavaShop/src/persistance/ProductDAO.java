package persistance;

import business.Product;
import exceptions.LocalFilesException;
import exceptions.OriginalProductNotFoundException;
import exceptions.PersistanceException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ProductDAO {
    public void checkStatus() throws PersistanceException;
    public LinkedList<Product> getProducts() throws LocalFilesException;
    public void writeProduct(Product product) throws LocalFilesException;
    public void updateProducts(List<Product> productList) throws LocalFilesException;
    public void removeProduct(Product product) throws LocalFilesException;
    public Product getProductByNameAndBrand(String productName, String productBrand) throws LocalFilesException;
    public void updateProduct(Product updatedProduct) throws LocalFilesException, OriginalProductNotFoundException;
    public boolean compareProducts(Product product1, Product product2);
}
