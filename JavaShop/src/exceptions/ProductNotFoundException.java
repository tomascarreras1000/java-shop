package exceptions;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(String product) {
        super("Product " + product + " not found");
    }
}
