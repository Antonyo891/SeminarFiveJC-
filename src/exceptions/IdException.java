package exceptions;

public class IdException extends Exception {

    private int id;
    public IdException(String s) {
    }

    public IdException(int id, String message) {
        super(message);
        this.id = id;
    }
}
