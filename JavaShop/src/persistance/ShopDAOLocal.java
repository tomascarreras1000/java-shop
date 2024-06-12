package persistance;

import business.*;
import com.google.gson.*;
import exceptions.LocalFilesException;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ShopDAOLocal implements ShopDAO {
    private Gson gson;

    public ShopDAOLocal() {
        this.gson = new Gson();
    }

    public LinkedList<Shop> getShops() throws LocalFilesException {
        LinkedList<Shop> shops = new LinkedList<>();
        try (FileReader reader = new FileReader("JavaShop/files/shops.json")) {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(reader);

            if (jsonElement.isJsonArray()) {
                for (JsonElement element : jsonElement.getAsJsonArray()) {
                    if (element.isJsonObject()) {
                        JsonObject shopObject = element.getAsJsonObject();

                        LinkedList<RetailProduct> list = new LinkedList();
                        for (JsonElement e : shopObject.get("catalogue").getAsJsonArray()) {
                            list.add(new RetailProduct(e.getAsJsonObject().get("name").getAsString(), e.getAsJsonObject().get("brand").getAsString(), e.getAsJsonObject().get("category").getAsString(), e.getAsJsonObject().get("retailPrice").getAsFloat()));
                        }

                        switch (shopObject.get("businessModel").getAsString()) {
                            case "SPONSORED":
                                SponsoredShop sponsoredShop = new SponsoredShop(shopObject.get("name").getAsString(), shopObject.get("description").getAsString(), shopObject.get("since").getAsInt(), shopObject.get("earnings").getAsFloat(), shopObject.get("sponsorBrand").getAsString(), list);
                                shops.add(sponsoredShop);
                                break;
                            case "LOYALTY":
                                LoyaltyShop loyaltyShop = new LoyaltyShop(shopObject.get("name").getAsString(), shopObject.get("description").getAsString(), shopObject.get("since").getAsInt(), shopObject.get("earnings").getAsFloat(), shopObject.get("loyaltyThreshold").getAsInt(), list);
                                shops.add(loyaltyShop);
                                break;
                            case "MAX_PROFIT":
                                MaxProfitShop maxProfitShop = new MaxProfitShop(shopObject.get("name").getAsString(), shopObject.get("description").getAsString(), shopObject.get("since").getAsInt(), shopObject.get("earnings").getAsFloat(), list);
                                shops.add(maxProfitShop);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new LocalFilesException("Error: The shops.json file can’t be accessed.");
        } catch (IOException e) {
            shops = new LinkedList<>();
        }
        return shops;
    }

    /**
     * Removes a shop from the list of shops and updates the file
     *
     * @param shopToRemove
     */
    public void removeShop(Shop shopToRemove) throws LocalFilesException {
        LinkedList<Shop> shops = getShops();
        for (Shop shop : shops) {
            if (shop.getName().equals(shopToRemove.getName())) {
                shops.remove(shop);
                break;
            }
        }
        updateShops(shops);
    }

    /**
     * Updates a shop, replacing the old one with the new one in the list of shops and updating the file
     *
     * @param shopToUpdate
     */
    public void updateShops(Shop shopToUpdate) throws LocalFilesException {
        LinkedList<Shop> shops = getShops();
        for (Shop shop : shops) {
            if (shop.getName().equals(shopToUpdate.getName())) {
                shops.remove(shop);
                shops.add(shopToUpdate);
                break;
            }
        }
        updateShops(shops);
    }

    /**
     * Updates the list of shops in the file shops.json with the new list of shops passed as parameter shopList
     *
     * @param shopList
     */
    public void updateShops(LinkedList<Shop> shopList) {

        JsonArray shopsArray = new JsonArray();

        for (Shop shop : shopList) {
            if (shop instanceof SponsoredShop) {
                shopsArray.add(SponsoredShopToJsonObject((SponsoredShop) shop));
            } else if (shop instanceof LoyaltyShop) {
                shopsArray.add(LoyaltyShopToJsonObject((LoyaltyShop) shop));
            } else if (shop instanceof MaxProfitShop) {
                shopsArray.add(MaxProfitShopToJsonObject((MaxProfitShop) shop));
            }
        }

        try (FileWriter writer = new FileWriter("JavaShop/files/shops.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(shopsArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkStatus() throws LocalFilesException {
        getShops();
    }

    public void createShop(Shop shop) throws LocalFilesException {
        LinkedList<Shop> shops = getShops();
        shops.add(shop);
        updateShops(shops);
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
