package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.profile.ui.event.LoadProfileRequestedEvent;
import hu.modeldriven.astah.profile.ui.event.ProfileAvailableEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class LoadProfileUseCase implements EventHandler<LoadProfileRequestedEvent> {

    private final EventBus eventBus;
    private final Component parentComponent;
    private final UMLModel model;

    public LoadProfileUseCase(EventBus eventBus, Component parentComponent, UMLModel model){
        this.eventBus = eventBus;
        this.parentComponent = parentComponent;
        this.model = model;
    }

    @Override
    public void handleEvent(LoadProfileRequestedEvent event) {
        final JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("UML profiles", "uml");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(parentComponent) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                UMLProfile profile = model.createProfile(file);
                eventBus.publish(new ProfileAvailableEvent(profile));
            } catch (Exception e) {
                eventBus.publish(new ExceptionOccurredEvent(e));
            }
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Collections.singletonList(LoadProfileRequestedEvent.class);
    }
}
