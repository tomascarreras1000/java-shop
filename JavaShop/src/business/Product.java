package business;

public abstract class Product {

    protected String name;
    protected String brand;
    protected Category category;

    public String getName() {
        return this.name;
    }

    public enum Category {
        GENERAL,
        REDUCED,
        SUPER_REDUCED
    };
}
