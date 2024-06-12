package persistance;

import business.BaseProduct;
import exceptions.LocalFilesException;
import exceptions.OriginalProductNotFoundException;
import exceptions.PersistanceException;

import java.util.LinkedList;

public interface ProductDAO {
    LinkedList<BaseProduct> getProducts() throws PersistanceException;
    void createProduct(BaseProduct product) throws PersistanceException;
    void updateProducts(LinkedList<BaseProduct> productList) throws PersistanceException;
    void removeProduct(BaseProduct product) throws PersistanceException;
    void updateProducts(BaseProduct updatedProduct) throws PersistanceException;
    void checkStatus() throws PersistanceException;
}