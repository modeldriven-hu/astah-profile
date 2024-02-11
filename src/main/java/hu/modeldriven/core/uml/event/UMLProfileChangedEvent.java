package hu.modeldriven.core.uml.event;

import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.uml.UMLProfile;

public class UMLProfileChangedEvent implements Event {

    private final UMLProfile profile;

    public UMLProfileChangedEvent(UMLProfile profile){
        this.profile = profile;
    }

    public UMLProfile profile() {
        return profile;
    }
}
