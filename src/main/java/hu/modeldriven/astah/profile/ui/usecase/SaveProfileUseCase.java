package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.profile.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.profile.ui.event.ProfileAvailableEvent;
import hu.modeldriven.astah.profile.ui.event.SaveProfileRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;
import hu.modeldriven.core.uml.UMLProfile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SaveProfileUseCase implements EventHandler<Event> {

    public static final String FILE_EXTENSION = ".uml";
    private final EventBus eventBus;
    private final Component parentComponent;

    private UMLProfile profile;

    public SaveProfileUseCase(EventBus eventBus, Component parentComponent) {
        this.eventBus = eventBus;
        this.parentComponent = parentComponent;
    }

    @Override
    public void handleEvent(Event event) {

        if (event instanceof ProfileAvailableEvent){
            this.profile = ((ProfileAvailableEvent) event).profile();
        }

        if (event instanceof SaveProfileRequestedEvent && this.profile != null) {
            saveFile();
        }
    }

    private void saveFile(){
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            FileNameExtensionFilter filter = new FileNameExtensionFilter("UML Profiles", FILE_EXTENSION);
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setFileFilter(filter);

            if (fileChooser.showSaveDialog(parentComponent) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = fileChooser.getSelectedFile();

            if (file.exists()) {
                int result = JOptionPane.showConfirmDialog(null,
                        "The file already exists. Do you want to overwrite it?",
                        "File Already Exists",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            if (!file.getName().endsWith(FILE_EXTENSION)) {
                file = new File(file.getParent() + File.separator + file.getName() + FILE_EXTENSION);
            }

            profile.save(file);

        } catch (Exception e) {
            eventBus.publish(new ExceptionOccurredEvent(e));
        }
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Arrays.asList(SaveProfileRequestedEvent.class, ProfileAvailableEvent.class);
    }
}
