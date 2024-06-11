package persistance;

import business.Product;
import exceptions.LocalFilesException;
import exceptions.OriginalProductNotFoundException;
import exceptions.PersistanceException;

import java.util.LinkedList;
import java.util.List;

public interface ProductDAO {
    void checkStatus() throws PersistanceException;
    LinkedList<Product> getProducts() throws LocalFilesException;
    void writeProduct(Product product) throws LocalFilesException;
    void updateProducts(List<Product> productList) throws LocalFilesException;
    void removeProduct(Product product) throws LocalFilesException;
    Product getProductByNameAndBrand(String productName, String productBrand) throws LocalFilesException;
    void updateProduct(Product updatedProduct) throws LocalFilesException, OriginalProductNotFoundException;
}