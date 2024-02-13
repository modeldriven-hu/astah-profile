package hu.modeldriven.astah.profile.event;

import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.uml.UMLProfile;

public class UMLProfileModifiedEvent implements Event {

    private final UMLProfile profile;

    public UMLProfileModifiedEvent(UMLProfile profile) {
        this.profile = profile;
    }

    public UMLProfile profile() {
        return profile;
    }
}
