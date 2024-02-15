package hu.modeldriven.core.uml;

public interface UMLProperty {

    String id();

    String name();

    void modifyName(String name);

    UMLPropertyType type();

    void modifyType(UMLPropertyType type);
}
