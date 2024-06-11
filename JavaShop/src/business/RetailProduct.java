package business;

public class RetailProduct extends Product {
    private float retailPrice;

    /**
     * Throws exception if the provided price is negative
     */
    public RetailProduct(String name, String brand, String category, float retailPrice) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.retailPrice = retailPrice;
    }

    public RetailProduct(BaseProduct baseProduct, float retailPrice) {
        this.name = baseProduct.getName();
        this.brand = baseProduct.getBrand();
        this.category = baseProduct.getCategory();
        this.retailPrice = retailPrice;
    }
}
