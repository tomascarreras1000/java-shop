package business;

public class LoyaltyShop extends Shop {
    private float loyaltyThreshold;

    public LoyaltyShop(String name, String description, int since, float loyaltyThreshold) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = 0f;
        this.loyaltyThreshold = loyaltyThreshold;
    }
}
