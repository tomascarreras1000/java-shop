package business;

import java.util.LinkedList;

public class SponsoredShop extends Shop {
    private String sponsorBrand;

    public SponsoredShop(String name, String description, int since, String sponsorBrand) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = 0f;
        this.sponsorBrand = sponsorBrand;
        this.catalogue = new LinkedList<RetailProduct>();

    }

    public SponsoredShop(String name, String description, int since, float earnings, String sponsorBrand, LinkedList<RetailProduct> catalogue) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        this.sponsorBrand = sponsorBrand;
        this.catalogue = catalogue;
    }
}
