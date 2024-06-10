package persistance;

import business.Shop;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopDAOLocal {
    private Gson gson;

    public ShopDAOLocal() {
        this.gson = new Gson();
    }

    public Shop[] readShop() {
        Shop[] shops = null;
        FileReader reader = null;
        try {
            File file = new File("files/shops.json");
            reader = new FileReader(file);
            shops = gson.fromJson(reader, Shop[].class);
        } catch (IOException e) {
            shops = new Shop[0];
        }
        return shops;
    }

    public void writeShop(Shop shop) {
        Shop[] shops = readShop();
        if (shops == null) {
            shops = new Shop[0];
        }
        ArrayList<Shop> list = new ArrayList<>(Arrays.asList(shops));
        list.add(shop);
        updateShops(list);
    }


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

    public void removeShop(int shopPosition) {
        Shop[] shops = readShop();
        ArrayList<Shop> shopList = new ArrayList<>(Arrays.asList(shops));
        shopList.remove(shopPosition);
        updateShops(shopList);
    }

    public void updateShop(Shop shopToUpdate) {
        Shop[] shops = readShop();
        for (int i = 0; i < shops.length; i++) {
            if (shops[i].getShopName() != null && shops[i].getShopName().equals(shopToUpdate.getShopName())) {
                shops[i] = shopToUpdate;
                updateShops(Arrays.asList(shops));
                return;
            }
        }
    }



    public List<Shop> getAllShops() {
        Shop[] shops = readShop();
        return Arrays.asList(shops);
    }
}
