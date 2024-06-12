package persistance;

import business.BaseProduct;
import exceptions.LocalFilesException;
import exceptions.OriginalProductNotFoundException;
import exceptions.PersistanceException;

import java.util.LinkedList;

public interface ProductDAO {
     LinkedList<BaseProduct> getProducts() throws LocalFilesException;
    void createProduct(BaseProduct product) throws LocalFilesException;
    void updateProducts(LinkedList<BaseProduct> productList) throws LocalFilesException;
    void removeProduct(BaseProduct product) throws LocalFilesException;
    BaseProduct getProductByNameAndBrand(String productName, String productBrand) throws LocalFilesException;
    void updateProducts(BaseProduct updatedProduct) throws LocalFilesException, OriginalProductNotFoundException;
    void checkStatus() throws PersistanceException;
}