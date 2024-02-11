package hu.modeldriven.core.uml.command;

public class CommandExecutionFailedException extends Exception {

    public CommandExecutionFailedException(Exception e) {
        super(e);
    }

    public CommandExecutionFailedException(String message) {
        super(message);
    }

}
