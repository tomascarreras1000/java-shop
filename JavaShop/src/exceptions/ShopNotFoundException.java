package exceptions;

public class ShopNotFoundException extends BusinessException {
    public ShopNotFoundException(String shop) {
        super("Shop " + shop + " not found");
    }
}
