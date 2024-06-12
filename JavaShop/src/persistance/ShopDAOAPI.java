package persistance;


import business.Shop;
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

public class ShopDAOAPI implements ShopDAO{

    private Gson gson;

    private static final String API_URL_TEMPLATE_SHOPS = "https://balandrau.salle.url.edu/dpoo/%s/shops";
    private static final String API_URL_TEMPLATE_POSITION = "https://balandrau.salle.url.edu/dpoo/%s/shops/%d";
    private static final String groupId = "S1-Project_08";


    public ShopDAOAPI() {
        this.gson = new Gson();
    }

    public void createShop(Shop shop) {

        try {

            URL url = new URL(String.format(API_URL_TEMPLATE_SHOPS, groupId));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = gson.toJson(shop);

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

                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("Failed to create shop, HTTP response code: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Shop> getShops() {

        try {
            URL url = new URL(String.format(API_URL_TEMPLATE_SHOPS, groupId));
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

                Type shopListType = new TypeToken<List<Shop>>(){}.getType();
                return gson.fromJson(response.toString(), shopListType);
            } else {
                System.out.println("Failed to fetch shops, HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Shop> searchShops(String name, String description, Integer foundation, String businessModel, Float totalEarnings) {

        try {
            String urlWithParameters = buildUrlWithParameters(groupId, name, description, foundation, businessModel, totalEarnings);
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

                Type shopListType = new TypeToken<List<Shop>>(){}.getType();
                return gson.fromJson(response.toString(), shopListType);
            } else {
                System.out.println("Failed to search shops, HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildUrlWithParameters(String groupId, String name, String description, Integer foundation, String businessModel, Float totalEarnings) {
        StringJoiner joiner = new StringJoiner("&");
        if (name != null) joiner.add("shopName=" + name);
        if (description != null) joiner.add("shopDescription=" + description);
        if (foundation != null) joiner.add("shopFoundationYear=" + String.valueOf(foundation));
        if (businessModel != null) joiner.add("shopBusinessModel=" + businessModel);
        if (totalEarnings != null) joiner.add("totalEarnings=" + totalEarnings);

        return String.format(API_URL_TEMPLATE_SHOPS, groupId) + "?" + joiner.toString();
    }

    public Shop getShopByPosition(int position) {

        try {
            URL url = new URL(String.format(API_URL_TEMPLATE_POSITION, groupId, position));
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

                return gson.fromJson(response.toString(), Shop.class);
            } else {
                System.out.println("Failed to retrieve shop, HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeShops(String name, String description, Integer foundation, String businessModel, Float totalEarnings) {

        try {
            String urlWithParameters = buildUrlWithParameters(groupId, name, description, foundation, businessModel, totalEarnings);
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
                System.out.println(connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void removeShop(int position) {
        Gson gson = new Gson();
        try {
            URL url = new URL(String.format(API_URL_TEMPLATE_POSITION, groupId, position));
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
                System.out.println(connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeShop(Shop shopToRemove) throws APINotWorkingException {

    }
    @Override
    public void removeShop(String shopName) throws LocalFilesException {

    }
    @Override
    public void updateShops(Shop shopToUpdate) throws LocalFilesException {

    }

    @Override
    public void updateShops(LinkedList<Shop> shopList) {

    }
    private void removeAllShops(){
        try {
            URL url = new URL(String.format(API_URL_TEMPLATE_SHOPS, groupId));
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
                System.out.println(connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void checkStatus() throws APINotWorkingException {
        try {
            URL url = new URL(String.format(API_URL_TEMPLATE_SHOPS, groupId));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) // API is up and running!
                throw new APINotWorkingException("Error: The API isn’t available.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
