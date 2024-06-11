package business;

public class MaxProfitShop extends Shop{
    public MaxProfitShop(String name, String description, int since) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = 0f;
    }

    public MaxProfitShop(String name, String description, int since, float earnings) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
    }
}
