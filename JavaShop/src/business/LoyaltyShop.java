package business;

import exceptions.BusinessException;

import java.util.LinkedList;

public class LoyaltyShop extends Shop {
    private float loyaltyThreshold;
    private float currentLoyalty;

    public LoyaltyShop(String name, String description, int since, float loyaltyThreshold) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = 0f;
        this.loyaltyThreshold = loyaltyThreshold;
        this.currentLoyalty = 0f;
        this.catalogue = new LinkedList<RetailProduct>();
    }

    // In case the current loyalty has to be persisted, currentLoyalty = earnings
    public LoyaltyShop(String name, String description, int since, float earnings, float loyaltyThreshold, LinkedList<RetailProduct> catalogue) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        this.loyaltyThreshold = loyaltyThreshold;
        this.currentLoyalty = earnings;
        this.catalogue = catalogue;
    }

    public float getLoyaltyThreshold() {
        return loyaltyThreshold;
    }

    public void setCurrentLoyalty(float newLoyalty) {
        this.currentLoyalty = newLoyalty;
    }

    public float getCurrentLoyalty() {
        return currentLoyalty;
    }

    public float purchaseProducts(LinkedList<RetailProduct> products) throws BusinessException {
        float newEarnings = 0;
        float initialLoyalty = currentLoyalty;

        for (RetailProduct product : products) {
            if (currentLoyalty >= loyaltyThreshold)
                newEarnings = newEarnings + calculateOriginalPrice(product);
            else newEarnings = newEarnings + calculateOriginalPriceLoyalty(product);
        }

        this.earnings += newEarnings;
        this.currentLoyalty += newEarnings;

        if (currentLoyalty >= loyaltyThreshold && initialLoyalty < loyaltyThreshold)
            return newEarnings*(-1);
        return newEarnings;
    }

    public float calculateOriginalPriceLoyalty(RetailProduct product) {
        float newPrice = calculateOriginalPrice(product);
        switch (product.getCategory()) {
            case "General":
                return newPrice / (1f + (21f / 100f));
            case "Reduced":
                if (product.getAverageStars() > 3.5)
                    return newPrice / (1f + (5f / 100f));
                return newPrice / (1f + (10f / 100f));
            case "SuperReduced":
                if (product.getRetailPrice() > 100)
                    return newPrice;
                return newPrice / (1f + (4f / 100f));
            default:
                return newPrice;
        }
    }
    @Override
    public float getProductPrice(RetailProduct product) {
        if (currentLoyalty >= loyaltyThreshold) {
            System.out.printf(String.valueOf(calculateOriginalPrice(product)));
            return calculateOriginalPrice(product);
        }
        return product.getRetailPrice();
    }
}
