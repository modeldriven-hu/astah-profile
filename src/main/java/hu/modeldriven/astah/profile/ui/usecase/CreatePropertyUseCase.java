package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.event.CreatePropertyRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;
import hu.modeldriven.core.uml.UMLPropertyType;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class CreatePropertyUseCase implements EventHandler<CreatePropertyRequestedEvent> {
    @Override
    public void handleEvent(CreatePropertyRequestedEvent event) {
        String propertyName = (String) JOptionPane.showInputDialog(
                null,
                "Property name:",
                "Create property",
                JOptionPane.INFORMATION_MESSAGE, null, null, "");

        if (propertyName != null) {
            event.treeNode().stereotype().createChildProperty(propertyName, UMLPropertyType.STRING);
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(CreatePropertyRequestedEvent.class);
    }
}
