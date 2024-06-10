package exceptions;

public class APINotWorkingException extends PersistanceException {
    public APINotWorkingException(String s) {
        super(s);
    }
}
