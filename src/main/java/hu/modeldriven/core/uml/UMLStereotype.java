package hu.modeldriven.core.uml;

import java.util.List;

public interface UMLStereotype {

    String name();

    void modifyName(String name);

    UMLProperty property(String name, UMLPropertyType type);

    void addProperty(UMLProperty ... propertyList);

    void removeProperty(UMLProperty attribute);

    List<UMLProperty> properties();

}
