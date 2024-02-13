package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;

import java.io.File;

public class EclipseModel implements UMLModel {

    private final EclipseRepresentation eclipseRepresentation;

    public EclipseModel() {
        this.eclipseRepresentation = new EclipseRepresentation();
    }

    @Override
    public UMLProfile createProfile(File file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UMLProfile createProfile(String name, String namespaceURI) {

        Profile profile = UMLFactory.eINSTANCE.createProfile();
        profile.setName(name);
        profile.setURI(namespaceURI);

        return new EclipseProfile(profile, eclipseRepresentation);
    }
}
