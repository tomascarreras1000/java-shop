package business;

public abstract class Product {

    protected String name;
    protected String brand;
    protected String category;

    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return "\"" + name + "\" by \"" + brand +"\"";
    }
}
