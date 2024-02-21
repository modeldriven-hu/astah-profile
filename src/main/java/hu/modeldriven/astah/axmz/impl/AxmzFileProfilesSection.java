package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.astah.axmz.AxmzFileSection;
import hu.modeldriven.astah.axmz.ZipFile;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AxmzFileProfilesSection implements AxmzFileSection {

    private final ZipFile modelFile;
    private final UMLModel model;
    private final List<AxmzFileProfileSection> profileSections;

    public AxmzFileProfilesSection(ZipFile modelFile, UMLModel model, List<AxmzFileProfileSection> profiles) {
        this.modelFile = modelFile;
        this.model = model;
        this.profileSections = profiles;
    }

    @Override
    public boolean appliesTo(Path file) {
        return file.toUri().getSchemeSpecificPart().endsWith(".profile.uml");
    }

    @Override
    public void process(Path file) {
        try {
            // Because the model cannot read a file directly from a zip file
            // we need to copy it out into a temporary file, and use this file as an
            // input to create a profile

            File tempFile = File.createTempFile("process-", ".profile.uml");
            tempFile.deleteOnExit();

            Files.copy(file, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            UMLProfile umlProfile = model.profile(tempFile);
            profileSections.add(new AxmzFileProfileSection(modelFile, file, umlProfile));
        } catch (Exception e) {
            e.printStackTrace();
            // In any case of error we will log and continue
        }
    }
}
