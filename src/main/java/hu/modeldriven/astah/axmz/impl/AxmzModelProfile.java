package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.core.zip.ZipFile;
import hu.modeldriven.core.uml.UMLProfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class AxmzModelProfile {

    private final ZipFile modelFile;
    private final Path profilePath;
    private final UMLProfile umlProfile;

    public AxmzModelProfile(ZipFile modelFile, Path profilePath, UMLProfile umlProfile) {
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
