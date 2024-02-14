package hu.modeldriven.astah.profile.ui.event;

import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProperty;

public class PropertySelectedEvent implements Event {

    private final UMLProperty property;

    public PropertySelectedEvent(UMLProperty property) {
        this.property = property;
    }

    public UMLProperty getProperty() {
        return property;
    }
}
