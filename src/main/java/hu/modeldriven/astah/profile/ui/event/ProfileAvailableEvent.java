package hu.modeldriven.astah.profile.ui.event;

import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.uml.UMLProfile;

public class ProfileAvailableEvent implements Event {

    private final UMLProfile profile;

    public ProfileAvailableEvent(UMLProfile profile) {
        this.profile = profile;
    }

    public UMLProfile profile() {
        return profile;
    }
}
