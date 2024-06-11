package exceptions;

public class InvalidRetailPriceException extends BusinessException {
    public InvalidRetailPriceException(String invalidRetailPrice) {
        super(invalidRetailPrice);
    }
}
