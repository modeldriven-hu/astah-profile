package hu.modeldriven.core.uml.simpleimpl;

import hu.modeldriven.core.uml.ProfileCreationFailedException;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;

import java.io.File;

public class SimpleUMLModel implements UMLModel {

    @Override
    public UMLProfile createProfile(File file) throws ProfileCreationFailedException {
        return null;
    }

    @Override
    public UMLProfile createProfile(String name, String namespaceURI) {
        return new SimpleUMLProfile(name, namespaceURI);
    }
}
