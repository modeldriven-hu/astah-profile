package hu.modeldriven.core.uml.command;

import java.util.concurrent.ExecutionException;

public class CommandExecutionFailedRuntimeException extends RuntimeException {

    public CommandExecutionFailedRuntimeException(Exception e){
        super(e);
    }

}
