package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.core.zip.ZipFile;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AxmzModelProfilePart implements AxmzModelPart {

    private final ZipFile modelFile;
    private final UMLModel model;
    private final List<AxmzModelProfile> profiles;

    public AxmzModelProfilePart(ZipFile modelFile, UMLModel model, List<AxmzModelProfile> profiles) {
        this.modelFile = modelFile;
        this.model = model;
        this.profiles = profiles;
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
            profiles.add(new AxmzModelProfile(modelFile, file, umlProfile));
        } catch (Exception e) {
            e.printStackTrace();
            // In any case of error we will log and continue
        }
    }
}
