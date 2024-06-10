public class BaseProduct extends Product {
    private float maxRetailPrice;

    private BaseProduct(String name, String brand, Category category, float maxRetailPrice) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.maxRetailPrice = maxRetailPrice;
    }

    /** Throws exception if the provided price exceeds maxRetailPrice */
    public RetailProduct generateRetailProduct(float price) throws Exception {
        if (price > maxRetailPrice) {
            throw new Exception("Retail price exceeds max retail price");
        }
        return new RetailProduct(name, brand, category, price);
    }
}
