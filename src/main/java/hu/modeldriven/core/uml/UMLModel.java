package hu.modeldriven.core.uml;

import java.io.File;

public interface UMLModel {

    UMLProfile createProfile(File file) throws ProfileCreationFailedException;

    UMLProfile createProfile(String name, String namespaceURI);

}
