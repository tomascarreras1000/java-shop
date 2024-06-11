package business;

public class SponsoredShop extends Shop {
    private String sponsorBrand;

    public SponsoredShop(String name, String description, int since, String sponsorBrand) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = 0f;
        this.sponsorBrand = sponsorBrand;
    }
}
