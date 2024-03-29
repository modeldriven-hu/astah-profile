package hu.modeldriven.core.uml.impl.eclipse;

import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;
import hu.modeldriven.core.uml.impl.generic.PrimitiveTypesInProfile;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Property;

public class EclipseUMLProperty implements UMLProperty {

    private final String id;
    private final Property property;
    private final PrimitiveTypesInProfile primitiveTypes;

    public EclipseUMLProperty(Property property, PrimitiveTypesInProfile primitiveTypes) {
        this.id = EcoreUtil.getIdentification(property);
        this.property = property;
        this.primitiveTypes = primitiveTypes;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.property.getName();
    }

    @Override
    public void modifyName(String name) {
        this.property.setName(name);
    }

    @Override
    public UMLPropertyType type() {
        return primitiveTypes.propertyType(property.getType());
    }

    @Override
    public void modifyType(UMLPropertyType type) {
        this.property.setType(primitiveTypes.primitiveType(type));
    }
}
