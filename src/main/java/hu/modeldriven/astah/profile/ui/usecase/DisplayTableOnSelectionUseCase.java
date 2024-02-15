package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.components.table.ProfileTableModel;
import hu.modeldriven.astah.profile.ui.components.table.PropertyTableModel;
import hu.modeldriven.astah.profile.ui.components.table.StereotypeTableModel;
import hu.modeldriven.astah.profile.ui.event.ProfileSelectedEvent;
import hu.modeldriven.astah.profile.ui.event.PropertySelectedEvent;
import hu.modeldriven.astah.profile.ui.event.StereotypeSelectedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventHandler;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLStereotype;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class DisplayTableOnSelectionUseCase implements EventHandler<Event> {

    private final JTable table;

    public DisplayTableOnSelectionUseCase(JTable table) {
        this.table = table;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof ProfileSelectedEvent) {
            handleProfileSelected(((ProfileSelectedEvent) event).profile());
        }

        if (event instanceof StereotypeSelectedEvent){
            handleStereotypeSelected(((StereotypeSelectedEvent)event).stereotype());
        }

        if (event instanceof PropertySelectedEvent){
            handlePropertySelected(((PropertySelectedEvent)event).property());
        }
    }

    private void handlePropertySelected(UMLProperty property) {
        PropertyTableModel propertyTableModel = new PropertyTableModel(property);
        table.setModel(propertyTableModel);
    }

    private void handleProfileSelected(UMLProfile profile) {
        ProfileTableModel tableModel = new ProfileTableModel(profile);
        table.setModel(tableModel);
    }

    private void handleStereotypeSelected(UMLStereotype stereotype) {
        StereotypeTableModel tableModel = new StereotypeTableModel(stereotype);
        table.setModel(tableModel);
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Arrays.asList(
                ProfileSelectedEvent.class,
                StereotypeSelectedEvent.class,
                PropertySelectedEvent.class
        );
    }
}
