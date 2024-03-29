package hu.modeldriven.core.uml.impl.eclipse;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.UMLProperty;
import hu.modeldriven.core.uml.UMLPropertyType;
import hu.modeldriven.core.uml.UMLStereotype;
import hu.modeldriven.core.uml.impl.generic.MetaClassInProfile;
import hu.modeldriven.core.uml.impl.generic.PrimitiveTypesInProfile;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import java.util.List;
import java.util.stream.Collectors;

public class EclipseUMLStereotype implements UMLStereotype {

    private final String id;
    private final Stereotype stereotype;
    private final PrimitiveTypesInProfile primitiveTypes;
    private final MetaClassInProfile metaClasses;

    public EclipseUMLStereotype(Stereotype stereotype, PrimitiveTypesInProfile primitiveTypes, MetaClassInProfile metaClasses) {
        this.id = EcoreUtil.getIdentification(stereotype);
        this.stereotype = stereotype;
        this.primitiveTypes = primitiveTypes;
        this.metaClasses = metaClasses;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.stereotype.getName();
    }

    @Override
    public void modifyName(String name) {
        stereotype.setName(name);
    }

    @Override
    public UMLMetaClass metaClass() {
        return this.metaClasses.metaClass(stereotype);
    }

    @Override
    public void modifyMetaClass(UMLMetaClass metaClass) {
        if (metaClass != null) {
            metaClasses.setMetaClass(this.stereotype, metaClass);
        }
    }

    @Override
    public UMLProperty createChildProperty(String name, UMLPropertyType type) {

        Property property = stereotype.createOwnedAttribute(
                name,
                primitiveTypes.primitiveType(type),
                0,
                1);

        return new EclipseUMLProperty(property, primitiveTypes);
    }

    @Override
    public void removeProperty(UMLProperty attribute) {

        for (Property property : this.stereotype.getAttributes()) {
            if (property.getName().equals(attribute.name())) {
                property.destroy();
                break;
            }
        }
    }

    @Override
    public List<UMLProperty> properties() {
        return this.stereotype.getAttributes().stream()
                .filter(p -> !this.internalProperty(p))
                .map(property -> new EclipseUMLProperty(property, primitiveTypes))
                .collect(Collectors.toList());
    }

    private boolean internalProperty(Property property) {
        return property.getName().startsWith("base_");
    }

    @Override
    public boolean contains(UMLProperty property) {
        return properties().stream()
                .anyMatch(p ->
                        p.name().equals(property.name()) &&
                                p.type().equals(property.type()));
    }

}
