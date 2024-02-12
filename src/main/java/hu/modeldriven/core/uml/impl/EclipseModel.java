package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;

import java.io.File;

public class EclipseModel implements UMLModel {

    private final ResourceSet resourceSet;

    public EclipseModel() {
        this.resourceSet = new ResourceSetBuilder().create();
    }

    @Override
    public UMLProfile profile(File file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UMLProfile profile(String name, String namespaceURI) {

        Profile profile = UMLFactory.eINSTANCE.createProfile();
        profile.setName(name);
        profile.setURI(namespaceURI);

        return new EclipseProfile(profile, resourceSet);
    }
}
