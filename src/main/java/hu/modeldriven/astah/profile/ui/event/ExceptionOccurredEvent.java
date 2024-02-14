package hu.modeldriven.astah.profile.ui.event;

import hu.modeldriven.core.eventbus.Event;

public class ExceptionOccurredEvent implements Event {
    private final Exception exception;

    public ExceptionOccurredEvent(Exception exception) {
        this.exception = exception;
    }

    public Exception exception() {
        return exception;
    }
}
