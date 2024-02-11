package hu.modeldriven.core.uml;

public interface UMLAttribute {

    String name();

    UMLAttributeType type();

    int lowerBound();

    int upperBound();

}
