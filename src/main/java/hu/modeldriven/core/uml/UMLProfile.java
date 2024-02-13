package hu.modeldriven.core.uml;

import java.io.File;
import java.util.List;

public interface UMLProfile {

    String name();

    void modifyName(String name);

    String uri();

    void modifyUri(String uri);

    UMLStereotype createChildStereotype(String name);

    void removeStereotype(UMLStereotype stereotype);

    List<UMLStereotype> stereotypes();

    void save(File file);

}
