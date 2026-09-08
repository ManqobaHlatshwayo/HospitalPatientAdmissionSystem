package hospitalsystem;

public class BedUnavailableException extends Exception {
    public BedUnavailableException(String message) {
        super(message);
    }
}