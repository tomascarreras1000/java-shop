package persistance;

import business.BaseProduct;
import business.Product;
import exceptions.LocalFilesException;
import exceptions.OriginalProductNotFoundException;
import exceptions.PersistanceException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public interface ProductDAO {
     LinkedList<BaseProduct> getProducts() throws LocalFilesException;
    void writeProduct(BaseProduct product) throws LocalFilesException;
    void updateProducts(List<BaseProduct> productList) throws LocalFilesException;
    void removeProduct(BaseProduct product) throws LocalFilesException;
    BaseProduct getProductByNameAndBrand(String productName, String productBrand) throws LocalFilesException;
    void updateProduct(BaseProduct updatedProduct) throws LocalFilesException, OriginalProductNotFoundException;
    void checkStatus() throws PersistanceException;
}