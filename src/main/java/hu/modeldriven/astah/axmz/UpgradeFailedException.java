package hu.modeldriven.astah.axmz;

public class UpgradeFailedException extends Exception {

    public UpgradeFailedException(Exception e) {
        super(e);
    }

    public UpgradeFailedException(String message) {
        super(message);
    }

}
