package business;

public class SponsoredShop extends Shop{
    private String sponsorBrand;
    public SponsoredShop(String name, String description, int since, float earnings, String sponsorBrand) {
        super(name, description, since, earnings);
        this.sponsorBrand = sponsorBrand;
    }
}
