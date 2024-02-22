package hu.modeldriven.core.uml;

import java.io.File;
import java.util.List;

public interface UMLProfile {

    String name();

    void modifyName(String name);

    String uri();

    void modifyUri(String uri);

    UMLStereotype createChildStereotype(String name);

    UMLStereotype createChildStereotype(String name, UMLMetaClass metaClass);

    void removeStereotype(UMLStereotype stereotype);

    List<UMLStereotype> stereotypes();

    boolean contains(UMLStereotype stereotype);

    void save(File file);

    UMLProfileDifference difference(UMLProfile newProfile);

}
