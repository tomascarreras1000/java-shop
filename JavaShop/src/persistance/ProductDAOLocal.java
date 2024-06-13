package persistance;

import business.BaseProduct;
import business.Product;
import com.google.gson.Gson;
import exceptions.LocalFilesException;
import exceptions.OriginalProductNotFoundException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class ProductDAOLocal implements ProductDAO {
    private Gson gson;

    public ProductDAOLocal() {
        this.gson = new Gson();
    }

    public LinkedList<BaseProduct> getProducts() throws LocalFilesException {
        BaseProduct[] products = null;
        FileReader reader = null;
        try {
            reader = new FileReader("JavaShop/files/products.json");
            products = gson.fromJson(reader, BaseProduct[].class);
        } catch (FileNotFoundException e) {
            try {
                FileWriter writer = new FileWriter("JavaShop/files/products.json");
                writer.write("[]");
                writer.flush();
                writer.close();
            } catch (IOException e2) {
                throw new LocalFilesException(e2.getMessage());
            }
        } finally {
            if (reader != null) {
                try {
                    products = gson.fromJson(reader, BaseProduct[].class);
                    reader.close();
                } catch (IOException e) {
                    throw new LocalFilesException("Error closing the file! " + e.getMessage());
                }
            }
        }
        if (products == null)
            return new LinkedList<>();
        return new LinkedList<>(Arrays.asList(products));
    }

    public void createProduct(BaseProduct product) throws LocalFilesException {
        LinkedList<BaseProduct> list = getProducts();
        list.add(product);
        updateProducts(list);
    }

    public void updateProducts(LinkedList<BaseProduct> productList) throws LocalFilesException {
        FileWriter writer = null;
        try {
            writer = new FileWriter("JavaShop/files/products.json");
            gson.toJson(productList, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new LocalFilesException(e.getMessage());
        }
    }

    public void removeProduct(BaseProduct product) throws LocalFilesException {
        LinkedList<BaseProduct> productList = getProducts();
        for (Product p : productList) {
            if (compareProducts(p, product)) {
                productList.remove(p);
                break;
            }
        }
        updateProducts(productList);
    }

    public void updateProducts(BaseProduct updatedProduct) throws LocalFilesException, OriginalProductNotFoundException {
        LinkedList<BaseProduct> products = getProducts();
        for (BaseProduct p : products) {
            if (compareProducts(p, updatedProduct)) {
                products.set(products.indexOf(p), updatedProduct);
                updateProducts(products);
                return;
            }
        }
        throw new OriginalProductNotFoundException("Error: The product can’t be updated.");
    }

    public void checkStatus() throws LocalFilesException {
        getProducts();
    }

    private boolean compareProducts(Product product1, Product product2) {
        return product1.getName().equals(product2.getName()) && product1.getBrand().equals(product2.getBrand());
    }
}