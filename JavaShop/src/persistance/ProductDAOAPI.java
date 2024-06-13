package persistance;

import business.BaseProduct;
import business.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.APINotWorkingException;
import exceptions.LocalFilesException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class ProductDAOAPI implements ProductDAO{

    private Gson gson;

    private static final String API_URL_TEMPLATE_PRODUCTS = "https://balandrau.salle.url.edu/dpoo/%s/products";
    private static final String API_URL_TEMPLATE_PRODUCTS_POSITION = "https://balandrau.salle.url.edu/dpoo/%s/products/%d";
    private static final String groupId = "S1-Project_08";

    public ProductDAOAPI() {
        this.gson = new Gson();
    }

    //TODO: Implement methods:
    @Override
    public void updateProducts(LinkedList<BaseProduct> productList) {

        for (BaseProduct product : productList) {
            try {
                updateProducts(product);
            } catch (APINotWorkingException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void removeProduct(BaseProduct product) throws APINotWorkingException {

        LinkedList<BaseProduct> products = getProducts();
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).equals(product)){
                removeProductByPosition(i);
                break;
            }
        }

    }
    @Override
    public void updateProducts(BaseProduct updatedProduct) throws APINotWorkingException {

        for(int i = 0; i < getProducts().size(); i++){
            if(getProducts().get(i).equals(updatedProduct)){
                removeProductByPosition(i);
                createProduct(updatedProduct);
                break;
            }
        }
    }
    /***********************************DONE*********************************************/
    public void createProduct(BaseProduct product) throws APINotWorkingException{

        try {

            URL url = new URL(String.format(API_URL_TEMPLATE_PRODUCTS, groupId));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = gson.toJson(product);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonPayload.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                throw new APINotWorkingException("Failed to create product, HTTP response code: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<BaseProduct> getProducts() throws APINotWorkingException{

        try {
            URL url = new URL(String.format(API_URL_TEMPLATE_PRODUCTS, groupId));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Type productListType = new TypeToken<LinkedList<BaseProduct>>(){}.getType();
                return gson.fromJson(response.toString(), productListType);
            } else {
                throw new APINotWorkingException("Failed to fetch products, HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //si voleu buscar nomes per nom poseu els altres parametres a null
    private LinkedList<BaseProduct> searchProducts(String name, String brand, Double mrp, String category) throws APINotWorkingException{
        Gson gson = new Gson();
        try {
            String urlWithParameters = buildUrlWithParameters(groupId, name, brand, mrp, category);
            URL url = new URL(urlWithParameters);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Type productListType = new TypeToken<LinkedList<BaseProduct>>(){}.getType();
                return gson.fromJson(response.toString(), productListType);
            } else {
                throw new APINotWorkingException("Failed to search products, HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new APINotWorkingException(e.getMessage());
        }
    }

    private String buildUrlWithParameters(String groupId, String name, String brand, Double mrp, String category) {
        StringJoiner joiner = new StringJoiner("&");
        if (name != null) joiner.add("productName=" + name);
        if (brand != null) joiner.add("productBrand=" + brand);
        if (mrp != null) joiner.add("productMaxPrice=" + String.valueOf(mrp));
        if (category != null) joiner.add("productCategory=" + category);

        return String.format(API_URL_TEMPLATE_PRODUCTS, groupId) + "?" + joiner.toString();
    }

    private BaseProduct getProductByPosition(int position) throws APINotWorkingException{
        Gson gson = new Gson();
        try {
            URL url = new URL(String.format(API_URL_TEMPLATE_PRODUCTS_POSITION, groupId, position));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return gson.fromJson(response.toString(), BaseProduct.class);
            } else {
                throw new APINotWorkingException("Failed to retrieve product, HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new APINotWorkingException(e.getMessage());
        }
    }

    private void removeProducts(String name, String brand, Double mrp, String category) throws APINotWorkingException{

        try {
            String urlWithParameters = buildUrlWithParameters(groupId, name, brand, mrp, category);
            URL url = new URL(urlWithParameters);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                throw new APINotWorkingException(connection.getResponseMessage());
            }
        } catch (IOException e) {
            throw new APINotWorkingException(e.getMessage());
        }
    }

    private BaseProduct removeProductByPosition(int position) throws APINotWorkingException{
        Gson gson = new Gson();
        try {
            URL url = new URL(String.format(API_URL_TEMPLATE_PRODUCTS_POSITION, groupId, position));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                throw new APINotWorkingException(connection.getResponseMessage());
            }
        } catch (IOException e) {
            throw new APINotWorkingException(e.getMessage());
        }
        return null;
    }

    public void checkStatus() throws APINotWorkingException{
        try {
            URL url = new URL(String.format(API_URL_TEMPLATE_PRODUCTS, groupId));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) // API is up and running!
                throw new APINotWorkingException("Error: The API isn’t available.\n");
        } catch (IOException e) {
            throw new APINotWorkingException(e.getMessage());
        }
    }
}
