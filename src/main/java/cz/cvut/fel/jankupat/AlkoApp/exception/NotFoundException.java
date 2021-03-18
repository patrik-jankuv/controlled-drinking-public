package cz.cvut.fel.jankupat.AlkoApp.exception;

/**
 * The type Not found exception.
 *
 * @author Patrik Jankuv
 * @created 8 /3/2020
 */
public class NotFoundException extends BaseException {

    /**
     * Instantiates a new Not found exception.
     *
     * @param message the message
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create not found exception.
     *
     * @param resourceName the resource name
     * @param identifier   the identifier
     * @return the not found exception
     */
    public static NotFoundException create(String resourceName, Object identifier) {
        return new NotFoundException(resourceName + " identified by " + identifier + " not found.");
    }
}