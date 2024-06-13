package persistance;


import business.LoyaltyShop;
import business.MaxProfitShop;
import business.Shop;
import business.SponsoredShop;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import exceptions.APINotWorkingException;

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

public class ShopDAOAPI implements ShopDAO {

    private Gson gson;

    private static final String API_URL_TEMPLATE_SHOPS = "https://balandrau.salle.url.edu/dpoo/%s/shops";
    private static final String API_URL_TEMPLATE_POSITION = "https://balandrau.salle.url.edu/dpoo/%s/shops/%d";
    private static final String groupId = "S1-Project_08";


    public ShopDAOAPI() {
        this.gson = new Gson();
    }

    @Override
    public void removeShop(Shop shopToRemove) throws APINotWorkingException {

        for(int i = 0; i < getShops().size(); i++){
            if(getShops().get(i).getName().equals(shopToRemove.getName())){
                removeShop(i);
                break;
            }
        }

    }

    @Override
    public void updateShops(Shop shopToUpdate) throws APINotWorkingException {

        LinkedList<Shop> shopList = getShops();
        for (int i = 0; i < shopList.size(); i++) {
            if (shopList.get(i).getName().equals(shopToUpdate.getName())) {
                removeShop(i);
                createShop(shopToUpdate);
                break;
            }
        }

    }

    @Override
    public void updateShops(LinkedList<Shop> shopList) {

        for (Shop shop : shopList) {
            try {
                updateShops(shop);
            } catch (APINotWorkingException e) {
                e.printStackTrace();
            }
        }

    }

    /*****************************DONE*********************************/

    public void createShop(Shop shop) throws APINotWorkingException {

        try {

            URL url = new URL(String.format(API_URL_TEMPLATE_SHOPS, groupId));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "";
            if (shop instanceof LoyaltyShop) {
                jsonPayload = LoyaltyShopToJsonObject((LoyaltyShop) shop).toString();
            } else if (shop instanceof MaxProfitShop) {
                jsonPayload = MaxProfitShopToJsonObject((MaxProfitShop) shop).toString();
            } else if (shop instanceof SponsoredShop) {
                jsonPayload = SponsoredShopToJsonObject((SponsoredShop) shop).toString();
            }

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

                throw new APINotWorkingException("Response: " + response.toString());
            } else {
                throw new APINotWorkingException("Failed to create shop, HTTP response code: " + responseCode);
            }

        } catch (IOException e) {
            throw new APINotWorkingException("Error: The API isn’t available.\n");
        }
    }

    public LinkedList<Shop> getShops() throws APINotWorkingException {

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

                Type shopListType = new TypeToken<LinkedList<Shop>>() {
                }.getType();
                return gson.fromJson(response.toString(),shopListType);
            } else {
                throw new APINotWorkingException("Failed to fetch shops, HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new APINotWorkingException("Error: The API isn’t available.\n");
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
            throw new APINotWorkingException("Error: The API isn’t available.\n");
        }
    }

    public void removeShop(int position) throws APINotWorkingException {
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
                throw new APINotWorkingException(connection.getResponseMessage());
            }
        } catch (IOException e) {
            throw new APINotWorkingException("Error: The API isn’t available.\n");
        }
    }

    private LinkedList<Shop> searchShops(String name, String description, Integer foundation, String businessModel, Float totalEarnings) throws APINotWorkingException {

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

                Type shopListType = new TypeToken<List<Shop>>() {
                }.getType();
                return gson.fromJson(response.toString(), shopListType);
            } else {
                throw new APINotWorkingException("Failed to search shops, HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new APINotWorkingException("Error: The API isn’t available.\n");
        }
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

    private Shop getShopByPosition(int position) throws APINotWorkingException {

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
                throw new APINotWorkingException("Failed to retrieve shop, HTTP response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new APINotWorkingException("Error: The API isn’t available.\n");
        }
    }

    private JsonObject LoyaltyShopToJsonObject(LoyaltyShop loyaltyShop) {
        JsonObject shopObject = new JsonObject();
        shopObject.addProperty("name", loyaltyShop.getName());
        shopObject.addProperty("description", loyaltyShop.getDescription());
        shopObject.addProperty("since", loyaltyShop.getSince());
        shopObject.addProperty("earnings", loyaltyShop.getEarnings());
        shopObject.addProperty("businessModel", "LOYALTY");
        shopObject.addProperty("loyaltyThreshold", loyaltyShop.getLoyaltyThreshold());
        shopObject.add("catalogue", gson.toJsonTree(loyaltyShop.getCatalogue()));
        return shopObject;
    }

    private JsonObject SponsoredShopToJsonObject(SponsoredShop sponsoredShop) {
        JsonObject shopObject = new JsonObject();
        shopObject.addProperty("name", sponsoredShop.getName());
        shopObject.addProperty("description", sponsoredShop.getDescription());
        shopObject.addProperty("since", sponsoredShop.getSince());
        shopObject.addProperty("earnings", sponsoredShop.getEarnings());
        shopObject.addProperty("sponsorBrand", sponsoredShop.getSponsorBrand());
        shopObject.addProperty("businessModel", "SPONSORED");
        shopObject.add("catalogue", gson.toJsonTree(sponsoredShop.getCatalogue()));
        return shopObject;
    }

    private JsonObject MaxProfitShopToJsonObject(MaxProfitShop maxProfitShop) {
        JsonObject shopObject = new JsonObject();
        shopObject.addProperty("name", maxProfitShop.getName());
        shopObject.addProperty("description", maxProfitShop.getDescription());
        shopObject.addProperty("since", maxProfitShop.getSince());
        shopObject.addProperty("earnings", maxProfitShop.getEarnings());
        shopObject.addProperty("businessModel", "MAX_PROFIT");
        shopObject.add("catalogue", gson.toJsonTree(maxProfitShop.getCatalogue()));
        return shopObject;
    }
}
