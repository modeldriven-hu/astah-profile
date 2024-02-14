package hu.modeldriven.core.uml.impl;

import hu.modeldriven.core.uml.UMLMetaClass;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.resource.UMLResource;

import java.util.EnumMap;
import java.util.Map;

public class MetaClassInProfile {

    private final Profile profile;
    private final Map<UMLMetaClass, org.eclipse.uml2.uml.Class> metaClassMap;

    public MetaClassInProfile(Profile profile, EclipseRepresentation eclipseRepresentation) {
        this.profile = profile;
        this.metaClassMap = new EnumMap<>(UMLMetaClass.class);

        Model umlMetamodel = (Model) eclipseRepresentation.load(URI.createURI(UMLResource.UML_METAMODEL_URI));

        createMetaClass(umlMetamodel, UMLMetaClass.CLASS, UMLPackage.Literals.CLASS.getName());
        createMetaClass(umlMetamodel, UMLMetaClass.PROPERTY, UMLPackage.Literals.PROPERTY.getName());
    }

    private void createMetaClass(Model umlMetamodel, UMLMetaClass metaClass, String name) {
        org.eclipse.uml2.uml.Class classMetaClass = (org.eclipse.uml2.uml.Class) umlMetamodel.getOwnedType(name);
        this.profile.createMetaclassReference(classMetaClass);
        this.metaClassMap.put(metaClass, classMetaClass);
    }

    public void setMetaClass(Stereotype stereotype, UMLMetaClass metaClass) {
        removeAllExtensions(stereotype);
        stereotype.createExtension(metaClassMap.get(metaClass), false);
    }

    private void removeAllExtensions(Stereotype stereotype) {
        for (Extension extension : stereotype.getProfile().getOwnedExtensions(false)) {
            if (extension.getEndTypes().contains(stereotype)) {
                for (Property property : stereotype.getAttributes()) {
                    Association assoc = property.getAssociation();
                    if (assoc != null && assoc == extension) {
                        // additional cleanup needed, because
                        // this would not be removed by ext.destroy():
                        property.destroy();
                        break;
                    }
                }
                // remove base class by destroying the extension
                extension.destroy();
                break;
            }
        }
    }

    public UMLMetaClass metaClass(Stereotype stereotype) {
        for (Extension extension : profile.getOwnedExtensions(false)) {
            if (extension.getEndTypes().contains(stereotype)) {
                for (Map.Entry<UMLMetaClass, org.eclipse.uml2.uml.Class> mapEntry : metaClassMap.entrySet()) {
                    if (extension.getMetaclass().getName().equals(mapEntry.getValue().getName())) {
                        return mapEntry.getKey();
                    }
                }
            }
        }

        return UMLMetaClass.UNKNOWN;
    }
}
