package hu.modeldriven.core.uml;

import java.util.List;

public interface UMLProfile {

    String name();

    String uri();

    List<UMLStereotype> stereotypes();

}
