public abstract class Product {

    protected String name;
    protected String brand;
    public enum Category {
        GENERAL,
        REDUCED,
        SUPER_REDUCED
    };
    protected Category category;
}
