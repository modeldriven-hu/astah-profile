package hu.modeldriven.core.uml;

import java.util.List;

public interface UMLStereotype {

    String name();

    void modifyName(String name);

    // FIXME does not work
    //UMLMetaClass metaClass();

    void modifyMetaClass(UMLMetaClass metaClass);

    UMLProperty createChildProperty(String name, UMLPropertyType type);

    void removeProperty(UMLProperty attribute);

    List<UMLProperty> properties();

}
