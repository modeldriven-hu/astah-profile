package hu.modeldriven.core.uml;

import java.util.Collection;
import java.util.List;

public interface UMLProfile {

    void setName(String name);

    String name();

    String uri();

    void setUri(String uri);

    Collection<UMLStereotype> stereotypes();

}
