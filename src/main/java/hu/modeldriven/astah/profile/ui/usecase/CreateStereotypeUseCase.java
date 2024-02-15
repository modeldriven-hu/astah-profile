package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.event.CreateStereotypeRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class CreateStereotypeUseCase implements EventHandler<CreateStereotypeRequestedEvent> {

    @Override
    public void handleEvent(CreateStereotypeRequestedEvent event) {
        String stereotypeName =   (String)JOptionPane.showInputDialog(
                null,
                "Stereotype name:",
                "Create stereotype",
                JOptionPane.INFORMATION_MESSAGE, null, null, "");

        if (stereotypeName != null) {
            event.treeNode().profile().createChildStereotype(stereotypeName);
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(CreateStereotypeRequestedEvent.class);
    }
}
