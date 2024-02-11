package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.command.ProfileCommand;
import hu.modeldriven.core.uml.event.UMLProfileCreatedEvent;

import java.util.List;

public class UMLModelImpl implements UMLModel, EventHandler<Event> {

    private UMLProfile profile;

    @Override
    public void handleEvent(Event event) {
        if (event instanceof UMLProfileCreatedEvent){
            this.profile = ((UMLProfileCreatedEvent) event).profile();
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return null;
    }

    @Override
    public void execute(ProfileCommand command) {
        // execute command on stack
    }

    @Override
    public UMLProfile profile() {
        return profile;
    }
}
