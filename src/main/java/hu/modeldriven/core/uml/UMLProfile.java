package hu.modeldriven.core.uml;

import java.util.Collection;
import java.util.List;

public interface UMLProfile {

    // Mutable version
    void setName(String name);

    // Immutable version
    UMLProfile name(String name);

    String name();

    String uri();

    Collection<UMLStereotype> stereotypes();

}
