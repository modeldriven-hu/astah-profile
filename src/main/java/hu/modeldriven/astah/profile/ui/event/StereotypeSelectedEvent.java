package hu.modeldriven.astah.profile.ui.event;

import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.uml.UMLStereotype;

public class StereotypeSelectedEvent implements Event {

    private final UMLStereotype stereotype;

    public StereotypeSelectedEvent(UMLStereotype stereotype) {
        this.stereotype = stereotype;
    }

    public UMLStereotype stereotype() {
        return stereotype;
    }
}
