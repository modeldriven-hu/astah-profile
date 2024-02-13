package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;
import hu.modeldriven.core.uml.UMLStereotype;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EclipseStereotype implements UMLStereotype {

    private final Stereotype stereotype;
    private final EclipseRepresentation eclipseRepresentation;
    private final List<UMLProperty> properties;

    private final PrimitiveTypesInProfile primitiveTypes;

    public EclipseStereotype(Stereotype stereotype, EclipseRepresentation eclipseRepresentation, PrimitiveTypesInProfile primitiveTypes) {
        this.stereotype = stereotype;
        this.eclipseRepresentation = eclipseRepresentation;
        this.properties = new ArrayList<>();
        this.primitiveTypes = primitiveTypes;
    }

    @Override
    public String name() {
        return stereotype.getName();
    }

    @Override
    public void modifyName(String name) {
        stereotype.setName(name);
    }

    @Override
    public UMLMetaClass metaClass() {
        return null;
    }

    @Override
    public void modifyMetaClass(UMLMetaClass metaClass) {
    }

    @Override
    public UMLProperty createChildProperty(String name, UMLPropertyType type) {

        Property property = stereotype.createOwnedAttribute(
                name,
                primitiveTypes.primitiveType(type),
                0,
                1);

        return new EclipseProperty(property, primitiveTypes);
    }

    @Override
    public void removeProperty(UMLProperty attribute) {
        this.properties.remove(attribute);
    }

    @Override
    public List<UMLProperty> properties() {
        return Collections.unmodifiableList(this.properties);
    }
}
