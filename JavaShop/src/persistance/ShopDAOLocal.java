package persistance;

import business.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ShopDAOLocal implements ShopDAO {
    private Gson gson;

    public ShopDAOLocal() {
        this.gson = new Gson();
    }

    public LinkedList<Shop> readShop() {
        LinkedList<Shop> shops = new LinkedList<>();
        try (FileReader reader = new FileReader("files/shops.json")) {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(reader);
            if (jsonElement.isJsonArray()) {
                JsonObject shopObject = jsonElement.getAsJsonObject();
                switch (shopObject.get("businessModel").getAsString()) {
                    case "SPONSORED":
                        SponsoredShop sponsoredShop = new SponsoredShop(shopObject.get("name").getAsString(), shopObject.get("description").getAsString(), shopObject.get("since").getAsInt(), shopObject.get("earnings").getAsFloat(), shopObject.get("sponsorBrand").getAsString(), new LinkedList(Arrays.asList(shopObject.get("catalogue").getAsJsonArray())));
                        shops.add(sponsoredShop);
                        break;
                    case "LOYALTY":
                        LoyaltyShop loyaltyShop = new LoyaltyShop(shopObject.get("name").getAsString(), shopObject.get("description").getAsString(), shopObject.get("since").getAsInt(), shopObject.get("earnings").getAsFloat(), shopObject.get("loyaltyThreshold").getAsInt(), new LinkedList(Arrays.asList(shopObject.get("catalogue").getAsJsonArray())));
                        shops.add(loyaltyShop);
                        break;
                    case "MAX_PROFIT":
                        MaxProfitShop maxProfitShop = new MaxProfitShop(shopObject.get("name").getAsString(), shopObject.get("description").getAsString(), shopObject.get("since").getAsInt(), shopObject.get("earnings").getAsFloat(), new LinkedList(Arrays.asList(shopObject.get("catalogue").getAsJsonArray())));
                        shops.add(maxProfitShop);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            shops = new LinkedList<>();
        }
        return shops;
    }

    public void writeShop(Shop shop) {
        LinkedList<Shop> shops = readShop();
        shops.add(shop);
        updateShops(shops);
    }

    public void removeShop(int shopPosition) {
        LinkedList<Shop> shops = readShop();
        shops.remove(shopPosition);
        updateShops(shops);
    }

    public void removeShop(Shop shopToRemove) {
        LinkedList<Shop> shops = readShop();
        for (Shop shop : shops) {
             if (shop.getName().equals(shopToRemove.getName())){
                    shops.remove(shop);
                    break;
                }
        }
        updateShops(shops);
    }

    /**
     * Updates a shop, replacing the old one with the new one in the list of shops and updating the file
     * @param shopToUpdate
     */
    public void updateShops(Shop shopToUpdate) {
        LinkedList<Shop> shops = readShop();
        for (Shop shop : shops) {
            if (shop.getName().equals(shopToUpdate.getName())) {
                shops.set(shops.indexOf(shop), shopToUpdate);
                updateShops(shops);
                break;
            }
        }
    }

    /**
     * Updates the list of shops in the file shops.json with the new list of shops passed as parameter shopList
     * @param shopList
     */
    public void updateShops(List<Shop> shopList) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("files/shops.json");
            gson.toJson(shopList, writer);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error writing in file!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public JsonObject LoyaltyShopToJsonObject(LoyaltyShop loyaltyShop) {
        JsonObject shopObject = new JsonObject();
        shopObject.addProperty("name", loyaltyShop.getName());
        shopObject.addProperty("description", loyaltyShop.getDescription());
        shopObject.addProperty("since", loyaltyShop.getSince());
        shopObject.addProperty("earnings", loyaltyShop.getEarnings());
        shopObject.addProperty("loyaltyThreshold", loyaltyShop.getLoyaltyThreshold());
        shopObject.add("catalogue", gson.toJsonTree(loyaltyShop.getCatalogue()));
        return shopObject;
    }
    public JsonObject SponsoredShopToJsonObject(SponsoredShop sponsoredShop) {
        JsonObject shopObject = new JsonObject();
        shopObject.addProperty("name", sponsoredShop.getName());
        shopObject.addProperty("description", sponsoredShop.getDescription());
        shopObject.addProperty("since", sponsoredShop.getSince());
        shopObject.addProperty("earnings", sponsoredShop.getEarnings());
        shopObject.addProperty("sponsorBrand", sponsoredShop.getSponsorBrand());
        shopObject.add("catalogue", gson.toJsonTree(sponsoredShop.getCatalogue()));
        return shopObject;
    }
    public JsonObject MaxProfitShopToJsonObject(MaxProfitShop maxProfitShop) {
        JsonObject shopObject = new JsonObject();
        shopObject.addProperty("name", maxProfitShop.getName());
        shopObject.addProperty("description", maxProfitShop.getDescription());
        shopObject.addProperty("since", maxProfitShop.getSince());
        shopObject.addProperty("earnings", maxProfitShop.getEarnings());
        shopObject.add("catalogue", gson.toJsonTree(maxProfitShop.getCatalogue()));
        return shopObject;
    }

    public void checkStatus() {
    }
}
