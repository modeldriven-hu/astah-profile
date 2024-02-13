package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;
import hu.modeldriven.core.uml.UMLStereotype;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EclipseStereotype implements UMLStereotype {

    private final Stereotype stereotype;
    private final EclipseRepresentation eclipseRepresentation;
    private final List<UMLProperty> properties;
    private final PrimitiveTypesInProfile primitiveTypes;
    private final MetaClassInProfile metaClasses;

    public EclipseStereotype(Stereotype stereotype, EclipseRepresentation eclipseRepresentation, PrimitiveTypesInProfile primitiveTypes, MetaClassInProfile metaClasses) {
        this.stereotype = stereotype;
        this.eclipseRepresentation = eclipseRepresentation;
        this.properties = new ArrayList<>();
        this.primitiveTypes = primitiveTypes;
        this.metaClasses = metaClasses;
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
        return metaClasses.metaClass(stereotype);
    }

    @Override
    public void modifyMetaClass(UMLMetaClass metaClass) {
        metaClasses.applyMetaClass(this.stereotype, metaClass);
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
