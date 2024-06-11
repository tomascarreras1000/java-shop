package persistance;

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
import java.util.List;

public class ProductDAOLocal implements ProductDAO{
    private Gson gson;

    public ProductDAOLocal() {
        this.gson = new Gson();
    }

    public LinkedList<Product> getProducts() throws LocalFilesException {
        Product[] products = null;
        FileReader reader = null;
        try {
            reader = new FileReader("files/products.json");
            products = gson.fromJson(reader, Product[].class);
        } catch (FileNotFoundException e) {
            throw new LocalFilesException("Error: The products.json file can’t be accessed.");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error closing the file! " + e.getMessage());
                }
            }
        }
        return new LinkedList<>(Arrays.asList(products));
    }

    public void writeProduct(Product product) throws LocalFilesException {
        LinkedList<Product> list = getProducts();
        list.add(product);
        updateProducts(list);
    }

    public void updateProducts(List<Product> productList) throws LocalFilesException {
        FileWriter writer = null;
        try {
            writer = new FileWriter("files/products.json");
            gson.toJson(productList, writer);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new LocalFilesException("Error writing in file!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProduct(Product product) throws LocalFilesException {
        LinkedList<Product> productList = getProducts();
        productList.remove(product);
        updateProducts(productList);
    }

    public Product getProductByNameAndBrand(String productName, String productBrand) throws LocalFilesException {
        LinkedList<Product> products = getProducts();
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName) && product.getName().equalsIgnoreCase(productBrand)) {
                return product;
            }
        }
        return null;
    }

    public void updateProduct(Product updatedProduct) throws LocalFilesException, OriginalProductNotFoundException {
        LinkedList<Product> products = getProducts();
        for (Product p : products) {
            if (compareProducts(p, updatedProduct)) {
                products.set(products.indexOf(p), updatedProduct);
                updateProducts(products);
                return;
            }
        }
        throw new OriginalProductNotFoundException("Error: The product can’t be updated.");
    }

    public void checkStatus() throws LocalFilesException {
        if (getProducts() == null) {
            throw new LocalFilesException("Error: The products.json file can’t be accessed.");
        }
    }

    public boolean compareProducts(Product product1, Product product2) {
        return product1.getName().equals(product2.getName()) && product1.getBrand().equals(product2.getBrand());
    }
}