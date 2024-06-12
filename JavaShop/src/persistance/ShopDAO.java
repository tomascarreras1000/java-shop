package persistance;

import business.Shop;

import exceptions.LocalFilesException;
import exceptions.PersistanceException;

import java.util.LinkedList;
import java.util.List;

public interface ShopDAO {
    void checkStatus() throws PersistanceException;
    LinkedList<Shop> getShops() throws PersistanceException;
    void removeShop(int shopPosition) throws PersistanceException;
    void removeShop(Shop shopToRemove) throws PersistanceException;
    void removeShop(String shopName) throws PersistanceException;
    void updateShops(Shop shopToUpdate) throws PersistanceException;
    void createShop(Shop shop) throws PersistanceException;
    void updateShops(LinkedList<Shop> shopList);
}
