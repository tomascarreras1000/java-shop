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
        return this.sponsorBrand;
    }

    @Override
    public float purchaseProducts(LinkedList<RetailProduct> products) throws BusinessException {
        float newEarnings = 0;
        boolean isSponsored = false;

        for (RetailProduct product : products) {
            if (product.getBrand().equals(this.sponsorBrand)) {
                isSponsored = true;
                break;
            }
        }

        for (RetailProduct product : products) {
            if (!isSponsored)
                newEarnings = newEarnings + calculateOriginalPrice(product);
            else newEarnings = newEarnings + calculateOriginalPriceSponsored(product);
        }

        this.earnings += newEarnings;
        return newEarnings;
    }
    public float calculateOriginalPriceSponsored(RetailProduct product) {
        float newPrice = product.getRetailPrice() / (1f + (10f / 100f));
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
}
