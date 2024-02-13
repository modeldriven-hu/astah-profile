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

        createMetaClass(umlMetamodel, UMLMetaClass.Class, UMLPackage.Literals.CLASS.getName());
        createMetaClass(umlMetamodel, UMLMetaClass.Property, UMLPackage.Literals.PROPERTY.getName());
    }

    private void createMetaClass(Model umlMetamodel, UMLMetaClass metaClass, String name) {
        org.eclipse.uml2.uml.Class classMetaClass = (org.eclipse.uml2.uml.Class) umlMetamodel.getOwnedType(name);
        this.profile.createMetaclassReference(classMetaClass);
        this.metaClassMap.put(metaClass, classMetaClass);
    }

    public void applyMetaClass(Stereotype stereotype, UMLMetaClass metaClass) {
        // FIXME you cannot add extensions two times to a stereotype!
        // destroyExtension
        stereotype.createExtension(metaClassMap.get(metaClass), false);
    }

    private void destroyExtension(Stereotype stereotype) {
        org.eclipse.uml2.uml.Class metaclass = null;
        Profile profile = stereotype.getProfile();
        if (metaclass != null && profile != null) {
            for (Extension ext : profile.getOwnedExtensions(false)) {
                if (ext.getMetaclass() == metaclass && ext.getEndTypes().contains(stereotype)) {
                    for (Property p : stereotype.getAttributes()) {
                        Association assoc = p.getAssociation();
                        if (assoc != null && assoc == ext) {
                            // additional cleanup needed, because
                            // this would not be removed by ext.destroy():
                            p.destroy();
                            break;
                        }
                    }
                    // remove base class by destroying the extension
                    ext.destroy();
                    break;
                }
            }
        }
    }

    public UMLMetaClass metaClass(Stereotype stereotype) {
        // FIXME does not work!
        for (Extension extension : stereotype.getExtensions()) {
            for (Map.Entry<UMLMetaClass, org.eclipse.uml2.uml.Class> mapEntry : metaClassMap.entrySet()) {
                if (extension.getMetaclass().getName().equals(mapEntry.getValue().getName())) {
                    return mapEntry.getKey();
                }
            }
        }
        return UMLMetaClass.Unknown;
    }
}
