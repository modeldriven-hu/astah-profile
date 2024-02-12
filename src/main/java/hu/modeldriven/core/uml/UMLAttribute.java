package hu.modeldriven.core.uml;

public interface UMLAttribute {

    String name();

    void modifyName(String name);

    UMLAttributeType type();

    void modifyType(UMLAttributeType type);

    Cardinality cardinality();

    void modifyCardinality(Cardinality cardinality);
}
