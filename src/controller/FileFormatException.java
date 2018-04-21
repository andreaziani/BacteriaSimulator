package controller;

/**
 * Exception that represents an attempt to read an object of a specific class
 * from a file that does not represents an object of that class.
 */
public class FileFormatException extends RuntimeException {

    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -9160480880215185673L;

    /**
     * Construct a new FileFormatException with a default error message.
     */
    public FileFormatException() {
        super("The format of the file is not valid for this request");
    }
}
