package business;

public abstract class Product {

    protected String name;
    protected String brand;
    protected Category category;

    public enum Category {
        GENERAL,
        REDUCED,
        SUPER_REDUCED
    };
}
