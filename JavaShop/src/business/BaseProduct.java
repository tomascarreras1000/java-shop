package business;

import java.util.LinkedList;

public class BaseProduct extends Product {
    private float maxRetailPrice;
    private LinkedList<ProductReview> reviews;

    public BaseProduct(String name, String brand, String category, float maxRetailPrice) {
        this.name = name;
        this.brand = brand;
        this.category = AssignCategory(category);
        this.maxRetailPrice = maxRetailPrice;
        reviews = new LinkedList<ProductReview>();
    }

    private String AssignCategory(String category) {
        if (category.equalsIgnoreCase("A")) {
            return "General";
        } else if (category.equalsIgnoreCase("B")) {
            return "Reduced";
        } else if (category.equalsIgnoreCase("C")) {
            return "Super Reduced";
        } else
            return null;
    }


    /**
     * Throws exception if the provided price exceeds maxRetailPrice
     */
    public RetailProduct generateRetailProduct(float price) throws Exception {
        if (price > maxRetailPrice) {
            throw new Exception("Retail price exceeds max retail price");
        }
        return new RetailProduct(name, brand, category, price);
    }

    public void addReview(ProductReview review) {
        reviews.add(review);
    }
}
