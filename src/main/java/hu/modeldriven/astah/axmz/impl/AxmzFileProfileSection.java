package hu.modeldriven.astah.axmz.impl;

import hu.modeldriven.core.uml.UMLProfile;

import java.net.URI;
import java.nio.file.Path;

public class AxmzFileProfileSection {

    private final URI modelURI;
    private final Path profilePath;
    private final UMLProfile umlProfile;

    public AxmzFileProfileSection(URI modelURI, Path profilePath, UMLProfile umlProfile) {
        this.modelURI = modelURI;
        this.profilePath = profilePath;
        this.umlProfile = umlProfile;
    }

    public URI modelURI() {
        return modelURI;
    }

    public Path profilePath() {
        return profilePath;
    }

    public UMLProfile umlProfile() {
        return umlProfile;
    }
}
