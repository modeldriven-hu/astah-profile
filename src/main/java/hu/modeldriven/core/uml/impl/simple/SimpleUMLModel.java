package hu.modeldriven.core.uml.impl.simple;

import hu.modeldriven.core.uml.ProfileCreationFailedException;
import hu.modeldriven.core.uml.UMLModel;
import hu.modeldriven.core.uml.UMLProfile;
import hu.modeldriven.core.uml.UMLProfileDifference;
import hu.modeldriven.core.uml.impl.difference.UMLProfileDifferenceImpl;

import java.io.File;

public class SimpleUMLModel implements UMLModel {

    @Override
    public UMLProfile profile(File file) throws ProfileCreationFailedException {
        return new SimpleUMLProfileFromFile(file).umlProfile();
    }

    @Override
    public UMLProfile profile(String name, String namespaceURI) {
        return new SimpleUMLProfile(name, namespaceURI);
    }

    @Override
    public UMLProfileDifference difference(UMLProfile originalProfile, UMLProfile newProfile) {
        return new UMLProfileDifferenceImpl(originalProfile, newProfile);
    }
}
