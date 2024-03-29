package hu.modeldriven.core.uml.impl.simple;

import hu.modeldriven.core.uml.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleUMLStereotype implements UMLStereotype {

    private final String id;
    private final UMLProfile parent;

    private final List<UMLProperty> properties;

    private String name;

    private UMLMetaClass metaClass;

    public SimpleUMLStereotype(UMLProfile parent, String name) {
        this(parent, name, UMLMetaClass.CLASS);
    }

    public SimpleUMLStereotype(UMLProfile parent, String name, UMLMetaClass metaClass) {
        this.id = UUID.randomUUID().toString();
        this.parent = parent;
        this.properties = new ArrayList<>();
        this.name = name;
        this.metaClass = metaClass;
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
    public UMLMetaClass metaClass() {
        return this.metaClass;
    }

    @Override
    public void modifyMetaClass(UMLMetaClass metaClass) {
        this.metaClass = metaClass;
    }

    @Override
    public UMLProperty createChildProperty(String name, UMLPropertyType type) {

        UMLProperty property = new SimpleUMLProperty(this, name, type);
        this.properties.add(property);

        return property;
    }

    @Override
    public void removeProperty(UMLProperty attribute) {
        this.properties.removeIf(a -> a.id().equals(attribute.id()));
    }

    @Override
    public List<UMLProperty> properties() {
        return this.properties;
    }

    @Override
    public boolean contains(UMLProperty property) {
        return properties().stream()
                .anyMatch(p ->
                        p.name().equals(property.name()) &&
                                p.type().equals(property.type()));
    }
}
