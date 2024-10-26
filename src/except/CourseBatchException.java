package except;

public class CourseBatchException extends Exception {
    public CourseBatchException(String message) {
        super(message);
    }

    public CourseBatchException(String message, Throwable cause) {
        super(message, cause);
    }
}