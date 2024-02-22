package hu.modeldriven.core.uml;

import java.util.List;

public interface UMLStereotype {

    String id();

    String name();

    void modifyName(String name);

    UMLMetaClass metaClass();

    void modifyMetaClass(UMLMetaClass metaClass);

    UMLProperty createChildProperty(String name, UMLPropertyType type);

    void removeProperty(UMLProperty attribute);

    List<UMLProperty> properties();

    boolean contains(UMLProperty property);
}
