package hu.modeldriven.core.uml.impl.simple;

import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;
import hu.modeldriven.core.uml.UMLStereotype;

import java.util.UUID;

public class SimpleUMLProperty implements UMLProperty {

    private final String id;

    private final UMLStereotype parent;

    private String name;

    private UMLPropertyType type;

    public SimpleUMLProperty(UMLStereotype parent, String name, UMLPropertyType type) {
        this.id = UUID.randomUUID().toString();
        this.parent = parent;
        this.name = name;
        this.type = type;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void modifyName(String name) {
        this.name = name;
    }

    @Override
    public UMLPropertyType type() {
        return this.type;
    }

    @Override
    public void modifyType(UMLPropertyType type) {
        this.type = type;
    }
}
