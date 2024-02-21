package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.core.uml.UMLProfile;

import java.io.File;
import java.nio.file.Path;

public class AxmzFileProfileSection {

    private final File modelFile;
    private final Path profilePath;
    private final UMLProfile umlProfile;

    public AxmzFileProfileSection(File modelFile, Path profilePath, UMLProfile umlProfile){
        this.modelFile = modelFile;
        this.profilePath = profilePath;
        this.umlProfile = umlProfile;
    }

    public File modelFile() {
        return modelFile;
    }

    public Path profilePath() {
        return profilePath;
    }

    public UMLProfile umlProfile() {
        return umlProfile;
    }
}
