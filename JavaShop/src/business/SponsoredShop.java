package business;

import exceptions.BusinessException;

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

    public String getSponsorBrand() {
        return sponsorBrand;
    }

    @Override
    public float purchaseProducts(LinkedList<RetailProduct> products) throws BusinessException {
        float newEarnings = 0;
        for (RetailProduct product : products) {
            newEarnings = newEarnings + getPriceFromProduct(product);
        }
        return newEarnings;
    }

    public float getPriceFromSponsoredProduct(RetailProduct product) {
        float newPrice = product.getRetailPrice() / (1 + (21 / 100));
        switch (product.getCategory()) {
            case "General":
                return newPrice / (1 + (21 / 100));
            case "Reduced":
                if (product.getAverageStars() > 3.5)
                    return newPrice / (1 + (5 / 100));
                return newPrice / (1 + (10 / 100));
            case "SuperReduced":
                if (product.getRetailPrice() > 100)
                    return newPrice;
                return newPrice / (1 + (4 / 100));
            default:
                return newPrice;
        }
    }
}
