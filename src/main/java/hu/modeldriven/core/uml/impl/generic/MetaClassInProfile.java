package hu.modeldriven.core.uml.impl.generic;

import hu.modeldriven.core.uml.UMLMetaClass;
import hu.modeldriven.core.uml.impl.eclipse.EclipseRepresentation;
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
        this.metaClassMap.put(UMLMetaClass.CLASS, importMetaClass(profile, umlMetamodel, UMLPackage.Literals.CLASS.getName()));
        this.metaClassMap.put(UMLMetaClass.PROPERTY, importMetaClass(profile, umlMetamodel, UMLPackage.Literals.PROPERTY.getName()));
    }

    private org.eclipse.uml2.uml.Class importMetaClass(Profile profile, Model umlMetamodel, String name) {

        for (ElementImport elementImport : profile.getMetaclassReferences()) {
            if (elementImport.getName().equals(name) && elementImport.getImportedElement() instanceof org.eclipse.uml2.uml.Class) {
                return (org.eclipse.uml2.uml.Class) elementImport.getImportedElement();
            }
        }

        org.eclipse.uml2.uml.Class classMetaClass = (org.eclipse.uml2.uml.Class) umlMetamodel.getOwnedType(name);
        this.profile.createMetaclassReference(classMetaClass);
        return classMetaClass;
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

    public org.eclipse.uml2.uml.Class extensionType(UMLMetaClass metaClass) {
        return metaClassMap.get(metaClass);
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
