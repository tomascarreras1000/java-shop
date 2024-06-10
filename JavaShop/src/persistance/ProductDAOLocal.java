package persistance;

import business.Product;
import com.google.gson.Gson;
import exceptions.LocalFilesException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProductDAOLocal {

    private Gson gson;

    public ProductDAOLocal() {
        this.gson = new Gson();
    }


    public Product[] readProduct() {
        Product[] products = null;
        FileReader reader = null;
        try {
            reader = new FileReader("files/products.json");
            products = gson.fromJson(reader, Product[].class);
        } catch (FileNotFoundException e) {
            System.out.println("Error: The products.json file can’t be accessed.");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Error closing the file! " + e.getMessage());
                }
            }
        }
        return products;
    }


    public void writeProduct(Product product) {
        Product[] products = readProduct();
        ArrayList<Product> list = new ArrayList<>(Arrays.asList(products));
        list.add(product);
        updateProducts(list);
    }

    public void updateProducts(List<Product> productList) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("files/products.json");
            gson.toJson(productList, writer);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error writing in file!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






    public void removeProduct(int productPosition) {
        //try {
            Product[] products = readProduct();
            ArrayList<Product> productList = new ArrayList<>(Arrays.asList(products));

            if (productPosition < 0 || productPosition >= productList.size()) {
                //throw new ProductPositionException(productPosition);
            }

            productList.remove(productPosition);
            updateProducts(productList);
        //} catch (ProductPositionException e) {
        //    System.err.println(e.getMessage());
        //}
    }


    public Product getProductByNameAndBrand(String productName, String productBrand) {
        Product[] products = readProduct();
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName) && product.getName().equalsIgnoreCase(productBrand)) {
                return product;
            }
        }
        return null;
    }
    public void updateProduct(Product productToUpdate) {
        Product[] products = readProduct();
        for (int i = 0; i < products.length; i++) {
            if (products[i].getName().equalsIgnoreCase(productToUpdate.getName())
                    && products[i].getName().equalsIgnoreCase(productToUpdate.getName())) {
                products[i] = productToUpdate;
                updateProducts(Arrays.asList(products));
                return;
            }
        }

    }

    public void check() throws LocalFilesException {
        //TODO: Implement this method
    }
}