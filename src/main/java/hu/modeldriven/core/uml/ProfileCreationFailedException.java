package hu.modeldriven.core.uml;

public class ProfileCreationFailedException extends Exception {

    public ProfileCreationFailedException(Exception e) {
        super(e);
    }

    public ProfileCreationFailedException(String message) {
        super(message);
    }
}
