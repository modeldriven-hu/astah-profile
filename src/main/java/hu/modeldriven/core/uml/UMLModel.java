package hu.modeldriven.core.uml;

import java.io.File;

public interface UMLModel {

    UMLProfile profile(File file);

    UMLProfile profile(String name, String namespaceURI);

}
