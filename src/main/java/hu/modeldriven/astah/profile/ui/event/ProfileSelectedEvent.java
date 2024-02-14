package hu.modeldriven.astah.profile.ui.event;

import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.uml.UMLProfile;

public class ProfileSelectedEvent implements Event {

    private final UMLProfile profile;

    public ProfileSelectedEvent(UMLProfile profile){
        this.profile = profile;
    }

    public UMLProfile profile() {
        return profile;
    }
}
