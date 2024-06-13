package business;

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

    public LoyaltyShop(String name, String description, int since, float earnings, float loyaltyThreshold, LinkedList<RetailProduct> catalogue) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        this.loyaltyThreshold = loyaltyThreshold;
        this.currentLoyalty = 0f;
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
}
