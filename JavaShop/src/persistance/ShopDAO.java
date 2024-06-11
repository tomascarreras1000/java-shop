package persistance;

import business.LoyaltyShop;
import business.MaxProfitShop;
import business.Shop;
import business.SponsoredShop;
import com.google.gson.*;
import exceptions.LocalFilesException;
import exceptions.PersistanceException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public interface ShopDAO {
    void checkStatus() throws PersistanceException;
    LinkedList<Shop> readShop() throws LocalFilesException;
    void removeShop(int shopPosition) throws LocalFilesException;
    void removeShop(Shop shopToRemove) throws LocalFilesException;
    void removeShop(String shopName) throws LocalFilesException;
    void updateShops(Shop shopToUpdate) throws LocalFilesException;
    void updateShops(List<Shop> shopList);
}
