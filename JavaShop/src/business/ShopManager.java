package business;

import exceptions.BusinessException;
import exceptions.PersistanceException;
import exceptions.ProductAlreadyExistsException;
import persistance.ShopDAO;

public class ShopManager {
    private ShopDAO shopDAO;
    public ShopManager(ShopDAO shopDAO) {
        this.shopDAO = shopDAO;
    }

    public void createShop(String name, String description, int since) throws PersistanceException {
        Shop shop = new MaxProfitShop(name, description, since);
        shopDAO.createShop(shop);
    }

    public void createShop(String name, String description, int since, float loyaltyThreshold) throws PersistanceException{
        Shop shop = new LoyaltyShop(name, description, since, loyaltyThreshold);
        shopDAO.createShop(shop);
    }

    public void createShop(String name, String description, int since, String sponsorBrand)throws PersistanceException {
        Shop shop = new SponsoredShop(name, description, since, sponsorBrand);
        shopDAO.createShop(shop);
    }

    public String getBusinessModelFromOptions(String option) {
        if (option.equalsIgnoreCase("A")) {
            return "Maximum Profit";
        } else if (option.equalsIgnoreCase("B")) {
            return "Loyalty";
        } else if (option.equalsIgnoreCase("C")) {
            return "Sponsored";
        } else {
            return null;
        }
    }

    /**
     * Finds a shop with provided name. Note that this search is case-sensitive.
     * @param name
     * @return
     */
    public Shop findShopByName(String name) throws PersistanceException {
        Shop returnShop = null;
        for (Shop shop : shopDAO.getShops()) { // TODO: CHANGE FUNCTION NAME
            if (shop.getName().equals(name)) {
                returnShop = shop;
                break;
            }
        }
        return returnShop;
    }

    public void removeShop(Shop shop) throws PersistanceException {
        shopDAO.removeShop(shop);
    }

    public void addProductToShop(String shopName, RetailProduct product) throws BusinessException, PersistanceException {
        for (Shop shop : shopDAO.getShops()) {
            if (shop.getName().equalsIgnoreCase(shopName)) {
                for (RetailProduct retailProduct : shop.getCatalogue())
                    if (retailProduct.getName().equalsIgnoreCase(product.getName()))
                        throw new ProductAlreadyExistsException();
                shop.addProduct(product);
                shopDAO.updateShops(shop);
            }
        }
    }

    public void removeRetailProductFromShop(Shop shop, RetailProduct productToRemove) throws PersistanceException, Exception {
        if (shop.getCatalogue().contains(productToRemove)) {
            shop.getCatalogue().remove(productToRemove);
            shopDAO.updateShops(shop);
        }
        else
            throw new Exception("\nERROR Product does not belong to shop.");
    }

    public void removeBaseProduct(BaseProduct productToRemove) throws PersistanceException {
        for (Shop shop : shopDAO.getShops()) {
            for (RetailProduct retailProduct : shop.getCatalogue())
                if (retailProduct.getName().equalsIgnoreCase(productToRemove.getName())) {
                    shop.removeProduct(retailProduct);
                    shopDAO.updateShops(shop);
                }
        }
    }
}
