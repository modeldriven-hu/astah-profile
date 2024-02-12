package hu.modeldriven.core.uml;

import java.util.List;

public interface UMLStereotype {

    String name();

    void modifyName(String name);

    UMLAttribute attribute(String name, UMLAttributeType type, Cardinality cardinality);

    void addAttribute(UMLAttribute attribute);

    void removeAttribute(UMLAttribute attribute);

    List<UMLAttribute> attributes();

}
