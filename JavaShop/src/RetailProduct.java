public class RetailProduct extends Product {
    private float retailPrice;

    /** Throws exception if the provided price is negative */
    public RetailProduct(String name, String brand, Category category, float retailPrice) throws Exception {
        this.name = name;
        this.brand = brand;
        this.category = category;
        if (retailPrice < 0)
            throw new Exception("Invalid retail price");
        this.retailPrice = retailPrice;
    }
}
