package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.astah.axmz.ZipFile;
import hu.modeldriven.core.uml.UMLProfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class AxmzFileProfileSection {

    private final ZipFile modelFile;
    private final Path profilePath;
    private final UMLProfile umlProfile;

    public AxmzFileProfileSection(ZipFile modelFile, Path profilePath, UMLProfile umlProfile) {
        this.modelFile = modelFile;
        this.profilePath = profilePath;
        this.umlProfile = umlProfile;
    }

    public ZipFile modelFile() {
        return modelFile;
    }

    public Path profilePath() {
        return profilePath;
    }

    public UMLProfile umlProfile() {
        return umlProfile;
    }

    public void save(File targetFile) throws IOException {
        modelFile.copyFile(targetFile, profilePath, ZipFile.CopyDirection.FILE_FROM_ZIP);
    }

}
