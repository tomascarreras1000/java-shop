package business;

import java.util.LinkedList;

public class MaxProfitShop extends Shop {
    public MaxProfitShop(String name, String description, int since) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = 0f;
        this.catalogue = new LinkedList<RetailProduct>();
    }

    public MaxProfitShop(String name, String description, int since, float earnings, LinkedList<RetailProduct> catalogue) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        this.catalogue = catalogue;
    }
}
