package business;

public class LoyaltyShop extends Shop {
    private float loyaltyThreshold;
    public LoyaltyShop(String name, String description, int since, float earnings, float loyaltyThreshold) {
        super(name, description, since, earnings);
        this.loyaltyThreshold = loyaltyThreshold;
    }
}
