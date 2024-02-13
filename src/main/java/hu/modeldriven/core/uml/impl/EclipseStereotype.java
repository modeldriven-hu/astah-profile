package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;
import hu.modeldriven.core.uml.UMLStereotype;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import java.util.List;
import java.util.stream.Collectors;

public class EclipseStereotype implements UMLStereotype {

    private final Stereotype stereotype;
    private final PrimitiveTypesInProfile primitiveTypes;
    private final MetaClassInProfile metaClasses;

    public EclipseStereotype(Stereotype stereotype, PrimitiveTypesInProfile primitiveTypes, MetaClassInProfile metaClasses) {
        this.stereotype = stereotype;
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

//    @Override
//    public UMLMetaClass metaClass() {
//        return metaClasses.metaClass(stereotype);
//    }

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
        this.stereotype.getAttributes().get(0).destroy();

        for (Property property : this.stereotype.getAttributes()){
                if (property.getName().equals(attribute.name())){
                    property.destroy();
                    break;
                }
        }
    }

    @Override
    public List<UMLProperty> properties() {
        return this.stereotype.getAttributes().stream()
                .filter(p -> !this.internalProperty(p))
                .map(property -> new EclipseProperty(property, primitiveTypes))
                .collect(Collectors.toList());
    }

    private boolean internalProperty(Property property) {
        return "base_Class".equals(property.getName());
    }
}
