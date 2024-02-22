package hu.modeldriven.astah.profile.ui.usecase;

import hu.modeldriven.astah.axmz.AstahProject;
import hu.modeldriven.astah.axmz.AxmzFile;
import hu.modeldriven.astah.axmz.impl.AxmzFileImpl;
import hu.modeldriven.astah.axmz.UpgradePlan;
import hu.modeldriven.astah.profile.ui.event.ExceptionOccurredEvent;
import hu.modeldriven.astah.profile.ui.event.ProfileAvailableEvent;
import hu.modeldriven.astah.profile.ui.event.UpdateModelRequestedEvent;
import hu.modeldriven.core.eventbus.Event;
import hu.modeldriven.core.eventbus.EventBus;
import hu.modeldriven.core.eventbus.EventHandler;
import hu.modeldriven.core.uml.UMLProfile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class UpdateModelUseCase implements EventHandler<Event> {

    private final EventBus eventBus;

    private final Component parentComponent;

    private UMLProfile umlProfile;

    public UpdateModelUseCase(EventBus eventBus, Component parentComponent){
        this.eventBus = eventBus;
        this.parentComponent = parentComponent;
    }

    @Override
    public void handleEvent(Event event) {

        if (event instanceof ProfileAvailableEvent){
            this.umlProfile = ((ProfileAvailableEvent) event).profile();
        }

        if (event instanceof UpdateModelRequestedEvent && this.umlProfile != null){
            handleUpdateModelRequested();
        }

    }

    private void handleUpdateModelRequested() {
        final JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Astah model", "axmz");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(parentComponent) == JFileChooser.APPROVE_OPTION) {
            try {
                updateModel(fileChooser.getSelectedFile());
            } catch (Exception e) {
                eventBus.publish(new ExceptionOccurredEvent(e));
            }
        }
    }

    private void updateModel(File modelFile) throws Exception{
        AxmzFile axmzFile = new AxmzFileImpl(modelFile);
        AstahProject project = axmzFile.project();

        UpgradePlan plan = project.upgradeProfile(umlProfile);

        plan.execute(upgradedFile(modelFile));
    }

    private File upgradedFile(File modelFile){

        Path parentDirectory = modelFile.toPath().getParent();

        // Get the filename and extension of the existing file
        String fileName = modelFile.getName();
        String extension = "";

        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex > 0) {
            extension = fileName.substring(extensionIndex);
            fileName = fileName.substring(0, extensionIndex);
        }

        // Create the new filename with extended information
        String newFileName = fileName + "-" + "upgraded" + extension;

        // Resolve the new file path in the same directory
        Path newFilePath = parentDirectory.resolve(newFileName);

        // Create the new file
        return new File(newFilePath.toString());
    }

    @Override
    public List<Class<? extends Event>> subscribedEvents() {
        return Arrays.asList(ProfileAvailableEvent.class, UpdateModelRequestedEvent.class);
    }
}
