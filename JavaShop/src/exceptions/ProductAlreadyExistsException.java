package exceptions;

public class ProductAlreadyExistsException extends BusinessException {
    public ProductAlreadyExistsException() {
        super("Product already exists");
    }
}
